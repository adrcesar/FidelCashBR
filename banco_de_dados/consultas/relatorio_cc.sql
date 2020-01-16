select tipo_cliente.id_empresa, cliente.cpf, cupom_fiscal.codigo_cupom, cupom_fiscal.data_compra,
	   cupom_fiscal_item.quantidade, cupom_fiscal_item.valor_produto,
	   cupom_fiscal_item.valor_desconto, cupom_fiscal_item.valor_item,
	   cupom_fiscal_item.id,
	   tipo_cliente_log.bonus,
		cupom_fiscal_item.credito, cupom_fiscal_item.VALOR_DESCONTO, cupom_fiscal_item.saldo
from   cupom_fiscal_item,
	   cupom_fiscal,
	   cliente,
	   tipo_cliente,
	   tipo_cliente_log
where  cupom_fiscal.id 			= cupom_fiscal_item.id_cupom_fiscal
and    cliente.id 				= cupom_fiscal.id_cliente
and    tipo_cliente.id 			= cliente.id_tipo_cliente
and    tipo_cliente.id          = tipo_cliente_log.id_tipo_cliente
and    cupom_fiscal.data_compra >= tipo_cliente_log.data_inicio
and    cupom_fiscal.data_compra <= COALESCE(tipo_cliente_log.data_fim, current_timestamp)
and    cliente.cpf = 16368579811
order  by  1, 9;
--
