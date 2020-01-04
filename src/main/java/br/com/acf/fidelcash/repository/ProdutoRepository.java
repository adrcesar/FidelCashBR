package br.com.acf.fidelcash.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	Optional<Produto> findByEmpresaAndCodigoProduto(Empresa empresa, String codigoProduto);

	List<Produto> findByEmpresa(Optional<Empresa> empresa);
	
	

}
