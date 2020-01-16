package br.com.acf.fidelcash.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer>{

	Optional<ContaCorrente> findByCliente(Cliente cliente);

	

}
