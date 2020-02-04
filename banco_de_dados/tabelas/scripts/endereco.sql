--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

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

--
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.endereco (id, tipo, logradouro, numero_logradouro, complemento_logradouro, bairro, municipio, cep) VALUES (58825, 'EMPRESA', 'RODOVIA ALKINDAR MONTEIRO JUNQUEIRA KM 53 00', 1013, 'KM 53 LOJA 2062', 'CAMPO NOVO', 'BRAGANCA PAULISTA', '12918900');


--
-- PostgreSQL database dump complete
--

