package br.com.acf.fidelcash.modelo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ContaCorrente {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
	
	@JoinColumn(name = "id_cupom_fiscal_item", referencedColumnName = "id")
	@ManyToOne
	private CupomFiscalItem cupomFiscalItem;
	
	private float credito;
	private float debito;
	private float saldo;
	
	@JoinColumn(name = "id_tipo_cliente_log", referencedColumnName = "id")
	@ManyToOne
	private TipoClienteLog tipoClienteLog;
	
	public ContaCorrente() {
		
	}
	
	public ContaCorrente(BigInteger id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaCorrente other = (ContaCorrente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public CupomFiscalItem getCupomFiscalItem() {
		return cupomFiscalItem;
	}

	public void setCupomFiscalItem(CupomFiscalItem cupomFiscalItem) {
		this.cupomFiscalItem = cupomFiscalItem;
	}

	public float getCredito() {
		return credito;
	}

	public void setCredito(float credito) {
		this.credito = credito;
	}

	public float getDebito() {
		return debito;
	}

	public void setDebito(float debito) {
		this.debito = debito;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public TipoClienteLog getTipoClienteLog() {
		return tipoClienteLog;
	}

	public void setTipoClienteLog(TipoClienteLog tipoClienteLog) {
		this.tipoClienteLog = tipoClienteLog;
	}
	
	

	
	
	
	
	
}
