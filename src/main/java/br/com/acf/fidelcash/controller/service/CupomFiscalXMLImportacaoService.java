package br.com.acf.fidelcash.controller.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.controller.dto.ImportacaoDto;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.CupomFiscal;
import br.com.acf.fidelcash.modelo.CupomFiscalItem;
import br.com.acf.fidelcash.modelo.CupomFiscalXML;
import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.SituacaoEmpresa;
import br.com.acf.fidelcash.modelo.SituacaoTipoCliente;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.TipoClienteLog;
import br.com.acf.fidelcash.modelo.Util;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;
import br.com.acf.fidelcash.repository.ClienteRepository;
import br.com.acf.fidelcash.repository.ContaCorrenteRepository;
import br.com.acf.fidelcash.repository.CupomFiscalItemRepository;
import br.com.acf.fidelcash.repository.CupomFiscalRepository;
import br.com.acf.fidelcash.repository.EmpresaRepository;
import br.com.acf.fidelcash.repository.ProdutoRepository;
import br.com.acf.fidelcash.repository.TipoClienteLogRepository;
import br.com.acf.fidelcash.repository.TipoClienteRepository;
import br.com.acf.fidelcash.repository.UtilRepository;

@Service
public class CupomFiscalXMLImportacaoService {
	
	@Autowired
	private UtilRepository utilRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private TipoClienteRepository tipoClienteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CupomFiscalRepository cupomFiscalRepository;
	
	@Autowired
	private CupomFiscalItemRepository cupomFiscalItemRepository;
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private TipoClienteLogRepository tipoClienteLogRepository;
	
