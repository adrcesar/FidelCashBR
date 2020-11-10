-- Table: public.endereco

-- DROP TABLE public.endereco;

CREATE TABLE public.endereco
(
    id integer NOT NULL DEFAULT nextval('endereco_id_seq'::regclass),
    tipo character varying(55) COLLATE pg_catalog."default" NOT NULL,
    logradouro character varying(255) COLLATE pg_catalog."default",
    numero_logradouro integer,
    complemento_logradouro character varying(255) COLLATE pg_catalog."default",
    bairro character varying(255) COLLATE pg_catalog."default",
    municipio character varying(255) COLLATE pg_catalog."default",
    cep character varying(15) COLLATE pg_catalog."default",
    CONSTRAINT endereco_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.endereco
    OWNER to postgres;