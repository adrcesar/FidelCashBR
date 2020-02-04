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
import br.com.acf.fidelcash.controller.service.ContaCorrenteService;
import br.com.acf.fidelcash.controller.service.EmpresaService;
import br.com.acf.fidelcash.modelo.Cliente;
import br.com.acf.fidelcash.modelo.ContaCorrente;
import br.com.acf.fidelcash.modelo.Empresa;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Transactional
public class ContaCorrenteTest {
	
	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ContaCorrenteService ccService;
	
	@Test
	@Sql({"/conta_corrente.sql"})
	public void SaldoContaCorrenteTeste() {

		BigInteger cnpj = new BigInteger("99999999999999");
		BigInteger cpf = new BigInteger("16368579811");
		
		Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);
		Optional<Cliente> cliente = clienteService.findByEmpresaAndCpf(empresa.get(), cpf);
		
		Optional<ContaCorrente> cc = ccService.findByCliente(cliente.get());
		
		System.out.println(cc.get().getSaldo());
		assertEquals(-5.223, cc.get().getSaldo(), 0.0001);
		
	}

}
