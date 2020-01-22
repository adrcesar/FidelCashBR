package br.com.acf.fidelcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;

public interface CampanhaRegrasRepository extends JpaRepository<CampanhaRegras, Integer>{

	List<CampanhaRegras> findAllByCampanha(Campanha campanha);

}
