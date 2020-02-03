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

import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImplantacaoService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.controller.service.ProdutoService;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
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

	@Test
	public void implantarEmpresa() throws IOException, CupomFiscalXMLException, EmpresaServiceException, UtilServiceException,
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
			UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(cnpj.toString());
			
			Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
			Optional<Produto> produto = produtoService.findByEmpresaAndCodigoProduto(empresa.get(), "29");
			assertEquals("PRIME PREMIUM MEDIO", produto.get().getDescricao());
	}

}
