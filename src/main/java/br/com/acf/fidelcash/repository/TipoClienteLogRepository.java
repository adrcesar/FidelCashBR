package br.com.acf.fidelcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.TipoClienteLog;

public interface TipoClienteLogRepository extends JpaRepository<TipoClienteLog, Integer>{

	List<TipoClienteLog> findByTipoClienteEmpresaOrderById(Empresa emp);

	

}
