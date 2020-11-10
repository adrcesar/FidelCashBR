-- Table: public.cupom_fiscal

-- DROP TABLE public.cupom_fiscal;

CREATE TABLE public.cupom_fiscal
(
    id bigint NOT NULL DEFAULT nextval('cupom_fiscal_id_seq'::regclass),
    id_cliente integer NOT NULL,
    codigo_cupom integer NOT NULL,
    data_compra timestamp without time zone NOT NULL,
    valor real NOT NULL,
    arquivo character varying(1020) COLLATE pg_catalog."default",
    CONSTRAINT cupom_fiscal_pkey PRIMARY KEY (id),
    CONSTRAINT cupom_fiscal_id_cliente_codigo_cupom_uk UNIQUE (id_cliente, codigo_cupom),
    CONSTRAINT cupom_fiscal_cliente_fk FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.cupom_fiscal
    OWNER to postgres;