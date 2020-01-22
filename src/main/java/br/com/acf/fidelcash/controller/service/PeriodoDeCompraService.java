package br.com.acf.fidelcash.controller.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.PeriodoDeCompraServiceException;
import br.com.acf.fidelcash.modelo.Campanha;
import br.com.acf.fidelcash.modelo.CampanhaRegras;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoSelecaoCliente;
import br.com.acf.fidelcash.modelo.TipoSelecaoProduto;

@Service
public class PeriodoDeCompraService extends CampanhaRegrasService{

	private Campanha campanhaPai;
	LocalDateTime dataInicioPeriodo;
	LocalDateTime dataFimPeriodo;	
	
	@Autowired
	private CupomFiscalService cfService;
	
	public void SetPeriodoCampanha(Campanha campanhaPai, LocalDateTime dataFinal, List<Integer> periodos) throws PeriodoDeCompraServiceException {
		this.campanhaPai = campanhaPai;
		criarCampanhaPai();
		this.dataFimPeriodo = dataFinal;
		for(Integer periodo : periodos) {
			this.dataInicioPeriodo = dataHoraMinutosSegundos(this.dataFimPeriodo.minusDays(periodo), 0, 0, 0);
			
			Campanha campanha = configurarCampanha();
			
			super.criarCampanhaRegra(campanha);
			
			this.dataFimPeriodo = dataHoraMinutosSegundos(this.dataInicioPeriodo.minusDays(1), 23, 59, 59);
		}
	}
	
	private LocalDateTime dataHoraMinutosSegundos(LocalDateTime data, int hour, int minute, int second) {
    	return LocalDateTime.of(data.getYear(), data.getMonthValue(), data.getDayOfMonth(), hour, minute, second);
    }
	
	private void criarCampanhaPai() {
		super.setCampanha(this.campanhaPai);
	}
	
	private Campanha configurarCampanha() {
		Campanha campanha = new Campanha();
		
		campanha.setEmpresa(campanhaPai.getEmpresa());
		
		String dataIniFormat = FormatarDataRetorno(this.dataInicioPeriodo);
		String dataFimFormat = FormatarDataRetorno(this.dataFimPeriodo);
		campanha.setDescricao("CLIENTES QUE COMPRARAM ENTRE " + dataIniFormat + " e " + dataFimFormat + ".");
		
		campanha.setDataInicio(campanhaPai.getDataInicio());
		
		campanha.setDataFim(campanhaPai.getDataFim());
		
		campanha.setCampanhaPai(campanhaPai);
		
		return campanha;
	}
	
	private String FormatarDataRetorno(LocalDateTime dataOrig) {
		return dataOrig.getDayOfMonth()+"/"+dataOrig.getMonthValue()+"/"+dataOrig.getYear();
	}
	
	@Override
	protected List<Cliente> selecionarClientes() throws PeriodoDeCompraServiceException  {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();
			List<CupomFiscal> cuponsFiscais = new ArrayList<CupomFiscal>();
			List<Cliente> clientesJaCadastrados = super.getClientesDaCampanhaByCampanhaPai(this.campanhaPai);
			
			if (clientesJaCadastrados.isEmpty()) {
				cuponsFiscais = cfService.findByDataCompraBetween(this.dataInicioPeriodo, this.dataFimPeriodo);
			}else {
				cuponsFiscais = cfService.findByDataCompraBetweenNotIn(this.dataInicioPeriodo, this.dataFimPeriodo, clientesJaCadastrados);
			}
			
			for(CupomFiscal cf : cuponsFiscais ) {
				if (!clientes.contains(cf.getCliente())) {
					clientes.add(cf.getCliente());
				}
			}
			return clientes;
		}catch (Exception e) {
			throw new PeriodoDeCompraServiceException("Erro ao selecionar clientes da campanha por periodo", "Erro ao selecionar clientes da campanha por periodo");
		}
	}

	@Override
	protected List<Produto> selecionarProdutos() {
		return null;
	}

	@Override
	protected void setCampanhaRegra(Campanha campanha, List<Cliente> clientes, List<Produto> produtos) {
		for(Cliente cliente : clientes) {
			CampanhaRegras regras = new CampanhaRegras();
			regras.setCampanha(campanha);
			regras.setCliente(cliente);
			regras.setTipoSelecaoCliente(TipoSelecaoCliente.INDIVIDUAL);
			regras.setTipoSelecaoProduto(TipoSelecaoProduto.TODOS);
			super.save(regras);
		}
		
	}

	public void deleteAll() {
		super.deleteAll();
		
	}

	
}
