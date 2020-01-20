package br.com.acf.fidelcash.controller.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;

@Service
public class PeriodoDeCompraService {

	@Autowired
	private CupomFiscalService cfService;
	
	public Map<Integer, List<Cliente>> selecionarClientes(LocalDateTime dataFinal, Map<Integer, Integer> mapPeriodos) throws PeriodoDeCompraServiceException {
		try {
			LocalDateTime dataFim = dataFinal; 
			LocalDateTime dataInicio;
			Map<Integer, List<Cliente>> mapClientesPorPeriodo = new HashMap<>();
			int nivelPeriodo = 1;
			List<Cliente> clientesJaInseridos = new ArrayList<Cliente>();
			for (Map.Entry<Integer, Integer> periodo : mapPeriodos.entrySet()) {
				dataInicio = dataInicioPeriodo(dataFim, periodo.getValue() - 1);
				
				List<CupomFiscal> cuponsFiscais = new ArrayList<CupomFiscal>();
				if(clientesJaInseridos.isEmpty()) {
					cuponsFiscais = cfService.findByDataCompraBetween(dataInicio, dataFim);
				} else {
					cuponsFiscais = cfService.findByDataCompraBetweenNotIn(dataInicio, dataFim, clientesJaInseridos);
				}
				
				List<Cliente> clientes = new ArrayList<Cliente>();
				for(CupomFiscal cf : cuponsFiscais ) {
					if (!clientes.contains(cf.getCliente())) {
						clientes.add(cf.getCliente());
					}
				}
				
				clientesJaInseridos = clientes;
				mapClientesPorPeriodo.put(nivelPeriodo, clientes);
				nivelPeriodo ++;
				
				dataFim = dataFimPeriodo(dataInicio);
			}
			return mapClientesPorPeriodo;
		}catch (Exception e) {
			throw new PeriodoDeCompraServiceException("Erro ao selecionar clientes da campanha por periodo", "Erro ao selecionar clientes da campanha por periodo");
		}
	}
	
	private LocalDateTime dataInicioPeriodo(LocalDateTime dataFim, Integer periodo) {
		LocalDateTime dataInicio = dataFim.minusDays(periodo);
		String data = (DateTimeFormatter.ISO_LOCAL_DATE).format(dataInicio);
		dataInicio = LocalDateTime.parse(data+"T00:00:00");
		return dataInicio;
	}
	
	private LocalDateTime dataFimPeriodo(LocalDateTime dataInicio) {
		LocalDateTime dataFim = dataInicio.minusDays(1);
		String data = (DateTimeFormatter.ISO_LOCAL_DATE).format(dataFim);
		dataFim = LocalDateTime.parse(data+"T23:59:59");
		return dataFim;
	}
}
