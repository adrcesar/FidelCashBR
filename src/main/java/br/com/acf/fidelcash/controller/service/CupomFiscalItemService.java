package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.CupomFiscalItemServiceException;
import br.com.acf.fidelcash.controller.service.exception.TipoClienteLogServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
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

	public void setCupomFiscalItem(List<Produto> produtos, List<CupomFiscalItem> itens, CupomFiscal cupomFiscal, ContaCorrente cc) throws CupomFiscalItemServiceException {
		try {
			for (int i = 0; i < produtos.size(); i++) {
				Produto prod = produtoService.setProduto(produtos.get(i), cupomFiscal.getCliente().getTipoCliente().getEmpresa());
				CupomFiscalItem cupomFiscalItem = itens.get(i);
				cupomFiscalItem.setCupomFiscal(cupomFiscal);
				cupomFiscalItem.setProduto(prod);
				cupomFiscalItem.setContaCorrente(cc);
				
				List<CupomFiscalItem> lancamentosFuturos = new ArrayList<CupomFiscalItem>();
				lancamentosFuturos = cfItemRepository.findByEmpresaClienteDataCompraSuperiorAAtual(prod.getEmpresa(), cupomFiscal.getCliente(), cupomFiscal.getDataCompra());
				float saldo = 0;
				if(lancamentosFuturos.isEmpty()) {
					saldo = getSaldoEmpresaCliente(prod.getEmpresa(), cupomFiscal.getCliente());
					cupomFiscalItem = setBonusOuCashBack(cupomFiscalItem, saldo);
					cfItemRepository.save(cupomFiscalItem);
					setSaldoContaCorrente(cc, cupomFiscalItem.getSaldo());
				} else {
					for(CupomFiscalItem itemFuturoExcluido : lancamentosFuturos) {
						cfItemRepository.delete(itemFuturoExcluido);
					}
					saldo = getSaldoEmpresaCliente(prod.getEmpresa(), cupomFiscal.getCliente());
					cupomFiscalItem = setBonusOuCashBack(cupomFiscalItem, saldo);
					cfItemRepository.save(cupomFiscalItem);
					setSaldoContaCorrente(cc, cupomFiscalItem.getSaldo());
					for(CupomFiscalItem itemFuturoReincluido : lancamentosFuturos) {
						saldo = getSaldoEmpresaCliente(prod.getEmpresa(), cupomFiscal.getCliente());
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
		TipoClienteLog log = tipoClienteLogService.bonusDoPeriodo(cfItem);
		if (Float.compare(cfItem.getValorDesconto(), 0) == 0) {
			cfItem = setContaCorrenteBonus(cfItem, saldoAnterior, log);
		} else {
			cfItem = setContaCorrenteResgateCashBack(cfItem, saldoAnterior, log);
		}
		return cfItem;
	}
	
	private CupomFiscalItem setContaCorrenteResgateCashBack(CupomFiscalItem cfItem, float saldoAnterior, TipoClienteLog log) {
		float credito;
		float valorItemMenosCreditoUtilizado = cfItem.getValorItem() - cfItem.getValorDesconto();
		if (valorItemMenosCreditoUtilizado <= 0) {
			credito = 0;
			cfItem.setCredito(credito);
		} else {
			credito = valorDoBonus(valorItemMenosCreditoUtilizado, log.getBonus());
			cfItem.setCredito(credito);
		}
		cfItem.setSaldo(saldoAnterior + credito - cfItem.getValorDesconto());
		cfItem.setTipoClienteLog(log);
		return cfItem;
	}
	
	private CupomFiscalItem setContaCorrenteBonus(CupomFiscalItem cfItem, float saldoAnterior, TipoClienteLog log) {
		float credito;
		credito = valorDoBonus(cfItem.getValorItem(), log.getBonus());
		cfItem.setCredito(credito);
		cfItem.setSaldo(saldoAnterior + credito);
		cfItem.setTipoClienteLog(log);
		return cfItem;
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
