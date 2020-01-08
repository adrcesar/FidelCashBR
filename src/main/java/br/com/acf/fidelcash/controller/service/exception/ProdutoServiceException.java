package br.com.acf.fidelcash.controller.service.exception;

public class ProdutoServiceException extends Exception {
	private static final long serialVersionUID = 1L;
    private String mensagem;

    public ProdutoServiceException(String message, String mensagem) {
        super(message);
        this.mensagem = mensagem;
    }
    
    public String getMensagem(){
        return mensagem;
    }
}
