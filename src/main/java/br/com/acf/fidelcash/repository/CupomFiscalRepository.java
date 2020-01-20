package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;

public interface CupomFiscalRepository extends JpaRepository<CupomFiscal, BigInteger>{

	Optional<CupomFiscal> findByCodigoCupomAndCliente(int codigoCupom, Cliente cliente);

	List<CupomFiscal> findByDataCompraBetween(LocalDateTime dataInicio, LocalDateTime dataFinal);

	@Query("SELECT c FROM CupomFiscal c WHERE c.dataCompra BETWEEN :dataInicio AND :dataFinal AND c.cliente NOT IN :clientes")
	List<CupomFiscal> findByDataCompraBetweenNotIn(LocalDateTime dataInicio, LocalDateTime dataFinal, List<Cliente> clientes);

	
	

}
