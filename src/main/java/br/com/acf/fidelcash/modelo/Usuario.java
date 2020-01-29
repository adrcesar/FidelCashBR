package br.com.acf.fidelcash.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String usuario;
	private String nome;
	private String email;
	private String senha;
	@Enumerated(EnumType.STRING)
	private SituacaoUsuario situacao;

	@JoinColumn(name = "id_usuario")
	@OneToMany(fetch = FetchType.EAGER)
	private List<UsuarioPerfil> perfis = new ArrayList<UsuarioPerfil>();
	
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
		Usuario other = (Usuario) obj;
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public SituacaoUsuario getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoUsuario situacao) {
		this.situacao = situacao;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.usuario;
	}
	@Override
	public boolean isAccountNonExpired() {
		if(this.situacao.equals(SituacaoUsuario.ATIVO)) {
			return true;
		}
		return false;
		
	}
	@Override
	public boolean isAccountNonLocked() {
		if(this.situacao.equals(SituacaoUsuario.ATIVO)) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		if(this.situacao.equals(SituacaoUsuario.ATIVO)) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isEnabled() {
		if(this.situacao.equals(SituacaoUsuario.ATIVO)) {
			return true;
		}
		return false;
	}

}
