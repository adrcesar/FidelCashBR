package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.dto.CupomFiscalItemExtratoDto;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.repository.CupomFiscalItemRepository;

@Service
public class CupomFiscalItemService {

	@Autowired
	private CupomFiscalItemRepository cfItemRepository;
	
	public List<CupomFiscalItem> findByClienteOrderByCupomFiscalDataCompra(int idCliente) {
		return cfItemRepository.findByClienteOrderByCupomFiscalDataCompra(idCliente);
	}

	public List<CupomFiscalItemExtratoDto> geraExtrato(List<CupomFiscalItem> cfItens) {
		float valorItem = 0;
		float bonusPercentual = 0;
		float bonusValor = 0;
		float saldo = 0;
		List<CupomFiscalItemExtratoDto> extrato = new ArrayList<CupomFiscalItemExtratoDto>();
		for (int i = 0; i < cfItens.size(); i++) {
			CupomFiscalItemExtratoDto lancamento = new CupomFiscalItemExtratoDto(cfItens.get(i));
			if (Float.compare(cfItens.get(i).getValorDesconto(), 0) == 0)  {
				valorItem = cfItens.get(i).getValorItem();
				bonusPercentual = cfItens.get(i).getCupomFiscal().getCliente().getTipoCliente().getBonus();
				bonusValor = ((valorItem * bonusPercentual) / 100);
				saldo = saldo + bonusValor;
				lancamento.gerarCredito(bonusValor, saldo);
				extrato.add(lancamento);
			} else {
				saldo = saldo - cfItens.get(i).getValorDesconto();
				lancamento.gerarDebito(cfItens.get(i).getValorDesconto(), saldo);
				extrato.add(lancamento);
			}
			
		}
		return extrato;
	}

}
