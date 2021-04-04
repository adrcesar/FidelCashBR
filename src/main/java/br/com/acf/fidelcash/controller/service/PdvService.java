package br.com.acf.fidelcash.controller.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import br.com.acf.fidelcash.modelo.Pdv;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;
import br.com.acf.fidelcash.repository.PdvRepository;

@Service
public class PdvService {
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private PdvRepository pdvRepository;
	
	@Transactional(rollbackFor = { Exception.class })
	public void save(Pdv pdv) {
		pdvRepository.save(pdv);
	}

	public Optional<Pdv> findById(Integer id) {
		return pdvRepository.findById(id);
	}

	public Optional<Pdv> findByMacAdress(String macAddress) {
		return pdvRepository.findByMacAddress(macAddress);
	}
	
	public void criaXmlDeConfiguracaoDoPdv(Pdv pdv) throws IOException, TransformerException, CupomFiscalXMLException {
		final String xmlStr = "<service>" +
							 "   <id>scheduling-tasks-service</id>" +
				             "   <name>scheduling-tasks-service</name>" +
							 "   <description>Upload de cupons fiscais</description>" +
				             "   <env value=\"" + pdv.getPastaDeUpload() + "\" name=\"HOME\"/>" +
							 "   <executable>java</executable>" +
				          //   "   <arguments>-Xrs -Xmx256m -jar \"C:\\Users\\Usuário\\Desktop\\TesteUploadXML\\scheduling-tasks-0.0.1-SNAPSHOT.jar\"</arguments>"  +
			                 "   <arguments>-Xrs -Xmx256m -jar \"" + pdv.getPastaDeUpload() +             "\\scheduling-tasks-0.0.1-SNAPSHOT.jar\"</arguments>"  +
							 "   <logmode>rotate</logmode>" +
				             "</service>"
							 ; 
		//Use method to convert XML string content to XML Document object
		System.out.println(xmlStr);
        Document doc = convertStringToXMLDocument( xmlStr );
        
        Optional<Util> diretorioPDV = utilService.findByEmpresaAndUtilidade(pdv.getEmpresa(), "pdv_empresa");
        criarDiretorioPdvEmpresa(diretorioPDV.get().getPasta(), pdv);
        
        DOMSource source = new DOMSource(doc);
        
        Optional<Util> utilPdv = utilService.findByEmpresaAndUtilidade(pdv.getEmpresa(), "pdv_"+pdv.getId().toString());
        
        FileWriter writer = new FileWriter(new File(utilPdv.get().getPasta() + "\\scheduling-tasks.xml"));
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }
	
	private void criarDiretorioPdvEmpresa(String pasta, Pdv pdv) throws CupomFiscalXMLException {
		String pastaPdvEmpresa = pasta + "\\" + pdv.getId().toString();
		Path pdvEmpresa = FileSystems.getDefault().getPath(pastaPdvEmpresa);
		criarDiretorio(pastaPdvEmpresa, pdvEmpresa);
		
		utilService.criarUtilidadeComEmpresa(pastaPdvEmpresa, "pdv_"+pdv.getId().toString(), "ARMAZENA ARQUIVOS DE CONFIGURACAO ESPECÍFICOS DO PDV DA EMPRESA", pdv.getEmpresa());
	}
	
	private void criarDiretorio(String pastaString, Path pastaPath) throws CupomFiscalXMLException {
		if (!Files.exists(pastaPath)) {
			File file = new File(pastaString);
			boolean bool = file.mkdirs();
			if (!bool) {
				throw new CupomFiscalXMLException(pastaString + " nao pode ser criada" , pastaString + " nao pode ser criada" );
			} 
		}
	}
	
	private static Document convertStringToXMLDocument(String xmlString) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

	public void copiarArquivosDePdvGeralParaPdvEmpresa(Pdv pdv) throws IOException {
		Optional<Util> util = utilService.findByEmpresaAndUtilidade(null, "pdv");
		Optional<Util> utilEmpresa = utilService.findByEmpresaAndUtilidade(pdv.getEmpresa(), "pdv_"+pdv.getId().toString());
		
		Path dir = Paths.get(util.get().getPasta());
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.*");
		
		String stringArquivoDestino;
		Path arquivoDestino;
		for (Path arquivo : directoryStream) {
			stringArquivoDestino = utilEmpresa.get().getPasta()  + "\\" + arquivo.getFileName();
			arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
			Files.copy(arquivo, arquivoDestino, StandardCopyOption.REPLACE_EXISTING);
			
			
		}
		
	}
				            
	




	

}
