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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.ProdutoServiceException;
import br.com.acf.fidelcash.controller.service.exception.TipoClienteServiceException;
import br.com.acf.fidelcash.controller.service.exception.UsuarioServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.CupomFiscalXML;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Perfil;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.SituacaoUsuario;
import br.com.acf.fidelcash.modelo.SituacaoUsuarioPerfil;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.Usuario;
import br.com.acf.fidelcash.modelo.UsuarioPerfil;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@Service
public class CupomFiscalXMLImplantacaoService {

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private TipoClienteService tipoClienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private UsuarioPerfilService usuarioPerfilService;
	
	@Autowired
	private CupomFiscalXMLService cfXMLService;
	
	
	
	@Transactional(rollbackFor = { Exception.class })
	public UtilDtoImplantacao implantarFidelCash(BigInteger cnpjEmpresa, Usuario logado)
			throws CupomFiscalXMLException, EmpresaServiceException, UtilServiceException, UsuarioServiceException {
		try {
			usuarioService.verificaPerfil(logado, "ADMINISTRADOR");
			
			empresaService.validaEmpresaImplantada(cnpjEmpresa);
			
			Optional<Util> diretorioPadrao = utilService.findByEmpresaAndUtilidade(null, "DIRETORIO_PADRAO");
			if(diretorioPadrao.isEmpty()) {
				utilService.criarDiretorio("D:\\Projetos\\fidelcash\\arquivos-xml");
				utilService.criarUtilidadeImplantacao("D:\\Projetos\\fidelcash\\arquivos-xml", "DIRETORIO_PADRAO", "PASTA QUE ARMAZENARÁ TODA A ESTRUTURA DE ARQUIVOS XML DAS EMPRESAS");
				diretorioPadrao = utilService.findByEmpresaAndUtilidade(null, "DIRETORIO_PADRAO");
			}
			String diretorioEmpresa = diretorioPadrao.get().getPasta()+"\\"+cnpjEmpresa+"\\implantacao\\upload";
			utilService.criarDiretorio(diretorioEmpresa);
			utilService.criarUtilidadeImplantacao(diretorioEmpresa,
												  cnpjEmpresa.toString(),
					                              "ARMAZENA OS ARQUIVOS XML QUE GERARAO A IMPLANTACAO DA EMPRESA");
			String pasta = utilService.getPastaXML(cnpjEmpresa.toString());
			Map<Path, String> mapArquivos = implantarEmpresaByXml(pasta, cnpjEmpresa);
			Optional<Empresa> empresaFind = empresaService.findByCnpj(cnpjEmpresa);
			criarDiretoriosDeImplantacao(pasta, empresaFind.get());
			movimentacaoDeArquivos(mapArquivos);
			criarDiretoriosDeImportacao(pasta, empresaFind.get());
			Optional<Util> util = utilService.findByEmpresaAndUtilidade(null, cnpjEmpresa.toString());
			Optional<TipoCliente> tipoCliente = tipoClienteService.findByEmpresaAndDescricao(empresaFind.get(), "PADRAO");
			List<Produto> produtos = produtoService.findByEmpresa(empresaFind.get());
			//
			setUsuarioGerenteDeLoja(empresaFind.get());
			//
			return utilService.dadosDaImplantacao(util, tipoCliente, produtos );
		} catch (IOException | ParseException | ParserConfigurationException | SAXException ex) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}
	
	private void setUsuarioGerenteDeLoja(Empresa empresa) {
		Usuario usuario = new Usuario();
		usuario.setEmail("adrcesar@gmail.com");
		usuario.setNome("GERENTE DA LOJA "+ empresa.getId());
		
		usuario.setSenha("$2a$10$zLiEBFy0Qm2EkCm/h5neAuk8ha2QkzBkM96Nglyb1i2U0PtaaeyiK");
		usuario.setSituacao(SituacaoUsuario.ATIVO);
		usuario.setUsuario("lj_" + empresa.getId());
		usuarioService.save(usuario);
		
		Optional<Perfil> perfilFind = perfilService.findByNome("GERENTE DE LOJA");
		if(perfilFind.isEmpty()) {
			Perfil perfil = new Perfil();
			perfil.setNome("GERENTE DE LOJA");
			perfilService.save(perfil);
			perfilFind = perfilService.findByNome("GERENTE DE LOJA");
		}
		
		
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		usuarioPerfil.setEmpresa(empresa);
		usuarioPerfil.setPerfil(perfilFind.get());
		usuarioPerfil.setUsuario(usuario);
		usuarioPerfil.setSituacao(SituacaoUsuarioPerfil.ATIVO);
		usuarioPerfilService.save(usuarioPerfil);
	}

