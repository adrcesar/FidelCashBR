package br.com.acf.fidelcash.modelo.exception;

public class CupomFiscalXMLException extends Exception {
	private static final long serialVersionUID = 1L;
    private String mensagem;

    public CupomFiscalXMLException(String message, String mensagem) {
        super(message);
	this.mensagem = mensagem;
    }
    
    public String getMensagem(){
        return mensagem;
    }
}
