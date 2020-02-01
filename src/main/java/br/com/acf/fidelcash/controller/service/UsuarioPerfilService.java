package br.com.acf.fidelcash.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.UsuarioPerfil;
import br.com.acf.fidelcash.repository.UsuarioPerfilRepository;

@Service
public class UsuarioPerfilService {
	
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	public void save(UsuarioPerfil usuarioPerfil) {
		usuarioPerfilRepository.save(usuarioPerfil);
		
	}

}
