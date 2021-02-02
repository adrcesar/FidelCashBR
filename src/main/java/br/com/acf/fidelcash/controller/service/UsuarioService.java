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
	
	@Autowired
	private UsuarioPerfilService usuarioPerfilService;
	
	
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
			System.out.println("PERFIL: " + perfil.getAuthority());
			if (perfil.getAuthority().equals("ADMINISTRADOR")){
				tipoPerfil = perfil.getAuthority();
				break;
			}
		}
		
		if(tipoPerfil == null) {
			throw new UsuarioServiceException("Usuário não tem permissão para realizar esta operação.", "Usuário não tem permissão para realizar esta operação.");
		}
	}

	public String findPerfilByUsuario(String usuario) throws UsuarioServiceException  {
		Optional<Usuario> usuarioFind = findByUsuario(usuario);
		String perfil = null, perfinFind; 
		
		perfinFind = usuarioPerfilService.findPerfilByUsuarioTipo(usuarioFind.get(), "OPERADOR DE LOJA");
		if(perfinFind != null)
			perfil = perfinFind;
		
		perfinFind = usuarioPerfilService.findPerfilByUsuarioTipo(usuarioFind.get(), "GERENTE DE LOJA");
		if(perfinFind != null)
			perfil = perfinFind;
		
		perfinFind = usuarioPerfilService.findPerfilByUsuarioTipo(usuarioFind.get(), "REDE");
		if(perfinFind != null)
			perfil = perfinFind;
		
		perfinFind = usuarioPerfilService.findPerfilByUsuarioTipo(usuarioFind.get(), "ADMINISTRADOR");
		if(perfinFind != null)
			perfil = perfinFind;
		
		if(perfil == null) {
			throw new UsuarioServiceException("Usuário não possui nenhum perfil cadastrado.", "Usuário não possui nenhum perfil cadastrado.");
		}
		
		return perfil;
	}
	
	
	
	
	
	
	
	

}
