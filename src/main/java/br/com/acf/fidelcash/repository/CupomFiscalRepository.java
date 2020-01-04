package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;

public interface CupomFiscalRepository extends JpaRepository<CupomFiscal, BigInteger>{

	Optional<CupomFiscal> findByCodigoCupomAndCliente(int codigoCupom, Cliente cliente);
	

}
