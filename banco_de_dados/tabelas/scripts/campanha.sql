--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2020-02-02 23:24:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 25094)
-- Name: campanha; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.campanha (
    id integer DEFAULT nextval('public.campanha_id_seq'::regclass) NOT NULL,
    descricao character varying(255) NOT NULL,
    data_inicio timestamp without time zone NOT NULL,
    data_fim timestamp without time zone NOT NULL,
    id_empresa integer NOT NULL,
    id_campanha_pai integer
);


ALTER TABLE public.campanha OWNER TO postgres;

--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE campanha; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.campanha IS 'grava nome e periodo da promocao';


--
-- TOC entry 2897 (class 0 OID 25094)
-- Dependencies: 226
-- Data for Name: campanha; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.campanha (id, descricao, data_inicio, data_fim, id_empresa, id_campanha_pai) FROM stdin;
\.


--
-- TOC entry 2769 (class 2606 OID 25099)
-- Name: campanha campanha_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campanha
    ADD CONSTRAINT campanha_pkey PRIMARY KEY (id);


--
-- TOC entry 2770 (class 2606 OID 25100)
-- Name: campanha campanha_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.campanha
    ADD CONSTRAINT campanha_empresa_fk FOREIGN KEY (id_empresa) REFERENCES public.empresa(id);


-- Completed on 2020-02-02 23:24:09

--
-- PostgreSQL database dump complete
--

