package br.com.acf.fidelcash.controller.form;

import java.math.BigInteger;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Pdv;
import br.com.acf.fidelcash.modelo.SituacaoPdv;

public class PdvForm {

	@NotNull @NotEmpty
	private String macAddress;
	
	@NotNull @NotEmpty
	private BigInteger cnpj;
	
	@NotNull @NotEmpty
	private String pastaDeUpload;

	
	
	public Pdv converter(EmpresaService empresaService) throws EmpresaServiceException {
		Optional<Empresa> empresa = empresaService.findByCnpj(this.cnpj);
		if (empresa.isEmpty()) {
			throw new EmpresaServiceException("CNPJ não encontrado", "CNPJ não encontrado");
		}
		
		SituacaoPdv situacao = SituacaoPdv.ATIVO;
		
		Pdv pdv = new Pdv(this.macAddress, empresa.get(), situacao, this.pastaDeUpload);
		
		return pdv;
	}



	public String getMacAddress() {
		return macAddress;
	}



	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}



	public BigInteger getCnpj() {
		return cnpj;
	}



	public void setCnpj(BigInteger cnpj) {
		this.cnpj = cnpj;
	}



	public String getPastaDeUpload() {
		return pastaDeUpload;
	}



	public void setPastaDeUpload(String pastaDeUpload) {
		this.pastaDeUpload = pastaDeUpload;
	}


}
