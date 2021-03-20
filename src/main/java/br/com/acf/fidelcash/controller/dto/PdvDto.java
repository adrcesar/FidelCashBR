package br.com.acf.fidelcash.controller.dto;

import br.com.acf.fidelcash.modelo.Pdv;
import br.com.acf.fidelcash.modelo.SituacaoPdv;

public class PdvDto {
	private Integer id;
	
	private String macAddress;
	
	private SituacaoPdv situacao;
	
	private String Empresa;
	
	private String mensagemErro;
	
	
	public PdvDto(Pdv pdv) {
		this.id = pdv.getId();
		this.macAddress = pdv.getMacAddress();
		this.situacao = pdv.getSituacao();
		this.Empresa = pdv.getEmpresa().getNomeFantasia();
	}

	public PdvDto(String erro) {
		this.mensagemErro = erro;
	}

	public Integer getId() {
		return id;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public SituacaoPdv getSituacao() {
		return situacao;
	}

	public String getEmpresa() {
		return Empresa;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	
	
	
	
}
