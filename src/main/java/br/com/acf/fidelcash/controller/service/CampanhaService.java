package br.com.acf.fidelcash.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.repository.CampanhaRepository;

@Service
public class CampanhaService {
	
	@Autowired
	private CampanhaRepository campanhaRepository;

	public void save(Campanha campanha) {
		campanhaRepository.save(campanha);
		
	}

	public Optional<Campanha> findById(Integer id) {
		return campanhaRepository.findById(id);
	}

	public void deleteAll() {
		campanhaRepository.deleteAll();
		
	}

	public List<Campanha> findAllByCampanhaPai(Campanha campanhaPai) {
		return campanhaRepository.findAllByCampanhaPaiOrderById(campanhaPai);
	}

	public List<Campanha> findAllByCampanhaPaiNotNull() {
		return campanhaRepository.findAllByCampanhaPaiNotNullOrderByEmpresa();
		 
	}

	

	
	
	
	

}
