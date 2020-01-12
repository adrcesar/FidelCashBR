select tipo_cliente.id_empresa, cliente.cpf, cupom_fiscal.codigo_cupom, cupom_fiscal.data_compra,
	   cupom_fiscal_item.quantidade, cupom_fiscal_item.valor_produto,
	   cupom_fiscal_item.valor_desconto, cupom_fiscal_item.valor_item,
	   conta_corrente.id, 
	   (select tipo_cliente_log.bonus
	    from   tipo_cliente_log
	    where  tipo_cliente_log.id = conta_corrente.id_tipo_cliente_log),
		conta_corrente.credito, conta_corrente.debito, conta_corrente.saldo
from   conta_corrente,
	   cupom_fiscal_item,
	   cupom_fiscal,
	   cliente,
	   tipo_cliente
where  cupom_fiscal_item.id 	= conta_corrente.id_cupom_fiscal_item  
and    cupom_fiscal.id 			= cupom_fiscal_item.id_cupom_fiscal
and    cliente.id 				= cupom_fiscal.id_cliente
and    tipo_cliente.id 			= cliente.id_tipo_cliente
and    cliente.cpf = 16368579811
order  by 1, 2, 3, 9;
--
