package br.com.acf.fidelcash.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
	Optional<Empresa> findByCnpj(BigInteger cnpj);
}
