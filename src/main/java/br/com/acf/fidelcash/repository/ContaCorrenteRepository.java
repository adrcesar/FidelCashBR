package br.com.acf.fidelcash.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, BigInteger>{

}
