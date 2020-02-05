package br.com.acf.fidelcash.controller.form;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaPeriodoDeCompra;
import br.com.acf.fidelcash.modelo.Empresa;

public class CampanhaPeriodoCompraForm {
	
	
	@NotNull @NotEmpty @Length(min = 10, max = 255)
	private String descricao;
		
	@NotNull
	private LocalDateTime dataInicio;
	
	@NotNull
	private LocalDateTime dataFim;
	
	@NotNull
	private Integer idEmpresa;
	
	@NotNull
	private LocalDateTime dataFinalPeriodo;
	
	@NotNull @NotEmpty
	private List<Integer> diasDosPeriodos;
	
	@NotNull @NotEmpty
	private List<Float> bonusDoPeriodo;

	public CampanhaPeriodoDeCompra converter(EmpresaService empresaService) throws EmpresaServiceException {
		Empresa empresa = empresaService.findById(this.idEmpresa);
		Campanha campanha = new Campanha(descricao, dataInicio, dataFim, empresa);
				
		return new CampanhaPeriodoDeCompra(campanha, this.dataFinalPeriodo, this.diasDosPeriodos, this.bonusDoPeriodo);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public LocalDateTime getDataFinalPeriodo() {
		return dataFinalPeriodo;
	}

	public void setDataFinalPeriodo(LocalDateTime dataFinalPeriodo) {
		this.dataFinalPeriodo = dataFinalPeriodo;
	}

	public List<Integer> getDiasDosPeriodos() {
		return diasDosPeriodos;
	}

	public void setDiasDosPeriodos(List<Integer> diasDosPeriodos) {
		this.diasDosPeriodos = diasDosPeriodos;
	}
	
	

}
