package br.com.acf.fidelcash.controller.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.ImportacaoDto;
import br.com.acf.fidelcash.controller.service.exception.ClienteServiceException;
import br.com.acf.fidelcash.controller.service.exception.CupomFiscalItemServiceException;
import br.com.acf.fidelcash.controller.service.exception.CupomFiscalServiceException;
import br.com.acf.fidelcash.controller.service.exception.CupomFiscalXMLUploadServiceException;
import br.com.acf.fidelcash.controller.service.exception.EmpresaServiceException;
import br.com.acf.fidelcash.controller.service.exception.TipoClienteServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.CupomFiscalXML;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@Service
public class CupomFiscalXMLImportacaoService {

	@Autowired
	private UtilService utilService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private TipoClienteService tipoClienteService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private CupomFiscalService cfService;

	@Autowired
	private CupomFiscalItemService cfItemService;
	
	@Autowired
	private ContaCorrenteService ccService;

	@Autowired
	private CupomFiscalXMLService cfXMLService;
	
	@Autowired
	private CupomFisccalXMLUploadService uploadService;

	
	
	
	
	
	public ImportacaoDto importarXml2(MultipartFile[] xml) throws CupomFiscalXMLUploadServiceException {
		Optional<Util> diretorioPadrao = utilService.findByEmpresaAndUtilidade(null, "DIRETORIO_PADRAO");
		String diretorioRaiz = diretorioPadrao.get().getPasta();    
		String upload = "upload";
		uploadService.salvarArquivos(diretorioRaiz, upload, xml);
		
		List<String> arquivos = new ArrayList<String>();
		arquivos.add("teste de arquivo");
		ImportacaoDto importacaoDto = new ImportacaoDto(diretorioRaiz, arquivos);
		
		
		return importacaoDto;
	}

	@Transactional(rollbackFor = { Exception.class })
	public List<ImportacaoDto> importarXml(MultipartFile[] XMLs) throws IOException, ParserConfigurationException, SAXException,
			ParseException, CupomFiscalXMLException, UtilServiceException, CupomFiscalXMLUploadServiceException {
		try {
			
			Optional<Util> diretorioPadrao = utilService.findByEmpresaAndUtilidade(null, "DIRETORIO_PADRAO");
			String diretorioRaiz = diretorioPadrao.get().getPasta();    
			String upload = "upload";
			uploadService.salvarArquivos(diretorioRaiz, upload, XMLs);
			
			//
			List<Util> pastas = utilService.getPastasImportacaoXML("upload_import_xml");
			Path dir = Paths.get(diretorioRaiz.concat("/upload"));
			DirectoryStream<Path> directoryUploadGeral = Files.newDirectoryStream(dir, "*.xml*");
			
			for (Path arquivo : directoryUploadGeral) {
				for (int i = 0; i < pastas.size(); i++) {
					String xml = arquivo.toString();
					CupomFiscalXML cfXML = cfXMLService.GerarDadosByXml(xml);
					Empresa empresaXML = cfXML.getEmpresa();
					
					if(empresaXML.getCnpj().compareTo(pastas.get(i).getEmpresa().getCnpj()) == 0) {
						String stringArquivoDestino = pastas.get(i).getPasta() + arquivo.getFileName();
						Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
						Files.move(arquivo, arquivoDestino, StandardCopyOption.ATOMIC_MOVE);
						
					}
					
				}
			}
			directoryUploadGeral.close();
			
			//String xml = arquivo.toString();
			//CupomFiscalXML cfXML = cfXMLService.GerarDadosByXml(xml);
			
			//
			
			//List<Util> pastas = utilService.getPastasImportacaoXML("upload_import_xml");
			
			List<ImportacaoDto> importacaoDto = new ArrayList<ImportacaoDto>();
			for (int i = 0; i < pastas.size(); i++) {
				
				
				Map<Path, String> mapArquivos = importarXml(pastas.get(i).getPasta(), pastas.get(i).getEmpresa());
				List<String> arquivos = movimentacaoDeArquivos(mapArquivos, pastas.get(i).getEmpresa());
				Collections.sort(arquivos);
				ImportacaoDto importacaoEmpresaDto = setImportacaoDto(pastas.get(i).getPasta(), arquivos);
				importacaoDto.add(importacaoEmpresaDto);
			}
			return importacaoDto;
		} catch (IOException | ParseException | ParserConfigurationException | SAXException ex) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}

