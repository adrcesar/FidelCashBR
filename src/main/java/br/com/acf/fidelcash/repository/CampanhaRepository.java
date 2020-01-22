package br.com.acf.fidelcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Campanha;

public interface CampanhaRepository extends JpaRepository<Campanha, Integer>{

	List<Campanha> findAllByCampanhaPai(Campanha campanhaPai);

}
