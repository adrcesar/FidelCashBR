package br.com.acf.fidelcash.controller.dto;

public class TokenDto {

	private String token;
	private String tipo;
	private String perfil;

	public TokenDto(String token, String tipo, String perfil) {
		this.token = token;
		this.tipo = tipo;
		this.perfil = perfil;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

	public String getPerfil() {
		return perfil;
	}
	
	
	
	

}
