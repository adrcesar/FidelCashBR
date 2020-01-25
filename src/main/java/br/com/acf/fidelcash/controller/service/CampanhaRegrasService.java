package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.repository.CampanhaRegrasRepository;

@Service
public abstract class CampanhaRegrasService {
	
	@Autowired
	private CampanhaRegrasRepository regrasRepository;
	
	@Autowired
	private CampanhaService campanhaService;
	
	final void criarCampanhaRegra(Campanha campanha) throws PeriodoDeCompraServiceException {
		setCampanha(campanha);
		
		List<Cliente> clientes = selecionarClientes();
		
		List<Produto> produtos = selecionarProdutos();
		
		setCampanhaRegra(campanha, clientes, produtos);
	}
	
	protected void setCampanha(Campanha campanha) {
		campanhaService.save(campanha);
	}
	
	protected abstract List<Cliente> selecionarClientes() throws PeriodoDeCompraServiceException;
	
	protected abstract List<Produto> selecionarProdutos();
	
	protected abstract void setCampanhaRegra(Campanha campanha, List<Cliente> clientes, List<Produto> produtos);

	protected List<Cliente> getClientesDaCampanhaByCampanhaPai(Campanha campanhaPai) {
		List<Campanha> campanhasFilhas = campanhaService.findAllByCampanhaPai(campanhaPai);
		List<CampanhaRegras> regras = new ArrayList<CampanhaRegras>();
		for(Campanha campanha : campanhasFilhas) {
			regras.addAll(regrasRepository.findAllByCampanha(campanha));
		}
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		for(CampanhaRegras regra : regras) {
			clientes.add(regra.getCliente());
		}
		return clientes;
	}
	
	protected void save(CampanhaRegras regras) {
		regrasRepository.save(regras);
	}

	
}
