package br.com.acf.fidelcash.controller.dto;

public class TokenDto {

	private String token;
	private String tipo;
	private String perfil;
	private String erro;

	public TokenDto(String token, String tipo, String perfil) {
		this.token = token;
		this.tipo = tipo;
		this.perfil = perfil;
	}
	
	public TokenDto(String erro) {
		this.erro = erro;
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

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
	
	
	
	

}
