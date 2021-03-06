package br.com.acf.fidelcash.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.EmpresaDto;
import br.com.acf.fidelcash.controller.dto.ImportacaoDto;
import br.com.acf.fidelcash.controller.dto.UtilDtoImplantacao;
import br.com.acf.fidelcash.controller.form.ImplantacaoForm;
import br.com.acf.fidelcash.controller.form.ImportacaoForm;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImplantacaoService;
import br.com.acf.fidelcash.controller.service.CupomFiscalXMLImportacaoService;
import br.com.acf.fidelcash.controller.service.exception.CupomFiscalXMLUploadServiceException;
import br.com.acf.fidelcash.controller.service.exception.UtilServiceException;
import br.com.acf.fidelcash.modelo.Usuario;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;

@RestController
@RequestMapping("/cupomfiscalxml")
public class CupomFiscalXMLController {
	
	@Autowired
	private CupomFiscalXMLImplantacaoService cfImplementa;
	
	@Autowired
	private CupomFiscalXMLImportacaoService cfImporta;	
	
	
//	 @AuthenticationPrincipal Usuario logado,
  //  @RequestParam MultipartFile[] xml 
	
	
	@PostMapping("/implantacao")
	public ResponseEntity<EmpresaDto> implantarFidel(     
			                                   @ModelAttribute ImplantacaoForm form,
			                                   @AuthenticationPrincipal Usuario logado
			                                  )  { 
		try {
			
			BigInteger cnpj = form.getCnpj();
			EmpresaDto empresaDto = cfImplementa.implantarFidelCash(cnpj, logado, form);
			return ResponseEntity.ok(empresaDto);
		} catch (Exception e) {
			EmpresaDto empresaDtoErro = new EmpresaDto(e.getMessage());
			return ResponseEntity.badRequest().body(empresaDtoErro);
		
		}
    }
	
	@PostMapping("/importacao")
	public ResponseEntity<List<ImportacaoDto>> importarXml(@ModelAttribute ImportacaoForm form) {
		try {
			List<ImportacaoDto> importacao =  cfImporta.importarXml(form);
			return ResponseEntity.ok(importacao);
		} catch (IOException | ParserConfigurationException | SAXException | ParseException
				| CupomFiscalXMLException | UtilServiceException | CupomFiscalXMLUploadServiceException  e) {
			List<ImportacaoDto >importacaoErro = new ArrayList<ImportacaoDto>();
			importacaoErro.get(0).setErro("Erro ao importar arquivos XML.");
			return ResponseEntity.badRequest().body(importacaoErro);
		} 
	}
	
	@PostMapping("/importacao2")
	public ResponseEntity<ImportacaoDto> importarXml2(@ModelAttribute ImplantacaoForm form) {
		try {
			ImportacaoDto importacao =  cfImporta.importarXml2(form.getXml());
			return ResponseEntity.ok(importacao);
		} catch (CupomFiscalXMLUploadServiceException  e) {
			ImportacaoDto importacaoErro = new ImportacaoDto();
			importacaoErro.setErro("Erro ao importar arquivos XML.");
			return ResponseEntity.badRequest().body(importacaoErro);
		} 
	}
	
	
	
	

}
