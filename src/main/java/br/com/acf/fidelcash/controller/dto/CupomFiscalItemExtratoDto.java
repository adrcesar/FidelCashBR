package br.com.acf.fidelcash.controller.dto;

import java.time.LocalDateTime;

import br.com.acf.fidelcash.modelo.CupomFiscalItem;

public class CupomFiscalItemExtratoDto {
	
	private String nomeFantasiaEmpresa;
	private String nomeCliente;
	private int codigoCupom;
	private LocalDateTime dataCompra; 
	private String produto;
	private float quantidade;
	private float valorProduto;
	private float valorItem;
	private float credito;
	private float debito;
	private float saldoLancamento;
	private static float saldoGeral;
	
	public CupomFiscalItemExtratoDto(CupomFiscalItem cfItem) {
		this.nomeFantasiaEmpresa = cfItem.getCupomFiscal().getCliente().getTipoCliente().getEmpresa().getNomeFantasia();
		this.nomeCliente = cfItem.getCupomFiscal().getCliente().getNome();
		this.codigoCupom = cfItem.getCupomFiscal().getCodigoCupom();
		this.dataCompra = cfItem.getCupomFiscal().getDataCompra();
		this.produto = cfItem.getProduto().getDescricao();
		this.quantidade = cfItem.getQuantidade();
		this.valorProduto = cfItem.getValorProduto();
		this.valorItem = cfItem.getValorItem();
		this.credito = 0;
		this.debito = 0;
		this.saldoLancamento = 0;
	}

	public String getNomeFantasiaEmpresa() {
		return nomeFantasiaEmpresa;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public String getProduto() {
		return produto;
	}

	public float getQuantidade() {
		return quantidade;
	}

	public float getValorProduto() {
		return valorProduto;
	}

	public float getValorItem() {
		return valorItem;
	}

	public float getCredito() {
		return credito;
	}

	public float getDebito() {
		return debito;
	}

	public float getSaldoLancamento() {
		return saldoLancamento;
	}

	public static float getSaldoGeral() {
		return saldoGeral;
	}

	public void gerarCredito(float bonusValor, float saldo) {
		this.credito = bonusValor;
		this.saldoLancamento = saldo;
		saldoGeral = this.saldoLancamento;
	}

	public void gerarDebito(float valorDesconto, float saldo) {
		this.debito = valorDesconto;
		this.saldoLancamento = saldo;
		saldoGeral = this.saldoLancamento;
	}

	public int getCodigoCupom() {
		return codigoCupom;
	}

	public LocalDateTime getDataCompra() {
		return dataCompra;
	}
	
	
	
	
}
