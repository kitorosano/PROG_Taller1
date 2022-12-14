DROP DATABASE IF EXISTS PROG_Taller3;

CREATE DATABASE PROG_Taller3;

USE PROG_Taller3;

CREATE TABLE usuarios (
  u_nickname VARCHAR(30) NOT NULL,
  u_nombre VARCHAR(30) NOT NULL,
  u_apellido VARCHAR(30) NOT NULL,
  u_correo VARCHAR(50) NOT NULL,
  u_fechaNacimiento DATE NOT NULL,
  u_contrasenia VARCHAR(30) NOT NULL,
  u_imagen VARCHAR(200),

  PRIMARY KEY (u_nickname),
  UNIQUE (u_correo)
);

CREATE TABLE espectadores (
  ue_nickname VARCHAR(30) NOT NULL,

  PRIMARY KEY (ue_nickname),
  FOREIGN KEY (ue_nickname) REFERENCES usuarios(u_nickname) ON DELETE CASCADE
);

CREATE TABLE artistas (
  ua_nickname VARCHAR(30) NOT NULL,
  ua_descripcion VARCHAR(500) NOT NULL,
  ua_biografia VARCHAR(500),
  ua_sitioWeb VARCHAR(50),

  PRIMARY KEY (ua_nickname),
  FOREIGN KEY (ua_nickname) REFERENCES usuarios(u_nickname) ON DELETE CASCADE
);

CREATE TABLE plataformas (
  pl_nombre VARCHAR(30) NOT NULL,
  pl_descripcion VARCHAR(100) NOT NULL,
  pl_url VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (pl_nombre)
);

CREATE TABLE espectaculos (
  es_nombre VARCHAR(50) NOT NULL,
  es_descripcion VARCHAR(200) NOT NULL,
  es_duracion DOUBLE NOT NULL,
  es_minEspectadores INTEGER NOT NULL,
  es_maxEspectadores INTEGER NOT NULL,
  es_url VARCHAR(50) NOT NULL,
  es_costo DOUBLE NOT NULL,
  es_estado ENUM('INGRESADO', 'ACEPTADO', 'RECHAZADO', 'FINALIZADO') NOT NULL,
  es_fechaRegistro DATETIME NOT NULL,
  es_imagen VARCHAR(200),
  es_plataformaAsociada VARCHAR(30) NOT NULL,
  es_artistaOrganizador VARCHAR(30) NOT NULL,

  PRIMARY KEY (es_nombre, es_plataformaAsociada),
  FOREIGN KEY (es_plataformaAsociada) REFERENCES plataformas (pl_nombre) ON DELETE CASCADE,
  FOREIGN KEY (es_artistaOrganizador) REFERENCES artistas (ua_nickname) ON DELETE CASCADE
);

CREATE TABLE funciones (
  fn_nombre VARCHAR(50) NOT NULL,
  fn_espectaculoAsociado VARCHAR(50) NOT NULL,
  fn_plataformaAsociada VARCHAR(30) NOT NULL,
  fn_fechaHoraInicio DATETIME NOT NULL,
  fn_fechaRegistro DATETIME NOT NULL,
  fn_imagen VARCHAR(200),

  PRIMARY KEY (fn_nombre, fn_espectaculoAsociado, fn_plataformaAsociada),
  FOREIGN KEY (fn_espectaculoAsociado, fn_plataformaAsociada) REFERENCES espectaculos (es_nombre, es_plataformaAsociada) ON DELETE CASCADE
);

CREATE TABLE categorias (
    cat_nombre VARCHAR(30) NOT NULL,

    PRIMARY KEY (cat_nombre)
);

CREATE TABLE espectaculos_categorias (
    es_cat_nombreEspectaculo VARCHAR(50) NOT NULL,
    es_cat_plataformaAsociada VARCHAR(30) NOT NULL,
    es_cat_nombreCategoria VARCHAR(30) NOT NULL,

    PRIMARY KEY (es_cat_nombreEspectaculo, es_cat_plataformaAsociada, es_cat_nombreCategoria),
    FOREIGN KEY (es_cat_nombreEspectaculo, es_cat_plataformaAsociada) REFERENCES espectaculos (es_nombre, es_plataformaAsociada) ON DELETE CASCADE,
    FOREIGN KEY (es_cat_nombreCategoria) REFERENCES categorias (cat_nombre) ON DELETE CASCADE
);

CREATE TABLE paquetes (
  paq_nombre VARCHAR(50) NOT NULL,
  paq_descripcion VARCHAR(100) NOT NULL,
  paq_descuento DOUBLE NOT NULL,
  paq_fechaExpiracion DATETIME NOT NULL,
  paq_fechaRegistro DATETIME NOT NULL,
  paq_imagen VARCHAR(200),

  PRIMARY KEY (paq_nombre)
);

CREATE TABLE espectaculos_paquetes (
  es_paq_nombreEspectaculo VARCHAR(50) NOT NULL,
  es_paq_plataformaAsociada VARCHAR(30) NOT NULL,
  es_paq_nombrePaquete VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (es_paq_nombreEspectaculo, es_paq_plataformaAsociada, es_paq_nombrePaquete),
  FOREIGN KEY (es_paq_nombreEspectaculo, es_paq_plataformaAsociada) REFERENCES espectaculos (es_nombre, es_plataformaAsociada) ON DELETE CASCADE,
  FOREIGN KEY (es_paq_nombrePaquete) REFERENCES paquetes (paq_nombre) ON DELETE CASCADE
);

