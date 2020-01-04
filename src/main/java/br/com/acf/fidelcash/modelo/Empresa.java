package br.com.acf.fidelcash.modelo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Empresa {
	
	private static final long serialVersionUID = 1L;
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigInteger cnpj;
    private String nomeRazao;
    private String nomeFantasia;
    @Enumerated(EnumType.STRING)
    private SituacaoEmpresa situacao;
    
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    @ManyToOne
    private Endereco endereco;
    
    @JoinColumn(name = "id_grupo_empresarial", referencedColumnName = "id")
    @ManyToOne
    private GrupoEmpresarial grupoEmpresarial;

    public Empresa() {
    }

    public Empresa(Integer id) {
        this.id = id;
    }

    public Empresa(Integer id, BigInteger cnpj, String nomeRazao, String nomeFantasia) {
        this.id = id;
        this.cnpj = cnpj;
        this.nomeRazao = nomeRazao;
        this.nomeFantasia = nomeFantasia;
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
		Empresa other = (Empresa) obj;
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

	public BigInteger getCnpj() {
		return cnpj;
	}

	public void setCnpj(BigInteger cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeRazao() {
		return nomeRazao;
	}

	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public SituacaoEmpresa getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoEmpresa situacao) {
		this.situacao = situacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public GrupoEmpresarial getGrupoEmpresarial() {
		return grupoEmpresarial;
	}

	public void setGrupoEmpresarial(GrupoEmpresarial grupoEmpresarial) {
		this.grupoEmpresarial = grupoEmpresarial;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
