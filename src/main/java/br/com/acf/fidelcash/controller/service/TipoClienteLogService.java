package br.com.acf.fidelcash.controller.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.TipoClienteLogServiceException;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.repository.TipoClienteLogRepository;


@Service
public class TipoClienteLogService {
	@Autowired
	private TipoClienteLogRepository tipoClienteLogRepository;

	public void save(TipoClienteLog log) {
		tipoClienteLogRepository.save(log);
		
	}

	public TipoClienteLog bonusDoPeriodo(CupomFiscalItem cfItem) throws TipoClienteLogServiceException {
		Empresa emp = cfItem.getCupomFiscal().getCliente().getTipoCliente().getEmpresa();
		List<TipoClienteLog> log = tipoClienteLogRepository.findByTipoClienteEmpresaOrderById(emp);
		float bonus = 0;
		boolean dataCupomValida = false;
		TipoClienteLog logBonus = new TipoClienteLog();
		for (int i = 0; i < log.size(); i++) {
			LocalDateTime dataInicio = log.get(i).getData_inicio();
			LocalDateTime dataFim = log.get(i).getData_fim();
			if (dataFim == null) {
				dataFim = LocalDateTime.now();
			}
			LocalDateTime dataCupom = cfItem.getCupomFiscal().getDataCompra();

			int compareDataInicio = dataCupom.compareTo(dataInicio);
			int compareDataFim = dataCupom.compareTo(dataFim);

			if (compareDataInicio >= 0 && compareDataFim <= 0) {
				dataCupomValida = true;
				logBonus = log.get(i);
				bonus = log.get(i).getBonus();
				break;
			}
		}
		if(!dataCupomValida) {
			throw new TipoClienteLogServiceException("Data do cupom invalida", "Data do cupom invalida");
		}
		if(bonus < 1) {
			throw new TipoClienteLogServiceException("Bonus do período inferior a 1%", "Bonus do período inferior a 1%");
		}
		return logBonus;
	}
}
