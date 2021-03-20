package br.com.acf.fidelcash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Pdv;

public interface PdvRepository extends JpaRepository<Pdv, Integer>{

	Pdv findByMacAddress(String macAddress);

}
