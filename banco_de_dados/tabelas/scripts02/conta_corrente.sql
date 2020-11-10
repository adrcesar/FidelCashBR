-- Table: public.conta_corrente

-- DROP TABLE public.conta_corrente;

CREATE TABLE public.conta_corrente
(
    id integer NOT NULL DEFAULT nextval('conta_corrente_id_seq'::regclass),
    id_cliente integer NOT NULL,
    saldo real NOT NULL,
    CONSTRAINT conta_corrente_pkey PRIMARY KEY (id),
    CONSTRAINT conta_corrente_cliente_fk FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.conta_corrente
    OWNER to postgres;
-- Index: conta_corrente_id_cliente_ind

-- DROP INDEX public.conta_corrente_id_cliente_ind;

CREATE INDEX conta_corrente_id_cliente_ind
    ON public.conta_corrente USING btree
    (id_cliente ASC NULLS LAST)
    TABLESPACE pg_default;