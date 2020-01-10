package br.com.acf.fidelcash.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.ProdutoServiceException;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.repository.ProdutoRepository;



@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto setProduto(Produto produto, Empresa empresa) throws ProdutoServiceException {
		try {
			Produto prod = produto;
			prod.setEmpresa(empresa);
			Optional<Produto> produtoFind = produtoRepository.findByEmpresaAndCodigoProduto(empresa, prod.getCodigoProduto());
			if (produtoFind.isEmpty()) {
				produtoRepository.save(prod);
				produtoFind = produtoRepository.findByEmpresaAndCodigoProduto(empresa, prod.getCodigoProduto());
			}
			return produtoFind.get();
		} catch (Exception e) {
			throw new ProdutoServiceException("Produto invalido", "Produto invalido");
		}

	}
	
	public List<Produto> findByEmpresa(Empresa empresa) {
		return produtoRepository.findByEmpresa(empresa);
	}
}
