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
	private CupomFiscalXMLImportacaoService cfImporta;
	
	@Autowired
	private CupomFiscalService cupomFiscalService;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UtilService utilService;

	private void criarDiretorioAuxiliar() throws EmpresaServiceException, UtilServiceException {
		utilService.criarDiretorio("C:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload");
		Optional<Util> diretorioEmpresaImplantacao = utilService.findByEmpresaAndUtilidade(null, "26501145000160");
		if(diretorioEmpresaImplantacao.isEmpty()) {
			Util util = new Util();
			util.setUtilidade("26501145000160");
			util.setPasta("C:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload");
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

	@Test
	public void implantarEmpresa() throws IOException, CupomFiscalXMLException, EmpresaServiceException,
			UtilServiceException, ParserConfigurationException, SAXException, ParseException {
		
		copiarDePara("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao", 
				   "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");
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
		
		copiarDePara("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");

		// implantar
		BigInteger cnpj = new BigInteger("99999999999999");
		@SuppressWarnings("unused")
		UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(cnpj.toString());

		Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
		Optional<Produto> produto = produtoService.findByEmpresaAndCodigoProduto(empresa.get(), "29");
		assertEquals("PRIME PREMIUM MEDIO", produto.get().getDescricao());
		// Importar
		// move arquivos para a pasta de de upload da importacao
		copiarDePara("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\importacao",
				   "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\importacao\\upload");
		//setar empresa para ATIVA
		empresa = empresaService.findByCnpj(cnpj);
		empresa.get().setSituacao(SituacaoEmpresa.ATIVA);
		empresaService.save(empresa.get());
		//IMPORTAR
		@SuppressWarnings("unused")
		List<ImportacaoDto> importacao = new ArrayList<ImportacaoDto>();
		importacao = cfImporta.importarXml();
		//VALIDAR
		BigInteger cpf = new BigInteger("16368579811");
		Optional<Cliente> cliente = clienteService.findByEmpresaAndCpf(empresa.get(), cpf);
		Optional<CupomFiscal> cf = cupomFiscalService.findByCodigoCupomCliente(43166, cliente.get());
		assertTrue(cf.isPresent());
		
		//implantar novamente a mesma empresa
		copiarDePara("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");
		// implantar
		Exception exception = assertThrows(EmpresaServiceException.class, () -> {
			cfImplementa.implantarFidelCash(cnpj.toString());
		});
		
		assertTrue(exception.getMessage().contains("Empresa ja cadastrada"));

	}

	@Test
	public void arquivoXmlNaoEncontrado()
			throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException {
		
		copiarDePara("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload");

		// implantar
		BigInteger cnpj = new BigInteger("26501145000160");

		Exception exception = assertThrows(CupomFiscalXMLException.class, () -> {
			cfImplementa.implantarFidelCash(cnpj.toString());
		});

		assertTrue(exception.getMessage().contains("Nenhum arquivo xml encontrado"));
	}

	@Test
	public void cnpjInformadoDiferenteDoXml()
			throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException {
		criarDiretorioAuxiliar();
		
		copiarDePara("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao",
				   "C:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload");

		// implantar
		BigInteger cnpj = new BigInteger("26501145000160");

		Exception exception = assertThrows(CupomFiscalXMLException.class, () -> {
			cfImplementa.implantarFidelCash(cnpj.toString());
		});

		assertTrue(exception.getMessage().contains("CNPJ informado é diferente do CNPJ do arquivo"));

		// limpar pasta upload
		Path dir = Paths.get("C:\\Projetos\\fidelcash\\arquivos-xml\\26501145000160\\implantacao\\upload");
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
		for (Path arquivo : directoryStream) {
			Files.delete(arquivo);
		}
	}
}
