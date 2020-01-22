package br.com.acf.fidelcash.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CampanhaRegras {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
	@JoinColumn(name = "id_campanha", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Campanha campanha;
	
	@JoinColumn(name = "id_cliente", referencedColumnName = "id")
	@ManyToOne(optional = true)
	private Cliente cliente;
	
	@Enumerated(EnumType.STRING)
	private TipoSelecaoCliente tipoSelecaoCliente; 
	
	@JoinColumn(name = "id_produto", referencedColumnName = "id")
	@ManyToOne(optional = true)
	private Produto produto;
	
	@Enumerated(EnumType.STRING)
	private TipoSelecaoProduto tipoSelecaoProduto;

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
		CampanhaRegras other = (CampanhaRegras) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoSelecaoCliente getTipoSelecaoCliente() {
		return tipoSelecaoCliente;
	}

	public void setTipoSelecaoCliente(TipoSelecaoCliente tipoSelecaoCliente) {
		this.tipoSelecaoCliente = tipoSelecaoCliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoSelecaoProduto getTipoSelecaoProduto() {
		return tipoSelecaoProduto;
	}

	public void setTipoSelecaoProduto(TipoSelecaoProduto tipoSelecaoProduto) {
		this.tipoSelecaoProduto = tipoSelecaoProduto;
	}
	
	

}
