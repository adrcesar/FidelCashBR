select * from usuario;

select * from perfil;

select * from usuario_perfil;

INSERT INTO public.usuario_perfil(
	id_usuario, id_perfil, situacao)
	VALUES (27, 29, 'ATIVO');