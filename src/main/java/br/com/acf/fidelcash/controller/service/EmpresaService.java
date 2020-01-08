package br.com.acf.fidelcash.controller.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.modelo.CupomFiscalXML;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;
import br.com.acf.fidelcash.repository.EmpresaRepository;

@Service
public class EmpresaService {
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EnderecoService enderecoService;
	
	public void validaEmpresaImplantada(String cnpjEmpresa) throws EmpresaServiceException {
		Optional<Empresa> empresaFind = empresaRepository.findByCnpj(new BigInteger(cnpjEmpresa));
		if (empresaFind.isPresent()) {
			throw new EmpresaServiceException("Empresa ja cadastrada", "Empresa ja cadastrada");
		}
	}
	
	public Empresa setEmpresa(Empresa empresaXml, CupomFiscalXML cfXML) throws EmpresaServiceException {
		try {
			Optional<Empresa> empresaFind = empresaRepository.findByCnpj(empresaXml.getCnpj());
			if (empresaFind.isEmpty()) {
				Endereco endereco = cfXML.getEndereco();
				enderecoService.save(endereco);
				endereco = enderecoService.findByMaxId();
				empresaXml.setEndereco(endereco);
				empresaXml.setSituacao(SituacaoEmpresa.INATIVA);
				empresaRepository.save(empresaXml);
				empresaFind = empresaRepository.findByCnpj(empresaXml.getCnpj());
			}
			return empresaFind.get();
		} catch (Exception e) {
			throw new EmpresaServiceException("Empresa invalida", "Empresa invalida");
		}
	}

	public Optional<Empresa> findByCnpj(BigInteger cnpj) {
		return empresaRepository.findByCnpj(cnpj);
	}
}
