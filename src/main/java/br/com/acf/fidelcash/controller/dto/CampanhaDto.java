package br.com.acf.fidelcash.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.acf.fidelcash.modelo.Campanha;

public class CampanhaDto {
	
	private Integer id;
	
	private String descricao;
	
	private LocalDateTime dataInicio;
	
	private LocalDateTime dataFim;
	
	private EmpresaDto empresa;
	
	private CampanhaDto campanhaPai;
	
	

	public CampanhaDto(Campanha campanha) {
		this.id = campanha.getId();
		this.descricao = campanha.getDescricao();
		this.dataInicio = campanha.getDataInicio();
		this.dataFim = campanha.getDataFim();
		this.empresa = new EmpresaDto(campanha.getEmpresa()); 
		if(campanha.getCampanhaPai() != null) {
			this.campanhaPai = new CampanhaDto(campanha.getCampanhaPai()); //CampanhaDto.converter(campanha.getCampanhaPai());
		}
		
	}
	
	public static List<CampanhaDto> converter(List<Campanha> campanhas) {
		return campanhas.stream().map(CampanhaDto::new).collect(Collectors.toList()); 
	}
	
		public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}
	
	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public CampanhaDto getCampanhaPai() {
		return campanhaPai;
	}

	

	
	
	

}
