package br.com.acf.fidelcash.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;
import br.com.acf.fidelcash.repository.UtilRepository;

@Service
public class UtilService {
	@Autowired
	private UtilRepository utilRepository;
	
	public String getPastaXML(String utilidade) throws UtilServiceException {
		Optional<Util> util = utilRepository.findByEmpresaAndUtilidade(null, utilidade);
		if (util.isPresent()) {
			return util.get().getPasta();
		} else {
			throw new UtilServiceException(utilidade + " nao esta cadastrado na coluna utilidade da tabela Util",
					utilidade + " nao esta cadastrado na coluna utilidade da tabela Util");
		}
	}
	
	public void criarUtilidadeImplantacao(String pastaImportacao, String utilidade, String acao) {
		Util util = setUtil(pastaImportacao, utilidade, acao);
		utilRepository.save(util);
	}
	
	public void criarUtilidadeImportacaoXml(String pastaImportacao, String utilidade, String acao, Empresa empresa) {
		Util util = setUtil(pastaImportacao, utilidade, acao);
		util.setEmpresa(empresa);
		utilRepository.save(util);
	}
	
	public Util setUtil(String pasta, String utilidade, String acao) {
		Optional<Util> utilFind = utilRepository.findByEmpresaAndUtilidade(null, utilidade);
		if (utilFind.isPresent()) {
			utilRepository.delete(utilFind.get());
		}
		
		Util util = new Util();
		util.setUtilidade(utilidade);
		util.setPasta(pasta);
		util.setAcao(acao);
		return util;
	}
	
	public UtilDtoImplantacao dadosDaImplantacao(Optional<Util> util, Optional<TipoCliente> tipoCliente, List<Produto> produtos) {
		UtilDtoImplantacao utilDto = new UtilDtoImplantacao();

		utilDto = dadosUtil(util, utilDto);

		utilDto = dadosEmpresa(produtos.get(0).getEmpresa(), utilDto);

		utilDto = dadosEndereco(produtos.get(0).getEmpresa().getEndereco(), utilDto);

		utilDto = dadosTipoCliente(tipoCliente, utilDto);
		
		utilDto = dadosProdutos(produtos, utilDto);

		return utilDto;
	}

	private UtilDtoImplantacao dadosProdutos(List<Produto> produtos, UtilDtoImplantacao utilDto) {
		if (produtos.isEmpty()) {
			utilDto.setErro(true);
			utilDto.setErroMensagem("Nenhum produto cadastrado");
		} else {
			utilDto.setProdutos(produtos);
		}
		return utilDto;
	}

	private UtilDtoImplantacao dadosTipoCliente(Optional<TipoCliente> tipoCliente, UtilDtoImplantacao utilDto) {
		if (tipoCliente.isEmpty()) {
			utilDto.setErro(true);
			utilDto.setErroMensagem("Nenhum endereço cadastrado");
		} else {
			utilDto.setTipoCliente(tipoCliente.get());
		}
		return utilDto;
	}

	private UtilDtoImplantacao dadosEndereco(Endereco endereco, UtilDtoImplantacao utilDto) {
		if (endereco == null) {
			utilDto.setErro(true);
			utilDto.setErroMensagem("Nenhum endereço cadastrado");
		} else {
			utilDto.setEndereco(endereco);
		}
		return utilDto;
	}

	private UtilDtoImplantacao dadosEmpresa(Empresa empresa, UtilDtoImplantacao utilDto) {
		if (empresa == null) {
			utilDto.setErro(true);
			utilDto.setErroMensagem("Empresa não cadastrada");
		} else {
			utilDto.setEmpresa(empresa);
		}
		return utilDto;
	}

	private UtilDtoImplantacao dadosUtil(Optional<Util> util, UtilDtoImplantacao utilDto) {
		if (util.isEmpty()) {
			utilDto.setErro(true);
			utilDto.setErroMensagem(
					util.get().getUtilidade() + " nao está cadastrado na coluna utilidade da tabela Util");
		} else {
			utilDto.setUtil(util.get());
		}
		return utilDto;
	}

	public Optional<Util> findByEmpresaAndUtilidade(Empresa empresa, String utilidade) {
		return utilRepository.findByEmpresaAndUtilidade(empresa, utilidade);
	}
	
	public List<Util> getPastasImportacaoXML(String utilidade) throws CupomFiscalXMLException, UtilServiceException {
		List<Util> util = utilRepository.findByUtilidade(utilidade);
		if (util.isEmpty()) {
			throw new UtilServiceException(utilidade + " nao esta cadastrado na coluna utilidade da tabela Util",
					utilidade + " nao esta cadastrado na coluna utilidade da tabela Util");
		} else {
			return util;
		}
	}

	public Util getPastaUtilidadeXML(Empresa empresa, String utilidade) throws CupomFiscalXMLException {
		Optional<Util> pasta = utilRepository.findByEmpresaAndUtilidade(empresa, utilidade);
		if (pasta.isEmpty()) {
			throw new CupomFiscalXMLException(utilidade + " nao esta cadastrado na coluna utilidade da tabela Util",
					utilidade + " nao esta cadastrado na coluna utilidade da tabela Util");
		} else {
			return pasta.get();
		}
	}

	public void deleteByEmpresaIsNull() {
		List<Util> utilidades = utilRepository.findByEmpresaIsNotNull();
		for(int i=0; i < utilidades.size(); i++) {
			utilRepository.delete(utilidades.get(i));
		}
		
		
	}

}
