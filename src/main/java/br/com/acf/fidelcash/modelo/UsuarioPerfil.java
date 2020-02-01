package br.com.acf.fidelcash.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

@Entity	
public class UsuarioPerfil implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
	private Usuario usuario;
	
	@JoinColumn(name = "id_perfil", referencedColumnName = "id")
    @ManyToOne
    private Perfil perfil;
	
	@JoinColumn(name = "id_grupo_empresarial", referencedColumnName = "id")
    @ManyToOne
    private GrupoEmpresarial grupoEmpresarial;
	
	@JoinColumn(name = "id_empresa", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
	
	@Enumerated(EnumType.STRING)
	private SituacaoUsuarioPerfil situacao;

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
		UsuarioPerfil other = (UsuarioPerfil) obj;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public GrupoEmpresarial getGrupoEmpresarial() {
		return grupoEmpresarial;
	}

	public void setGrupoEmpresarial(GrupoEmpresarial grupoEmpresarial) {
		this.grupoEmpresarial = grupoEmpresarial;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public SituacaoUsuarioPerfil getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoUsuarioPerfil situacao) {
		this.situacao = situacao;
	}

	@Override
	public String getAuthority() {
		return this.perfil.getNome();
	}

	
	
	
}
