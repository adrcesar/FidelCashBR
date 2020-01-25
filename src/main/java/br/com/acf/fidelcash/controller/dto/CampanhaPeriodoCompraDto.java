package br.com.acf.fidelcash.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;

public class CampanhaPeriodoCompraDto {

	private Integer id;
	private CampanhaDto campanha;
	private ClienteDto cliente;
	private List<ProdutoDto> produtos;
	
	public CampanhaPeriodoCompraDto(CampanhaRegras regra, List<Produto> produtos) {
		this.id = regra.getId();
		this.campanha = CampanhaDto.converter(regra.getCampanha());
		this.cliente = ClienteDto.converter(regra.getCliente());
		//produtos
		List<Produto> produtosEmpresa  = new ArrayList<Produto>();
		for(Produto produto : produtos) {
			if(produto.getEmpresa().equals(regra.getCampanha().getEmpresa())) {
				produtosEmpresa.add(produto);
			}
		}
		this.produtos = ProdutoDto.converter(produtosEmpresa);
	}

	public static List<CampanhaPeriodoCompraDto> converter(List<CampanhaRegras> regras, List<Produto> produtos) {
		List<CampanhaPeriodoCompraDto> periodosDto = new ArrayList<CampanhaPeriodoCompraDto>();
		for(CampanhaRegras regra : regras) {
			periodosDto.add(new CampanhaPeriodoCompraDto(regra, produtos));
		}
		return periodosDto;
		//return regras.stream().map(CampanhaPeriodoCompraDto::new).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public CampanhaDto getCampanha() {
		return campanha;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public List<ProdutoDto> getProdutos() {
		return produtos;
	}
	
	
	
	

	

}
