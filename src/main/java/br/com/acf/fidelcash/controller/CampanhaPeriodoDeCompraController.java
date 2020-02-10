package br.com.acf.fidelcash.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import br.com.acf.fidelcash.controller.dto.CampanhaDto;
import br.com.acf.fidelcash.controller.dto.CampanhaPeriodoCompraDto;
import br.com.acf.fidelcash.controller.form.CampanhaPeriodoCompraForm;
import br.com.acf.fidelcash.controller.service.CampanhaPeriodoDeCompraService;
import br.com.acf.fidelcash.controller.service.CampanhaService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.ProdutoService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.controller.service.exception.UsuarioServiceException;
import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaPeriodoDeCompra;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.Usuario;


@RestController
@RequestMapping("/campanhas/periodocompras")
public class CampanhaPeriodoDeCompraController {

	@Autowired
	private CampanhaPeriodoDeCompraService periodoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private CampanhaService campanhaService;

	@GetMapping
	public List<CampanhaPeriodoCompraDto> lista() {
		List<Campanha> campanhas = campanhaService.findAllByCampanhaPaiNotNull();
		List<CampanhaRegras> regras = periodoService.findAllByCampanha(campanhas);

		List<Produto> produtos = new ArrayList<Produto>();
		for (CampanhaRegras regra : regras) {
			if (produtos.isEmpty()) {
				produtos.addAll(produtoService.findByEmpresa(regra.getCampanha().getEmpresa()));
			} else {
				int ultimaPosicao = produtos.size() - 1;
				Empresa empresaProduto = produtos.get(ultimaPosicao).getEmpresa();
				Empresa empresaRegra = regra.getCampanha().getEmpresa();
				if (!empresaProduto.equals(empresaRegra)) {
					produtos.addAll(produtoService.findByEmpresa(regra.getCampanha().getEmpresa()));
				}
			}

		}
		return CampanhaPeriodoCompraDto.converter(regras, produtos);
	}

	@GetMapping("/{idCampanha}")
	public List<CampanhaPeriodoCompraDto> getByIdCampanha(@PathVariable Integer idCampanha) {
		Optional<Campanha> campanhaPai = campanhaService.findById(idCampanha);
		
		List<Campanha> campanhas = campanhaService.findAllByCampanhaPai(campanhaPai.get());
		
		List<CampanhaRegras> regras = new ArrayList<CampanhaRegras>();
		for (Campanha campanha : campanhas) {
			regras.addAll(periodoService.findAllByCampanha(campanha));
		}
		List<Produto> produtos = new ArrayList<Produto>();
		for (CampanhaRegras regra : regras) {
			if (produtos.isEmpty()) {
				produtos.addAll(produtoService.findByEmpresa(regra.getCampanha().getEmpresa()));
			} else {
				int ultimaPosicao = produtos.size() - 1;
				Empresa empresaProduto = produtos.get(ultimaPosicao).getEmpresa();
				Empresa empresaRegra = regra.getCampanha().getEmpresa();
				if (!empresaProduto.equals(empresaRegra)) {
					produtos.addAll(produtoService.findByEmpresa(regra.getCampanha().getEmpresa()));
				}
			}

		}
		return CampanhaPeriodoCompraDto.converter(regras, produtos);
	}

	@PostMapping
	@Transactional
	// @Valid - utiliza o bean validation da classe TopicoForm
	public ResponseEntity<CampanhaDto> cadastrar(@RequestBody @Valid CampanhaPeriodoCompraForm form,
			UriComponentsBuilder uriBuilder, @AuthenticationPrincipal Usuario logado) throws EmpresaServiceException, PeriodoDeCompraServiceException, UsuarioServiceException {
		CampanhaPeriodoDeCompra periodo = form.converter(empresaService);
		periodoService.SetPeriodoCampanha(periodo, logado);
		URI uri = uriBuilder.path("/campanha/periodocompra/{idCampanha}").buildAndExpand(periodo.getCampanhaPai().getId()).toUri();
		return ResponseEntity.created(uri).body(new CampanhaDto(periodo.getCampanhaPai()));
	}

}
