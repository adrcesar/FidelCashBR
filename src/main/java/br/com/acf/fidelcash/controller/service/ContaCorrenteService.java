package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.ContaCorrenteServiceException;
import br.com.acf.fidelcash.controller.service.exception.TipoClienteLogServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.repository.ContaCorrenteRepository;

@Service
public class ContaCorrenteService {
	@Autowired
	private ContaCorrenteRepository ccRepository;
	
	@Autowired
	private TipoClienteLogService tipoClienteLogService;

	public void setContaCorrente(List<CupomFiscalItem> itens) throws ContaCorrenteServiceException {
		for (int i = 0; i < itens.size(); i++) {
			CupomFiscal cf = itens.get(i).getCupomFiscal();
			Empresa empresa = itens.get(i).getCupomFiscal().getCliente().getTipoCliente().getEmpresa();
			Cliente cliente = itens.get(i).getCupomFiscal().getCliente();
			List<ContaCorrente> lancamentosPosteriores = lancamentosPosterioresAoAtual(empresa, cliente, cf);
			float saldo = 0;
			if (lancamentosPosteriores.isEmpty()) {
				saldo = getSaldoEmpresaCliente(empresa, cliente);
				insertCC(itens.get(i), saldo);
			}
			System.out.println(saldo);
		}
	}
	
	private List<ContaCorrente> lancamentosPosterioresAoAtual(Empresa empresa, Cliente cliente, CupomFiscal cf) {
		List<ContaCorrente> itens = ccRepository.findByEmpresaAndClienteAndDataCompraSuperiorQueAtual(empresa, cliente, cf.getDataCompra());
		return itens;
	}
	
	private float getSaldoEmpresaCliente(Empresa empresa, Cliente cliente) {
		List<ContaCorrente> cc = new ArrayList<ContaCorrente>();
		cc = ccRepository.findFirstByEmpresaAndClienteOrderByIdDesc(empresa, cliente);
		float saldo = 0;
		if (!cc.isEmpty()) {
			saldo = cc.get(0).getSaldo();
		}
		return saldo;
	}
	
	private void insertCC(CupomFiscalItem cfItem, float saldoAnterior) throws ContaCorrenteServiceException {
		try {
			ContaCorrente cc = new ContaCorrente();
			cc.setCupomFiscalItem(cfItem);
			TipoClienteLog log = tipoClienteLogService.bonusDoPeriodo(cfItem);
			float bonusPercentual = log.getBonus();
			if (Float.compare(cfItem.getValorDesconto(), 0) == 0) {
				cc = setContaCorrenteBonus(cfItem, saldoAnterior, cc, bonusPercentual);
			} else {
				setContaCorrenteResgateCashBack(cfItem, saldoAnterior, cc, bonusPercentual);
			}
			ccRepository.save(cc);
		} catch (TipoClienteLogServiceException e) {
			throw new ContaCorrenteServiceException(e.getMensagem(), e.getMensagem());
		}
	}

	private float valorDoBonus(float valor, float percentual) {
		return ((valor * percentual) / 100);
	}
	
	private ContaCorrente setContaCorrenteBonus(CupomFiscalItem cfItem, float saldoAnterior, ContaCorrente cc,
			float bonusPercentual) {
		float credito;
		cc.setDebito(0);
		credito = valorDoBonus(cfItem.getValorItem(), bonusPercentual);
		cc.setCredito(credito);
		cc.setSaldo(saldoAnterior + credito);
		return cc;
	}
	
	private ContaCorrente setContaCorrenteResgateCashBack(CupomFiscalItem cfItem, float saldoAnterior, ContaCorrente cc,
			float bonusPercentual) {
		float credito;
		float valorItemMenosCreditoUtilizado = cfItem.getValorItem() - cfItem.getValorDesconto();
		if (valorItemMenosCreditoUtilizado <= 0) {
			credito = 0;
			cc.setCredito(credito);
		} else {
			credito = valorDoBonus(valorItemMenosCreditoUtilizado, bonusPercentual);
			cc.setCredito(credito);
		}
		cc.setDebito(cfItem.getValorDesconto());
		cc.setSaldo(saldoAnterior + credito - cfItem.getValorDesconto());
		return cc;
	}
	

	public float getSaldoCliente(Cliente cliente) {
		List<ContaCorrente> cc = new ArrayList<ContaCorrente>();
		cc = ccRepository.findFirstByClienteOrderByIdDesc(cliente);
		float saldo = 0;
		if (!cc.isEmpty()) {
			saldo = cc.get(0).getSaldo();
		}
		return saldo;
	}

	public void deleteAll() {
		ccRepository.deleteAll();
	}

}
