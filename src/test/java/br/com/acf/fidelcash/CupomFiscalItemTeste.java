package br.com.acf.fidelcash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.acf.fidelcash.controller.dto.CupomFiscalItemExtratoDto;
import br.com.acf.fidelcash.controller.service.CupomFiscalItemService;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;


@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CupomFiscalItemTeste {
	
	@Autowired
	private CupomFiscalItemService cfItemService;
	
	@Test
	public void extratoClienteTeste() {
		List<CupomFiscalItem> cfItens = cfItemService.findByClienteOrderByCupomFiscalDataCompra(47);
		assertTrue(cfItens.size() > 0); 
		List<CupomFiscalItemExtratoDto> extrato = cfItemService.geraExtrato(cfItens);
		System.out.println(extrato.get(1).getSaldoLancamento());
		System.out.println(CupomFiscalItemExtratoDto.getSaldoGeral());
		assertEquals(-5.7, CupomFiscalItemExtratoDto.getSaldoGeral(), 0.0001);
	}
	
}
