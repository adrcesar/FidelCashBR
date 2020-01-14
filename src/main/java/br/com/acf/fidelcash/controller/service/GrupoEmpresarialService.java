package br.com.acf.fidelcash.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.repository.GrupoEmpresarialRepository;

@Service
public class GrupoEmpresarialService {
	
	@Autowired
	private GrupoEmpresarialRepository grupoRepository;

	public void deleteAll() {
		grupoRepository.deleteAll();
		
	}

}
