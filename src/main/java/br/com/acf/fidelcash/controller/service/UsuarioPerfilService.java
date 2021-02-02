package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Usuario;
import br.com.acf.fidelcash.modelo.UsuarioPerfil;
import br.com.acf.fidelcash.repository.UsuarioPerfilRepository;

@Service
public class UsuarioPerfilService {
	
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	public void save(UsuarioPerfil usuarioPerfil) {
		usuarioPerfilRepository.save(usuarioPerfil);
		
	}

	public String findPerfilByUsuarioTipo(Usuario usuario, String tipo) {
		List<UsuarioPerfil> perfil = new ArrayList<UsuarioPerfil>();
		perfil = usuarioPerfilRepository.findPerfilByUsuarioTipo(usuario, tipo);
		if(!perfil.isEmpty()) {
			return perfil.get(0).getPerfil().getNome();
		}
		return null;
	}

}
