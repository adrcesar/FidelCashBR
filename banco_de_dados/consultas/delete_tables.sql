delete from util where id_empresa is not null;
delete from conta_corrente;
delete from cupom_fiscal_item;
delete from cupom_fiscal;
delete from cliente;
delete from tipo_cliente_log;
delete from tipo_cliente;
delete from produto;
delete from empresa;
delete from grupo_empresarial;
delete from endereco;
commit;
--delete from util where id = 101
select * from util;