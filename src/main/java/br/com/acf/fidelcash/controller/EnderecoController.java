package br.com.acf.fidelcash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acf.fidelcash.controller.dto.EnderecoDTO;
import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.repository.EnderecoRepository;



@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@GetMapping
	public Page<EnderecoDTO> topicos(@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		Page<Endereco> enderecos = enderecoRepository.findAll(paginacao);
		return EnderecoDTO.converter(enderecos);
	}

}
