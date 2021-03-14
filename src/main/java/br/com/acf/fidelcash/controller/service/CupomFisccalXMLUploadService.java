package br.com.acf.fidelcash.controller.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.acf.fidelcash.controller.service.exception.CupomFiscalXMLUploadServiceException;


@Service
public class CupomFisccalXMLUploadService {

	@Transactional(rollbackFor = { Exception.class })
	public void salvarArquivos(String raiz, String diretorio, MultipartFile[] XMLs)
			throws CupomFiscalXMLUploadServiceException {
		Arrays.asList(XMLs).stream().forEach(xml -> {

			try {
				this.salvar(raiz, diretorio, xml);
			} catch (CupomFiscalXMLUploadServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	private void salvar(String raiz, String diretorio, MultipartFile arquivo)
			throws CupomFiscalXMLUploadServiceException {
		Path diretorioPath = Paths.get(raiz, diretorio);
		
		int posicaoUltimaBarra = arquivo.getOriginalFilename().lastIndexOf("/");
		String nomeArquivo = "";
		if(posicaoUltimaBarra != -1) {
			nomeArquivo = arquivo.getOriginalFilename().substring(posicaoUltimaBarra + 1 );
		} else {
			nomeArquivo = arquivo.getOriginalFilename();
		}
		 
		
		Path arquivoPath = diretorioPath.resolve(nomeArquivo);
		System.out.println(arquivoPath.toString());

		try {
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());
		} catch (IllegalStateException | IOException e) {
			throw new CupomFiscalXMLUploadServiceException("Erro no upload do arquivo", "Erro no upload do arquivo");
		}

	}

}
