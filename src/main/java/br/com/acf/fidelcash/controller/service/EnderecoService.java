package br.com.acf.fidelcash.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.repository.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;

	public void save(Endereco endereco) {
		enderecoRepository.save(endereco);
		
	}

	public Endereco findByMaxId() {
		return enderecoRepository.findByMaxId();
	}

	public void deleteAll() {
		enderecoRepository.deleteAll();
		
	}

}
