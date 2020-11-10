-- Table: public.usuario

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario
(
    id integer NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
    usuario character varying(55) COLLATE pg_catalog."default" NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(25) COLLATE pg_catalog."default" NOT NULL,
    situacao character varying(25) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_situacao_ck CHECK (situacao::bpchar = ANY (ARRAY['ATIVO'::bpchar, 'INATIVO'::bpchar]))
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to postgres;