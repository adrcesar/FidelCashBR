package br.com.acf.fidelcash.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Pdv;

public interface PdvRepository extends JpaRepository<Pdv, Integer>{

	Optional<Pdv> findByMacAddress(String macAddress);

}
