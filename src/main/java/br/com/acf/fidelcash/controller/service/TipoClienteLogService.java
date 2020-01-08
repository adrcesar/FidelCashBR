package br.com.acf.fidelcash.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.repository.TipoClienteLogRepository;


@Service
public class TipoClienteLogService {
	@Autowired
	private TipoClienteLogRepository tipoClienteLogRepository;

	public void save(TipoClienteLog log) {
		tipoClienteLogRepository.save(log);
		
	}
	
	

}
