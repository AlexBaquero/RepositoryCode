-- Usuarios

CREATE TABLE public.usuarios
(
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    contrasena character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fondos integer,
    tipo_usuario varchar(15) Default 'Normal' CHECK (tipo_usuario IN ('Normal', 'Regulador')),
    CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuarios
    OWNER to postgres;


-- Inversores

CREATE TABLE public.inversores
(
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "DNI" character varying(9) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(15) COLLATE pg_catalog."default" NOT NULL,
    apellido1 character varying(15) COLLATE pg_catalog."default" NOT NULL,
    apellido2 character varying(15) COLLATE pg_catalog."default" NOT NULL,
    calle character varying(20) COLLATE pg_catalog."default" NOT NULL,
    portal integer NOT NULL,
    piso integer NOT NULL,
    puerta character varying(2) COLLATE pg_catalog."default" NOT NULL,
    telefono integer,
    cuentaEnEspera BOOLEAN,
    CONSTRAINT inversores_pkey PRIMARY KEY ("DNI"),
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.inversores
    OWNER to postgres;


-- EMPRESAS

CREATE TABLE public.empresas
(
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "CIF" character varying(9) COLLATE pg_catalog."default" NOT NULL,
    "nombreComercial" character varying(20) COLLATE pg_catalog."default" NOT NULL,
    ciudad character varying(15) COLLATE pg_catalog."default" NOT NULL,
    calle character varying(15) COLLATE pg_catalog."default" NOT NULL,
    numero integer NOT NULL,
    telefono integer,
    participaciones integer,
    "participacionesSinVender" integer NOT NULL,
    cuentaEnEspera BOOLEAN,
    CONSTRAINT empresas_pkey PRIMARY KEY ("CIF"),
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.empresas
    OWNER to postgres;

-- COMPRAVENTA

CREATE TABLE public."compraVenta"
(
    "id_usuarioVend" character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "id_usuarioCompr" character varying(20) COLLATE pg_catalog."default" NOT NULL,
    empresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fecha date NOT NULL,
    "numeroParticipaciones" integer NOT NULL,
    CONSTRAINT "compraVenta_pkey" PRIMARY KEY ("id_usuarioVend", "id_usuarioCompr", empresa, fecha),
    CONSTRAINT empresa FOREIGN KEY (empresa)
        REFERENCES public.empresas ("CIF") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID,
    CONSTRAINT "id_usuarioCompr" FOREIGN KEY ("id_usuarioCompr")
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID,
    CONSTRAINT "id_usuarioVend" FOREIGN KEY ("id_usuarioVend")
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."compraVenta"
    OWNER to postgres;

-- PAGOSDIVIDENDO

CREATE TABLE public."pagosDividendos"
(
    empresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "fechaPublicacion" date NOT NULL,
    "fechaPago" date NOT NULL,
    beneficio real NOT NULL,
    CONSTRAINT "pagosDividendos_pkey" PRIMARY KEY (empresa, "fechaPublicacion", "fechaPago"),
    CONSTRAINT empresa FOREIGN KEY (empresa)
        REFERENCES public.empresas ("CIF") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."pagosDividendos"
    OWNER to postgres;

-- ANUNCIOS

CREATE TABLE public.anuncios
(
    id_anuncio integer NOT NULL,
    "empresaPago" character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "fechaPublicacion" date NOT NULL,
    "fechaPago" date NOT NULL,
    informacion character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT id_anuncio PRIMARY KEY (id_anuncio),
    CONSTRAINT "pagoDividendo" FOREIGN KEY ("fechaPago", "fechaPublicacion", "empresaPago")
        REFERENCES public."pagosDividendos" ("fechaPago", "fechaPublicacion", empresa) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.anuncios
    OWNER to postgres;

-- POSEER

CREATE TABLE public.poseer
(
    empresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fecha date NOT NULL,
    cantidad integer NOT NULL,
    CONSTRAINT poseer_pkey PRIMARY KEY (empresa, id_usuario, fecha),
    CONSTRAINT empresa FOREIGN KEY (empresa)
        REFERENCES public.empresas ("CIF") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.poseer
    OWNER to postgres;

-- HACEROFERTAVENTA

CREATE TABLE public."hacerOfertaVenta"
(
    "fechaOferta" date NOT NULL,
    precio float NOT NULL,
    disponibilidad boolean NOT NULL,
    empresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    "numeroParticipaciones" integer NOT NULL,
    CONSTRAINT "hacerOfertaVenta_pkey" PRIMARY KEY ("fechaOferta", empresa, id_usuario),
    CONSTRAINT empresa FOREIGN KEY (empresa)
        REFERENCES public.empresas ("CIF") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."hacerOfertaVenta"
    OWNER to postgres;