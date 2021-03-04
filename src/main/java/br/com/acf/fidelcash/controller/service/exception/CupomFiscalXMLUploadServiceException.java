package br.com.acf.fidelcash.controller.service.exception;

public class CupomFiscalXMLUploadServiceException extends Exception{
	
	private static final long serialVersionUID = 1L;
    private String mensagem;

    public CupomFiscalXMLUploadServiceException(String message, String mensagem) {
        super(message);
        this.mensagem = mensagem;
    }
    
    public String getMensagem(){
        return mensagem;
    }

}



