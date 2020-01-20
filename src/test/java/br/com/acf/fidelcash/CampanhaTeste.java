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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import br.com.acf.fidelcash.controller.service.PeriodoDeCompraService;
import br.com.acf.fidelcash.controller.service.ProdutoService;
import br.com.acf.fidelcash.controller.service.TipoClienteLogService;
import br.com.acf.fidelcash.controller.service.TipoClienteService;
import br.com.acf.fidelcash.controller.service.UtilService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CampanhaTeste {
	
	public static boolean testeConfigurado = false;
	public static int numeroDeTestes = 0;
	
	@Autowired
	private CupomFiscalXMLImplantacaoService cfImplementa;

	@Autowired
	private CupomFiscalXMLImportacaoService cfImporta;

	@Autowired
	private EmpresaService empresaService;

	

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
	
	@Autowired
	private ContaCorrenteService ccService;
	
	@Autowired
	private PeriodoDeCompraService periodoDeCompraService;

	
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
			List<ImportacaoDto> importacao = new ArrayList<ImportacaoDto>();
			importacao = cfImporta.importarXml();

			testeConfigurado = true;
		}

	}
	
	@AfterEach
	public void LimparBaseDados() {
		if(numeroDeTestes == 2) {
			utilService.deleteByEmpresaIsNull();
			cfItemService.deleteAll();
			ccService.deleteAll();
			cfService.deleteAll();
			clienteService.deleteAll();
			tipoClienteLogService.deleteAll();
			tipoClienteService.deleteAll();
			produtoService.deleteAll();
			empresaService.deleteAll();
			grupoEmpresarialService.deleteAll();
			enderecoService.deleteAll();
		}
	}
	
	@Test
	public void selecionarClientesCampanha() throws PeriodoDeCompraServiceException {
		LocalDateTime dataFinal = LocalDateTime.parse("2018-08-31T23:59:59"); 
		Map<Integer, Integer> mapPeriodos = new HashMap<>();
		mapPeriodos.put(1, 7);
		mapPeriodos.put(2, 7);
		mapPeriodos.put(3, 7);
		mapPeriodos.put(4, 9);
		mapPeriodos.put(5, 60);
		
		Map<Integer, List<Cliente>> mapClientesPorPeriodo = new HashMap<>();		
		mapClientesPorPeriodo = periodoDeCompraService.selecionarClientes(dataFinal, mapPeriodos);
		int numeroClientes = 0;
		for (Map.Entry<Integer, List<Cliente>> periodo : mapClientesPorPeriodo.entrySet()) {
			if (periodo.getKey() == 1) {
				numeroClientes = 7;
			} else if(periodo.getKey() == 2) {
				numeroClientes = 9;
			} else if(periodo.getKey() == 3) {
				numeroClientes = 3;
			}else if(periodo.getKey() == 4) {
				numeroClientes = 11;
			}else if(periodo.getKey() == 5) {
				numeroClientes = 1;
			}
			List<Cliente> clientes = periodo.getValue();
			assertEquals(numeroClientes, clientes.size());
		}
	}


}
