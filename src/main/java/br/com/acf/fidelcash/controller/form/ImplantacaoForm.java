package br.com.acf.fidelcash.controller.form;

import java.math.BigInteger;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

//import org.hibernate.validator.constraints.br.CNPJ;

public class ImplantacaoForm {
	
	
	
	
	public ImplantacaoForm() {
		System.out.println("será que isso vai rodar");
	}

	@NotNull @NotEmpty //@CNPJ
	private BigInteger cnpj;
	
	@NotNull @NotEmpty @Email(message = "Email inválido")
	private String email;
	
	private MultipartFile[] xml;

	public BigInteger getCnpj() {
		return cnpj;
	}

	public void setCnpj(BigInteger cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MultipartFile[] getXml() {
		return xml;
	}

	public void setXml(MultipartFile[] xml) {
		this.xml = xml;
	}

	
	
	

	
	
	
}


