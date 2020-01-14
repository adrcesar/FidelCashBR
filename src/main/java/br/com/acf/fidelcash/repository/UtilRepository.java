package br.com.acf.fidelcash.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Util;

public interface UtilRepository extends JpaRepository<Util, Integer>{

	Optional<Util> findByEmpresaAndUtilidade(Empresa empresa, String utilidade);
	
	List<Util> findByUtilidade(String utilidade);

	List<Util> findByEmpresaIsNotNull();
	
	
	

}
