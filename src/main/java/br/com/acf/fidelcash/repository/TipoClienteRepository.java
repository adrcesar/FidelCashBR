package br.com.acf.fidelcash.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.TipoCliente;

public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer>{

	Optional<TipoCliente> findByEmpresaAndDescricao(Empresa empresa, String string);

}
