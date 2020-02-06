select c.cpf, b.codigo_cupom, b.data_compra,
	   a.quantidade, a.valor_produto,
	   a.valor_desconto, a.valor_item,
	   a.id as item,
	   (select d.bonus
	    from   tipo_cliente_log d
	    where  d.id = a.id_tipo_cliente_log) as bonusNormal,
	   (select e.bonus
	    from   campanha e,
		       campanha_regras f
	    where  f.id = a.id_campanha_regras
	    and    e.id = f.id_campanha) as bonusCampanha,
		a.credito, a.VALOR_DESCONTO, a.saldo
from   cupom_fiscal_item a,
	   cupom_fiscal b,
	   cliente c
where  b.id 			= a.id_cupom_fiscal
and    c.id 				= b.id_cliente
and    c.cpf = 06321239666 --16368579811
order  by  8;
