--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2020-02-02 23:25:33

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
-- TOC entry 207 (class 1259 OID 16584)
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    id integer DEFAULT nextval('public.empresa_id_seq'::regclass) NOT NULL,
    id_grupo_empresarial integer,
    cnpj bigint NOT NULL,
    nome_razao character varying(255) NOT NULL,
    nome_fantasia character varying(255) NOT NULL,
    id_endereco integer NOT NULL,
    situacao character varying(15) NOT NULL
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- TOC entry 2903 (class 0 OID 16584)
-- Dependencies: 207
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.empresa (id, id_grupo_empresarial, cnpj, nome_razao, nome_fantasia, id_endereco, situacao) FROM stdin;
291	\N	99999999999999	ADRIANO CESAR FERREIRA SORVETERIA ME	ICE CREAMY	58785	ATIVA
\.


--
-- TOC entry 2770 (class 2606 OID 16594)
-- Name: empresa empresa_cnpj_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_cnpj_unique UNIQUE (cnpj);


--
-- TOC entry 2774 (class 2606 OID 16592)
-- Name: empresa empresa_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pk PRIMARY KEY (id);


--
-- TOC entry 2768 (class 1259 OID 16605)
-- Name: empresa_cnpj_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX empresa_cnpj_index ON public.empresa USING btree (cnpj);


--
-- TOC entry 2771 (class 1259 OID 16606)
-- Name: empresa_id_endereco_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX empresa_id_endereco_index ON public.empresa USING btree (id_endereco);


--
-- TOC entry 2772 (class 1259 OID 16607)
-- Name: empresa_id_grupo_empresarial_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX empresa_id_grupo_empresarial_index ON public.empresa USING btree (id_grupo_empresarial);


--
-- TOC entry 2775 (class 2606 OID 16595)
-- Name: empresa empresa_endereco_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_endereco_fk FOREIGN KEY (id_endereco) REFERENCES public.endereco(id);


--
-- TOC entry 2776 (class 2606 OID 16600)
-- Name: empresa empresa_grupo_empresarial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_grupo_empresarial_fk FOREIGN KEY (id_grupo_empresarial) REFERENCES public.grupo_empresarial(id);


-- Completed on 2020-02-02 23:25:34

--
-- PostgreSQL database dump complete
--

