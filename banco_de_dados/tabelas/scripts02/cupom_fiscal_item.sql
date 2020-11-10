-- Table: public.cupom_fiscal_item

-- DROP TABLE public.cupom_fiscal_item;

CREATE TABLE public.cupom_fiscal_item
(
    id bigint NOT NULL DEFAULT nextval('cupom_fiscal_item_id_seq'::regclass),
    id_cupom_fiscal bigint NOT NULL,
    id_produto integer NOT NULL,
    quantidade real NOT NULL,
    valor_produto real NOT NULL,
    valor_desconto real NOT NULL,
    valor_item real NOT NULL,
    credito real,
    debito real,
    saldo real,
    id_conta_corrente integer NOT NULL,
    id_tipo_cliente_log integer,
    id_campanha_regras integer,
    CONSTRAINT cupom_fiscal_item_pkey PRIMARY KEY (id),
    CONSTRAINT cupom_fiscal_item_campanha_regras_fk FOREIGN KEY (id_campanha_regras)
        REFERENCES public.campanha_regras (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cupom_fiscal_item_conta_corrente_fk FOREIGN KEY (id_conta_corrente)
        REFERENCES public.conta_corrente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cupom_fiscal_item_cupom_fiscal_fk FOREIGN KEY (id_cupom_fiscal)
        REFERENCES public.cupom_fiscal (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cupom_fiscal_item_produto_fk FOREIGN KEY (id_produto)
        REFERENCES public.produto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cupom_fiscal_item_tipo_cliente_log_fk FOREIGN KEY (id_tipo_cliente_log)
        REFERENCES public.tipo_cliente_log (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.cupom_fiscal_item
    OWNER to postgres;
-- Index: cupom_fiscal_item_id_campanha_regras_ind

-- DROP INDEX public.cupom_fiscal_item_id_campanha_regras_ind;

CREATE INDEX cupom_fiscal_item_id_campanha_regras_ind
    ON public.cupom_fiscal_item USING btree
    (id_campanha_regras ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: cupom_fiscal_item_id_conta_corrente_ind

-- DROP INDEX public.cupom_fiscal_item_id_conta_corrente_ind;

CREATE INDEX cupom_fiscal_item_id_conta_corrente_ind
    ON public.cupom_fiscal_item USING btree
    (id_conta_corrente ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: cupom_fiscal_item_id_cupom_fiscal_ind

-- DROP INDEX public.cupom_fiscal_item_id_cupom_fiscal_ind;

CREATE INDEX cupom_fiscal_item_id_cupom_fiscal_ind
    ON public.cupom_fiscal_item USING btree
    (id_cupom_fiscal ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: cupom_fiscal_item_id_produto_ind

-- DROP INDEX public.cupom_fiscal_item_id_produto_ind;

CREATE INDEX cupom_fiscal_item_id_produto_ind
    ON public.cupom_fiscal_item USING btree
    (id_produto ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: cupom_fiscal_item_id_tipo_cliente_log_ind

-- DROP INDEX public.cupom_fiscal_item_id_tipo_cliente_log_ind;

CREATE INDEX cupom_fiscal_item_id_tipo_cliente_log_ind
    ON public.cupom_fiscal_item USING btree
    (id_tipo_cliente_log ASC NULLS LAST)
    TABLESPACE pg_default;