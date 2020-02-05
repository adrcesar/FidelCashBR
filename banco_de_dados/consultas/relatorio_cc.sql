select c.cpf, b.codigo_cupom, b.data_compra,
	   a.quantidade, a.valor_produto,
	   a.valor_desconto, a.valor_item,
	   a.id as item,
	   (select d.bonus
	    from   tipo_cliente_log d,
	           tipo_cliente e
	   where   e.id = d.id_tipo_cliente
	   and     e.id = c.id_tipo_cliente
	   and    b.data_compra >= d.data_inicio
       and    b.data_compra <= COALESCE(d.data_fim, current_timestamp)) as bonus,
		a.credito, a.VALOR_DESCONTO, a.saldo
from   cupom_fiscal_item a,
	   cupom_fiscal b,
	   cliente c
where  b.id 			= a.id_cupom_fiscal
and    c.id 				= b.id_cliente
and    c.cpf = 16368579811
order  by  8;
