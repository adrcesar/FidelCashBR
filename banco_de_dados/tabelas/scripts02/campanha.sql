-- Table: public.campanha

-- DROP TABLE public.campanha;

CREATE TABLE public.campanha
(
    id integer NOT NULL DEFAULT nextval('campanha_id_seq'::regclass),
    id_empresa integer NOT NULL,
    descricao character varying(255) COLLATE pg_catalog."default" NOT NULL,
    data_inicio timestamp without time zone NOT NULL,
    data_fim timestamp without time zone NOT NULL,
    bonus real NOT NULL,
    id_campanha_pai integer,
    CONSTRAINT campanha_pkey PRIMARY KEY (id),
    CONSTRAINT campanha_campanha_pai_fk FOREIGN KEY (id_campanha_pai)
        REFERENCES public.campanha (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT campanha_empresa_fk FOREIGN KEY (id_empresa)
        REFERENCES public.empresa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.campanha
    OWNER to postgres;
-- Index: campanha_id_campanha_pai_ind

-- DROP INDEX public.campanha_id_campanha_pai_ind;

CREATE INDEX campanha_id_campanha_pai_ind
    ON public.campanha USING btree
    (id_campanha_pai ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: campanha_id_empresa_ind

-- DROP INDEX public.campanha_id_empresa_ind;

CREATE INDEX campanha_id_empresa_ind
    ON public.campanha USING btree
    (id_empresa ASC NULLS LAST)
    TABLESPACE pg_default;