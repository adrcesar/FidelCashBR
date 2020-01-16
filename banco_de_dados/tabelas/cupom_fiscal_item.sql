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
    credito real NOT NULL,
    saldo real NOT NULL,
    id_conta_corrente integer,
    CONSTRAINT cupom_fiscal_item_pk PRIMARY KEY (id),
    CONSTRAINT cupom_fiscal_item_conta_corrente_fk FOREIGN KEY (id_conta_corrente)
        REFERENCES public.conta_corrente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT cupom_fiscal_item_cupom_fiscal_fk FOREIGN KEY (id_cupom_fiscal)
        REFERENCES public.cupom_fiscal (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cupom_fiscal_item_produto_fk FOREIGN KEY (id_produto)
        REFERENCES public.produto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.cupom_fiscal_item
    OWNER to postgres;

-- Index: cupom_fiscal_item_id_conta_corrente_index

-- DROP INDEX public.cupom_fiscal_item_id_conta_corrente_index;

CREATE INDEX cupom_fiscal_item_id_conta_corrente_index
    ON public.cupom_fiscal_item USING btree
    (id_conta_corrente)
    TABLESPACE pg_default;

-- Index: cupom_fiscal_item_id_cupom_fiscal_index

-- DROP INDEX public.cupom_fiscal_item_id_cupom_fiscal_index;

CREATE INDEX cupom_fiscal_item_id_cupom_fiscal_index
    ON public.cupom_fiscal_item USING btree
    (id_cupom_fiscal)
    TABLESPACE pg_default;

-- Index: cupom_fiscal_item_id_produto_index

-- DROP INDEX public.cupom_fiscal_item_id_produto_index;

CREATE INDEX cupom_fiscal_item_id_produto_index
    ON public.cupom_fiscal_item USING btree
    (id_produto)
    TABLESPACE pg_default;