CREATE TABLE artistas_funciones (
  ua_fn_nickname VARCHAR(30) NOT NULL,
  ua_fn_nombreFuncion VARCHAR(50) NOT NULL,
  ua_fn_espectaculoAsociado VARCHAR(50) NOT NULL,
  ua_fn_plataformaAsociada VARCHAR(30) NOT NULL,
  
  PRIMARY KEY (ua_fn_nickname, ua_fn_nombreFuncion, ua_fn_espectaculoAsociado, ua_fn_plataformaAsociada),
  FOREIGN KEY (ua_fn_nickname) REFERENCES artistas (ua_nickname) ON DELETE CASCADE,
  FOREIGN KEY (ua_fn_nombreFuncion, ua_fn_espectaculoAsociado, ua_fn_plataformaAsociada) REFERENCES funciones (fn_nombre, fn_espectaculoAsociado, fn_plataformaAsociada) ON DELETE CASCADE
);

CREATE TABLE espectadores_funciones (
  ue_fn_nickname VARCHAR(30) NOT NULL,
  ue_fn_nombreFuncion VARCHAR(50) NOT NULL,
  ue_fn_espectaculoAsociado VARCHAR(50) NOT NULL,
  ue_fn_plataformaAsociada VARCHAR(30) NOT NULL,
  ue_fn_nombrePaquete VARCHAR(50),
  ue_fn_canjeado BOOLEAN NOT NULL,
  ue_fn_costo DOUBLE NOT NULL,
  ue_fn_fechaRegistro DATETIME NOT NULL,

  PRIMARY KEY (ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada),
  FOREIGN KEY (ue_fn_nickname) REFERENCES espectadores (ue_nickname) ON DELETE CASCADE,
  FOREIGN KEY (ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada) REFERENCES funciones (fn_nombre, fn_espectaculoAsociado, fn_plataformaAsociada) ON DELETE CASCADE,
  FOREIGN KEY (ue_fn_nombrePaquete) REFERENCES paquetes (paq_nombre) ON DELETE CASCADE
);

CREATE TABLE espectadores_funciones_canjeados (
    ue_fn_c_nickname VARCHAR(30) NOT NULL,
    ue_fn_c_nombreFuncion VARCHAR(50) NOT NULL,
    ue_fn_c_espectaculoAsociado VARCHAR(50) NOT NULL,
    ue_fn_c_plataformaAsociada VARCHAR(30) NOT NULL,

    ue_fn_c_nicknameCanjeado VARCHAR(30) NOT NULL,
    ue_fn_c_nombreFuncionCanjeado VARCHAR(50) NOT NULL,
    ue_fn_c_espectaculoAsociadoCanjeado VARCHAR(50) NOT NULL,
    ue_fn_c_plataformaAsociadaCanjeado VARCHAR(30) NOT NULL,

    PRIMARY KEY (ue_fn_c_nickname, ue_fn_c_nombreFuncion, ue_fn_c_espectaculoAsociado, ue_fn_c_plataformaAsociada, ue_fn_c_nicknameCanjeado, ue_fn_c_nombreFuncionCanjeado, ue_fn_c_espectaculoAsociadoCanjeado, ue_fn_c_plataformaAsociadaCanjeado),
    FOREIGN KEY (ue_fn_c_nickname, ue_fn_c_nombreFuncion, ue_fn_c_espectaculoAsociado, ue_fn_c_plataformaAsociada) REFERENCES espectadores_funciones (ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada) ON DELETE CASCADE,
    FOREIGN KEY (ue_fn_c_nicknameCanjeado, ue_fn_c_nombreFuncionCanjeado, ue_fn_c_espectaculoAsociadoCanjeado, ue_fn_c_plataformaAsociadaCanjeado) REFERENCES espectadores_funciones (ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada) ON DELETE CASCADE
);

CREATE TABLE espectadores_paquetes (
    ue_paq_nickname VARCHAR(30) NOT NULL,
    ue_paq_nombrePaquete VARCHAR(50) NOT NULL,
    ue_paq_fechaRegistro DATETIME NOT NULL,

    PRIMARY KEY (ue_paq_nickname, ue_paq_nombrePaquete),
    FOREIGN KEY (ue_paq_nickname) REFERENCES espectadores (ue_nickname) ON DELETE CASCADE,
    FOREIGN KEY (ue_paq_nombrePaquete) REFERENCES paquetes (paq_nombre) ON DELETE CASCADE
);

CREATE TABLE espectaculos_favoritos (
    es_fav_espectaculoAsociado VARCHAR(50) NOT NULL,
    es_fav_plataformaAsociada VARCHAR(30) NOT NULL,
    es_fav_nickname VARCHAR(30) NOT NULL,

    PRIMARY KEY (es_fav_espectaculoAsociado, es_fav_plataformaAsociada, es_fav_nickname),
    FOREIGN KEY (es_fav_espectaculoAsociado, es_fav_plataformaAsociada) REFERENCES espectaculos (es_nombre, es_plataformaAsociada) ON DELETE CASCADE,
    FOREIGN KEY (es_fav_nickname) REFERENCES espectadores (ue_nickname) ON DELETE CASCADE
);