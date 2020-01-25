package br.com.acf.fidelcash.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acf.fidelcash.controller.dto.CampanhaPeriodoCompraDto;
import br.com.acf.fidelcash.controller.service.PeriodoDeCompraService;
import br.com.acf.fidelcash.controller.service.ProdutoService;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;


@RestController
@RequestMapping("/campanha/periodocompra")
public class CampanhaPeriodoCompra {

	@Autowired
	private PeriodoDeCompraService periodoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public List<CampanhaPeriodoCompraDto> lista(){
		List<CampanhaRegras> regras = periodoService.findAllByCampanhaPaiNotNull();
		
		List<Produto> produtos = new ArrayList<Produto>();
		for(CampanhaRegras regra : regras) {
			if(produtos.isEmpty()) {
				produtos.addAll(produtoService.findByEmpresa(regra.getCampanha().getEmpresa()));
			} else {
				int ultimaPosicao = produtos.size() - 1;
				Empresa empresaProduto = produtos.get(ultimaPosicao).getEmpresa();
				Empresa empresaRegra = regra.getCampanha().getEmpresa();
				if (!empresaProduto.equals(empresaRegra)) {
					produtos.addAll(produtoService.findByEmpresa(regra.getCampanha().getEmpresa()));
				}
			}
			
		}
		return CampanhaPeriodoCompraDto.converter(regras, produtos);
		
	}
	
	
}
