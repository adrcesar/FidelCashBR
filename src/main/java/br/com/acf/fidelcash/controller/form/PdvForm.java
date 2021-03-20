package br.com.acf.fidelcash.controller.form;

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
	private Integer idEmpresa;

	
	
	public Pdv converter(EmpresaService empresaService) throws EmpresaServiceException {
		Empresa empresa = empresaService.findById(this.idEmpresa);
		
		SituacaoPdv situacao = SituacaoPdv.ATIVO;
		
		Pdv pdv = new Pdv(this.macAddress, empresa, situacao);
		
		return pdv;
	}



	public String getMacAddress() {
		return macAddress;
	}



	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}



	public Integer getIdEmpresa() {
		return idEmpresa;
	}



	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	

	


	
	
	
	
	
	
}
