package br.com.acf.fidelcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoSelecaoCliente;
import br.com.acf.fidelcash.modelo.TipoSelecaoProduto;

public interface CampanhaRegrasRepository extends JpaRepository<CampanhaRegras, Integer>{

	List<CampanhaRegras> findAllByCampanha(Campanha campanha);

	@Query("SELECT c FROM CampanhaRegras c WHERE c.campanha = :campanha AND (c.cliente = :cliente or c.tipoSelecaoCliente = :todos)")
	List<CampanhaRegras> findAllByCampanhaCliente(Campanha campanha, Cliente cliente, TipoSelecaoCliente todos);

	@Query("SELECT c FROM CampanhaRegras c WHERE c.id = :id AND (c.produto = :produto or c.tipoSelecaoProduto = :todos)")
	List<CampanhaRegras> findAllByIdProduto(Integer id, Produto produto, TipoSelecaoProduto todos);

}
