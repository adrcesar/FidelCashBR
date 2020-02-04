package br.com.acf.fidelcash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.acf.fidelcash.controller.service.ClienteService;
import br.com.acf.fidelcash.controller.service.CupomFiscalItemService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.Empresa;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Transactional
public class CupomFiscalItemTest {
	
	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CupomFiscalItemService cfItemService;
	
	@Test
	@Sql({"/cupom_fiscal_item.sql"})
	public void saldoUltimoLancamentoCupomFiscalItem() {

		BigInteger cnpj = new BigInteger("99999999999999");
		BigInteger cpf = new BigInteger("16368579811");
		
		Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
		Optional<Cliente> cliente = clienteService.findByEmpresaAndCpf(empresa.get(), cpf);
		
		
		float saldo = 0;

		if (cliente.isPresent()) {
			saldo = cfItemService.getSaldoCliente(cliente.get());
		} 

		System.out.println(saldo);
		assertEquals(-5.223, saldo, 0.0001);
		
	}

}
