package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.TipoCliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Optional<Cliente> findByTipoClienteAndCpf(TipoCliente tipoCliente, BigInteger cpf);

	Optional<Cliente> findByCpf(BigInteger cpf);

	
	

}
