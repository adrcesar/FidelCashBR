package br.com.acf.fidelcash.controller.form;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//import org.hibernate.validator.constraints.br.CNPJ;

public class ImplantacaoForm {
	
	//@NotNull @NotEmpty //@CNPJ
	private BigInteger cnpj;

	public BigInteger getCnpj() {
		return cnpj;
	}

	public void setCnpj(BigInteger cnpj) {
		this.cnpj = cnpj;
	}

	
	
	
}


