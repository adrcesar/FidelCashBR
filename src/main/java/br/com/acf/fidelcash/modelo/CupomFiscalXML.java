package br.com.acf.fidelcash.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;



public class CupomFiscalXML {
	
	private Element raizCFe = null;
    private List<Element> elementos = new ArrayList<>();
    private Map<Element, Integer> elementoAnalise = new HashMap<>();
    private Map<Element, Integer> elementoTamanho = new HashMap<>();
    private List<String> nivel01 = new ArrayList<>();
    private List<String> nivel02 = new ArrayList<>();
    private List<String> retornoNivel02Nome = new ArrayList<>();
    private List<String> retornoNivel02Valor = new ArrayList<>();
    
    //variáveis de retorno
    Endereco endereco = new Endereco();
    Empresa empresa = new Empresa();
    CupomFiscal cupomFiscal = new CupomFiscal();
    List<Produto> produtos = new ArrayList<>();
    List<CupomFiscalItem> itens = new ArrayList<>();
    Cliente cliente = new Cliente();
    
    public CupomFiscalXML(String arquivoXML) throws ParserConfigurationException, SAXException, IOException, ParseException, CupomFiscalXMLException {
        InputStream isArquivoXML = new MyInputStream(arquivoXML);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(isArquivoXML);
            raizCFe = doc.getDocumentElement();
            setValoresProcuradosNoXML();
            lerXML();
        } catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
            isArquivoXML.close();
            throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
        }

    }
    
    private static class MyInputStream extends FileInputStream {

        public MyInputStream(String filename) throws FileNotFoundException {
            super(filename);
        }

        @Override
        public void close() throws IOException {
            // here we log when the stream is close.
            System.out.println("file input stream closed.");
            super.close();
        }

    }
    
    private void setValoresProcuradosNoXML() {
        //empresa
        nivel01.add("emit");
        nivel02.add("CNPJ");
        nivel01.add("emit");
        nivel02.add("xNome");
        nivel01.add("emit");
        nivel02.add("xFant");
        //endereco
        nivel01.add("enderEmit");
        nivel02.add("xLgr");
        nivel01.add("enderEmit");
        nivel02.add("nro");
        nivel01.add("enderEmit");
        nivel02.add("xCpl");
        nivel01.add("enderEmit");
        nivel02.add("xBairro");
        nivel01.add("enderEmit");
        nivel02.add("xMun");
        nivel01.add("enderEmit");
        nivel02.add("CEP");
        //cupom fiscal
        nivel01.add("ide");
        nivel02.add("nCFe");
        nivel01.add("ide");
        nivel02.add("dEmi");
        nivel01.add("ide");
        nivel02.add("hEmi");
        nivel01.add("total");
        nivel02.add("vCFe");
        //produto
        nivel01.add("prod");
        nivel02.add("cProd");
        nivel01.add("prod");
        nivel02.add("xProd");
        nivel01.add("prod");
        nivel02.add("uCom");
        nivel01.add("prod");
        nivel02.add("vUnCom");
        //itens cupom fiscal
        nivel01.add("prod");
        nivel02.add("qCom");
        nivel01.add("prod");
        nivel02.add("vProd");
        nivel01.add("prod");
        nivel02.add("vDesc");
        nivel01.add("prod");
        nivel02.add("vItem");
        //cliente
        nivel01.add("dest");
        nivel02.add("CPF");
    }
    
    private void lerXML() throws ParseException, CupomFiscalXMLException {
        elementos.add(raizCFe);
        try {
            chamarProximoNivel();
        } catch (CupomFiscalXMLException e) {
            if (e.getMensagem().equals("Fim do Arquivo")) {
                setCupomFiscalXML();
            } else {
                throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
            }
        } catch (Exception e) {
            throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
        }
    }
    
    private NodeList getLista(Element elemento) {
        NodeList lista = elemento.getChildNodes();
        return lista;
    }

    private Element getElemento(NodeList lista, int posicao) {
        Element element = (Element) lista.item(posicao);
        return element;
    }
    
    private void chamarProximoNivel() throws CupomFiscalXMLException {
        Element elemento = elementos.get(elementos.size() - 1);
        Element proximoElemento;

        if (elementoAnalise.containsKey(elemento)) {
            NodeList lista = getLista(elemento);
            proximoElemento = getElemento(lista, elementoAnalise.get(elemento));
            NodeList proximalista = getLista(proximoElemento);
            int tamanho = proximalista.getLength();
            elementos.add(proximoElemento);
            elementoTamanho.put(proximoElemento, tamanho);
            elementoAnalise.put(proximoElemento, 0);
        } else {
            elementoAnalise.put(elemento, 0);
            NodeList lista = getLista(elemento);
            elementoTamanho.put(elemento, lista.getLength());
        }
        Element elementoAtual = elementos.get(elementos.size() - 1);
        if (isUltimoElementoDaCadeia(elementoAtual)) {
            if (isElementoProcurado(elementoAtual, nivel01, nivel02)) {
                Element elementoAnterior = elementos.get(elementos.size() - 2);
                setDescontoZeroQuandoNaoExisteNoXML(elementoAnterior, elementoAtual);
                retornoNivel02Nome.add(elementoAnterior.getNodeName() + "_" + elementoAtual.getNodeName());
                retornoNivel02Valor.add(elementoAtual.getTextContent());
            }
            ExcluirNiveisJaLidos();
        } else {
            chamarProximoNivel();
        }
    }
    
    private void setDescontoZeroQuandoNaoExisteNoXML(Element elementoAnterior, Element elementoAtual) {
        if (elementoAnterior.getNodeName().equals("prod") && elementoAtual.getNodeName().equals("vItem")) {
            String ultimoElementoRetorno = retornoNivel02Nome.get(retornoNivel02Nome.size() - 1);
            if (!ultimoElementoRetorno.equals("prod_vDesc")) {
                retornoNivel02Nome.add("prod_vDesc");
                retornoNivel02Valor.add("0");
            }
        }
    }
    
    private boolean isUltimoElementoDaCadeia(Element elemento) {
        if (elementoAnalise.get(elemento).equals(0) && elementoTamanho.get(elemento).equals(1)) {
            NodeList lista = elemento.getChildNodes();
            Node nodeValor = lista.item(0);
            if (nodeValor.getNodeName().equals("#text")) {
                return true;
            }
        }
        if (elementoTamanho.get(elemento).equals(0)) {
            return true;
        }
        return false;
    }
    
    private boolean isElementoProcurado(Element elemento, List<String> nivel01, List<String> nivel02) {
        for (int i = 0; i < nivel02.size(); i++) {
            if (elemento.getNodeName().equals(nivel02.get(i))) {
                Element elementoAnterior = elementos.get(elementos.size() - 2);
                if (elementoAnterior.getNodeName().equals(nivel01.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    private void ExcluirNiveisJaLidos() throws CupomFiscalXMLException {
        Element elemento = elementos.get(elementos.size() - 1);
        elementos.remove(elemento);

        if (elementos.size() <= 0) {
            throw new CupomFiscalXMLException("Fim do Arquivo", "Fim do Arquivo");
        }
        Element elementoAnterior = elementos.get(elementos.size() - 1);

        if (elementoAnalise.get(elementoAnterior).equals(elementoTamanho.get(elementoAnterior) - 1)) {
            ExcluirNiveisJaLidos();
        }
        elementoAnalise.put(elementoAnterior, elementoAnalise.get(elementoAnterior) + 1);
        chamarProximoNivel();
    }
    
    private void setCupomFiscalXML() throws ParseException, CupomFiscalXMLException {
        try {
            endereco.setTipo(TipoEndereco.EMPRESA);

            empresa.setEndereco(endereco);
            empresa.setSituacao(SituacaoEmpresa.ATIVA);

            String dataCupom = null, horaCupom = null;

            List<String> codigoProduto = new ArrayList<>();
            List<String> descricaoProduto = new ArrayList<>();
            List<String> unidadeProduto = new ArrayList<>();
            List<Float> valorProduto = new ArrayList<>();

            List<Float> quantidade = new ArrayList<>();
            List<Float> valorProdutoItem = new ArrayList<>();
            List<Float> desconto = new ArrayList<>();
            List<Float> valorItem = new ArrayList<>();

            for (int i = 0; i < retornoNivel02Nome.size(); i++) {
                //empresa
                if (retornoNivel02Nome.get(i).equals("emit_CNPJ")) {
                    empresa.setCnpj(new BigInteger(retornoNivel02Valor.get(i)));
                }
                if (retornoNivel02Nome.get(i).equals("emit_xNome")) {
                    empresa.setNomeRazao(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("emit_xFant")) {
                    empresa.setNomeFantasia(retornoNivel02Valor.get(i));
                }
                //endereco
                if (retornoNivel02Nome.get(i).equals("enderEmit_xLgr")) {
                    endereco.setLogradouro(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("enderEmit_nro")) {
                    endereco.setNumeroLogradouro(Integer.parseInt(retornoNivel02Valor.get(i)));
                }
                if (retornoNivel02Nome.get(i).equals("enderEmit_xCpl")) {
                    endereco.setComplementoLogradouro(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("enderEmit_xBairro")) {
                    endereco.setBairro(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("enderEmit_xMun")) {
                    endereco.setMunicipio(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("enderEmit_CEP")) {
                    endereco.setCep(retornoNivel02Valor.get(i));
                }
                //cupom fiscal
                if (retornoNivel02Nome.get(i).equals("ide_nCFe")) {
                    cupomFiscal.setCodigoCupom(Integer.parseInt(retornoNivel02Valor.get(i)));
                }
                if (retornoNivel02Nome.get(i).equals("ide_dEmi")) {
                    dataCupom = retornoNivel02Valor.get(i);
                }
                if (retornoNivel02Nome.get(i).equals("ide_hEmi")) {
                    horaCupom = retornoNivel02Valor.get(i);
                }
                if (retornoNivel02Nome.get(i).equals("total_vCFe")) {
                    cupomFiscal.setValor(Float.parseFloat(retornoNivel02Valor.get(i)));
                }
                //produto
                if (retornoNivel02Nome.get(i).equals("prod_cProd")) {
                    codigoProduto.add(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("prod_xProd")) {
                    descricaoProduto.add(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("prod_uCom")) {
                    unidadeProduto.add(retornoNivel02Valor.get(i));
                }
                if (retornoNivel02Nome.get(i).equals("prod_vUnCom")) {
                    valorProduto.add(Float.parseFloat(retornoNivel02Valor.get(i)));
                }
                //itens cupom fiscal
                if (retornoNivel02Nome.get(i).equals("prod_qCom")) {
                    quantidade.add(Float.parseFloat(retornoNivel02Valor.get(i)));
                }
                if (retornoNivel02Nome.get(i).equals("prod_vProd")) {
                    valorProdutoItem.add(Float.parseFloat(retornoNivel02Valor.get(i)));
                }
                if (retornoNivel02Nome.get(i).equals("prod_vDesc")) {
                    desconto.add(Float.parseFloat(retornoNivel02Valor.get(i)));
                }
                if (retornoNivel02Nome.get(i).equals("prod_vItem")) {
                    valorItem.add(Float.parseFloat(retornoNivel02Valor.get(i)));
                }
                //cliente
                if (retornoNivel02Nome.get(i).equals("dest_CPF")) {
                    cliente.setCpf(new BigInteger(retornoNivel02Valor.get(i)));
                    cliente.setSituacao(SituacaoCliente.AGUARDANDO);
                }
            }

            LocalDateTime compra = dataHoraCompra(dataCupom, horaCupom);
            cupomFiscal.setDataCompra(compra);

            for (int i = 0; i < codigoProduto.size(); i++) {
                Produto produto = new Produto();
                produto.setCodigoProduto(codigoProduto.get(i));
                produto.setDescricao(descricaoProduto.get(i));
                produto.setUnidadeComercial(unidadeProduto.get(i));
                produto.setValor(valorProduto.get(i));
                produto.setSituacao(SituacaoProduto.ATIVO);
                produtos.add(produto);

                CupomFiscalItem cupomFiscalItem = new CupomFiscalItem();
                cupomFiscalItem.setQuantidade(quantidade.get(i));
                cupomFiscalItem.setValorProduto(valorProdutoItem.get(i));
                cupomFiscalItem.setValorDesconto(desconto.get(i));
                cupomFiscalItem.setValorItem(valorItem.get(i));
                itens.add(cupomFiscalItem);
            }
        } catch (Exception ex) {
            throw new CupomFiscalXMLException("Arquivo inconsistente", "Arquivo inconsistente");
        }
    }
    
    private LocalDateTime dataHoraCompra(String data, String horario) {
    	int year = Integer.parseInt(data.substring(0, 4));
    	int month = Integer.parseInt(data.substring(4, 6));
    	int dayOfMonth = Integer.parseInt(data.substring(6, 8));
    	int hour = Integer.parseInt(horario.substring(0, 2));
    	int minute = Integer.parseInt(horario.substring(2, 4));
    	int second = Integer.parseInt(horario.substring(4, 6));
    	return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CupomFiscal getCupomFiscal() {
        return cupomFiscal;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<CupomFiscalItem> getItens() {
        return itens;
    }



}
