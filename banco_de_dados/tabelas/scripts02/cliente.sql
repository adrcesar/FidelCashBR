-- Table: public.cliente

-- DROP TABLE public.cliente;

CREATE TABLE public.cliente
(
    id integer NOT NULL DEFAULT nextval('cliente_id_seq'::regclass),
    nome character(55) COLLATE pg_catalog."default",
    email character(55) COLLATE pg_catalog."default",
    ddd_celular integer,
    numero_celular integer,
    cpf bigint,
    situacao character(15) COLLATE pg_catalog."default" NOT NULL,
    id_endereco integer,
    id_tipo_cliente integer NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id),
    CONSTRAINT cliente_tipo_cliente_cpf_uk UNIQUE (id_tipo_cliente, cpf),
    CONSTRAINT cliente_endereco_fk FOREIGN KEY (id_endereco)
        REFERENCES public.endereco (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cliente_tipo_cliente_fk FOREIGN KEY (id_tipo_cliente)
        REFERENCES public.tipo_cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cliente_situacao_ck CHECK (situacao = ANY (ARRAY['ATIVO'::bpchar, 'INATIVO'::bpchar]))
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.cliente
    OWNER to postgres;
-- Index: cliente_id_endereco_ind

-- DROP INDEX public.cliente_id_endereco_ind;

CREATE INDEX cliente_id_endereco_ind
    ON public.cliente USING btree
    (id_endereco ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: cliente_id_tipo_cliente_cpf_ind

-- DROP INDEX public.cliente_id_tipo_cliente_cpf_ind;

CREATE INDEX cliente_id_tipo_cliente_cpf_ind
    ON public.cliente USING btree
    (id_tipo_cliente ASC NULLS LAST, cpf ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: cliente_id_tipo_cliente_ind

-- DROP INDEX public.cliente_id_tipo_cliente_ind;

CREATE INDEX cliente_id_tipo_cliente_ind
    ON public.cliente USING btree
    (id_tipo_cliente ASC NULLS LAST)
    TABLESPACE pg_default;