	private Map<Path, String> importarXml(String directory, Empresa empresaUtil)
			throws CupomFiscalXMLException, IOException {
		try {
			Path arquivoException;
			Map<Path, String> mapArquivo = new HashMap<>();
			Path dir = Paths.get(directory);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
			for (Path arquivo : directoryStream) {
				arquivoException = arquivo;
				try {
					GerarDadosByXML(arquivo, empresaUtil);
					mapArquivo.put(arquivo, "importados_xml");
				} catch (CupomFiscalXMLException ex) {
					if (ex.getMensagem().equals("Empresa do arquivo xml nao cadastrada no sistema")) {
						mapArquivo.put(arquivoException, "empresa_xml_nao_cadastrada_sistema");
					} else if (ex.getMensagem().equals("Empresa Inativa")) {
						mapArquivo.put(arquivoException, "empresa_inativa");
					} else if (ex.getMensagem().equals("Tipo de Cliente PADRAO inexistente")) {
						mapArquivo.put(arquivoException, "tipo_cliente_inexistente");
					} else if (ex.getMensagem().equals("Situacao do Tipo de Cliente PADRAO Inativa")) {
						mapArquivo.put(arquivoException, "tipo_cliente_padrao_inativo");
					} else if (ex.getMensagem().equals("Bonus do Tipo de Cliente PADRAO inferior A 1%")) {
						mapArquivo.put(arquivoException, "bonus_menor_que_1%");
					} else if (ex.getMensagem().equals("Erro no cadastro de cliente")) {
						mapArquivo.put(arquivoException, "erro_cadastro_cliente");
					} else if (ex.getMensagem()
							.equals("CNPJ da empresa do XML diferente do CNPJ cadastrado na tabela Util")) {
						mapArquivo.put(arquivoException, "cnpj_util_diferente_cnpj_xml");
					} else if (ex.getMensagem().equals("Arquivo Sem Cliente")) {
						mapArquivo.put(arquivoException, "sem_cpf_import_xml");
					} else if (ex.getMensagem().equals("registro duplicado")) {
						mapArquivo.put(arquivoException, "registro_duplicado_import_xml");
					} else if (ex.getMensagem().equals("Erro ao gerar cupom fiscal")) {
						mapArquivo.put(arquivoException, "erro_cupom_fiscal");
					} else if (ex.getMensagem().equals("Erro Item do Cupom Fiscal")) {
						mapArquivo.put(arquivoException, "erro_item_cupom_fiscal");
					} else if (ex.getMensagem().equals("Data do cupom invalida")) {
						mapArquivo.put(arquivoException, "data_cupom_invalida");
					} else if (ex.getMensagem().equals("Bonus do período inferior a 1%")) {
						mapArquivo.put(arquivoException, "bonus_inferior_1_porcento");
					}else {
						mapArquivo.put(arquivoException, "erro_desconhecido");
					}
				}
			}
			directoryStream.close();
			return mapArquivo;
		} catch (Exception e) {
			throw new CupomFiscalXMLException("Erro ao abrir pasta de upload dos xml",
					"Erro ao abrir pasta de upload dos xml");
		}
	}

