package br.com.acf.fidelcash.modelo;

import java.util.ArrayList;
import java.util.List;

public class CupomFiscalXML {
	
	private Endereco endereco = new Endereco();
    private Empresa empresa = new Empresa();
    private CupomFiscal cupomFiscal = new CupomFiscal();
    private List<Produto> produtos = new ArrayList<>();
    private List<CupomFiscalItem> itens = new ArrayList<>();
    private Cliente cliente = new Cliente();
	
    public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public CupomFiscal getCupomFiscal() {
		return cupomFiscal;
	}
	public void setCupomFiscal(CupomFiscal cupomFiscal) {
		this.cupomFiscal = cupomFiscal;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public List<CupomFiscalItem> getItens() {
		return itens;
	}
	public void setItens(List<CupomFiscalItem> itens) {
		this.itens = itens;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
    
    
}
