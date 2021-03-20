package br.com.acf.fidelcash.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.acf.fidelcash.controller.dto.PdvDto;
import br.com.acf.fidelcash.controller.form.PdvForm;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.PdvService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.modelo.Pdv;
import br.com.acf.fidelcash.modelo.Usuario;

@RestController
@RequestMapping("/pdv")
public class PdvController {
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private PdvService pdvService;
	
	@PostMapping
	public ResponseEntity<PdvDto> cadastrarPdv(
			@RequestBody PdvForm form,
			UriComponentsBuilder uriBuilder, 
			@AuthenticationPrincipal Usuario logado) throws EmpresaServiceException {
		
		try {
			Pdv pdv = form.converter(empresaService);
			pdvService.save(pdv);
			//pdv = pdvService.findByMacAdress(pdv.getMacAddress());
			
			
			URI uri = uriBuilder.path("/pdv/{id}").buildAndExpand(pdv.getId()).toUri();
			return ResponseEntity.created(uri).body(new PdvDto(pdv));
		} catch (Exception e) {
			String erro;
			if (e.getMessage().indexOf("pdv_mac_address_uk") > 0) {
				erro = "PDV já cadastrado";
			} else {
				erro = e.getMessage();
			}
			PdvDto erroPdvDto = new PdvDto(erro);
			return ResponseEntity.badRequest().body(erroPdvDto);
		}
		
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PdvDto> getById(@PathVariable Integer id){
		Optional<Pdv> pdv = pdvService.findById(id);
		if (pdv.isPresent()) {
			return ResponseEntity.ok(new PdvDto(pdv.get()));
		}
		return ResponseEntity.notFound().build();
	}
}
