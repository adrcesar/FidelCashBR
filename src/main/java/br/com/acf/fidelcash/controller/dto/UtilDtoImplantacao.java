package br.com.acf.fidelcash.controller.dto;



import java.util.ArrayList;
import java.util.List;

import br.com.acf.fidelcash.modelo.Empresa;
import br.com.acf.fidelcash.modelo.Endereco;
import br.com.acf.fidelcash.modelo.Produto;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.Util;

public class UtilDtoImplantacao {
	
	private Util util;
	
	private EmpresaDto empresa;
	
	private EnderecoDTO endereco;
	
	private TipoClienteImplantacaoDto tipoCliente;
	
	private List<ProdutoImplantacaoDto> produtos;
	
	private boolean erro = false;
	private String erroMensagem;
	
	public UtilDtoImplantacao() {
		
	}
	
	public void setUtil(Util util) {
		this.util = util;
	}
	
	public Util getUtil() {
		return util;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = new EmpresaDto(empresa);
	}
	
	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = new EnderecoDTO(endereco);
	}

	
	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}
	
	

	public void setErroMensagem(String erroMensagem) {
		this.erroMensagem = erroMensagem;
	}

	public String getErroMensagem() {
		return erroMensagem;
	}

	public TipoClienteImplantacaoDto getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = new TipoClienteImplantacaoDto(tipoCliente);
	}

	public List<ProdutoImplantacaoDto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = new ArrayList<ProdutoImplantacaoDto>();
		for(int i = 0; i < produtos.size(); i++) {
			ProdutoImplantacaoDto prod = new ProdutoImplantacaoDto(produtos.get(i));
			this.produtos.add(prod);
		}
	}
	
	
	
	

	

	


	
	
	
	
}
