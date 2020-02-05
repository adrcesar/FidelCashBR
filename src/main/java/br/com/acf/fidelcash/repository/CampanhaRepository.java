package br.com.acf.fidelcash.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.Empresa;

public interface CampanhaRepository extends JpaRepository<Campanha, Integer>{

	List<Campanha> findAllByCampanhaPaiOrderById(Campanha campanhaPai);

	List<Campanha> findAllByCampanhaPaiNotNullOrderByEmpresa();

	@Query("SELECT c FROM Campanha c WHERE c.empresa = :empresa AND c.dataInicio <= :dataCupom AND c.dataFim >= :dataCupom ORDER BY c.bonus DESC")
	List<Campanha> findAllByEmpresaPeriodoOrderByBonusDesc(Empresa empresa, LocalDateTime dataCupom);

}
