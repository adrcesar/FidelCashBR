package br.com.acf.fidelcash.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.repository.ProdutoRepository;



@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	
	public void save(Produto prod) {
		produtoRepository.save(prod);
	}
	
	public List<Produto> findByEmpresa(Empresa empresa) {
		return produtoRepository.findByEmpresa(empresa);
	}

	public void deleteAll() {
		produtoRepository.deleteAll();
		
	}

	public Optional<Produto> findByEmpresaAndCodigoProduto(Empresa empresa, String codigoProduto) {
		return produtoRepository.findByEmpresaAndCodigoProduto(empresa, codigoProduto);
	}
}
