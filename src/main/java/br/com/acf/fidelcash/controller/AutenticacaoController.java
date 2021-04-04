package br.com.acf.fidelcash.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acf.fidelcash.config.Security.TokenService;
import br.com.acf.fidelcash.controller.dto.TokenDto;
import br.com.acf.fidelcash.controller.form.LoginForm;
import br.com.acf.fidelcash.controller.service.UsuarioService;



@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			String perfil = usuarioService.findPerfilByUsuario(form.getUsuario());
			
			
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer", perfil));
		} catch (Exception e) {
			String erro = null;
			if (e.getMessage().equals("Bad credentials")) {
				erro = "Usuário inválido";
			} else {
				erro = e.getMessage();
			}
			return ResponseEntity.badRequest().body(new TokenDto(erro));
		}
	}

}
