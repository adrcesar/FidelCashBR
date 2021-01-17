package br.com.acf.fidelcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
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
import br.com.acf.fidelcash.controller.service.CampanhaPeriodoDeCompraService;
import br.com.acf.fidelcash.controller.service.CampanhaService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImplantacaoService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImportacaoService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.UsuarioService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.controller.service.exception.UsuarioServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaPeriodoDeCompra;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;
import br.com.acf.fidelcash.modelo.Usuario;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
//@Transactional
public class CampanhaClientesPorPeriodoDeCompraTeste {
	
	@Autowired
	private CupomFiscalXMLImplantacaoService cfImplementa;

	@Autowired
	private CupomFiscalXMLImportacaoService cfImporta;

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private CampanhaService campanhaService;

	@Autowired
	private CampanhaPeriodoDeCompraService periodoDeCompraService;

	@Autowired
	private UsuarioService usuarioService;
	
	@BeforeEach
	public void setup() throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException,
			ParserConfigurationException, SAXException, ParseException {

		
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
			UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(cnpj);

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
			//importar
			@SuppressWarnings("unused")
			List<ImportacaoDto> importacao = new ArrayList<ImportacaoDto>();
			importacao = cfImporta.importarXml();

			

	}
	
	@Test
	public void criarCampanhaPeriodoTeste() throws PeriodoDeCompraServiceException, UsuarioServiceException {
		//CAMPANHA
		Campanha campanhaPai = new Campanha();
		campanhaPai.setDescricao("CAMPANHA BONUS POR PERIODO DE COMPRAS");
		campanhaPai.setDataInicio(LocalDateTime.parse("2018-09-01T00:00:00"));
		campanhaPai.setDataFim(LocalDateTime.parse("2018-09-30T23:59:59"));
		
		BigInteger cnpj = new BigInteger("99999999999999");
		Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
		campanhaPai.setEmpresa(empresa.get());
		
		
		//PERIDO DE BUSCA DOS CUPONS FISCAIS
		LocalDateTime dataFinal = LocalDateTime.parse("2018-08-31T23:59:59"); 
		List<Integer> periodo = new ArrayList<Integer>();
		periodo.add(8);
		periodo.add(8);
		periodo.add(8);
		periodo.add(30);
		List<Float> bonus = new ArrayList<Float>();
		bonus.add(2f);
		bonus.add(7.5f);
		bonus.add(10f);
		bonus.add(30f);
		
		CampanhaPeriodoDeCompra campanhaPeriodoDeCompra =  new CampanhaPeriodoDeCompra(campanhaPai, dataFinal, periodo, bonus);
		
		Optional<Usuario> usuario = usuarioService.findByUsuario("castro");
		
		periodoDeCompraService.SetPeriodoCampanha(campanhaPeriodoDeCompra, usuario.get());
		// validacoes
		List<Campanha> campanhas = campanhaService.findAllByCampanhaPai(campanhaPai);
		
		BigInteger cpf = new BigInteger("16368579811");
		List<CampanhaRegras> regras = periodoDeCompraService.findAllByCampanhaAndClienteCpf(campanhas.get(0), cpf);
		assertFalse(regras.isEmpty());
		regras = periodoDeCompraService.findAllByCampanhaAndClienteCpf(campanhas.get(3), cpf);
		assertTrue(regras.isEmpty());
		assertEquals("CAMPANHA BONUS POR PERIODO DE COMPRAS", campanhaPai.getDescricao());
		
	}


}
