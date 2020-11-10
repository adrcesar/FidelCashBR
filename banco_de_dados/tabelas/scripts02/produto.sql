-- Table: public.produto

-- DROP TABLE public.produto;

CREATE TABLE public.produto
(
    id integer NOT NULL DEFAULT nextval('produto_id_seq'::regclass),
    id_empresa integer NOT NULL,
    codigo_produto character varying(55) COLLATE pg_catalog."default" NOT NULL,
    descricao character varying(55) COLLATE pg_catalog."default" NOT NULL,
    unidade_comercial character varying(15) COLLATE pg_catalog."default" NOT NULL,
    valor real NOT NULL,
    situacao character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (id),
    CONSTRAINT produto_id_empresa_codigo_produto_uk UNIQUE (id_empresa, codigo_produto),
    CONSTRAINT produto_empresa_fk FOREIGN KEY (id_empresa)
        REFERENCES public.empresa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT produto_situacao_ck CHECK (situacao::bpchar = ANY (ARRAY['ATIVO'::bpchar, 'INATIVO'::bpchar]))
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.produto
    OWNER to postgres;
-- Index: produto_id_empresa_codigo_produto_ind

-- DROP INDEX public.produto_id_empresa_codigo_produto_ind;

CREATE INDEX produto_id_empresa_codigo_produto_ind
    ON public.produto USING btree
    (id_empresa ASC NULLS LAST, codigo_produto COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: produto_id_empresa_ind

-- DROP INDEX public.produto_id_empresa_ind;

CREATE INDEX produto_id_empresa_ind
    ON public.produto USING btree
    (id_empresa ASC NULLS LAST)
    TABLESPACE pg_default;