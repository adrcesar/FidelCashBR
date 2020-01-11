package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.CupomFiscalServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.repository.CupomFiscalRepository;

@Service
public class CupomFiscalService {
	@Autowired
	private CupomFiscalRepository cfRespository;

	public CupomFiscal setCupomFiscal(CupomFiscal cupomFiscal, Cliente cliente) throws CupomFiscalServiceException {

		cupomFiscal.setCliente(cliente);
		Optional<CupomFiscal> cupomFiscalFind = cfRespository.findByCodigoCupomAndCliente(cupomFiscal.getCodigoCupom(),
				cliente);
		if (cupomFiscalFind.isPresent()) {
			throw new CupomFiscalServiceException("registro duplicado", "registro duplicado");
		}
		try {
			cfRespository.save(cupomFiscal);
			cupomFiscalFind = cfRespository.findByCodigoCupomAndCliente(cupomFiscal.getCodigoCupom(), cliente);
			return cupomFiscalFind.get();
		} catch (Exception e) {
			throw new CupomFiscalServiceException("Erro ao gerar cupom fiscal", "Erro ao gerar cupom fiscal");
		}
	}

}
