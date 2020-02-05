INSERT INTO public.endereco (id, tipo, logradouro, numero_logradouro, complemento_logradouro, bairro, municipio, cep) VALUES (58825, 'EMPRESA', 'RODOVIA ALKINDAR MONTEIRO JUNQUEIRA KM 53 00', 1013, 'KM 53 LOJA 2062', 'CAMPO NOVO', 'BRAGANCA PAULISTA', '12918900');

INSERT INTO public.empresa (id, id_grupo_empresarial, cnpj, nome_razao, nome_fantasia, id_endereco, situacao) VALUES (331, NULL, 99999999999999, 'ADRIANO CESAR FERREIRA SORVETERIA ME', 'ICE CREAMY', 58825, 'ATIVA');

INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5706, 331, '29', 'PRIME PREMIUM MEDIO', 'Un', 12.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5707, 331, '210', 'Pedra 4+ Grande', 'Un', 17.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5708, 331, '23', 'ORIGIN PREMIUM M', 'Un', 12.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5709, 331, '02.', 'Apaixonante M', 'Un', 13.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5710, 331, '5021', 'Ad Cascao Recheado', 'Un', 2, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5711, 331, '63', 'TWIST APAIXONANTE', 'Un', 15.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5712, 331, '68', 'POTE DE MASSA PEQUENO', 'Un', 6.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5713, 331, '18', 'BUBLY DO CHEF PEQUEN', 'Un', 8.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5714, 331, '35.', 'QUALITY PREMIUM M', 'Un', 12.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5715, 331, '28', 'PRIME PREMIUM GRANDE', 'Un', 15.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5716, 331, '15', 'BLUE SKY PEQUENO', 'Un', 8.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5717, 331, '05.', 'Belga Kit Kat M', 'Un', 13.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5718, 331, '11', 'Pedra 4+ Medio', 'Un', 14.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5719, 331, '20', 'CHOQUISSIMO MEDIO', 'Un', 10.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5720, 331, '67', 'POTE DE MASSA MEDIO', 'Un', 8.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5721, 331, '22', 'ORIGIN PREMIUM G', 'Un', 15.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5722, 331, '26', 'INTENSE PREMIUM MED', 'Un', 12.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5723, 331, '56', 'Milk Shake Ninho M', 'Un', 12.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5724, 331, '12', 'Pedra 4+ Pequeno', 'Un', 12.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5725, 331, '69', 'AGUA 300 ML', 'Un', 3, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5726, 331, '08.', 'Belga Toblerone M', 'Un', 13.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5727, 331, '51', 'MILK SHAKE CHOCOLATE PEQU', 'Un', 10.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5728, 331, '27', 'INTENSE PREMIUM PEQU', 'Un', 10.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5729, 331, '33', 'PRIVILEGE PREMIUM PE', 'Un', 10.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5730, 331, '03.', 'Apaixonante P', 'Un', 11.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5731, 331, '25', 'INTENSE PREMIUM GR', 'Un', 15.9, 'ATIVO');
INSERT INTO public.produto (id, id_empresa, codigo_produto, descricao, unidade_comercial, valor, situacao) VALUES (5732, 331, '43', 'Cascao Simples', 'Un', 7.9, 'ATIVO');

INSERT INTO public.tipo_cliente (id, id_empresa, descricao, bonus, situacao) VALUES (312, 331, 'PADRAO', 5, 'ATIVO');

INSERT INTO public.tipo_cliente_log (id, id_tipo_cliente, bonus, data_inicio, data_fim) VALUES (192, 312, 5, '2010-02-05', NULL);

INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4433, NULL, NULL, NULL, NULL, 16368579811, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4434, NULL, NULL, NULL, NULL, 24870666871, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4435, NULL, NULL, NULL, NULL, 15590083850, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4436, NULL, NULL, NULL, NULL, 32131353804, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4437, NULL, NULL, NULL, NULL, 6321239666, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4438, NULL, NULL, NULL, NULL, 21035149800, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4439, NULL, NULL, NULL, NULL, 28506110858, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4440, NULL, NULL, NULL, NULL, 12425084819, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4441, NULL, NULL, NULL, NULL, 16827213889, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4442, NULL, NULL, NULL, NULL, 32167540817, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4443, NULL, NULL, NULL, NULL, 39578687850, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4444, NULL, NULL, NULL, NULL, 27878259828, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4445, NULL, NULL, NULL, NULL, 4355827801, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4446, NULL, NULL, NULL, NULL, 6750270808, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4447, NULL, NULL, NULL, NULL, 15334186832, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4448, NULL, NULL, NULL, NULL, 26554025880, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4449, NULL, NULL, NULL, NULL, 15849202897, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4450, NULL, NULL, NULL, NULL, 3647867870, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4451, NULL, NULL, NULL, NULL, 26846455825, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4452, NULL, NULL, NULL, NULL, 41886351856, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4453, NULL, NULL, NULL, NULL, 47517448870, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4454, NULL, NULL, NULL, NULL, 35009999803, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4455, NULL, NULL, NULL, NULL, 3900267600, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4456, NULL, NULL, NULL, NULL, 12405764806, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4457, NULL, NULL, NULL, NULL, 40494710861, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4458, NULL, NULL, NULL, NULL, 46422114893, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4459, NULL, NULL, NULL, NULL, 8984965863, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4460, NULL, NULL, NULL, NULL, 26844278870, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4461, NULL, NULL, NULL, NULL, 39003209855, 'AGUARDANDO', NULL, 312);
INSERT INTO public.cliente (id, nome, email, ddd_celular, numero_celular, cpf, situacao, id_endereco, id_tipo_cliente) VALUES (4462, NULL, NULL, NULL, NULL, 15503875885, 'AGUARDANDO', NULL, 312);

INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5893, 41823, '2018-08-01 13:28:35', 12.9, 4433);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5894, 41902, '2018-08-04 16:48:08', 30.8, 4434);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5895, 41920, '2018-08-04 18:44:04', 28.8, 4435);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5896, 41923, '2018-08-04 19:15:18', 31.7, 4436);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5897, 41935, '2018-08-04 20:30:16', 28.8, 4437);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5898, 41949, '2018-08-04 21:42:00', 24.8, 4438);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5899, 41971, '2018-08-05 15:18:05', 14.9, 4439);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5900, 41991, '2018-08-05 16:48:50', 38.7, 4440);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5901, 41998, '2018-08-05 17:14:21', 17.8, 4441);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5902, 42076, '2018-08-08 15:23:21', 26.8, 4442);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5903, 42136, '2018-08-10 14:20:11', 15.9, 4443);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5904, 42169, '2018-08-10 21:58:48', 17.9, 4444);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5905, 42179, '2018-08-11 14:24:19', 34.7, 4445);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5906, 42278, '2018-08-12 15:05:21', 30.8, 4446);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5907, 42317, '2018-08-12 16:29:23', 25.7, 4447);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5908, 42593, '2018-08-19 14:48:19', 25.8, 4448);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5909, 42611, '2018-08-19 15:24:17', 14.9, 4449);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5910, 42614, '2018-08-19 15:38:19', 39.7, 4450);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5911, 42635, '2018-08-19 16:38:38', 31.8, 4451);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5912, 42646, '2018-08-19 17:18:28', 13.9, 4452);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5913, 42669, '2018-08-19 18:37:14', 12.9, 4453);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5914, 42679, '2018-08-19 19:26:08', 10.9, 4454);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5915, 42766, '2018-08-23 13:34:08', 9.81, 4455);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5916, 42813, '2018-08-24 15:25:01', 13.9, 4456);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5917, 42886, '2018-08-25 16:01:33', 16.9, 4457);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5918, 42893, '2018-08-25 16:38:16', 38.7, 4458);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5919, 43036, '2018-08-27 17:17:55', 16.9, 4459);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5920, 43092, '2018-08-30 11:18:44', 15.9, 4460);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5921, 43141, '2018-08-30 21:55:41', 14.9, 4433);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5922, 43152, '2018-08-31 15:20:52', 13.9, 4461);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5923, 43166, '2018-08-31 18:09:49', 12.72, 4433);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5924, 43172, '2018-08-31 19:22:39', 13.9, 4433);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5925, 43175, '2018-08-31 20:35:10', 15.8, 4433);
INSERT INTO public.cupom_fiscal (id, codigo_cupom, data_compra, valor, id_cliente) VALUES (5926, 43183, '2018-08-31 21:28:22', 30.8, 4462);

INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6779, 4434, 1.54);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6780, 4435, 1.4399999);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6781, 4436, 1.585);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6782, 4437, 1.44);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6783, 4438, 1.24);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6784, 4439, 0.745);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6785, 4440, 1.935);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6786, 4441, 0.89);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6787, 4442, 1.34);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6788, 4443, 0.795);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6789, 4444, 0.895);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6790, 4445, 1.7349999);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6791, 4446, 1.54);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6792, 4447, 1.285);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6793, 4448, 1.29);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6794, 4449, 0.745);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6795, 4450, 1.985);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6796, 4451, 1.59);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6797, 4452, 0.695);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6798, 4453, 0.645);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6799, 4454, 0.545);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6800, 4455, -0.65400004);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6801, 4456, 0.695);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6802, 4457, 0.845);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6803, 4458, 1.935);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6804, 4459, 0.845);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6805, 4460, 0.795);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6806, 4461, 0.69500005);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6778, 4433, -5.223);
INSERT INTO public.conta_corrente (id, id_cliente, saldo) VALUES (6807, 4462, 1.5400001);

INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20065, 5893, 5706, 1, 12.9, 0, 12.9, 0.645, 0.645, 6778, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20066, 5894, 5707, 1, 17.9, 0, 17.9, 0.895, 0.895, 6779, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20067, 5894, 5708, 1, 12.9, 0, 12.9, 0.645, 1.54, 6779, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20068, 5895, 5708, 1, 12.9, 0, 12.9, 0.645, 0.645, 6780, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20069, 5895, 5709, 1, 13.9, 0, 13.9, 0.695, 1.3399999, 6780, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20070, 5895, 5710, 1, 2, 0, 2, 0.1, 1.4399999, 6780, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20071, 5896, 5711, 1, 15.9, 0, 15.9, 0.795, 0.795, 6781, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20072, 5896, 5712, 1, 6.9, 0, 6.9, 0.345, 1.14, 6781, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20073, 5896, 5713, 1, 8.9, 0, 8.9, 0.445, 1.585, 6781, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20074, 5897, 5714, 1, 12.9, 0, 12.9, 0.645, 0.645, 6782, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20075, 5897, 5715, 1, 15.9, 0, 15.9, 0.795, 1.44, 6782, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20076, 5898, 5716, 1, 8.9, 0, 8.9, 0.445, 0.445, 6783, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20077, 5898, 5717, 1, 13.9, 0, 13.9, 0.695, 1.14, 6783, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20078, 5898, 5710, 1, 2, 0, 2, 0.1, 1.24, 6783, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20079, 5899, 5718, 1, 14.9, 0, 14.9, 0.745, 0.745, 6784, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20080, 5900, 5709, 1, 13.9, 0, 13.9, 0.695, 0.695, 6785, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20081, 5900, 5709, 1, 13.9, 0, 13.9, 0.695, 1.39, 6785, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20082, 5900, 5719, 1, 10.9, 0, 10.9, 0.545, 1.935, 6785, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20083, 5901, 5720, 1, 8.9, 0, 8.9, 0.445, 0.445, 6786, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20084, 5901, 5720, 1, 8.9, 0, 8.9, 0.445, 0.89, 6786, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20085, 5902, 5721, 1, 15.9, 0, 15.9, 0.795, 0.795, 6787, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20086, 5902, 5719, 1, 10.9, 0, 10.9, 0.545, 1.34, 6787, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20087, 5903, 5717, 1, 13.9, 0, 13.9, 0.695, 0.695, 6788, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20088, 5903, 5710, 1, 2, 0, 2, 0.1, 0.795, 6788, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20089, 5904, 5707, 1, 17.9, 0, 17.9, 0.895, 0.895, 6789, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20090, 5905, 5722, 1, 12.9, 0, 12.9, 0.645, 0.645, 6790, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20091, 5905, 5720, 1, 8.9, 0, 8.9, 0.445, 1.0899999, 6790, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20092, 5905, 5723, 1, 12.9, 0, 12.9, 0.645, 1.7349999, 6790, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20093, 5906, 5724, 1, 12.9, 0, 12.9, 0.645, 0.645, 6791, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20094, 5906, 5718, 1, 14.9, 0, 14.9, 0.745, 1.39, 6791, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20095, 5906, 5725, 1, 3, 0, 3, 0.15, 1.54, 6791, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20096, 5907, 5720, 1, 8.9, 0, 8.9, 0.445, 0.445, 6792, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20097, 5907, 5712, 1, 6.9, 0, 6.9, 0.345, 0.78999996, 6792, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20098, 5907, 5712, 1, 6.9, 0, 6.9, 0.345, 1.135, 6792, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20099, 5907, 5725, 1, 3, 0, 3, 0.15, 1.285, 6792, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20100, 5908, 5724, 1, 12.9, 0, 12.9, 0.645, 0.645, 6793, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20101, 5908, 5724, 1, 12.9, 0, 12.9, 0.645, 1.29, 6793, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20102, 5909, 5718, 1, 14.9, 0, 14.9, 0.745, 0.745, 6794, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20103, 5910, 5707, 1, 17.9, 0, 17.9, 0.895, 0.895, 6795, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20104, 5910, 5718, 1, 14.9, 0, 14.9, 0.745, 1.64, 6795, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20105, 5910, 5712, 1, 6.9, 0, 6.9, 0.345, 1.985, 6795, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20106, 5911, 5726, 1, 13.9, 0, 13.9, 0.695, 0.695, 6796, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20107, 5911, 5726, 1, 13.9, 0, 13.9, 0.695, 1.39, 6796, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20108, 5911, 5710, 1, 2, 0, 2, 0.1, 1.49, 6796, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20109, 5911, 5710, 1, 2, 0, 2, 0.1, 1.59, 6796, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20110, 5912, 5709, 1, 13.9, 0, 13.9, 0.695, 0.695, 6797, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20111, 5913, 5708, 1, 12.9, 0, 12.9, 0.645, 0.645, 6798, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20112, 5914, 5727, 1, 10.9, 0, 10.9, 0.545, 0.545, 6799, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20113, 5915, 5728, 1, 10.9, 1.09, 9.81, 0.43600002, -0.65400004, 6800, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20114, 5916, 5709, 1, 13.9, 0, 13.9, 0.695, 0.695, 6801, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20115, 5917, 5718, 1, 14.9, 0, 14.9, 0.745, 0.745, 6802, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20116, 5917, 5710, 1, 2, 0, 2, 0.1, 0.845, 6802, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20117, 5918, 5709, 1, 13.9, 0, 13.9, 0.695, 0.695, 6803, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20118, 5918, 5729, 1, 10.9, 0, 10.9, 0.545, 1.24, 6803, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20119, 5918, 5709, 1, 13.9, 0, 13.9, 0.695, 1.935, 6803, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20120, 5919, 5718, 1, 14.9, 0, 14.9, 0.745, 0.745, 6804, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20121, 5919, 5710, 1, 2, 0, 2, 0.1, 0.845, 6804, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20122, 5920, 5709, 1, 13.9, 0, 13.9, 0.695, 0.695, 6805, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20123, 5920, 5710, 1, 2, 0, 2, 0.1, 0.795, 6805, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20124, 5921, 5718, 1, 14.9, 0, 14.9, 0.745, 1.39, 6778, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20125, 5922, 5730, 1, 11.9, 0, 11.9, 0.595, 0.595, 6806, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20126, 5922, 5710, 1, 2, 0, 2, 0.1, 0.69500005, 6806, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20127, 5923, 5731, 1, 15.9, 3.18, 12.72, 0.477, -1.3130001, 6778, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20128, 5924, 5709, 1, 13.9, 0, 13.9, 0.695, -0.6180001, 6778, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20129, 5925, 5732, 1, 7.9, 5, 2.9, 0, -5.618, 6778, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20130, 5925, 5732, 1, 7.9, 0, 7.9, 0.395, -5.223, 6778, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20131, 5926, 5718, 1, 14.9, 0, 14.9, 0.745, 0.745, 6807, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20132, 5926, 5710, 1, 2, 0, 2, 0.1, 0.845, 6807, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20133, 5926, 5730, 1, 11.9, 0, 11.9, 0.595, 1.44, 6807, 192);
INSERT INTO public.cupom_fiscal_item (id, id_cupom_fiscal, id_produto, quantidade, valor_produto, valor_desconto, valor_item, credito, saldo, id_conta_corrente, id_tipo_cliente_log) VALUES (20134, 5926, 5710, 1, 2, 0, 2, 0.1, 1.5400001, 6807, 192);