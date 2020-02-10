package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.controller.service.exception.UsuarioServiceException;
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

	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
		
	}

	public void verificaPerfil(Usuario logado, String string) throws UsuarioServiceException {
		String tipoPerfil = null;
		for(GrantedAuthority perfil :  logado.getAuthorities()) {
			if (perfil.getAuthority().equals("ADMINISTRADOR")){
				tipoPerfil = perfil.getAuthority();
				break;
			}
		}
		if(tipoPerfil == null) {
			throw new UsuarioServiceException("Usuário não tem permissão para cadastrar campanha.", "Usuário não tem permissão para cadastrar campanha.");
		}
		
	}
	
	

}
