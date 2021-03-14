package br.com.acf.fidelcash.config.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.UsuarioService;
import br.com.acf.fidelcash.modelo.Usuario;


@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioService.findByUsuario(username);
		System.out.println("maldita senha dos infermos " + new BCryptPasswordEncoder().encode(usuario.get().getSenha()));
		
		if(usuario.isPresent()) {
			return usuario.get();
		}
		throw new UsernameNotFoundException("Dados inválidos!!!");
	}
	
}
