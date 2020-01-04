package br.com.acf.fidelcash.controller.dto;

import java.math.BigInteger;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;

public class EmpresaDto {
	private Integer id;
	private BigInteger cnpj;
	private String nomeRazao;
	private String nomeFantasia;
	private SituacaoEmpresa situacaoEmpresa;
	
	public EmpresaDto(Empresa empresa) {
		this.id = empresa.getId();
		this.cnpj = empresa.getCnpj();
		this.nomeRazao = empresa.getNomeRazao();
		this.nomeFantasia = empresa.getNomeFantasia();
		this.situacaoEmpresa = empresa.getSituacao();
	}
	
	

	public Integer getId() {
		return id;
	}

	public BigInteger getCnpj() {
		return cnpj;
	}

	public String getNomeRazao() {
		return nomeRazao;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public SituacaoEmpresa getSituacaoEmpresa() {
		return situacaoEmpresa;
	}
	
	
	
	
}
