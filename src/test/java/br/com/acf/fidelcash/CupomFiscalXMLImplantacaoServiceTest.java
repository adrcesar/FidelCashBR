package br.com.acf.fidelcash;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.ImportacaoDto;
import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.service.ClienteService;
import br.com.acf.fidelcash.controller.service.CupomFiscalService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImplantacaoService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImportacaoService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.ProdutoService;
import br.com.acf.fidelcash.controller.service.UtilService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Transactional
public class CupomFiscalXMLImplantacaoServiceTest {
	@Autowired
	private CupomFiscalXMLImplantacaoService cfImplementa;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UtilService utilService;

	private void criarDiretorioAuxiliar(String cnpj) throws EmpresaServiceException, UtilServiceException {
		String pastaBase = "D:\\Projetos\\fidelcash\\arquivos-xml\\";
		String pastaUpload = pastaBase+cnpj+"\\implantacao\\upload";
		utilService.criarDiretorio(pastaUpload);
		Optional<Util> diretorioEmpresaImplantacao = utilService.findByEmpresaAndUtilidade(null, cnpj);
		if(diretorioEmpresaImplantacao.isEmpty()) {
			Util util = new Util();
			util.setUtilidade(cnpj);
			util.setPasta(pastaUpload);
			util.setAcao("ARMAZENA OS ARQUIVOS XML QUE GERARAO A IMPLANTACAO DA EMPRESA");
			utilService.save(util);
		}
	}
	
	private void copiarDePara(String diretorioOrigem, String diretorioDestino) throws IOException {
		Path dir = Paths.get(diretorioOrigem);
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
		for (Path arquivoOrigem : directoryStream) {
			String stringArquivoDestino = diretorioDestino + "\\" + arquivoOrigem.getFileName();
			Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
			Files.copy(arquivoOrigem, arquivoDestino, StandardCopyOption.REPLACE_EXISTING);
		}
	}
	//sve14ae13l - sve14a27cxh

	@Test
	public void implantarEmpresa() throws IOException, CupomFiscalXMLException, EmpresaServiceException,
			UtilServiceException, ParserConfigurationException, SAXException, ParseException {
		
		//SEMPRE MANTER OS ARQUIVOS NA PASTA IMPLANTACAO
		//ESSES ARQUIVOS SERAO COPIADOS PARA UPLOAD E APAGADOS NO FINAL DO PROCESSO
		//OS ARQUIVOS NA PASTA IMPLANTACAO GARANTE QUE SEMPRE EXISTIRÁ DADOS PARA TESTE
		copiarDePara("D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao", 
				   "D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");
		// implantar
		BigInteger cnpj = new BigInteger("99999999999999");
		@SuppressWarnings("unused")
		UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(cnpj.toString());

		Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
		Optional<Produto> produto = produtoService.findByEmpresaAndCodigoProduto(empresa.get(), "29");
		assertEquals("PRIME PREMIUM MEDIO", produto.get().getDescricao());
	}

	@Test
	public void EmpresaJaCadastrada() throws IOException, CupomFiscalXMLException, EmpresaServiceException,
			UtilServiceException, ParserConfigurationException, SAXException, ParseException {
		
		//SEMPRE MANTER OS ARQUIVOS NA PASTA IMPLANTACAO
		//ESSES ARQUIVOS SERAO COPIADOS PARA UPLOAD E APAGADOS NO FINAL DO PROCESSO
		//OS ARQUIVOS NA PASTA IMPLANTACAO GARANTE QUE SEMPRE EXISTIRÁ DADOS PARA TESTE
		copiarDePara("D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");

		// implantar
		BigInteger cnpj = new BigInteger("99999999999999");
		@SuppressWarnings("unused")
		UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(cnpj.toString());

		Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
		Optional<Produto> produto = produtoService.findByEmpresaAndCodigoProduto(empresa.get(), "29");
		assertEquals("PRIME PREMIUM MEDIO", produto.get().getDescricao());
		
		//implantar novamente a mesma empresa
		copiarDePara("D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");
		// implantar
		Exception exception = assertThrows(EmpresaServiceException.class, () -> {
			cfImplementa.implantarFidelCash(cnpj.toString());
		});
		
		assertTrue(exception.getMessage().contains("Empresa já cadastrada"));

	}

	@Test
	public void arquivoXmlNaoEncontrado()
			throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException {
		
		copiarDePara("D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");

		// implantar
		// COPIO ARQUIVOS DE CNPJ 99999999999999 PARA A PASTA DE UPLOAD
		// MAS INFORMO UM OUTRO CNPJ(26501145000160) PARA IMPLANTACAO
		//IMPORTANTE - A PASTA: D:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload TEM QUE ESTAR VAZIA
		BigInteger cnpj = new BigInteger("26501145000160");

		Exception exception = assertThrows(CupomFiscalXMLException.class, () -> {
			cfImplementa.implantarFidelCash(cnpj.toString());
		});

		assertTrue(exception.getMessage().contains("Nenhum arquivo xml encontrado"));
	}

	@Test
	public void cnpjInformadoDiferenteDoXml()
			throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException {
		criarDiretorioAuxiliar("26501145000160");
		
		copiarDePara("D:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "D:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload");

		// implantar
		// COPIO ARQUIVOS XML COM CPF 99999999999999 para a pasta de implantacao\\upload da empresa 26501145000160
		// QUANDO TENTO IMPLNATAR 26501145000160 o sistema encontra xml 99999999999999 e gera o erro
		BigInteger cnpj = new BigInteger("26501145000160");

		Exception exception = assertThrows(CupomFiscalXMLException.class, () -> {
			cfImplementa.implantarFidelCash(cnpj.toString());
		});
		
		assertTrue(exception.getMessage().contains("CNPJ informado é diferente do CNPJ do arquivo"));

		// limpar pasta upload de 26501145000160 pois ele possue xml de 99999999999999
		Path dir = Paths.get("D:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload");
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
		for (Path arquivo : directoryStream) {
			Files.delete(arquivo);
		}
	}
}
