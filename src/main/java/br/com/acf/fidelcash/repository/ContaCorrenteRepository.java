package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.Empresa;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, BigInteger>{

	@Query("SELECT c FROM ContaCorrente c WHERE c.cupomFiscalItem.cupomFiscal.cliente.tipoCliente.empresa = :empresa AND c.cupomFiscalItem.cupomFiscal.cliente = :cliente ORDER BY c.id DESC")
	List<ContaCorrente> findFirstByEmpresaAndClienteOrderByIdDesc(Empresa empresa, Cliente cliente);

	@Query("SELECT c FROM ContaCorrente c WHERE c.cupomFiscalItem.cupomFiscal.cliente.tipoCliente.empresa = :empresa AND c.cupomFiscalItem.cupomFiscal.cliente = :cliente AND c.cupomFiscalItem.cupomFiscal.dataCompra > :dataCompra ORDER BY c.id DESC")
	List<ContaCorrente> findByEmpresaAndClienteAndDataCompraSuperiorQueAtual(Empresa empresa, Cliente cliente,
			LocalDateTime dataCompra);

	@Query("SELECT c FROM ContaCorrente c WHERE c.cupomFiscalItem.cupomFiscal.cliente = :cliente ORDER BY c.id DESC")
	List<ContaCorrente> findFirstByClienteOrderByIdDesc(Cliente cliente);

}
