package br.com.acf.fidelcash.modelo;

import java.time.LocalDateTime;
import java.util.List;



public class CampanhaPeriodoDeCompra {
	private Campanha campanhaPai;
	private LocalDateTime dataFimPeriodo;
	private List<Integer> diasDosPeriodos;
	
	public CampanhaPeriodoDeCompra() {
		// TODO Auto-generated constructor stub
	}
	
	public CampanhaPeriodoDeCompra(Campanha campanha, LocalDateTime dataFinalPeriodo, List<Integer> diasDosPeriodos) {
		this.campanhaPai = campanha;
		this.dataFimPeriodo = dataFinalPeriodo;
		this.diasDosPeriodos = diasDosPeriodos;
	}
	public Campanha getCampanhaPai() {
		return campanhaPai;
	}
	public void setCampanhaPai(Campanha campanhaPai) {
		this.campanhaPai = campanhaPai;
	}
	public LocalDateTime getDataFimPeriodo() {
		return dataFimPeriodo;
	}
	public void setDataFimPeriodo(LocalDateTime dataFimPeriodo) {
		this.dataFimPeriodo = dataFimPeriodo;
	}
	public List<Integer> getDiasDosPeriodos() {
		return diasDosPeriodos;
	}
	public void setDiasDosPeriodos(List<Integer> diasDosPeriodos) {
		this.diasDosPeriodos = diasDosPeriodos;
	}	
	
	
	
}
