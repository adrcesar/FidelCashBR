package br.com.acf.fidelcash.controller.dto;


import br.com.acf.fidelcash.modelo.SituacaoTipoCliente;
import br.com.acf.fidelcash.modelo.TipoCliente;

public class TipoClienteImplantacaoDto {
	private Integer id;
	private String descricao;
    private float bonus;
    private SituacaoTipoCliente situacao;
    
	public TipoClienteImplantacaoDto(TipoCliente tipoCliente) {
		super();
		this.id = tipoCliente.getId();
		this.descricao = tipoCliente.getDescricao();
		this.bonus = tipoCliente.getBonus();
		this.situacao = tipoCliente.getSituacao();
	}
	
	public Integer getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public float getBonus() {
		return bonus;
	}
	public SituacaoTipoCliente getSituacao() {
		return situacao;
	}
    
    
}
