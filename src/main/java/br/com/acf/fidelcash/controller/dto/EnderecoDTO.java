package br.com.acf.fidelcash.controller.dto;

import org.springframework.data.domain.Page;

import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.modelo.TipoEndereco;

public class EnderecoDTO {
	
	
    private Integer id;
	private TipoEndereco tipo;
    private String logradouro;
    private int numeroLogradouro;
    private String complementoLogradouro;
    private String bairro;
    private String municipio;
    private String cep;
    
	public EnderecoDTO(Endereco endereco) {
		this.id = endereco.getId();
		this.tipo = endereco.getTipo();
		this.logradouro = endereco.getLogradouro();
		this.numeroLogradouro = endereco.getNumeroLogradouro();
		this.complementoLogradouro = endereco.getComplementoLogradouro();
		this.bairro = endereco.getBairro();
		this.municipio = endereco.getMunicipio();
		this.cep = endereco.getCep();
	}

	public Integer getId() {
		return id;
	}

	public TipoEndereco getTipo() {
		return tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public int getNumeroLogradouro() {
		return numeroLogradouro;
	}

	public String getComplementoLogradouro() {
		return complementoLogradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public String getCep() {
		return cep;
	}

	public static Page<EnderecoDTO> converter(Page<Endereco> enderecos) {
		return enderecos.map(EnderecoDTO::new);
	}
	
	
    
    

}
