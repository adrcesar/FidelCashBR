package br.com.acf.fidelcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Usuario;
import br.com.acf.fidelcash.modelo.UsuarioPerfil;

public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Integer> {

	@Query("SELECT c FROM UsuarioPerfil c WHERE c.usuario = :usuario and c.perfil.nome = :tipo")
	List<UsuarioPerfil> findPerfilByUsuarioTipo(Usuario usuario, String tipo);

}
