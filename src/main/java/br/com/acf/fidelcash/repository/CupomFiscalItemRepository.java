package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import br.com.acf.fidelcash.modelo.CupomFiscalItem;

public interface CupomFiscalItemRepository extends JpaRepository<CupomFiscalItem, BigInteger>{
	
	@Query("SELECT c FROM CupomFiscalItem c WHERE c.cupomFiscal.cliente.id = :idCliente order by c.cupomFiscal.dataCompra")
	List<CupomFiscalItem> findByClienteOrderByCupomFiscalDataCompra(int idCliente);
	

}
