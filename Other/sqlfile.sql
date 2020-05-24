--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2 (Ubuntu 12.2-2.pgdg16.04+1)
-- Dumped by pg_dump version 12.2

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
-- Name: stock; Type: TABLE; Schema: public; Owner: liyyjhbihezjus
--

CREATE TABLE public.stock (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    quantity integer NOT NULL,
    name_of_store character varying(50) NOT NULL
);


ALTER TABLE public.stock OWNER TO liyyjhbihezjus;

--
-- Name: stock_id_seq; Type: SEQUENCE; Schema: public; Owner: liyyjhbihezjus
--

CREATE SEQUENCE public.stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stock_id_seq OWNER TO liyyjhbihezjus;

--
-- Name: stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liyyjhbihezjus
--

ALTER SEQUENCE public.stock_id_seq OWNED BY public.stock.id;


--
-- Name: stores; Type: TABLE; Schema: public; Owner: liyyjhbihezjus
--

CREATE TABLE public.stores (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    distance numeric(5,2) NOT NULL,
    phonenumber character varying(12),
    address character varying(50),
    image character varying(500)
);


ALTER TABLE public.stores OWNER TO liyyjhbihezjus;

--
-- Name: stores_id_seq; Type: SEQUENCE; Schema: public; Owner: liyyjhbihezjus
--

CREATE SEQUENCE public.stores_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stores_id_seq OWNER TO liyyjhbihezjus;

--
-- Name: stores_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liyyjhbihezjus
--

ALTER SEQUENCE public.stores_id_seq OWNED BY public.stores.id;


--
-- Name: test; Type: TABLE; Schema: public; Owner: liyyjhbihezjus
--

CREATE TABLE public.test (
    name character varying(50) NOT NULL,
    price numeric(5,2)
);


ALTER TABLE public.test OWNER TO liyyjhbihezjus;

--
-- Name: stock id; Type: DEFAULT; Schema: public; Owner: liyyjhbihezjus
--

ALTER TABLE ONLY public.stock ALTER COLUMN id SET DEFAULT nextval('public.stock_id_seq'::regclass);


--
-- Name: stores id; Type: DEFAULT; Schema: public; Owner: liyyjhbihezjus
--

ALTER TABLE ONLY public.stores ALTER COLUMN id SET DEFAULT nextval('public.stores_id_seq'::regclass);


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: liyyjhbihezjus
--

COPY public.stock (id, name, quantity, name_of_store) FROM stdin;
24	milk	100	Luckys
25	milk	50	Safeway
26	milk	100	King Soopers
27	bread	50	Luckys
28	cookies	75	Safeway
\.


--
-- Data for Name: stores; Type: TABLE DATA; Schema: public; Owner: liyyjhbihezjus
--

COPY public.stores (id, name, distance, phonenumber, address, image) FROM stdin;
1	Safeway	3.33	303-651-7952	1050 Ken Pratt Blvd, Longmont, CO 80501	https://i.pinimg.com/originals/43/7d/84/437d84f879ed0edf62230744962b9337.png
2	Luckys	4.42	303-444-0215	3960 Broadway #104, Boulder, CO 80304	https://upload.wikimedia.org/wikipedia/en/thumb/a/a3/Lucky%27s_Market_logo.s\nvg/1280px-Lucky%27s_Market_logo.svg.png
3	King Soopers	2.34	\N	\N	\N
\.


--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: liyyjhbihezjus
--

COPY public.test (name, price) FROM stdin;
asfdsf	5.99
dfasfasdf	8.00
\.


--
-- Name: stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liyyjhbihezjus
--

SELECT pg_catalog.setval('public.stock_id_seq', 28, true);


--
-- Name: stores_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liyyjhbihezjus
--

SELECT pg_catalog.setval('public.stores_id_seq', 3, true);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: liyyjhbihezjus
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id);


--
-- Name: stores stores_pkey; Type: CONSTRAINT; Schema: public; Owner: liyyjhbihezjus
--

ALTER TABLE ONLY public.stores
    ADD CONSTRAINT stores_pkey PRIMARY KEY (id);


--
-- Name: test test_pkey; Type: CONSTRAINT; Schema: public; Owner: liyyjhbihezjus
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT test_pkey PRIMARY KEY (name);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: liyyjhbihezjus
--

REVOKE ALL ON SCHEMA public FROM postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO liyyjhbihezjus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: LANGUAGE plpgsql; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON LANGUAGE plpgsql TO liyyjhbihezjus;


--
-- PostgreSQL database dump complete
--

