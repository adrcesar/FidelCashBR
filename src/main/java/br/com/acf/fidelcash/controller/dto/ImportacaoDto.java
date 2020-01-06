package br.com.acf.fidelcash.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportacaoDto {
	private String erro;
	private String pastaOrigem;
	private List<String> pastasDestino;
	

	public ImportacaoDto() {
		
	}
	
	public ImportacaoDto(String pastaOrigem, List<String> arquivosProcessados) {
		this.pastaOrigem = pastaOrigem;
		
		this.pastasDestino = new ArrayList<String>();
		for(int i = 0; i < arquivosProcessados.size(); i++) {
			this.pastasDestino.add(arquivosProcessados.get(i));
		}
	}

	public List<String> getPastasDestino() {
		return this.pastasDestino;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getPastaOrigem() {
		return pastaOrigem;
	}
	
	
	
	

	
	
}
