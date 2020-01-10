package br.com.acf.fidelcash.controller.service.exception;

public class ContaCorrenteServiceException extends Exception {
	private static final long serialVersionUID = 1L;
    private String mensagem;

    public ContaCorrenteServiceException(String message, String mensagem) {
        super(message);
        this.mensagem = mensagem;
    }
    
    public String getMensagem(){
        return mensagem;
    }
}
