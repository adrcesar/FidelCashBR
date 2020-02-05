package br.com.acf.fidelcash.controller.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.CupomFiscalItemServiceException;
import br.com.acf.fidelcash.controller.service.exception.TipoClienteLogServiceException;
import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.modelo.TipoSelecaoCliente;
import br.com.acf.fidelcash.modelo.TipoSelecaoProduto;
import br.com.acf.fidelcash.repository.CupomFiscalItemRepository;

@Service
public class CupomFiscalItemService {

	@Autowired
	private CupomFiscalItemRepository cfItemRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private TipoClienteLogService tipoClienteLogService;
	
	@Autowired
	private ContaCorrenteService ccService;
	
	@Autowired
	private CampanhaService campanhaService;
	
	@Autowired
	private CampanhaRegrasService campanhaRegrasService;

	public void setCupomFiscalItem(List<Produto> produtos, List<CupomFiscalItem> itens, CupomFiscal cupomFiscal, ContaCorrente cc) throws CupomFiscalItemServiceException {
		try {
			for (int i = 0; i < produtos.size(); i++) {
				Empresa empresa = cupomFiscal.getCliente().getTipoCliente().getEmpresa();
				Optional<Produto> prod = produtoService.findByEmpresaAndCodigoProduto(empresa, produtos.get(i).getCodigoProduto());
				if(prod.isEmpty()) {
					prod = Optional.of(produtos.get(i));
					prod.get().setEmpresa(empresa);
					produtoService.save(prod.get());
				}
				CupomFiscalItem cupomFiscalItem = itens.get(i);
				cupomFiscalItem.setCupomFiscal(cupomFiscal);
				cupomFiscalItem.setProduto(prod.get());
				cupomFiscalItem.setContaCorrente(cc);
				
				List<CupomFiscalItem> lancamentosFuturos = new ArrayList<CupomFiscalItem>();
				lancamentosFuturos = cfItemRepository.findByEmpresaClienteDataCompraSuperiorAAtual(prod.get().getEmpresa(), cupomFiscal.getCliente(), cupomFiscal.getDataCompra());
				float saldo = 0;
				if(lancamentosFuturos.isEmpty()) {
					saldo = getSaldoEmpresaCliente(prod.get().getEmpresa(), cupomFiscal.getCliente());
					cupomFiscalItem = setBonusOuCashBack(cupomFiscalItem, saldo);
					cfItemRepository.save(cupomFiscalItem);
					setSaldoContaCorrente(cc, cupomFiscalItem.getSaldo());
				} else {
					for(CupomFiscalItem itemFuturoExcluido : lancamentosFuturos) {
						cfItemRepository.delete(itemFuturoExcluido);
					}
					saldo = getSaldoEmpresaCliente(prod.get().getEmpresa(), cupomFiscal.getCliente());
					cupomFiscalItem = setBonusOuCashBack(cupomFiscalItem, saldo);
					cfItemRepository.save(cupomFiscalItem);
					setSaldoContaCorrente(cc, cupomFiscalItem.getSaldo());
					for(CupomFiscalItem itemFuturoReincluido : lancamentosFuturos) {
						saldo = getSaldoEmpresaCliente(prod.get().getEmpresa(), cupomFiscal.getCliente());
						itemFuturoReincluido = setBonusOuCashBack(itemFuturoReincluido, saldo);
						cfItemRepository.save(itemFuturoReincluido);
						setSaldoContaCorrente(cc, itemFuturoReincluido.getSaldo());
					}
				}
			}
			
		} catch (Exception  e) {
			throw new CupomFiscalItemServiceException("Erro Item do Cupom Fiscal", "Erro Item do Cupom Fiscal");
		}
	}

	private void setSaldoContaCorrente(ContaCorrente cc, float saldo) {
		cc.setSaldo(saldo);
		ccService.setContaCorrente(cc);
	}
	
	private CupomFiscalItem setBonusOuCashBack(CupomFiscalItem cfItem, float saldoAnterior) throws TipoClienteLogServiceException {
		if (Float.compare(cfItem.getValorDesconto(), 0) == 0) {
			cfItem = setContaCorrenteBonus(cfItem, saldoAnterior);
		} else {
			cfItem = setContaCorrenteResgateCashBack(cfItem, saldoAnterior);
		}
		return cfItem;
	}
	
