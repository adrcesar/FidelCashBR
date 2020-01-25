package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;

public interface PeriodoDeCompraRepository extends JpaRepository<CampanhaRegras, Integer>{

	List<CampanhaRegras> findAllByCampanhaOrderById(Campanha campanha);

	List<CampanhaRegras> findAllByCampanhaAndClienteCpf(Campanha campanha, BigInteger cpf);

}
