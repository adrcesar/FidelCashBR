-- cpf 16368579811
select 0 as bonus, data_compra, id_cliente, cpf
from  cupom_fiscal,
      cliente
where cliente.id = cupom_fiscal.id_cliente
and data_compra <= '2018-08-31'
and   data_compra >= '2018-08-24'
union
select 6 as bonus, data_compra, id_cliente, cpf
from  cupom_fiscal,
      cliente
where cliente.id = cupom_fiscal.id_cliente
and data_compra <= '2018-08-23'
and   data_compra >= '2018-08-16'
union
select 8 as bonus, data_compra, id_cliente, cpf
from  cupom_fiscal,
      cliente
where cliente.id = cupom_fiscal.id_cliente
and  data_compra <= '2018-08-15'
and   data_compra >= '2018-08-08'
union
select 10 as bonus, data_compra, id_cliente, cpf
from  cupom_fiscal,
      cliente
where cliente.id = cupom_fiscal.id_cliente
and   data_compra <= '2018-08-07'
and   data_compra >= '2018-08-01'
order by  1, 3

