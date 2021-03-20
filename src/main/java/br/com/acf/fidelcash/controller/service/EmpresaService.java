package br.com.acf.fidelcash.controller.service;

import java.math.BigInteger;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	public void validaEmpresaImplantada(BigInteger cnpjEmpresa) throws EmpresaServiceException {
		Optional<Empresa> empresaFind = empresaRepository.findByCnpj(cnpjEmpresa);
		if (empresaFind.isPresent()) {
			throw new EmpresaServiceException("Empresa já cadastrada", "Empresa já cadastrada");
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
				empresaXml.setSituacao(SituacaoEmpresa.ATIVA);
				empresaRepository.save(empresaXml);
				empresaFind = empresaRepository.findByCnpj(empresaXml.getCnpj());
			}
			return empresaFind.get();
		} catch (Exception e) {
			throw new EmpresaServiceException("Empresa inválida", "Empresa inválida");
		}
	}

	public Optional<Empresa> findByCnpj(BigInteger cnpj) {
		return empresaRepository.findByCnpj(cnpj);
	}

	public Empresa validaImportacaoEmpresa(Empresa empresaXML, Empresa empresaUtil) throws EmpresaServiceException {
		if(empresaXML.getCnpj().compareTo(empresaUtil.getCnpj()) != 0) {
			throw new EmpresaServiceException("CNPJ da empresa do XML diferente do CNPJ cadastrado na tabela Util", "CNPJ da empresa do XML diferente do CNPJ cadastrado na tabela Util");
		}
		Optional<Empresa> empresaFind = empresaRepository.findByCnpj(empresaXML.getCnpj());
        if (empresaFind.isEmpty()) {
            throw new EmpresaServiceException("Empresa do arquivo xml nao cadastrada no sistema", "Empresa do arquivo xml nao cadastrada no sistema");
        } else {
            if (empresaFind.get().getSituacao() == SituacaoEmpresa.INATIVA) {
                throw new EmpresaServiceException("Empresa Inativa", "Empresa Inativa");
            }
            return empresaFind.get();
        }
		
	}

	public void save(Empresa empresa) {
		empresaRepository.save(empresa);
		
	}

	public void deleteAll() {
		empresaRepository.deleteAll();
		
	}

	public Empresa findById(@NotNull @NotEmpty Integer idEmpresa) throws EmpresaServiceException {
		Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
		if(empresa.isEmpty()) {
			throw new EmpresaServiceException("Empresa nao encontrada", "Empresa nao encontrada");
		}
		return empresa.get();
	}
}
