package br.com.acf.fidelcash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
	
	@Query("SELECT e FROM Endereco e WHERE e.id = (SELECT max(x.id) FROM Endereco x)")
	Endereco findByMaxId();
	
	

}
