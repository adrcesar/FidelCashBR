package br.com.acf.fidelcash.controller.dto;


import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.SituacaoProduto;

public class ProdutoImplantacaoDto {
	private Integer id;
    
	private String codigoProduto;
    
    private String descricao;
    
    private String unidadeComercial;
    
    private float valor;
    
    private SituacaoProduto situacao;

	public ProdutoImplantacaoDto(Produto produto) {
		this.id = produto.getId();
		this.codigoProduto = produto.getCodigoProduto();
		this.descricao = produto.getDescricao();
		this.unidadeComercial = produto.getUnidadeComercial();
		this.valor = produto.getValor();
		this.situacao = produto.getSituacao();
	}

	public Integer getId() {
		return id;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUnidadeComercial() {
		return unidadeComercial;
	}

	public float getValor() {
		return valor;
	}

	public SituacaoProduto getSituacao() {
		return situacao;
	}
	
	
	
	
    
    
}
