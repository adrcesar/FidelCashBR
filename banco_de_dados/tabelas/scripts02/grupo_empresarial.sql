-- Table: public.grupo_empresarial

-- DROP TABLE public.grupo_empresarial;

CREATE TABLE public.grupo_empresarial
(
    id integer NOT NULL DEFAULT nextval('grupo_empresarial_id_seq'::regclass),
    descricao character varying(55) COLLATE pg_catalog."default" NOT NULL,
    situacao character varying(55) COLLATE pg_catalog."default" NOT NULL,
    id_endereco integer NOT NULL,
    CONSTRAINT grupo_empresarial_pk PRIMARY KEY (id),
    CONSTRAINT grupo_empresarial_endereco_fk FOREIGN KEY (id_endereco)
        REFERENCES public.endereco (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT grupo_empresarial_situacao_ck CHECK (situacao::bpchar = ANY (ARRAY['ATIVO'::bpchar, 'INATIVO'::bpchar])) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.grupo_empresarial
    OWNER to postgres;
-- Index: grupo_empresarial_id_endereco_ind

-- DROP INDEX public.grupo_empresarial_id_endereco_ind;

CREATE INDEX grupo_empresarial_id_endereco_ind
    ON public.grupo_empresarial USING btree
    (id_endereco ASC NULLS LAST)
    TABLESPACE pg_default;