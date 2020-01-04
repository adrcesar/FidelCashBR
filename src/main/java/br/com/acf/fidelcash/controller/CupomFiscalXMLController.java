package br.com.acf.fidelcash.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImplantacaoService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImportacaoService;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@RestController
@RequestMapping("/cupomfiscalxml")
public class CupomFiscalXMLController {
	
	@Autowired
	private CupomFiscalXMLImplantacaoService cfImplementa;
	
	@Autowired
	private CupomFiscalXMLImportacaoService cfImporta;	
	
	@PostMapping("/implantacao/{implantacaoEmpresa}")
	public ResponseEntity<UtilDtoImplantacao> implantarFidel(@PathVariable String implantacaoEmpresa) { // poderia retornar ResponseEntity<String>
		try {
			UtilDtoImplantacao utilDto = cfImplementa.implantarFidelCash(implantacaoEmpresa);
			if(utilDto.isErro()) {
				return ResponseEntity.badRequest().body(utilDto);
			} else {
				return ResponseEntity.ok(utilDto);
			}
		} catch (Exception e) {
			UtilDtoImplantacao utilDtoErro = new UtilDtoImplantacao();
			utilDtoErro.setErro(true);
			utilDtoErro.setErroMensagem(e.getMessage());
			return ResponseEntity.badRequest().body(utilDtoErro);
		
		}
    }
	
	@PostMapping("/importacao")
	public String importarXml() {
		String retorno = "";
			try {
				retorno = cfImporta.importarXml();
			} catch (IOException | ParserConfigurationException | SAXException | ParseException
					| CupomFiscalXMLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return retorno;
	}
	
	
	
	

}
