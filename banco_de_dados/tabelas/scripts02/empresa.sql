-- Table: public.empresa

-- DROP TABLE public.empresa;

CREATE TABLE public.empresa
(
    id integer NOT NULL DEFAULT nextval('empresa_id_seq'::regclass),
    cnpj bigint NOT NULL,
    nome_razao character varying(255) COLLATE pg_catalog."default" NOT NULL,
    nome_fantasia character varying(255) COLLATE pg_catalog."default" NOT NULL,
    situacao character varying(15) COLLATE pg_catalog."default" NOT NULL,
    id_endereco integer NOT NULL,
    id_grupo_empresarial integer,
    CONSTRAINT empresa_pkey PRIMARY KEY (id),
    CONSTRAINT empresa_cnpj_uk UNIQUE (cnpj),
    CONSTRAINT empresa_endereco_fk FOREIGN KEY (id_endereco)
        REFERENCES public.endereco (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT empresa_grupo_empresarial_fk FOREIGN KEY (id_grupo_empresarial)
        REFERENCES public.grupo_empresarial (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT empresa_situacao_ck CHECK (situacao::bpchar = ANY (ARRAY['ATIVA'::bpchar, 'INATIVA'::bpchar]))
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.empresa
    OWNER to postgres;
-- Index: empresa_cnpj_ind

-- DROP INDEX public.empresa_cnpj_ind;

CREATE INDEX empresa_cnpj_ind
    ON public.empresa USING btree
    (cnpj ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: empresa_id_endereco_ind

-- DROP INDEX public.empresa_id_endereco_ind;

CREATE INDEX empresa_id_endereco_ind
    ON public.empresa USING btree
    (id_endereco ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: empresa_id_grupo_empresarial_ind

-- DROP INDEX public.empresa_id_grupo_empresarial_ind;

CREATE INDEX empresa_id_grupo_empresarial_ind
    ON public.empresa USING btree
    (id_grupo_empresarial ASC NULLS LAST)
    TABLESPACE pg_default;