	private CupomFiscalItem setContaCorrenteResgateCashBack(CupomFiscalItem cfItem, float saldoAnterior) throws TipoClienteLogServiceException {
		TipoClienteLog log = tipoClienteLogService.bonusDoPeriodo(cfItem);
		
		CampanhaRegras regra = new CampanhaRegras();
		regra = getCampanhaRegras(cfItem);
		
		float credito = 0;
		setCfItem(regra, credito, cfItem, saldoAnterior, log);
		cfItem.setSaldo(cfItem.getSaldo() - cfItem.getValorDesconto());
		
		return cfItem;
	}
	
	
	private CupomFiscalItem setContaCorrenteBonus(CupomFiscalItem cfItem, float saldoAnterior) throws TipoClienteLogServiceException {
		TipoClienteLog log = tipoClienteLogService.bonusDoPeriodo(cfItem);
		
		CampanhaRegras regra = new CampanhaRegras();
		regra = getCampanhaRegras(cfItem);
		
		float credito = 0;
		setCfItem(regra, credito, cfItem, saldoAnterior, log);
		return cfItem;
	}
	
	private void setCfItem(CampanhaRegras regra, float credito, CupomFiscalItem cfItem, float saldoAnterior, TipoClienteLog log) {
		if(!(regra.getId() == null)) {
			if(regra.getCampanha().getBonus() > log.getBonus()) {
				credito = valorDoBonus(cfItem.getValorItem(), regra.getCampanha().getBonus());
				setCfItemCampanha(cfItem, credito, saldoAnterior);
			} else {
				credito = valorDoBonus(cfItem.getValorItem(), log.getBonus());
				setCfItemLog(cfItem, credito, saldoAnterior, log);
			}
		} else {
			credito = valorDoBonus(cfItem.getValorItem(), log.getBonus());
			setCfItemLog(cfItem, credito, saldoAnterior, log);
		}
	}
	
	private void setCfItemCampanha(CupomFiscalItem cfItem, float credito, float saldoAnterior) {
		cfItem.setCredito(credito);
		cfItem.setSaldo(saldoAnterior + credito);
	}
	
	private void setCfItemLog(CupomFiscalItem cfItem, float credito, float saldoAnterior, TipoClienteLog log) {
		cfItem.setCredito(credito);
		cfItem.setSaldo(saldoAnterior + credito);
		cfItem.setTipoClienteLog(log);
	}
	
	private CampanhaRegras getCampanhaRegras(CupomFiscalItem cfItem) {
		Empresa empresa = cfItem.getCupomFiscal().getCliente().getTipoCliente().getEmpresa();
		LocalDateTime dataCupom = cfItem.getCupomFiscal().getDataCompra();
		CampanhaRegras regra = new CampanhaRegras();
		
		List<Campanha> campanhasAtivas = campanhaService.findAllByEmpresaPeriodoOrderByBonusDesc(empresa, dataCupom);
		if(!campanhasAtivas.isEmpty()) {
			for(Campanha campanha : campanhasAtivas) {
				Cliente cliente = cfItem.getCupomFiscal().getCliente();
				List<CampanhaRegras> regrasClientes = campanhaRegrasService.findAllByCampanhaCliente(campanha, cliente, TipoSelecaoCliente.TODOS);
				if(!regrasClientes.isEmpty()) {
					for(CampanhaRegras regraCliente : regrasClientes) {
						Produto produto = cfItem.getProduto();
						List<CampanhaRegras> regrasProdutos = campanhaRegrasService.findAllByIdProduto(regraCliente.getId(), produto, TipoSelecaoProduto.TODOS);
						if(!regrasProdutos.isEmpty()) {
							regra = regraCliente;
							break;
						}
					}
				}
			}
		}
		return regra;
	}
	
	private float valorDoBonus(float valor, float percentual) {
		return ((valor * percentual) / 100);
	}
	
	
	private float getSaldoEmpresaCliente(Empresa empresa, Cliente cliente) {
		List<CupomFiscalItem> cfItem = new ArrayList<CupomFiscalItem>();
		cfItem = cfItemRepository.findFirstByEmpresaAndClienteOrderByIdDesc(empresa, cliente);
		float saldo = 0;
		if (!cfItem.isEmpty()) {
			saldo = cfItem.get(0).getSaldo();
		}
		return saldo;
	}

	public void deleteAll() {
		cfItemRepository.deleteAll();
		
	}

	public float getSaldoCliente(Cliente cliente) {
		List<CupomFiscalItem> cfItem = new ArrayList<CupomFiscalItem>();
		cfItem = cfItemRepository.findFirstByClienteOrderByIdDesc(cliente);
		//cfItemRepository
		float saldo = 0;
		if (!cfItem.isEmpty()) {
			saldo = cfItem.get(0).getSaldo();
		}
		return saldo;
	}
	
	
	
	

}
