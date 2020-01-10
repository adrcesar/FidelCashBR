-- Table: public.conta_corrente

-- DROP TABLE public.conta_corrente;

CREATE TABLE public.conta_corrente
(
    id bigint NOT NULL DEFAULT nextval('conta_corrente_id_seq'::regclass),
    id_cupom_fiscal_item bigint NOT NULL,
    credito real NOT NULL,
    debito real NOT NULL,
    saldo real NOT NULL,
    id_tipo_cliente_log integer,
    CONSTRAINT conta_corrente_pkey PRIMARY KEY (id),
    CONSTRAINT conta_corrente_cupom_fiscal_item_fk FOREIGN KEY (id_cupom_fiscal_item)
        REFERENCES public.cupom_fiscal_item (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT conta_corrente_tipo_cliente_log_fk FOREIGN KEY (id_tipo_cliente_log)
        REFERENCES public.tipo_cliente_log (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.conta_corrente
    OWNER to postgres;
COMMENT ON TABLE public.conta_corrente
    IS 'conta corrente dos clientes';

-- Index: conta_corrente_id_cupom_fiscal_item_index

-- DROP INDEX public.conta_corrente_id_cupom_fiscal_item_index;

CREATE INDEX conta_corrente_id_cupom_fiscal_item_index
    ON public.conta_corrente USING btree
    (id_cupom_fiscal_item)
    TABLESPACE pg_default;

-- Index: conta_corrente_id_tipo_cliente_log_index

-- DROP INDEX public.conta_corrente_id_tipo_cliente_log_index;

CREATE INDEX conta_corrente_id_tipo_cliente_log_index
    ON public.conta_corrente USING btree
    (id_tipo_cliente_log)
    INCLUDE(id_tipo_cliente_log)
    TABLESPACE pg_default;