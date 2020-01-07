package br.com.acf.fidelcash.controller.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;
import br.com.acf.fidelcash.repository.EmpresaRepository;
import br.com.acf.fidelcash.repository.EnderecoRepository;
import br.com.acf.fidelcash.repository.ProdutoRepository;
import br.com.acf.fidelcash.repository.TipoClienteLogRepository;
import br.com.acf.fidelcash.repository.TipoClienteRepository;
import br.com.acf.fidelcash.repository.UtilRepository;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.modelo.SituacaoTipoCliente;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.modelo.CupomFiscalXML;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;

@Service
public class CupomFiscalXMLImplantacaoService {

	@Autowired
	private UtilRepository utilRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private TipoClienteRepository tipoClienteRepository;
	
	@Autowired
	private TipoClienteLogRepository tipoClienteLogRepository;

	@Transactional(rollbackFor = { Exception.class })
	public UtilDtoImplantacao implantarFidelCash(String cnpjEmpresa)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		try {
			validaEmpresaImplantada(cnpjEmpresa);
			String pasta = getPastaXML(cnpjEmpresa);
			Map<Path, String> mapArquivos = implantarEmpresaByXml(pasta, cnpjEmpresa);
			criarDiretoriosDeImplantacao(pasta, cnpjEmpresa);
			movimentacaoDeArquivos(mapArquivos);
			criarDiretoriosDeImportacao(pasta, cnpjEmpresa);
			return dadosDaImplantacao(cnpjEmpresa);
		} catch (IOException | ParseException | ParserConfigurationException | SAXException ex) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}

	private void criarDiretoriosDeImportacao(String pasta, String cnpjEmpresa) throws CupomFiscalXMLException {
		int tamanho = cnpjEmpresa.length();
		int posicao = pasta.indexOf(cnpjEmpresa);
		posicao = posicao + tamanho;
		String pastaImportacaoOrigem = pasta.substring(0, posicao);
		
		criarDiretorioErro(pastaImportacaoOrigem, cnpjEmpresa);
		
		criarDiretorioImportados(pastaImportacaoOrigem, cnpjEmpresa);
		
		criarDiretorioRegistroDuplicado(pastaImportacaoOrigem, cnpjEmpresa);
		
		criarDiretorioUpload(pastaImportacaoOrigem, cnpjEmpresa);
		
		criarDiretorioXmlSemCpf(pastaImportacaoOrigem, cnpjEmpresa);
	}
	
	private void criarDiretoriosDeImplantacao(String pasta, String cnpjEmpresa) throws CupomFiscalXMLException {
		String pastaImportados = "implantacao";
		int tamanho = pastaImportados.length();
		int posicao = pasta.indexOf(pastaImportados);
		posicao = posicao + tamanho;
		String pastaImplantacao = pasta.substring(0, posicao);
		
		criarDiretorioImplantacaoImportados(pastaImplantacao, cnpjEmpresa);
	}

	private void criarDiretorioImplantacaoImportados(String pastaImplantacao, String cnpjEmpresa) throws CupomFiscalXMLException {
		String utilidade = cnpjEmpresa + "_implantados_xml";
		String pastaImplantacaoImportados = pastaImplantacao + "\\importados\\";
		Path pastaImportados = FileSystems.getDefault().getPath(pastaImplantacaoImportados);
		criarDiretorio(pastaImplantacaoImportados, pastaImportados);
		
		criarUtilidadeImplantacao(pastaImplantacaoImportados, utilidade, "ARMAZENA ARQUIVOS XML IMPLANTADOS COM SUCESSO.");
	}

	private void criarDiretorioXmlSemCpf(String pastaImportacaoOrigem, String cnpjEmpresa) throws CupomFiscalXMLException {
		String pastaImportacaoXmlSemCpf = pastaImportacaoOrigem + "\\importacao\\xml_sem_cpf\\";
		Path pastaXmlSemCpf = FileSystems.getDefault().getPath(pastaImportacaoXmlSemCpf);
		criarDiretorio(pastaImportacaoXmlSemCpf, pastaXmlSemCpf);
		
		criarUtilidadeImportacao(pastaImportacaoXmlSemCpf, "sem_cpf_import_xml", "ARMAZENA ARQUIVOS XML SEM INFORMACAO DO CPF DO CLIENTE.", cnpjEmpresa);
	}

	private void criarDiretorioUpload(String pastaImportacaoOrigem, String cnpjEmpresa) throws CupomFiscalXMLException {
		String pastaImportacaoUpload = pastaImportacaoOrigem + "\\importacao\\upload\\";
		Path pastaUpload = FileSystems.getDefault().getPath(pastaImportacaoUpload);
		criarDiretorio(pastaImportacaoUpload, pastaUpload);
		
		criarUtilidadeImportacao(pastaImportacaoUpload, "upload_import_xml", "ARMAZENA ARQUIVOS XML QUE SERAO IMPORTADOS PARA O SISTEMA.", cnpjEmpresa);
	}

	private void criarDiretorioRegistroDuplicado(String pastaImportacaoOrigem, String cnpjEmpresa) throws CupomFiscalXMLException {
		String pastaImportacaoRegistroDuplicado = pastaImportacaoOrigem + "\\importacao\\registro_duplicado\\";
		Path pastaRegistroDuplicado = FileSystems.getDefault().getPath(pastaImportacaoRegistroDuplicado);
		criarDiretorio(pastaImportacaoRegistroDuplicado, pastaRegistroDuplicado);
		
		criarUtilidadeImportacao(pastaImportacaoRegistroDuplicado, 
				       "registro_duplicado_import_xml", "RMAZENA ARQUIVOS XML DUPLICADOS - JA FORAM IMPORTADOS PELO O SISTEMA.",
				       cnpjEmpresa);
	}

	private void criarDiretorioImportados(String pastaImportacaoOrigem, String cnpjEmpresa) throws CupomFiscalXMLException {
		String pastaImportacaoImportados = pastaImportacaoOrigem + "\\importacao\\importados\\";
		Path pastaImportados = FileSystems.getDefault().getPath(pastaImportacaoImportados);
		criarDiretorio(pastaImportacaoImportados, pastaImportados);
		
		criarUtilidadeImportacao(pastaImportacaoImportados, "importados_xml", "ARMAZENA ARQUIVOS XML IMPORTADOS PARA O SISTEMA.", cnpjEmpresa);
	}

	private void criarDiretorioErro(String pastaImportacaoOrigem, String cnpjEmpresa) throws CupomFiscalXMLException {
		String pastaImportacaoErro = pastaImportacaoOrigem + "\\importacao\\erro\\";
		Path pastaErro = FileSystems.getDefault().getPath(pastaImportacaoErro);
		criarDiretorio(pastaImportacaoErro, pastaErro);
		
		criarUtilidadeImportacao(pastaImportacaoErro, "erro_import_xml", "ARMAZENA ARQUIVOS XML QUE SOFRERAM ERROS DURANTE A IMPORTACAO.", cnpjEmpresa);
	}

	private void criarUtilidadeImportacao(String pastaImportacao, String utilidade, String acao, String cnpjEmpresa) {
		Util util = setUtil(pastaImportacao, utilidade, acao);
		
		Optional<Empresa> empresaFind = empresaRepository.findByCnpj(new BigInteger(cnpjEmpresa));
		util.setEmpresa(empresaFind.get());
		
		utilRepository.save(util);
	}

	private void criarUtilidadeImplantacao(String pastaImportacao, String utilidade, String acao) {
		Util util = setUtil(pastaImportacao, utilidade, acao);
		
		utilRepository.save(util);
	}
	
	private Util setUtil(String pastaImportacao, String utilidade, String acao) {
		Optional<Util> utilFind = utilRepository.findByEmpresaAndUtilidade(null, utilidade);
		if (utilFind.isPresent()) {
			utilRepository.delete(utilFind.get());
		}
		
		Util util = new Util();
		util.setUtilidade(utilidade);
		util.setPasta(pastaImportacao);
		util.setAcao(acao);
		return util;
	}

	private void criarDiretorio(String pastaString, Path pastaPath) throws CupomFiscalXMLException {
		if (!Files.exists(pastaPath)) {
			File file = new File(pastaString);
			boolean bool = file.mkdir();
			if (!bool) {
				throw new CupomFiscalXMLException(pastaString + " nao pode ser criada" , pastaString + " nao pode ser criada" );
			} 
		}
	}

	private void validaEmpresaImplantada(String cnpjEmpresa) throws CupomFiscalXMLException {
		Optional<Empresa> empresaFind = empresaRepository.findByCnpj(new BigInteger(cnpjEmpresa));
		if (empresaFind.isPresent()) {
			throw new CupomFiscalXMLException("Empresa ja cadastrada", "Empresa ja cadastrada");
		}
	}

	private void movimentacaoDeArquivos(Map<Path, String> mapArquivos)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		
		for (Map.Entry<Path, String> entry : mapArquivos.entrySet()) {
			try {
				moverArquivos(entry.getKey(), entry.getValue());
			} catch (IOException e) {
				throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
			}
		}
	}
	
	

	private String getPastaXML(String utilidade) throws CupomFiscalXMLException {
		Optional<Util> util = utilRepository.findByEmpresaAndUtilidade(null, utilidade);
		if (util.isPresent()) {
			return util.get().getPasta();
		} else {
			throw new CupomFiscalXMLException(utilidade + " nao esta cadastrado na coluna utilidade da tabela Util",
					utilidade + " nao esta cadastrado na coluna utilidade da tabela Util");
		}
	}

	private Map<Path, String> implantarEmpresaByXml(String directory, String cnpjEmpresa)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		Map<Path, String> mapArquivo = new HashMap<>();
		Path dir = Paths.get(directory);
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
		int quantidadeDeRegistros = 0;
		for (Path arquivo : directoryStream) {
			try {
				GerarDadosByXML(arquivo, cnpjEmpresa);
				mapArquivo.put(arquivo, cnpjEmpresa + "_implantados_xml");
				quantidadeDeRegistros++;
			} catch (CupomFiscalXMLException ex) {
				if (ex.getMensagem().equals("Arquivo inconsistente")) {
					throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
				} else if (ex.getMensagem().equals("Empresa Invalida")) {
					throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
				} else if (ex.getMensagem().equals("Produto invalido")) {
					throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
				} else if (ex.getMensagem().contains("CNPJ informado é diferente do CNPJ do arquivo")) {
					throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
				} else {
					throw new CupomFiscalXMLException("Erro na Implantacao", "Erro na implantacao");
				}
			} catch (IOException | ParseException | ParserConfigurationException | SAXException ex) {
				throw new CupomFiscalXMLException("Erro na Implantacao", "Erro na implantacao");
			}
		}
		if (quantidadeDeRegistros == 0) {
			throw new CupomFiscalXMLException("Nenhum arquivo xml encontrado", "Nenhum arquivo xml encontrado");
		}
		directoryStream.close();
		return mapArquivo;
	}

	private void GerarDadosByXML(Path arquivo, String cnpjEmpresa)
			throws ParserConfigurationException, SAXException, IOException, ParseException, CupomFiscalXMLException {
		try {
			String xml = arquivo.toString();

			CupomFiscalXML cfXML = new CupomFiscalXML(xml);
			Empresa empresaXML = cfXML.getEmpresa();
			List<Produto> produtos = cfXML.getProdutos();
			validaCnpjImplantacao(xml, new BigInteger(cnpjEmpresa), empresaXML.getCnpj());
			Empresa empresa = setEmpresa(empresaXML, cfXML);
			setTipoCliente(empresa);
			for (int i = 0; i < produtos.size(); i++) {
				setProduto(produtos.get(i), empresa);
			}
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}

	private void validaCnpjImplantacao(String arquivo, BigInteger cnpjParametro, BigInteger cnpjXml)
			throws CupomFiscalXMLException {
		if (cnpjParametro.compareTo(cnpjXml) != 0) {
			throw new CupomFiscalXMLException("CNPJ informado é diferente do CNPJ do arquivo " + arquivo,
					"CNPJ informado é diferente do CNPJ do arquivo " + arquivo);
		}
	}

	private void moverArquivos(Path arquivo, String operacao) throws FileNotFoundException, IOException,
			CupomFiscalXMLException, ParseException, ParserConfigurationException, SAXException {
		String pastaOperacao = getPastaXML(operacao);
		String stringArquivoDestino = pastaOperacao + arquivo.getFileName();
		Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
		try {
			Files.move(arquivo, arquivoDestino, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}

	private Empresa setEmpresa(Empresa empresa, CupomFiscalXML cfXML) throws CupomFiscalXMLException {
		try {
			Optional<Empresa> empresaFind = empresaRepository.findByCnpj(empresa.getCnpj());
			if (empresaFind.isEmpty()) {
				Endereco endereco = cfXML.getEndereco();
				enderecoRepository.save(endereco);
				endereco = enderecoRepository.findByMaxId();
				empresa.setEndereco(endereco);
				empresa.setSituacao(SituacaoEmpresa.INATIVA);
				empresaRepository.save(empresa);
				empresaFind = empresaRepository.findByCnpj(empresa.getCnpj());
			}
			return empresaFind.get();
		} catch (Exception e) {
			throw new CupomFiscalXMLException("Empresa invalida", "Empresa invalida");
		}
	}

	private Produto setProduto(Produto produto, Empresa empresa) throws CupomFiscalXMLException {
		try {
			Produto prod = produto;
			prod.setEmpresa(empresa);
			Optional<Produto> produtoFind = produtoRepository.findByEmpresaAndCodigoProduto(empresa,
					prod.getCodigoProduto());
			if (produtoFind.isEmpty()) {
				produtoRepository.save(prod);
				produtoFind = produtoRepository.findByEmpresaAndCodigoProduto(empresa, prod.getCodigoProduto());
			}
			return produtoFind.get();
		} catch (Exception e) {
			throw new CupomFiscalXMLException("Produto invalido", "Produto invalido");
		}

	}

	private void setTipoCliente(Empresa empresa) throws CupomFiscalXMLException {
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
				tipoClienteLogRepository.save(log);
			}
		} catch (Exception ex) {
			throw new CupomFiscalXMLException("Tipo de Cliente invalido", "Tipo de Cliente invalido");
		}
	}

	private UtilDtoImplantacao dadosDaImplantacao(String cnpjEmpresa) {
		UtilDtoImplantacao utilDto = new UtilDtoImplantacao();

		Optional<Util> util = utilRepository.findByEmpresaAndUtilidade(null, cnpjEmpresa);
		utilDto = dadosUtil(util, utilDto);

		Optional<Empresa> empresa = empresaRepository.findByCnpj(new BigInteger(cnpjEmpresa));
		utilDto = dadosEmpresa(empresa, utilDto);

		Optional<Endereco> endereco = enderecoRepository.findById(empresa.get().getEndereco().getId());
		utilDto = dadosEndereco(endereco, utilDto);

		Optional<TipoCliente> tipoCliente = tipoClienteRepository.findByEmpresaAndDescricao(empresa.get(), "PADRAO");
		utilDto = dadosTipoCliente(tipoCliente, utilDto);

		List<Produto> produtos = produtoRepository.findByEmpresa(empresa);
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

	private UtilDtoImplantacao dadosEndereco(Optional<Endereco> endereco, UtilDtoImplantacao utilDto) {
		if (endereco.isEmpty()) {
			utilDto.setErro(true);
			utilDto.setErroMensagem("Nenhum endereço cadastrado");
		} else {
			utilDto.setEndereco(endereco.get());
		}
		return utilDto;
	}

	private UtilDtoImplantacao dadosEmpresa(Optional<Empresa> empresa, UtilDtoImplantacao utilDto) {
		if (empresa.isEmpty()) {
			utilDto.setErro(true);
			utilDto.setErroMensagem("Empresa não cadastrada");
		} else {
			utilDto.setEmpresa(empresa.get());
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
}
