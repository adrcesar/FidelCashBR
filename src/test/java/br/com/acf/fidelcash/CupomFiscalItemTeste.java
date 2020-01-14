package br.com.acf.fidelcash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.ImportacaoDto;
import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.service.ClienteService;
import br.com.acf.fidelcash.controller.service.ContaCorrenteService;
import br.com.acf.fidelcash.controller.service.CupomFiscalItemService;
import br.com.acf.fidelcash.controller.service.CupomFiscalService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImplantacaoService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImportacaoService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.EnderecoService;
import br.com.acf.fidelcash.controller.service.GrupoEmpresarialService;
import br.com.acf.fidelcash.controller.service.ProdutoService;
import br.com.acf.fidelcash.controller.service.TipoClienteLogService;
import br.com.acf.fidelcash.controller.service.TipoClienteService;
import br.com.acf.fidelcash.controller.service.UtilService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CupomFiscalItemTeste {

	public static boolean testeConfigurado = false;

	@Autowired
	private CupomFiscalXMLImplantacaoService cfImplementa;

	@Autowired
	private CupomFiscalXMLImportacaoService cfImporta;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ContaCorrenteService ccService;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private CupomFiscalItemService cfItemService;
	
	@Autowired
	private CupomFiscalService cfService;
	
	@Autowired
	private TipoClienteLogService tipoClienteLogService;
	
	@Autowired
	private TipoClienteService tipoClienteService;
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private GrupoEmpresarialService grupoEmpresarialService;
	
	@Autowired
	private EnderecoService enderecoService;
	

	@BeforeEach
	public void setup() throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException,
			ParserConfigurationException, SAXException, ParseException {

		if (!testeConfigurado) {
			// move arquivos para a pasta de de upload da implantacao

			Path dir = Paths.get("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao");
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
			String diretorioDestino = "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\implantacao\\upload";
			for (Path arquivoOrigem : directoryStream) {
				String stringArquivoDestino = diretorioDestino + "\\" + arquivoOrigem.getFileName();
				Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
				Files.copy(arquivoOrigem, arquivoDestino, StandardCopyOption.REPLACE_EXISTING);

			}
			// implantar
			BigInteger cnpj = new BigInteger("99999999999999");
			@SuppressWarnings("unused")
			UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(cnpj.toString());

			// move arquivos para a pasta de de upload da importacao
			dir = Paths.get("C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\importacao");
			directoryStream = Files.newDirectoryStream(dir, "*.xml*");
			diretorioDestino = "C:\\Projetos\\fidelcash\\arquivos-xml\\99999999999999\\importacao\\upload";
			for (Path arquivoOrigem : directoryStream) {
				String stringArquivoDestino = diretorioDestino + "\\" + arquivoOrigem.getFileName();
				Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
				Files.copy(arquivoOrigem, arquivoDestino, StandardCopyOption.REPLACE_EXISTING);
			}

			Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
			empresa.get().setSituacao(SituacaoEmpresa.ATIVA);

			empresaService.save(empresa.get());

			@SuppressWarnings("unused")
			List<ImportacaoDto> importacao = cfImporta.importarXml();

			testeConfigurado = true;
		}

	}
	
	@AfterEach
	public void LimparBaseDados() {
		utilService.deleteByEmpresaIsNull();
		ccService.deleteAll();
		cfItemService.deleteAll();
		cfService.deleteAll();
		clienteService.deleteAll();
		tipoClienteLogService.deleteAll();
		tipoClienteService.deleteAll();
		produtoService.deleteAll();
		empresaService.deleteAll();
		grupoEmpresarialService.deleteAll();
		enderecoService.deleteAll();
	}
	

	@Test
	public void extratoClienteTeste() {

		BigInteger cpf = new BigInteger("16368579811");

		Optional<Cliente> cliente = clienteService.findByCpf(cpf);
		float saldo = 0;

		if (cliente.isPresent()) {
			saldo = ccService.getSaldoCliente(cliente.get());
		}

		System.out.println(saldo);
		assertEquals(-5.223, saldo, 0.0001);
	}

}
