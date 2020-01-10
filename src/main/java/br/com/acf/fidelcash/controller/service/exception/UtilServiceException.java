package br.com.acf.fidelcash.controller.service.exception;

public class UtilServiceException extends Exception {
	private static final long serialVersionUID = 1L;
    private String mensagem;

    public UtilServiceException(String message, String mensagem) {
        super(message);
        this.mensagem = mensagem;
    }
    
    public String getMensagem(){
        return mensagem;
    }
}