	private List<String> movimentacaoDeArquivos(Map<Path, String> mapArquivos, Empresa empresa)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		List<String> arquivos = new ArrayList<String>();
		for (Map.Entry<Path, String> entry : mapArquivos.entrySet()) {
			try {
				String arquivo = moverArquivos(entry.getKey(), entry.getValue(), empresa);
				arquivos.add(arquivo);
			} catch (IOException e) {
				throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
			}
		}
		return arquivos;
	}

	private String moverArquivos(Path arquivo, String operacao, Empresa empresa) throws FileNotFoundException,
			IOException, CupomFiscalXMLException, ParseException, ParserConfigurationException, SAXException {
		String oper;
		if ((operacao.equals("importados_xml")) || (operacao.equals("sem_cpf_import_xml"))
				|| (operacao.equals("registro_duplicado_import_xml"))) {
			oper = operacao;
		} else {
			oper = "erro_import_xml";
		}
		Util pastaOperacao = utilService.getPastaUtilidadeXML(empresa, oper);
		String stringArquivoDestino;
		if (oper.equals("erro_import_xml")) {
			stringArquivoDestino = pastaOperacao.getPasta() + operacao + "_" + arquivo.getFileName();
		} else {
			stringArquivoDestino = pastaOperacao.getPasta() + arquivo.getFileName();
		}
		Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
		try {
			if ((operacao.equals("importados_xml")) || (operacao.equals("sem_cpf_import_xml"))
					|| (operacao.equals("registro_duplicado_import_xml"))) {
				Files.move(arquivo, arquivoDestino, StandardCopyOption.ATOMIC_MOVE);
			} else {
				Files.copy(arquivo, arquivoDestino, StandardCopyOption.REPLACE_EXISTING);
			}
			return arquivoDestino.toString();

		} catch (IOException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}

	private ImportacaoDto setImportacaoDto(String pastaOrigem, List<String> arquivos) {
		ImportacaoDto importacaoDto = new ImportacaoDto(pastaOrigem, arquivos);
		return importacaoDto;
	}

	private void GerarDadosByXML(Path arquivo, Empresa empresaUtil) throws CupomFiscalXMLException {
		try {
			String xml = arquivo.toString();
			CupomFiscalXML cfXML = cfXMLService.GerarDadosByXml(xml);
			Empresa empresaXML = cfXML.getEmpresa();

			List<Produto> produtos = cfXML.getProdutos();
			CupomFiscal cupomFiscal = cfXML.getCupomFiscal();
			
			//int posicao = xml.lastIndexOf("\\");
			//String teste = xml.substring(posicao);
			cupomFiscal.setArquivo(xml.substring(xml.lastIndexOf("\\") + 1));
			
			List<CupomFiscalItem> itens = cfXML.getItens();
			Cliente cliente = cfXML.getCliente();

			Empresa emp = empresaService.validaImportacaoEmpresa(empresaXML, empresaUtil);
			TipoCliente tipoCliente = tipoClienteService.validaImportacaoTipoCliente(emp, "PADRAO");
			cliente = clienteService.validaImportacaoCliente(cliente, tipoCliente);
			ContaCorrente cc = ccService.setContaCorrente(cliente);
			cupomFiscal = cfService.setCupomFiscal(cupomFiscal, cliente);
			cfItemService.setCupomFiscalItem(produtos, itens, cupomFiscal, cc);
			

		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		} catch (EmpresaServiceException e) {
			throw new CupomFiscalXMLException(e.getMensagem(), e.getMensagem());
		} catch (TipoClienteServiceException e) {
			throw new CupomFiscalXMLException(e.getMensagem(), e.getMensagem());
		} catch (ClienteServiceException e) {
			throw new CupomFiscalXMLException(e.getMensagem(), e.getMensagem());
		} catch (CupomFiscalServiceException e) {
			throw new CupomFiscalXMLException(e.getMensagem(), e.getMensagem());
		} catch (CupomFiscalItemServiceException e) {
			throw new CupomFiscalXMLException(e.getMensagem(), e.getMensagem());
		} //catch (ContaCorrenteServiceException e) {
			//throw new CupomFiscalXMLException(e.getMensagem(), e.getMensagem());
		//}
	}

	
}
