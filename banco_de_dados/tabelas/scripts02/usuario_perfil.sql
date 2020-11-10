-- Table: public.usuario_perfil

-- DROP TABLE public.usuario_perfil;

CREATE TABLE public.usuario_perfil
(
    id integer NOT NULL DEFAULT nextval('usuario_perfil_id_seq'::regclass),
    id_usuario integer NOT NULL,
    id_perfil integer NOT NULL,
    id_grupo_empresarial integer,
    id_empresa integer,
    situacao character varying(55) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuario_perfil_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_perfil_empresa FOREIGN KEY (id_empresa)
        REFERENCES public.empresa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT usuario_perfil_grupo_empresarial_fk FOREIGN KEY (id_grupo_empresarial)
        REFERENCES public.grupo_empresarial (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT usuario_perfil_perfil_fk FOREIGN KEY (id_perfil)
        REFERENCES public.perfil (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT usuario_perfil_usuario_fk FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario_perfil
    OWNER to postgres;