-- Table: public.campanha_regras

-- DROP TABLE public.campanha_regras;

CREATE TABLE public.campanha_regras
(
    id integer NOT NULL DEFAULT nextval('campanha_regras_id_seq'::regclass),
    tipo_selecao_cliente character varying(25) COLLATE pg_catalog."default" NOT NULL,
    id_cliente integer,
    tipo_selecao_produto character varying(25) COLLATE pg_catalog."default" NOT NULL,
    id_produto integer,
    id_campanha integer NOT NULL,
    CONSTRAINT campanha_regras_pkey PRIMARY KEY (id),
    CONSTRAINT campanha_regras_campanha_fk FOREIGN KEY (id_campanha)
        REFERENCES public.campanha (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT campanha_regras_cliente_fk FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT campanha_regras_produto_fk FOREIGN KEY (id_produto)
        REFERENCES public.produto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT campanha_regras_tipo_selecao_cliente_ck CHECK (tipo_selecao_cliente::bpchar = ANY (ARRAY['INDIVIDUAL'::bpchar, 'TODOS'::bpchar])) NOT VALID,
    CONSTRAINT campanha_regras_tipo_selecao_produto_ck CHECK (tipo_selecao_produto::bpchar = ANY (ARRAY['INDIVIDUAL'::bpchar, 'TODOS'::bpchar])) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.campanha_regras
    OWNER to postgres;