package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.CupomFiscalItemServiceException;
import br.com.acf.fidelcash.controller.service.exception.TipoClienteLogServiceException;
import br.com.acf.fidelcash.modelo.Cliente;

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

	public List<CupomFiscalItem> setCupomFiscalItem(List<Produto> produtos, List<CupomFiscalItem> itens, CupomFiscal cupomFiscal) throws CupomFiscalItemServiceException {
		try {
			List<CupomFiscalItem> cfItens =  new ArrayList<CupomFiscalItem>();
			for (int i = 0; i < produtos.size(); i++) {
				Produto prod = produtoService.setProduto(produtos.get(i), cupomFiscal.getCliente().getTipoCliente().getEmpresa());
				CupomFiscalItem cupomFiscalItem;
				cupomFiscalItem = itens.get(i);
				cupomFiscalItem.setCupomFiscal(cupomFiscal);
				cupomFiscalItem.setProduto(prod);
				// inicio mudanca
				float saldo = 0;
				saldo = getSaldoEmpresaCliente(prod.getEmpresa(), cupomFiscal.getCliente());
				cupomFiscalItem = setBonusOuCashBack(cupomFiscalItem, saldo);
				
				
				
				// fim mudanca
				cfItemRepository.save(cupomFiscalItem);
				cfItens.add(cupomFiscalItem);
			}
			return cfItens;
		} catch (Exception  e) {
			throw new CupomFiscalItemServiceException("Erro Item do Cupom Fiscal", "Erro Item do Cupom Fiscal");
		}
	}
	
	private CupomFiscalItem setBonusOuCashBack(CupomFiscalItem cfItem, float saldoAnterior) throws TipoClienteLogServiceException {
		TipoClienteLog log = tipoClienteLogService.bonusDoPeriodo(cfItem);
		float bonusPercentual = log.getBonus();
		if (Float.compare(cfItem.getValorDesconto(), 0) == 0) {
			cfItem = setContaCorrenteBonus(cfItem, saldoAnterior, bonusPercentual);
		} else {
			cfItem = setContaCorrenteResgateCashBack(cfItem, saldoAnterior, bonusPercentual);
		}
		return cfItem;
	}
	
	private CupomFiscalItem setContaCorrenteResgateCashBack(CupomFiscalItem cfItem, float saldoAnterior, float bonusPercentual) {
		float credito;
		float valorItemMenosCreditoUtilizado = cfItem.getValorItem() - cfItem.getValorDesconto();
		if (valorItemMenosCreditoUtilizado <= 0) {
			credito = 0;
			cfItem.setCredito(credito);
		} else {
			credito = valorDoBonus(valorItemMenosCreditoUtilizado, bonusPercentual);
			cfItem.setCredito(credito);
		}
		cfItem.setSaldo(saldoAnterior + credito - cfItem.getValorDesconto());
		return cfItem;
	}
	
	private CupomFiscalItem setContaCorrenteBonus(CupomFiscalItem cfItem, float saldoAnterior, float bonusPercentual) {
		float credito;
		credito = valorDoBonus(cfItem.getValorItem(), bonusPercentual);
		cfItem.setCredito(credito);
		cfItem.setSaldo(saldoAnterior + credito);
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
