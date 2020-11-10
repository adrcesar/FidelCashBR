-- Table: public.tipo_cliente

-- DROP TABLE public.tipo_cliente;

CREATE TABLE public.tipo_cliente
(
    id integer NOT NULL DEFAULT nextval('tipo_cliente_id_seq'::regclass),
    id_empresa integer NOT NULL,
    descricao character varying(55) COLLATE pg_catalog."default" NOT NULL,
    bonus real NOT NULL,
    situacao character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipo_cliente_pkey PRIMARY KEY (id),
    CONSTRAINT tipo_cliente_id_empresa_descricao_uk UNIQUE (id_empresa, descricao),
    CONSTRAINT tipo_cliente_empresa_fk FOREIGN KEY (id_empresa)
        REFERENCES public.empresa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tipo_cliente_situacao_ck CHECK (situacao::bpchar = ANY (ARRAY['ATIVO'::bpchar, 'INATIVO'::bpchar]))
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.tipo_cliente
    OWNER to postgres;
-- Index: tipo_cliente_empresa_ind

-- DROP INDEX public.tipo_cliente_empresa_ind;

CREATE INDEX tipo_cliente_empresa_ind
    ON public.tipo_cliente USING btree
    (id_empresa ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: tipo_cliente_id_empresa_descricao_ind

-- DROP INDEX public.tipo_cliente_id_empresa_descricao_ind;

CREATE INDEX tipo_cliente_id_empresa_descricao_ind
    ON public.tipo_cliente USING btree
    (id_empresa ASC NULLS LAST, descricao COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;