package br.com.acf.fidelcash.modelo;

import java.time.LocalDateTime;
import java.util.List;



public class CampanhaPeriodoDeCompra {
	private Campanha campanhaPai;
	private LocalDateTime dataFimPeriodo;
	private List<Integer> diasDosPeriodos;
	private List<Float> bonusDoPeriodo;
	
	public CampanhaPeriodoDeCompra() {
		
	}
	
	public CampanhaPeriodoDeCompra(Campanha campanha, LocalDateTime dataFinalPeriodo, List<Integer> diasDosPeriodos, List<Float> bonus) {
		this.campanhaPai = campanha;
		this.dataFimPeriodo = dataFinalPeriodo;
		this.diasDosPeriodos = diasDosPeriodos;
		this.bonusDoPeriodo = bonus;
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

	public List<Float> getBonusDoPeriodo() {
		return bonusDoPeriodo;
	}

	public void setBonusDoPeriodo(List<Float> bonus) {
		this.bonusDoPeriodo = bonus;
	}	
	
	
	
	
}
