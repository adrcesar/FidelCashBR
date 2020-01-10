package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acf.fidelcash.controller.service.exception.ClienteServiceException;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.TipoCliente;
import br.com.acf.fidelcash.modelo.exception.CupomFiscalXMLException;
import br.com.acf.fidelcash.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente validaImportacaoCliente(Cliente cliente, TipoCliente tipoCliente) throws CupomFiscalXMLException, ClienteServiceException {
        if (cliente.getCpf() == null) {
            throw new CupomFiscalXMLException("Arquivo Sem Cliente", "Arquivo Sem Cliente");
        }
        Cliente clienteReturn = setClienteImportacaoXml(cliente, tipoCliente);
        return clienteReturn;
    }
	
	private Cliente setClienteImportacaoXml(Cliente cliente, TipoCliente tipoCliente) throws CupomFiscalXMLException, ClienteServiceException {
        try {
            Optional<Cliente> clienteFind = clienteRepository.findByTipoClienteAndCpf(tipoCliente, cliente.getCpf());
            if (clienteFind.isEmpty()) {
                cliente.setTipoCliente(tipoCliente);
                clienteRepository.save(cliente);
                clienteFind = clienteRepository.findByTipoClienteAndCpf(tipoCliente, cliente.getCpf());
            }
            return clienteFind.get();
        } catch (Exception e) {
            throw new ClienteServiceException("Erro no cadastro de cliente", "Erro no cadastro de cliente");
        }
    }
	
	

}
