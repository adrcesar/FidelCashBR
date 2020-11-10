-- Table: public.tipo_cliente_log

-- DROP TABLE public.tipo_cliente_log;

CREATE TABLE public.tipo_cliente_log
(
    id integer NOT NULL DEFAULT nextval('tipo_cliente_log_id_seq'::regclass),
    id_tipo_cliente integer NOT NULL,
    bonus real NOT NULL,
    data_inicio timestamp without time zone NOT NULL,
    data_fim timestamp without time zone,
    CONSTRAINT tipo_cliente_log_pkey PRIMARY KEY (id),
    CONSTRAINT tipo_cliente_log_tipo_cliente_fk FOREIGN KEY (id_tipo_cliente)
        REFERENCES public.tipo_cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.tipo_cliente_log
    OWNER to postgres;
-- Index: tipo_cliente_log_id_tipo_cliente

-- DROP INDEX public.tipo_cliente_log_id_tipo_cliente;

CREATE INDEX tipo_cliente_log_id_tipo_cliente
    ON public.tipo_cliente_log USING btree
    (id_tipo_cliente ASC NULLS LAST)
    TABLESPACE pg_default;