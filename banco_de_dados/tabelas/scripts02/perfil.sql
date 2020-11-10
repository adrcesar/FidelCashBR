-- Table: public.perfil

-- DROP TABLE public.perfil;

CREATE TABLE public.perfil
(
    id integer NOT NULL DEFAULT nextval('perfil_id_seq'::regclass),
    nome character varying(55) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT perfil_pkey PRIMARY KEY (id),
    CONSTRAINT perfil_nome_uk UNIQUE (nome)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.perfil
    OWNER to postgres;
-- Index: perfil_nome_ind

-- DROP INDEX public.perfil_nome_ind;

CREATE INDEX perfil_nome_ind
    ON public.perfil USING btree
    (nome COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;