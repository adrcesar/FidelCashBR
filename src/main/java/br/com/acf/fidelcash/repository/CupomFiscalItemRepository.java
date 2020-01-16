package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Empresa;

public interface CupomFiscalItemRepository extends JpaRepository<CupomFiscalItem, BigInteger>{
	
	@Query("SELECT c FROM CupomFiscalItem c WHERE c.cupomFiscal.cliente.id = :idCliente order by c.cupomFiscal.dataCompra")
	List<CupomFiscalItem> findByClienteOrderByCupomFiscalDataCompra(int idCliente);

	List<CupomFiscalItem> findFirstByCupomFiscalOrderByIdDesc(CupomFiscal cupomFiscal);

	@Query("SELECT c FROM CupomFiscalItem c WHERE c.cupomFiscal.cliente.tipoCliente.empresa = :empresa AND c.cupomFiscal.cliente = :cliente ORDER BY c.id DESC")
	List<CupomFiscalItem> findFirstByEmpresaAndClienteOrderByIdDesc(Empresa empresa, Cliente cliente);

	@Query("SELECT c FROM CupomFiscalItem c WHERE c.cupomFiscal.cliente = :cliente ORDER BY c.id DESC")
	List<CupomFiscalItem> findFirstByClienteOrderByIdDesc(Cliente cliente);
	

}
