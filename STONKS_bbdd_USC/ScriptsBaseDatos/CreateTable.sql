--Usuarios
CREATE TABLE public.usuarios
(
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    contrasena character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fondos real NOT NULL,
    tipo_usuario character varying(15) COLLATE pg_catalog."default" NOT NULL,
    enespera integer NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario),
    CONSTRAINT usuario FOREIGN KEY (id_usuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


--Inversores
CREATE TABLE public.inversores
(
    idusuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    dni character varying(9) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(15) COLLATE pg_catalog."default" NOT NULL,
    apellido1 character varying(15) COLLATE pg_catalog."default" NOT NULL,
    apellido2 character varying(15) COLLATE pg_catalog."default" NOT NULL,
    calle character varying(20) COLLATE pg_catalog."default" NOT NULL,
    portal integer NOT NULL,
    piso integer NOT NULL,
    puerta character varying(2) COLLATE pg_catalog."default" NOT NULL,
    telefono integer NOT NULL,
    CONSTRAINT inversores_pkey PRIMARY KEY (idusuario, dni),
    CONSTRAINT "idUsuario" FOREIGN KEY (idusuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--Empresas
CREATE TABLE public.empresas
(
    idusuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    cif character varying(9) COLLATE pg_catalog."default" NOT NULL,
    nombrecomercial character varying(20) COLLATE pg_catalog."default" NOT NULL,
    ciudad character varying(15) COLLATE pg_catalog."default" NOT NULL,
    calle character varying(50) COLLATE pg_catalog."default" NOT NULL,
    numero integer NOT NULL,
    telefono integer NOT NULL,
    participaciones integer NOT NULL,
    participacionessinvender integer NOT NULL,
    dinerobloqueado real NOT NULL,
    CONSTRAINT empresas_pkey PRIMARY KEY (idusuario, cif),
    CONSTRAINT idusuario FOREIGN KEY (idusuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


--hacerofertaventa
CREATE TABLE public.hacerofertaventa
(
    fechaoferta timestamp without time zone NOT NULL,
    precio real NOT NULL,
    numparticipaciones integer NOT NULL,
    disponibilidad boolean NOT NULL,
    empresa character varying(9) COLLATE pg_catalog."default" NOT NULL,
    idempresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    idusuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT hacerofertaventa_pkey PRIMARY KEY (fechaoferta, empresa, idempresa, idusuario),
    CONSTRAINT empresas FOREIGN KEY (empresa, idempresa)
        REFERENCES public.empresas (cif, idusuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT idusuario FOREIGN KEY (idusuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--Poseer
CREATE TABLE public.poseer
(
    empresa character varying(9) COLLATE pg_catalog."default" NOT NULL,
    idempresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    idusuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fecha timestamp without time zone NOT NULL,
    cantidad integer NOT NULL,
    CONSTRAINT poseer_pkey PRIMARY KEY (empresa, idempresa, idusuario, fecha),
    CONSTRAINT empresa FOREIGN KEY (empresa, idempresa)
        REFERENCES public.empresas (cif, idusuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT idusuario FOREIGN KEY (idusuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--compraventa
CREATE TABLE public.compraventa
(
    idusuariovendedor character varying(20) COLLATE pg_catalog."default" NOT NULL,
    idusuariocomprador character varying(20) COLLATE pg_catalog."default" NOT NULL,
    empresa character varying(9) COLLATE pg_catalog."default" NOT NULL,
    idempresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fechacompra timestamp without time zone NOT NULL,
    numeroparticipaciones integer NOT NULL,
    comision real NOT NULL,
    CONSTRAINT compraventa_pkey PRIMARY KEY (idusuariovendedor, idusuariocomprador, empresa, idempresa, fechacompra),
    CONSTRAINT empresa FOREIGN KEY (empresa, idempresa)
        REFERENCES public.empresas (cif, idusuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT idusuariocompr FOREIGN KEY (idusuariocomprador)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT idusuariovend FOREIGN KEY (idusuariovendedor)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--pagosdividendo
CREATE TABLE public.pagosdividendos
(
    empresa character varying(9) COLLATE pg_catalog."default" NOT NULL,
    idempresa character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fechapago timestamp without time zone NOT NULL,
    beneficio real NOT NULL,
    CONSTRAINT pagosdividendos_pkey PRIMARY KEY (empresa, idempresa, fechapago),
    CONSTRAINT empresa FOREIGN KEY (empresa, idempresa)
        REFERENCES public.empresas (cif, idusuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--Anuncios
CREATE TABLE public.anuncios
(
    idanuncio integer NOT NULL,
    informacion character varying(100) COLLATE pg_catalog."default" NOT NULL,
    fechapubli timestamp without time zone NOT NULL,
    fechapago timestamp without time zone NOT NULL,
    empresa character varying(9) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT anuncios_pkey PRIMARY KEY (idanuncio)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--TablaActualizacionesPrecios
CREATE TABLE public.tablaactualizacionesprecios
(
    nombrecripto character varying(20) COLLATE pg_catalog."default" NOT NULL,
    precio real NOT NULL,
    CONSTRAINT "Tablaactualizacionesprecios_pkey" PRIMARY KEY (nombrecripto)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--Monederos

CREATE TABLE public.monederos
(
    id_usuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    tipo character varying(20) COLLATE pg_catalog."default" NOT NULL,
    cantidad real NOT NULL,
    CONSTRAINT monederos_pkey PRIMARY KEY (id_usuario, tipo),
    CONSTRAINT id_usuario FOREIGN KEY (id_usuario)
        REFERENCES public.usuarios (id_usuario) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


--CompraVentaCriptos
CREATE TABLE public.compraventacriptos
(
    idcomprador character varying(20) COLLATE pg_catalog."default" NOT NULL,
    idvendedor character varying(20) COLLATE pg_catalog."default" NOT NULL,
    tipo character varying(20) COLLATE pg_catalog."default" NOT NULL,
    fechacompra timestamp without time zone NOT NULL,
    cantidad real NOT NULL,
    precio real NOT NULL,
    CONSTRAINT compraventacriptos_pkey PRIMARY KEY (idcomprador, idvendedor, tipo, fechacompra),
    CONSTRAINT "idComprador" FOREIGN KEY (idcomprador, tipo)
        REFERENCES public.monederos (id_usuario, tipo) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT "idVendedor" FOREIGN KEY (idcomprador, tipo)
        REFERENCES public.monederos (id_usuario, tipo) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
        NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

--HacerOfertaVentaCripto
CREATE TABLE public.hacerofertaventacripto
(
    fechaoferta timestamp without time zone NOT NULL,
    cantidad real NOT NULL,
    tipo character varying(20) COLLATE pg_catalog."default" NOT NULL,
    idusuario character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT hacerofertaventacripto_pkey PRIMARY KEY (fechaoferta, idusuario, tipo),
    CONSTRAINT usuario FOREIGN KEY (idusuario, tipo)
        REFERENCES public.monederos (id_usuario, tipo) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;