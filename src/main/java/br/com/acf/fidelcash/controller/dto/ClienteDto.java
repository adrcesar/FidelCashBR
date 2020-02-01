package br.com.acf.fidelcash.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.SituacaoCliente;

public class ClienteDto {
	private Integer id;
    private String nome;
    private String email;
    private Integer dddCelular;
    private Integer numeroCelular;
    private SituacaoCliente situacao;
    
    public ClienteDto(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.dddCelular = cliente.getDddCelular();
		this.numeroCelular = cliente.getNumeroCelular();
		this.situacao = cliente.getSituacao();
	}

    public static List<ClienteDto> converter(List<Cliente> clientes) {
		return clientes.stream().map(ClienteDto::new).collect(Collectors.toList()); 
		
	}
    
    //public static ClienteDto converter(Cliente cliente) {
	//	return new ClienteDto(cliente); 
		
	//}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public Integer getDddCelular() {
		return dddCelular;
	}

	public Integer getNumeroCelular() {
		return numeroCelular;
	}

	public SituacaoCliente getSituacao() {
		return situacao;
	}
    
    

}
