package br.com.acf.fidelcash.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acf.fidelcash.controller.dto.CupomFiscalItemExtratoDto;
import br.com.acf.fidelcash.controller.service.CupomFiscalItemService;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;


@RestController
@RequestMapping("/itemcupomfiscal")
public class CupomFiscalItemController {
	
	@Autowired
	private CupomFiscalItemService cfItemService;

	@GetMapping("/{idCliente}")
	public Page<CupomFiscalItemExtratoDto> geraExtrato(@PathVariable Integer idCliente,
													   @PageableDefault(page = 0, size = 10) Pageable pageable) {
		List<CupomFiscalItem> cfItens = cfItemService.findByClienteOrderByCupomFiscalDataCompra(idCliente);
		List<CupomFiscalItemExtratoDto> extrato = cfItemService.geraExtrato(cfItens);
		//
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > extrato.size() ? extrato.size() : (start + pageable.getPageSize());
		Page<CupomFiscalItemExtratoDto> page = new PageImpl<CupomFiscalItemExtratoDto>(extrato.subList(start, end), pageable, extrato.size());
		//
		return page;
	}

}
