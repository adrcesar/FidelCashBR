package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.repository.ContaCorrenteRepository;

@Service
public class ContaCorrenteService {
	@Autowired
	private ContaCorrenteRepository ccRepository;

	public ContaCorrente setContaCorrente(Cliente cliente) {
		ContaCorrente contaCorrente = new ContaCorrente();
		Optional<ContaCorrente> cc = ccRepository.findByCliente(cliente);
		if(cc.isEmpty()) {
			contaCorrente.setCliente(cliente);
			contaCorrente.setSaldo(0);
			ccRepository.save(contaCorrente);
		} else {
			contaCorrente = cc.get();
		}
		return contaCorrente;
		
	}

	public void setContaCorrente(ContaCorrente cc) {
		ccRepository.save(cc);
		
	}

	public Optional<ContaCorrente> findByCliente(Cliente cliente) {
		return ccRepository.findByCliente(cliente);
	}

	public void deleteAll() {
		ccRepository.deleteAll();
		
	}

}
