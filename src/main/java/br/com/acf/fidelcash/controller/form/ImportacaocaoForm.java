package br.com.acf.fidelcash.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

//import org.hibernate.validator.constraints.br.CNPJ;

public class ImportacaocaoForm {
	
	@NotNull @NotEmpty
	private MultipartFile[] xml;

	public MultipartFile[] getXml() {
		return xml;
	}

	public void setXml(MultipartFile[] xml) {
		this.xml = xml;
	}

		
}


