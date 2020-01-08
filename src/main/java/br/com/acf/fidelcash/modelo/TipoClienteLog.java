package br.com.acf.fidelcash.modelo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TipoClienteLog {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JoinColumn(name = "id_tipo_cliente", referencedColumnName = "id")
	@ManyToOne
	private TipoCliente tipoCliente;
	
	private float bonus;
    private LocalDateTime data_inicio;
    private LocalDateTime data_fim;
	
    public TipoClienteLog() {
		
	}

	public TipoClienteLog(Integer id) {
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
		TipoClienteLog other = (TipoClienteLog) obj;
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

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public float getBonus() {
		return bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public LocalDateTime getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(LocalDateTime data_inicio) {
		this.data_inicio = data_inicio;
	}

	public LocalDateTime getData_fim() {
		return data_fim;
	}

	public void setData_fim(LocalDateTime data_fim) {
		this.data_fim = data_fim;
	}

	
	
	
    
    

}
