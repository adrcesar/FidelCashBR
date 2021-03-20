package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.Perfil;
import br.com.acf.fidelcash.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	public void save(Perfil perfil) {
		perfilRepository.save(perfil);
		
	}
	
	public Optional<Perfil> findByNome(String nome) {
		return perfilRepository.findByNome(nome);
	}
	
	

}
