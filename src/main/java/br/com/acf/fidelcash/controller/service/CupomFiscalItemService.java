package br.com.acf.fidelcash.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.CupomFiscalItemServiceException;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.repository.CupomFiscalItemRepository;

@Service
public class CupomFiscalItemService {

	@Autowired
	private CupomFiscalItemRepository cfItemRepository;
	
	@Autowired
	private ProdutoService produtoService;

	public List<CupomFiscalItem> setCupomFiscalItem(List<Produto> produtos, List<CupomFiscalItem> itens, CupomFiscal cupomFiscal) throws CupomFiscalItemServiceException {
		try {
			List<CupomFiscalItem> cfItens =  new ArrayList<CupomFiscalItem>();
			for (int i = 0; i < produtos.size(); i++) {
				Produto prod = produtoService.setProduto(produtos.get(i), cupomFiscal.getCliente().getTipoCliente().getEmpresa());
				CupomFiscalItem cupomFiscalItem;
				cupomFiscalItem = itens.get(i);
				cupomFiscalItem.setCupomFiscal(cupomFiscal);
				cupomFiscalItem.setProduto(prod);
				cfItemRepository.save(cupomFiscalItem);
				cfItens.add(cupomFiscalItem);
			}
			return cfItens;
		} catch (Exception  e) {
			throw new CupomFiscalItemServiceException("Erro Item do Cupom Fiscal", "Erro Item do Cupom Fiscal");
		}
	}

	public void deleteAll() {
		cfItemRepository.deleteAll();
		
	}
	
	
	
	

}