	private void criarDiretoriosDeImportacao(String pasta, Empresa empresa) throws CupomFiscalXMLException {
		int tamanho = empresa.getCnpj().toString().length();
		int posicao = pasta.indexOf(empresa.getCnpj().toString());
		posicao = posicao + tamanho;
		String pastaImportacaoOrigem = pasta.substring(0, posicao);
		
		criarDiretorioErro(pastaImportacaoOrigem, empresa);
		
		criarDiretorioImportados(pastaImportacaoOrigem, empresa);
		
		criarDiretorioRegistroDuplicado(pastaImportacaoOrigem, empresa);
		
		criarDiretorioUpload(pastaImportacaoOrigem, empresa);
		
		criarDiretorioXmlSemCpf(pastaImportacaoOrigem, empresa);
	}
	
	private void criarDiretoriosDeImplantacao(String pasta, Empresa empresa) throws CupomFiscalXMLException {
		String pastaImportados = "implantacao";
		int tamanho = pastaImportados.length();
		int posicao = pasta.indexOf(pastaImportados);
		posicao = posicao + tamanho;
		String pastaImplantacao = pasta.substring(0, posicao);
		
		criarDiretorioImplantacaoImportados(pastaImplantacao, empresa.getCnpj().toString());
	}

	private void criarDiretorioImplantacaoImportados(String pastaImplantacao, String cnpj) throws CupomFiscalXMLException {
		String utilidade = cnpj.toString() + "_implantados_xml";
		String pastaImplantacaoImportados = pastaImplantacao + "\\importados\\";
		Path pastaImportados = FileSystems.getDefault().getPath(pastaImplantacaoImportados);
		criarDiretorio(pastaImplantacaoImportados, pastaImportados);
		
		utilService.criarUtilidadeImplantacao(pastaImplantacaoImportados, utilidade, "ARMAZENA ARQUIVOS XML IMPLANTADOS COM SUCESSO.");
	}

	private void criarDiretorioXmlSemCpf(String pastaImportacaoOrigem, Empresa empresa ) throws CupomFiscalXMLException {
		String pastaImportacaoXmlSemCpf = pastaImportacaoOrigem + "\\importacao\\xml_sem_cpf\\";
		Path pastaXmlSemCpf = FileSystems.getDefault().getPath(pastaImportacaoXmlSemCpf);
		criarDiretorio(pastaImportacaoXmlSemCpf, pastaXmlSemCpf);
		
		utilService.criarUtilidadeImportacaoXml(pastaImportacaoXmlSemCpf, "sem_cpf_import_xml", "ARMAZENA ARQUIVOS XML SEM INFORMACAO DO CPF DO CLIENTE.", empresa);
	}

	private void criarDiretorioUpload(String pastaImportacaoOrigem, Empresa empresa) throws CupomFiscalXMLException {
		String pastaImportacaoUpload = pastaImportacaoOrigem + "\\importacao\\upload\\";
		Path pastaUpload = FileSystems.getDefault().getPath(pastaImportacaoUpload);
		criarDiretorio(pastaImportacaoUpload, pastaUpload);
		
		utilService.criarUtilidadeImportacaoXml(pastaImportacaoUpload, "upload_import_xml", "ARMAZENA ARQUIVOS XML QUE SERAO IMPORTADOS PARA O SISTEMA.", empresa);
	}

	private void criarDiretorioRegistroDuplicado(String pastaImportacaoOrigem, Empresa empresa) throws CupomFiscalXMLException {
		String pastaImportacaoRegistroDuplicado = pastaImportacaoOrigem + "\\importacao\\registro_duplicado\\";
		Path pastaRegistroDuplicado = FileSystems.getDefault().getPath(pastaImportacaoRegistroDuplicado);
		criarDiretorio(pastaImportacaoRegistroDuplicado, pastaRegistroDuplicado);
		
		utilService.criarUtilidadeImportacaoXml(pastaImportacaoRegistroDuplicado, 
				       "registro_duplicado_import_xml", "RMAZENA ARQUIVOS XML DUPLICADOS - JA FORAM IMPORTADOS PELO O SISTEMA.",
				       empresa);
	}

