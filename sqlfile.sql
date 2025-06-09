--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)

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
-- Name: bookmarks; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.bookmarks (
    id integer NOT NULL,
    user_id integer NOT NULL,
    event_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.bookmarks OWNER TO conf_user;

--
-- Name: bookmarks_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.bookmarks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bookmarks_id_seq OWNER TO conf_user;

--
-- Name: bookmarks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.bookmarks_id_seq OWNED BY public.bookmarks.id;


--
-- Name: conferences; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.conferences (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    location character varying(255),
    description text
);


ALTER TABLE public.conferences OWNER TO conf_user;

--
-- Name: conferences_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.conferences_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.conferences_id_seq OWNER TO conf_user;

--
-- Name: conferences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.conferences_id_seq OWNED BY public.conferences.id;


--
-- Name: event_attendance; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.event_attendance (
    id integer NOT NULL,
    user_id integer NOT NULL,
    event_id integer NOT NULL,
    attended boolean DEFAULT false NOT NULL,
    check_in_time timestamp without time zone
);


ALTER TABLE public.event_attendance OWNER TO conf_user;

--
-- Name: event_attendance_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.event_attendance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.event_attendance_id_seq OWNER TO conf_user;

--
-- Name: event_attendance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.event_attendance_id_seq OWNED BY public.event_attendance.id;


--
-- Name: event_types; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.event_types (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    color character varying(20) DEFAULT '#3f51b5'::character varying,
    icon character varying(50)
);


ALTER TABLE public.event_types OWNER TO conf_user;

--
-- Name: event_types_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.event_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.event_types_id_seq OWNER TO conf_user;

--
-- Name: event_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.event_types_id_seq OWNED BY public.event_types.id;


--
-- Name: events; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.events (
    id integer NOT NULL,
    conference_id integer NOT NULL,
    type_id integer,
    name character varying(255) NOT NULL,
    description text,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL,
    room character varying(100),
    max_attendees integer,
    speaker_info text
);


ALTER TABLE public.events OWNER TO conf_user;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.events_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.events_id_seq OWNER TO conf_user;

--
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.events_id_seq OWNED BY public.events.id;


--
-- Name: reports; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.reports (
    id integer NOT NULL,
    conference_id integer NOT NULL,
    generated_at timestamp without time zone DEFAULT now() NOT NULL,
    report_data jsonb NOT NULL,
    report_type character varying(50) NOT NULL
);


ALTER TABLE public.reports OWNER TO conf_user;

--
-- Name: reports_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.reports_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reports_id_seq OWNER TO conf_user;

--
-- Name: reports_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.reports_id_seq OWNED BY public.reports.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: conf_user
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    name character varying(100),
    surname character varying(100),
    role character varying(20) NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['admin'::character varying, 'attendee'::character varying, 'guest'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO conf_user;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: conf_user
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO conf_user;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: conf_user
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: bookmarks id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.bookmarks ALTER COLUMN id SET DEFAULT nextval('public.bookmarks_id_seq'::regclass);


--
-- Name: conferences id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.conferences ALTER COLUMN id SET DEFAULT nextval('public.conferences_id_seq'::regclass);


--
-- Name: event_attendance id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_attendance ALTER COLUMN id SET DEFAULT nextval('public.event_attendance_id_seq'::regclass);


--
-- Name: event_types id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_types ALTER COLUMN id SET DEFAULT nextval('public.event_types_id_seq'::regclass);


--
-- Name: events id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.events ALTER COLUMN id SET DEFAULT nextval('public.events_id_seq'::regclass);


--
-- Name: reports id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.reports ALTER COLUMN id SET DEFAULT nextval('public.reports_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: bookmarks; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.bookmarks (id, user_id, event_id, created_at) FROM stdin;
5	8	3	2025-06-08 13:34:57.229462
6	8	10	2025-06-08 13:47:40.261962
7	8	12	2025-06-08 18:51:51.689019
8	8	26	2025-06-08 19:00:02.769304
9	8	30	2025-06-08 19:31:32.736667
10	9	9	2025-06-09 12:37:28.23953
\.


--
-- Data for Name: conferences; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.conferences (id, name, start_date, end_date, location, description) FROM stdin;
1	Tech Conference 2024	2024-10-15	2024-10-17	Online	Global online conference on emerging technologies.
2	Local Dev Meetup	2024-11-05	2024-11-05	Community Hall, City Center	One-day meetup for local developers.
3	Future of AI Summit	2025-03-20	2025-03-22	Convention Center, New York	International summit on Artificial Intelligence advancements.
\.


--
-- Data for Name: event_attendance; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.event_attendance (id, user_id, event_id, attended, check_in_time) FROM stdin;
\.


--
-- Data for Name: event_types; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.event_types (id, name, color, icon) FROM stdin;
5	Keynote	#FF0000	mic
6	Tech	#007bff	laptop
7	Security	#dc3545	shield
8	Workshop	#28a745	tools
9	Panel	#6f42c1	users
\.


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.events (id, conference_id, type_id, name, description, start_time, end_time, room, max_attendees, speaker_info) FROM stdin;
3	1	7	Cybersecurity in 2025	Protecting data in an increasingly connected world.	2025-04-18 13:00:00	2025-04-18 14:00:00	Room 3C	59	Marcus Reed
4	1	6	The Future of Web3	Decentralization and the next-gen internet.	2025-04-20 11:00:00	2025-04-20 12:00:00	Innovation Hub	16	Sophia Torres
5	1	8	Building Scalable Apps	Workshop on cloud-native architecture for scale.	2025-04-22 14:30:00	2025-04-22 16:30:00	Room 2A	99	Ethan Cole
6	1	9	Women in Tech: Panel Discussion	Celebrating diversity and leadership in tech.	2025-04-25 15:00:00	2025-04-25 16:00:00	Main Hall	49	Various Speakers
7	1	8	Machine Learning Crash Course	Hands-on session on machine learning basics.	2025-04-27 10:00:00	2025-04-27 12:00:00	Room 5D	99	Laura Cheng
8	1	7	Cloud Security Deep Dive	Securing infrastructures in the cloud era.	2025-05-01 09:00:00	2025-05-01 10:30:00	Security Lab	62	Daniel Kim
9	1	6	The Power of APIs	Designing powerful and flexible APIs.	2025-05-03 13:00:00	2025-05-03 14:00:00	Room 4B	33	Sara Patel
2	1	6	The Rise of Quantum Computing	Introduction to quantum computing breakthroughs.	2025-04-16 10:30:00	2025-04-16 11:30:00	Room 1B	27	Dr. Emily Stone
1	1	5	Opening Keynote	Kickoff the conference with inspiring tech visions.	2025-04-15 09:00:00	2025-04-22 09:00:00	Main Hall	92	Alex Johnson
10	1	8	DevOps Best Practices	Automating and optimizing development pipelines.	2025-05-05 11:00:00	2025-05-05 13:00:00	Room 6C	44	Nathan Green
11	1	9	The Ethics of AI	Navigating ethical dilemmas in artificial intelligence.	2025-05-08 14:00:00	2025-05-08 15:30:00	Main Hall	33	Various Experts
12	1	6	5G and Beyond	Exploring the future of connectivity.	2025-05-10 10:00:00	2025-05-10 11:00:00	Innovation Hub	92	Olivia Davis
13	1	6	VR/AR: The New Frontier	How virtual and augmented reality are reshaping industries.	2025-05-14 15:00:00	2025-05-14 16:00:00	Room 2C	99	Victor Wang
14	1	6	Next-Gen Programming Languages	Discover emerging languages and their impacts.	2025-05-18 13:00:00	2025-05-18 14:00:00	Room 1A	26	Grace Liu
15	1	6	Remote Work Tech Innovations	Adapting technology to the future of work.	2025-06-01 11:00:00	2025-06-01 12:00:00	Room 5B	15	Chris Bell
16	1	5	Closing Ceremony	Wrapping up with reflections and future visions.	2025-06-14 16:00:00	2025-06-14 17:00:00	Main Hall	46	Keynote Speaker
17	1	7	Ethical Hacking 101	Fundamentals of ethical hacking and penetration testing.	2025-05-17 10:00:00	2025-05-17 11:30:00	Room 3A	61	Natalie Brooks
18	1	9	AI in Healthcare	How AI is transforming diagnosis and patient care.	2025-05-18 14:00:00	2025-05-18 15:30:00	Main Hall	52	Panel of Experts
19	1	5	Tech for Sustainability	Innovative solutions for a greener planet.	2025-05-19 09:00:00	2025-05-19 10:00:00	Main Hall	25	Dr. Anil Mehta
20	1	6	Smart Cities Explained	How IoT and data shape the cities of tomorrow.	2025-05-20 11:00:00	2025-05-20 12:00:00	Room 4A	67	Isabella Knight
21	1	8	Agile Transformation	Practical strategies for embracing agile culture.	2025-05-22 13:00:00	2025-05-22 15:00:00	Room 6B	73	Michael Trent
22	1	6	Edge Computing Demystified	The next step in decentralized computing.	2025-05-23 10:00:00	2025-05-23 11:00:00	Room 2B	73	Harper Lin
23	1	9	Startup Pitch Showcase	Emerging startups pitch their disruptive ideas.	2025-05-24 15:00:00	2025-05-24 17:00:00	Innovation Hub	16	Various Startups
24	1	8	Data Science for Everyone	Hands-on intro to data science concepts and tools.	2025-05-25 09:30:00	2025-05-25 11:30:00	Room 1D	99	Emily Rhodes
25	1	6	Next-Gen UI/UX Design	Design trends that elevate user experience.	2025-06-26 11:00:00	2025-06-26 12:00:00	Design Studio	18	Adrian Foster
26	1	6	AI-Powered Customer Support	Using NLP and automation for smarter service.	2025-05-27 14:00:00	2025-05-27 15:00:00	Room 5C	34	Maya Alston
27	1	7	Data Privacy in the AI Era	Balancing innovation and user privacy.	2025-06-28 13:00:00	2025-06-28 14:00:00	Security Lab	12	Liam Novak
28	1	6	The Robotics Revolution	Exploring advancements in robotics and automation.	2025-06-29 10:00:00	2025-06-29 11:00:00	Demo Room	24	Rachel Kim
29	1	8	Inclusive Design Principles	Creating products for everyone, by design.	2025-06-30 12:00:00	2025-06-30 13:30:00	Room 3B	45	Samuel White
30	1	7	Post-Quantum Cryptography	Preparing for a secure future beyond quantum threats.	2025-07-01 09:00:00	2025-07-01 10:00:00	Room 2C	36	Dr. Zoe Grant
\.


--
-- Data for Name: reports; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.reports (id, conference_id, generated_at, report_data, report_type) FROM stdin;
1	1	2025-06-01 11:47:17.478973	{"total_attendees": 500, "most_popular_event": "Opening Keynote"}	SummaryReport
2	2	2025-06-01 11:47:17.478973	{"attendees_per_session": {"Spring Boot Basics": 75, "Lunch & Learn Networking": 60}}	SessionReport
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: conf_user
--

COPY public.users (id, email, password, name, surname, role, created_at) FROM stdin;
1	admin@example.com	hashed_password_admin	Admin	User	admin	2025-06-01 11:47:16.915989
2	john.doe@example.com	hashed_password_john	John	Doe	attendee	2025-06-01 11:47:16.915989
3	jane.smith@example.com	hashed_password_jane	Jane	Smith	attendee	2025-06-01 11:47:16.915989
4	guest@example.com	hashed_password_guest	Guest	Account	guest	2025-06-01 11:47:16.915989
6	wiewiora840@gmail.com	$2a$10$.OH/RQeCqwq09OMmZ4yxPubhW8PE1YL2zjR9jm2Qv4M1ONSyvKRgS	\N	\N	attendee	2025-06-05 21:38:31.57639
7	ffbjdywehmghxgjsxq@nespf.com	$2a$10$UaaE/ia7fCHGkG28qjK2MewctGuTIWNY/DjxeWyTmD.DOutxm8cj6	\N	\N	attendee	2025-06-08 11:50:44.4383
8	qbpyyrubjfsnnvqvkp@enotj.com	$2a$10$BHKFhpAWl2lYJD39qpT1r.g8HIUxaoqlZr.gqe2YhuX41SBktpzMO	ASDasd	ASDasdqwe	attendee	2025-06-08 12:59:09.265296
9	rhdkewyruhtbytbqnx@nesopf.com	$2a$10$3Za.zISq14FLvQlCuxzszuwpZ.w86zyoaAUkkzGv/bXRbu/lqj9py	Jan	Kowalski	attendee	2025-06-09 12:36:40.971866
\.


--
-- Name: bookmarks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.bookmarks_id_seq', 10, true);


--
-- Name: conferences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.conferences_id_seq', 3, true);


--
-- Name: event_attendance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.event_attendance_id_seq', 1, false);


--
-- Name: event_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.event_types_id_seq', 9, true);


--
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.events_id_seq', 5, true);


--
-- Name: reports_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.reports_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: conf_user
--

SELECT pg_catalog.setval('public.users_id_seq', 9, true);


--
-- Name: bookmarks bookmarks_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.bookmarks
    ADD CONSTRAINT bookmarks_pkey PRIMARY KEY (id);


--
-- Name: bookmarks bookmarks_user_id_event_id_key; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.bookmarks
    ADD CONSTRAINT bookmarks_user_id_event_id_key UNIQUE (user_id, event_id);


--
-- Name: conferences conferences_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.conferences
    ADD CONSTRAINT conferences_pkey PRIMARY KEY (id);


--
-- Name: event_attendance event_attendance_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_attendance
    ADD CONSTRAINT event_attendance_pkey PRIMARY KEY (id);


--
-- Name: event_attendance event_attendance_user_id_event_id_key; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_attendance
    ADD CONSTRAINT event_attendance_user_id_event_id_key UNIQUE (user_id, event_id);


--
-- Name: event_types event_types_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_types
    ADD CONSTRAINT event_types_pkey PRIMARY KEY (id);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: reports reports_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.reports
    ADD CONSTRAINT reports_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: idx_attendance_event; Type: INDEX; Schema: public; Owner: conf_user
--

CREATE INDEX idx_attendance_event ON public.event_attendance USING btree (event_id);


--
-- Name: idx_bookmarks_user; Type: INDEX; Schema: public; Owner: conf_user
--

CREATE INDEX idx_bookmarks_user ON public.bookmarks USING btree (user_id);


--
-- Name: idx_events_conference; Type: INDEX; Schema: public; Owner: conf_user
--

CREATE INDEX idx_events_conference ON public.events USING btree (conference_id);


--
-- Name: idx_events_type; Type: INDEX; Schema: public; Owner: conf_user
--

CREATE INDEX idx_events_type ON public.events USING btree (type_id);


--
-- Name: bookmarks bookmarks_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.bookmarks
    ADD CONSTRAINT bookmarks_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.events(id) ON DELETE CASCADE;


--
-- Name: bookmarks bookmarks_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.bookmarks
    ADD CONSTRAINT bookmarks_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: event_attendance event_attendance_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_attendance
    ADD CONSTRAINT event_attendance_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.events(id) ON DELETE CASCADE;


--
-- Name: event_attendance event_attendance_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.event_attendance
    ADD CONSTRAINT event_attendance_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: events events_conference_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_conference_id_fkey FOREIGN KEY (conference_id) REFERENCES public.conferences(id) ON DELETE CASCADE;


--
-- Name: events events_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_type_id_fkey FOREIGN KEY (type_id) REFERENCES public.event_types(id) ON DELETE SET NULL;


--
-- Name: reports reports_conference_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: conf_user
--

ALTER TABLE ONLY public.reports
    ADD CONSTRAINT reports_conference_id_fkey FOREIGN KEY (conference_id) REFERENCES public.conferences(id) ON DELETE CASCADE;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO conf_user;


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: public; Owner: conf_user
--

ALTER DEFAULT PRIVILEGES FOR ROLE conf_user IN SCHEMA public GRANT ALL ON SEQUENCES TO conf_user;


--
-- Name: DEFAULT PRIVILEGES FOR FUNCTIONS; Type: DEFAULT ACL; Schema: public; Owner: conf_user
--

ALTER DEFAULT PRIVILEGES FOR ROLE conf_user IN SCHEMA public GRANT ALL ON FUNCTIONS TO conf_user;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: conf_user
--

ALTER DEFAULT PRIVILEGES FOR ROLE conf_user IN SCHEMA public GRANT ALL ON TABLES TO conf_user;


--
-- PostgreSQL database dump complete
--

