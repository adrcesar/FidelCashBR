package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.Usuario;
import br.com.acf.fidelcash.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Usuario> findByUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}
	
	

}