	private void criarDiretorioImportados(String pastaImportacaoOrigem, Empresa empresa) throws CupomFiscalXMLException {
		String pastaImportacaoImportados = pastaImportacaoOrigem + "\\importacao\\importados\\";
		Path pastaImportados = FileSystems.getDefault().getPath(pastaImportacaoImportados);
		criarDiretorio(pastaImportacaoImportados, pastaImportados);
		
		utilService.criarUtilidadeImportacaoXml(pastaImportacaoImportados, "importados_xml", "ARMAZENA ARQUIVOS XML IMPORTADOS PARA O SISTEMA.", empresa);
	}

	private void criarDiretorioErro(String pastaImportacaoOrigem, Empresa empresa) throws CupomFiscalXMLException {
		String pastaImportacaoErro = pastaImportacaoOrigem + "\\importacao\\erro\\";
		Path pastaErro = FileSystems.getDefault().getPath(pastaImportacaoErro);
		criarDiretorio(pastaImportacaoErro, pastaErro);
		
		utilService.criarUtilidadeImportacaoXml(pastaImportacaoErro, "erro_import_xml", "ARMAZENA ARQUIVOS XML QUE SOFRERAM ERROS DURANTE A IMPORTACAO.", empresa);
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

	private void movimentacaoDeArquivos(Map<Path, String> mapArquivos)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException, UtilServiceException {
		
		for (Map.Entry<Path, String> entry : mapArquivos.entrySet()) {
			try {
				moverArquivos(entry.getKey(), entry.getValue());
			} catch (IOException e) {
				throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
			}
		}
	}
	
	private Map<Path, String> implantarEmpresaByXml(String directory, BigInteger cnpjEmpresa) throws CupomFiscalXMLException {
		try {
			Map<Path, String> mapArquivo = new HashMap<>();
			Path dir = Paths.get(directory);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
			int quantidadeDeRegistros = 0;
			for (Path arquivo : directoryStream) {
				GerarDadosByXML(arquivo, cnpjEmpresa);
				mapArquivo.put(arquivo, cnpjEmpresa + "_implantados_xml");
				quantidadeDeRegistros++;
			}
			if (quantidadeDeRegistros == 0) {
				throw new CupomFiscalXMLException("Nenhum arquivo xml encontrado", "Nenhum arquivo xml encontrado");
			}
			directoryStream.close();
			return mapArquivo;
		} catch (CupomFiscalXMLException ex) {
			throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
		} catch (EmpresaServiceException ex) {
			throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
		} catch (IOException ex) {
			throw new CupomFiscalXMLException("Erro na Implantacao", "Erro na implantacao");
		} catch (TipoClienteServiceException ex) {
			throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
		} catch (ProdutoServiceException ex) {
			throw new CupomFiscalXMLException(ex.getMensagem(), ex.getMensagem());
		}
	}

	private void GerarDadosByXML(Path arquivo, BigInteger cnpjEmpresa)
			throws CupomFiscalXMLException, EmpresaServiceException, TipoClienteServiceException, ProdutoServiceException {
		try {
			String xml = arquivo.toString();
			CupomFiscalXML cfXML = cfXMLService.GerarDadosByXml(xml);
			Empresa empresaXML = cfXML.getEmpresa();
			List<Produto> produtos = cfXML.getProdutos();
			validaCnpjXml(xml, cnpjEmpresa, empresaXML.getCnpj());
			Empresa empresa = empresaService.setEmpresa(empresaXML, cfXML);
			tipoClienteService.setTipoCliente(empresa);
			for (int i = 0; i < produtos.size(); i++) {
				produtos.get(i).setEmpresa(empresa);
				Optional<Produto> prodFind = produtoService.findByEmpresaAndCodigoProduto(empresa, produtos.get(i).getCodigoProduto());
				if(prodFind.isEmpty()) {
					produtoService.save(produtos.get(i));
				}
			}
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}

	private void validaCnpjXml(String arquivo, BigInteger cnpjParametro, BigInteger cnpjXml)
			throws CupomFiscalXMLException {
		if (cnpjParametro.compareTo(cnpjXml) != 0) {
			throw new CupomFiscalXMLException("CNPJ informado é diferente do CNPJ do arquivo " + arquivo,
					"CNPJ informado é diferente do CNPJ do arquivo " + arquivo);
		}
	}

	private void moverArquivos(Path arquivo, String operacao) throws FileNotFoundException, IOException,
			CupomFiscalXMLException, ParseException, ParserConfigurationException, SAXException, UtilServiceException {
		String pastaOperacao = utilService.getPastaXML(operacao);
		String stringArquivoDestino = pastaOperacao + arquivo.getFileName();
		Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
		try {
			Files.move(arquivo, arquivoDestino, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}
	
	
}
