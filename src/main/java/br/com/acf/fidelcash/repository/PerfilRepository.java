package br.com.acf.fidelcash.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acf.fidelcash.modelo.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{

	Optional<Perfil> findByNome(String nome);

}