	@Transactional(rollbackFor = {Exception.class})
	public List<ImportacaoDto> importarXml()
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		try {
			List<Util> pastas = getPastaImportacaoXML("upload_import_xml");
			List<ImportacaoDto> importacaoDto = new ArrayList<ImportacaoDto>();
			for (int i = 0; i < pastas.size(); i++) {
				Map<Path, String> mapArquivos = importarXml(pastas.get(i).getPasta(), pastas.get(i).getEmpresa());
				List<String> arquivos = movimentacaoDeArquivos(mapArquivos, pastas.get(i).getEmpresa());
				Collections.sort(arquivos);
				ImportacaoDto importacaoEmpresaDto = setImportacaoDto(pastas.get(i).getPasta(), arquivos);
				importacaoDto.add(importacaoEmpresaDto);
			}
			return importacaoDto;
		} catch (IOException | ParseException | ParserConfigurationException | SAXException ex) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}
	
	private List<Util> getPastaImportacaoXML(String utilidade) throws CupomFiscalXMLException {
		List<Util> util = utilRepository.findByUtilidade(utilidade);
		if (util.isEmpty()) {
			throw new CupomFiscalXMLException(utilidade + " nao esta cadastrado na coluna utilidade da tabela Util",
					utilidade + " nao esta cadastrado na coluna utilidade da tabela Util");
		} else {
			return util;
		}
	}
	
	private Map<Path, String> importarXml(String directory, Empresa empresaUtil)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		Path arquivoException;
		Map<Path, String> mapArquivo = new HashMap<>();
		Path dir = Paths.get(directory);
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, "*.xml*");
		for (Path arquivo : directoryStream) {
			arquivoException = arquivo;
			try {
				GerarDadosByXML(arquivo, empresaUtil);
				mapArquivo.put(arquivo, "importados_xml");
			} catch (CupomFiscalXMLException ex) {
				if (ex.getMensagem().equals("Empresa do arquivo xml nao cadastrada no sistema")) {
					mapArquivo.put(arquivoException, "empresa_xml_nao_cadastrada_sistema");
				} else if (ex.getMensagem().equals("Empresa Inativa")) {
					mapArquivo.put(arquivoException, "empresa_inativa");
				} else if (ex.getMensagem().equals("Tipo de Cliente PADRAO inexistente")) {
					mapArquivo.put(arquivoException, "tipo_cliente_inexistente");
				}  else if (ex.getMensagem().equals("Situacao do Tipo de Cliente PADRAO Inativa")) {
                	mapArquivo.put(arquivoException, "tipo_cliente_padrao_inativo");
                } else if (ex.getMensagem().equals("Bonus do Tipo de Cliente PADRAO inferior A 1%")) {
                	mapArquivo.put(arquivoException, "bonus_menor_que_1%");
				} else if (ex.getMensagem().equals("Erro no cadastro de cliente")) {
                	mapArquivo.put(arquivoException, "erro_cadastro_cliente");
				} else if (ex.getMensagem().equals("CNPJ da empresa do XML diferente do CNPJ cadastrado na tabela Util")) {
                	mapArquivo.put(arquivoException, "cnpj_util_diferente_cnpj_xml");
				} else if (ex.getMensagem().equals("Arquivo Sem Cliente")) {
                	mapArquivo.put(arquivoException, "sem_cpf_import_xml");
				} else if (ex.getMensagem().equals("registro duplicado")) {
                	mapArquivo.put(arquivoException, "registro_duplicado_import_xml");
				} else if (ex.getMensagem().equals("Erro ao gerar cupom fiscal")) {
                	mapArquivo.put(arquivoException, "erro_cupom_fiscal");
				} else if (ex.getMensagem().equals("Erro Item do Cupom Fiscal")) {
                	mapArquivo.put(arquivoException, "erro_item_cupom_fiscal");
				}	else {
					mapArquivo.put(arquivoException, "erro_desconhecido");
				}
			} catch (IOException | ParseException | ParserConfigurationException | SAXException ex) {
				mapArquivo.put(arquivoException, "erro_desconhecido");
			}
		}
		directoryStream.close();
		return mapArquivo;
	}
	
	private List<String> movimentacaoDeArquivos(Map<Path, String> mapArquivos, Empresa empresa)
			throws IOException, ParserConfigurationException, SAXException, ParseException, CupomFiscalXMLException {
		List<String> arquivos = new ArrayList<String>();
		for (Map.Entry<Path, String> entry : mapArquivos.entrySet()) {
			try {
				String arquivo = moverArquivos(entry.getKey(), entry.getValue(), empresa);
				arquivos.add(arquivo);
			} catch (IOException e) {
				throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
			}
		}
		return arquivos;
	}
	
	private String moverArquivos(Path arquivo, String operacao, Empresa empresa) throws FileNotFoundException, IOException,
			CupomFiscalXMLException, ParseException, ParserConfigurationException, SAXException {
		String oper;
		if ((operacao.equals("importados_xml")) || (operacao.equals("sem_cpf_import_xml")) || (operacao.equals("registro_duplicado_import_xml"))) {
			oper = operacao;
		} else {
			oper = "erro_import_xml";
		}
		Util pastaOperacao = getPastaUtilidadeXML(empresa, oper);
		String stringArquivoDestino;
		if(oper.equals("erro_import_xml")){
			stringArquivoDestino = pastaOperacao.getPasta() + operacao + "_" + arquivo.getFileName();
		} else {
			stringArquivoDestino = pastaOperacao.getPasta() + arquivo.getFileName();
		}
		Path arquivoDestino = FileSystems.getDefault().getPath(stringArquivoDestino);
		try {
			if((operacao.equals("importados_xml")) || (operacao.equals("sem_cpf_import_xml")) || (operacao.equals("registro_duplicado_import_xml"))){
				Files.move(arquivo, arquivoDestino, StandardCopyOption.ATOMIC_MOVE);
			} else {
				Files.copy(arquivo, arquivoDestino, StandardCopyOption.REPLACE_EXISTING);
			}
			return arquivoDestino.toString();
				
		} catch (IOException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}
	
	private ImportacaoDto setImportacaoDto(String pastaOrigem, List<String> arquivos) {
		ImportacaoDto importacaoDto = new ImportacaoDto(pastaOrigem, arquivos);
		return importacaoDto;
	}
	
	private Util getPastaUtilidadeXML(Empresa empresa, String utilidade) throws CupomFiscalXMLException {
		Optional<Util> pasta = utilRepository.findByEmpresaAndUtilidade(empresa, utilidade);
		if (pasta.isEmpty()) {
			throw new CupomFiscalXMLException(utilidade + " nao esta cadastrado na coluna utilidade da tabela Util",
					utilidade + " nao esta cadastrado na coluna utilidade da tabela Util");
		} else {
			return pasta.get();
		}
	}
	
	private void GerarDadosByXML(Path arquivo, Empresa empresaUtil)
			throws ParserConfigurationException, SAXException, IOException, ParseException, CupomFiscalXMLException {
		try {
			String xml = arquivo.toString();
			CupomFiscalXML cfXML = new CupomFiscalXML(xml);
			Empresa empresaXML = cfXML.getEmpresa();
			
			List<Produto> produtos = cfXML.getProdutos();
			CupomFiscal cupomFiscal = cfXML.getCupomFiscal();
			List<CupomFiscalItem> itens = cfXML.getItens();
			Cliente cliente = cfXML.getCliente();
			//
			Empresa emp = validaImportacaoEmpresa(empresaXML, empresaUtil); 
			TipoCliente tipoCliente = validaImportacaoTipoCliente(emp, "PADRAO"); 
			cliente = validaImportacaoCliente(cliente, tipoCliente); 
			cupomFiscal = setCupomFiscal(cupomFiscal, emp, cliente);
			setCupomFiscalItem(produtos, itens, emp, cupomFiscal); 
				 
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
		}
	}
	
	private Empresa validaImportacaoEmpresa(Empresa empresaXML, Empresa empresaUtil) throws CupomFiscalXMLException {
		if(empresaXML.getCnpj().compareTo(empresaUtil.getCnpj()) != 0) {
			throw new CupomFiscalXMLException("CNPJ da empresa do XML diferente do CNPJ cadastrado na tabela Util", "CNPJ da empresa do XML diferente do CNPJ cadastrado na tabela Util");
		}
		Optional<Empresa> empresaFind = empresaRepository.findByCnpj(empresaXML.getCnpj());
        if (empresaFind.isEmpty()) {
            throw new CupomFiscalXMLException("Empresa do arquivo xml nao cadastrada no sistema", "Empresa do arquivo xml nao cadastrada no sistema");
        } else {
            if (empresaFind.get().getSituacao() == SituacaoEmpresa.INATIVA) {
                throw new CupomFiscalXMLException("Empresa Inativa", "Empresa Inativa");
            }
            return empresaFind.get();
        }
    }
	
	private TipoCliente validaImportacaoTipoCliente(Empresa empresa, String descricao) throws CupomFiscalXMLException {
        Optional<TipoCliente> tipoClienteFind = tipoClienteRepository.findByEmpresaAndDescricao(empresa, descricao);
        if (tipoClienteFind.isEmpty()) {
            throw new CupomFiscalXMLException("Tipo de Cliente PADRAO inexistente", "Tipo de Cliente PADRAO inexistente");
        } else {
            if (tipoClienteFind.get().getBonus() < 1) {
                throw new CupomFiscalXMLException("Bonus do Tipo de Cliente PADRAO inferior A 1%", "Bonus do Tipo de Cliente PADRAO inferior A 1%");
            }
            if (tipoClienteFind.get().getSituacao() == SituacaoTipoCliente.INATIVO) {
                throw new CupomFiscalXMLException("Situacao do Tipo de Cliente PADRAO Inativa", "Situacao do Tipo de Cliente PADRAO Inativa");
            }
            return tipoClienteFind.get();
        }
    }
	
	private Cliente validaImportacaoCliente(Cliente cliente, TipoCliente tipoCliente) throws CupomFiscalXMLException {
        if (cliente.getCpf() == null) {
            throw new CupomFiscalXMLException("Arquivo Sem Cliente", "Arquivo Sem Cliente");
        }
        Cliente clienteReturn = setCliente(cliente, tipoCliente);
        return clienteReturn;
    }
	
	private Cliente setCliente(Cliente cliente, TipoCliente tipoCliente) throws CupomFiscalXMLException {
        try {
            Optional<Cliente> clienteFind = clienteRepository.findByTipoClienteAndCpf(tipoCliente, cliente.getCpf());
            if (clienteFind.isEmpty()) {
                cliente.setTipoCliente(tipoCliente);
                clienteRepository.save(cliente);
                clienteFind = clienteRepository.findByTipoClienteAndCpf(tipoCliente, cliente.getCpf());
            }
            return clienteFind.get();
        } catch (Exception e) {
            throw new CupomFiscalXMLException("Erro no cadastro de cliente", "Erro no cadastro de cliente");
        }
    }
	
	private CupomFiscal setCupomFiscal(CupomFiscal cupomFiscal, Empresa empresa, Cliente cliente) throws CupomFiscalXMLException {
        try {
            cupomFiscal.setCliente(cliente);

            Optional<CupomFiscal> cupomFiscalFind = cupomFiscalRepository.findByCodigoCupomAndCliente(cupomFiscal.getCodigoCupom(), cliente);
            if (cupomFiscalFind.isPresent()) {
                throw new CupomFiscalXMLException("registro duplicado", "registro duplicado");
            }
            cupomFiscalRepository.save(cupomFiscal);
            cupomFiscalFind = cupomFiscalRepository.findByCodigoCupomAndCliente(cupomFiscal.getCodigoCupom(), cliente);
            return cupomFiscalFind.get();
        } catch (CupomFiscalXMLException ex) {
            throw new CupomFiscalXMLException("registro duplicado", "registro duplicado");
        } catch (Exception e) {
            throw new CupomFiscalXMLException("Erro ao gerar cupom fiscal", "Erro ao gerar cupom fiscal");
        }
    }
	
	private void setCupomFiscalItem(List<Produto> produtos, List<CupomFiscalItem> itens, Empresa empresa,
									CupomFiscal cupomFiscal) throws CupomFiscalXMLException {
		try {
			for (int i = 0; i < produtos.size(); i++) {
				Produto prod = setProduto(produtos.get(i), empresa);
				CupomFiscalItem cupomFiscalItem;
				cupomFiscalItem = itens.get(i);
				cupomFiscalItem.setCupomFiscal(cupomFiscal);
				cupomFiscalItem.setProduto(prod);
				cupomFiscalItemRepository.save(cupomFiscalItem);
				
				List<CupomFiscalItem> cfItens = cupomFiscalItemRepository.findFirstByCupomFiscalOrderByIdDesc(cupomFiscal);
				setContaCorrente(cfItens.get(0));
				
			}
		} catch (Exception e) {
			throw new CupomFiscalXMLException("Erro Item do Cupom Fiscal", "Erro Item do Cupom Fiscal");
		}
	}
	
	private void setContaCorrente(CupomFiscalItem cfItem) {
		CupomFiscal cf = cfItem.getCupomFiscal();
		Empresa empresa = cfItem.getCupomFiscal().getCliente().getTipoCliente().getEmpresa();
		Cliente cliente = cfItem.getCupomFiscal().getCliente();
		List<ContaCorrente> lancamentosPosteriores = LancamentosPosterioresAoAtual(empresa, cliente, cf);
		float saldo = 0;
		if(lancamentosPosteriores.isEmpty()) {
			saldo = getSaldoEmpresaCliente(empresa, cliente);
			insertCC(cfItem, saldo);
		}
		System.out.println(saldo);
		
	}
	
	private List<ContaCorrente> LancamentosPosterioresAoAtual(Empresa empresa, Cliente cliente, CupomFiscal cf) {
		List<ContaCorrente> itens = contaCorrenteRepository.findByEmpresaAndClienteAndDataCompraSuperiorQueAtual(empresa, cliente, cf.getDataCompra());
		return itens;
	}
	
	private float getSaldoEmpresaCliente(Empresa empresa, Cliente cliente) {
		List<ContaCorrente> cc = new ArrayList<ContaCorrente>();
		cc = contaCorrenteRepository.findFirstByEmpresaAndClienteOrderByIdDesc(empresa, cliente);
		float saldo = 0;
		if(cc.isEmpty()) {
			saldo = 0;
		} else {
			saldo = cc.get(0).getSaldo();
		}
		return saldo;
	}
	
	private void insertCC(CupomFiscalItem cfItem, float saldoAnterior) {
		ContaCorrente cc = new ContaCorrente();
		cc.setCupomFiscalItem(cfItem);
		if (Float.compare(cfItem.getValorDesconto(), 0) == 0)  {
			cc.setBonus(0);
			cc.setDebito(0);
			float bonusPercentual = bonusDoPeriodo(cfItem);
			float credito = ((cfItem.getValorItem() * bonusPercentual) / 100);
			cc.setCredito(credito);
			cc.setSaldo(saldoAnterior + credito);
			contaCorrenteRepository.save(cc);
		} else {
			cc.setBonus(0);
			cc.setCredito(0);
			cc.setDebito(cfItem.getValorDesconto());
			cc.setSaldo(saldoAnterior - cfItem.getValorDesconto());
			contaCorrenteRepository.save(cc);
		}
	}
	
	public float bonusDoPeriodo(CupomFiscalItem cfItem) {
		Empresa emp = cfItem.getCupomFiscal().getCliente().getTipoCliente().getEmpresa();
		List<TipoClienteLog> log = tipoClienteLogRepository.findByTipoClienteEmpresaOrderById(emp);
		float bonus = 0;
		for (int i = 0; i < log.size(); i++) {
			LocalDateTime dataInicio = log.get(i).getData_inicio();
			LocalDateTime dataFim = log.get(i).getData_fim();
			if(dataFim == null) {
				dataFim = LocalDateTime.now(); 
			}
			LocalDateTime dataCupom = cfItem.getCupomFiscal().getDataCompra();
			
			int compareDataInicio = dataCupom.compareTo(dataInicio);
			int compareDataFim = dataCupom.compareTo(dataFim);
			
			if(compareDataInicio >= 0 && compareDataFim <= 0) {
				bonus = log.get(i).getBonus();
				break;
			}
		}
		return bonus;
	}
	
	

	private Produto setProduto(Produto produto, Empresa empresa) {
        Produto prod = produto;
        prod.setEmpresa(empresa);
        Optional<Produto> produtoFind = produtoRepository.findByEmpresaAndCodigoProduto(empresa, prod.getCodigoProduto());
        if (produtoFind.isEmpty()) {
        	produtoRepository.save(prod);
            produtoFind = produtoRepository.findByEmpresaAndCodigoProduto(empresa, prod.getCodigoProduto());
        }
        return produtoFind.get();
    }


}
