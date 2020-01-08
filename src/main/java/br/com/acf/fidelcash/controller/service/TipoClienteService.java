package br.com.acf.fidelcash.controller.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.TipoClienteServiceException;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.SituacaoTipoCliente;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.repository.TipoClienteRepository;

@Service
public class TipoClienteService {
	@Autowired
	private TipoClienteRepository tipoClienteRepository;
	
	@Autowired
	private TipoClienteLogService tipoClienteLogService;
	
	public void setTipoCliente(Empresa empresa) throws TipoClienteServiceException {
		try {
			Optional<TipoCliente> tipoClienteFind = tipoClienteRepository.findByEmpresaAndDescricao(empresa, "PADRAO");
			if (tipoClienteFind.isEmpty()) {
				TipoCliente tipoCliente = new TipoCliente();
				tipoCliente.setBonus(5);
				tipoCliente.setDescricao("PADRAO");
				tipoCliente.setEmpresa(empresa);
				tipoCliente.setSituacao(SituacaoTipoCliente.ATIVO);
				tipoClienteRepository.save(tipoCliente);
				
				TipoClienteLog log = new TipoClienteLog();
				log.setTipoCliente(tipoCliente);
				log.setBonus(tipoCliente.getBonus());
				log.setData_inicio(LocalDateTime.now().minusDays(3650));// verificar como tratar este parametro no futuro
				tipoClienteLogService.save(log);
			}
		} catch (Exception ex) {
			throw new TipoClienteServiceException("Tipo de Cliente invalido", "Tipo de Cliente invalido");
		}
	}

	public Optional<TipoCliente> findByEmpresaAndDescricao(Empresa empresa, String string) {
		return tipoClienteRepository.findByEmpresaAndDescricao(empresa, string);
	}

}
