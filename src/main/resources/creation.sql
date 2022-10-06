DROP DATABASE IF EXISTS PROG_Taller1;

CREATE DATABASE PROG_Taller1;

USE PROG_Taller1;

CREATE TABLE artistas (
  ua_nickname VARCHAR(30) NOT NULL,
  ua_nombre VARCHAR(30) NOT NULL,
  ua_apellido VARCHAR(30) NOT NULL,
  ua_correo VARCHAR(50) NOT NULL,
  ua_fechaNacimiento DATE NOT NULL,
  ua_contrasena VARCHAR(30) NOT NULL,
  ua_imagen VARCHAR(200) NOT NULL,
  ua_descripcion VARCHAR(100) NOT NULL,
  ua_biografia VARCHAR(200) NOT NULL,
  ua_sitioWeb VARCHAR(50) NOT NULL,

  PRIMARY KEY (ua_nickname, ua_correo)
);

CREATE TABLE espectadores (
  ue_nickname VARCHAR(30) NOT NULL,
  ue_nombre VARCHAR(30) NOT NULL,
  ue_apellido VARCHAR(30) NOT NULL,
  ue_correo VARCHAR(50) NOT NULL,
  ue_fechaNacimiento DATE NOT NULL,
  ue_contrasena VARCHAR(30) NOT NULL,
  ue_imagen VARCHAR(200) NOT NULL,

  PRIMARY KEY (ue_nickname, ue_correo)
);

CREATE TABLE plataformas (
  pl_nombre VARCHAR(30) NOT NULL,
  pl_descripcion VARCHAR(100) NOT NULL,
  pl_url VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (pl_nombre)
);

CREATE TABLE espectaculos (
  es_nombre VARCHAR(50) NOT NULL,
  es_descripcion VARCHAR(100) NOT NULL,
  es_duracion DOUBLE NOT NULL,
  es_minEspectadores INTEGER NOT NULL,
  es_maxEspectadores INTEGER NOT NULL,
  es_url VARCHAR(50) NOT NULL,
  es_costo DOUBLE NOT NULL,
  es_fechaRegistro DATETIME NOT NULL,
  es_nombrePlataforma VARCHAR(30) NOT NULL,
  es_artistaOrganizador VARCHAR(30) NOT NULL,

  FOREIGN KEY (es_nombrePlataforma) REFERENCES plataformas (pl_nombre) ON DELETE CASCADE,
  FOREIGN KEY (es_artistaOrganizador) REFERENCES artistas (ua_nickname) ON DELETE CASCADE,
  PRIMARY KEY (es_nombre, es_nombrePlataforma)
);
CREATE TABLE funciones (
  fn_nombre VARCHAR(50) NOT NULL,
  fn_espectaculoAsociado VARCHAR(50) NOT NULL,
  fn_fechaHoraInicio DATETIME NOT NULL,
  fn_fechaRegistro DATETIME NOT NULL,

  PRIMARY KEY (fn_nombre),
  FOREIGN KEY (fn_espectaculoAsociado) REFERENCES espectaculos (es_nombre) ON DELETE CASCADE
);

CREATE TABLE paquetes (
  paq_nombre VARCHAR(50) NOT NULL,
  paq_descripcion VARCHAR(100) NOT NULL,
  paq_descuento DOUBLE NOT NULL,
  paq_fechaExpiracion DATETIME NOT NULL,
  paq_fechaRegistro DATETIME NOT NULL,

  PRIMARY KEY (paq_nombre)
);

CREATE TABLE espectaculos_paquetes (
  es_paq_nombrePaquete VARCHAR(50) NOT NULL,
  es_paq_nombreEspectaculo VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (es_paq_nombrePaquete, es_paq_nombreEspectaculo),
  FOREIGN KEY (es_paq_nombrePaquete) REFERENCES paquetes (paq_nombre) ON DELETE CASCADE,
  FOREIGN KEY (es_paq_nombreEspectaculo) REFERENCES espectaculos (es_nombre) ON DELETE CASCADE
);

CREATE TABLE artistas_funciones (
  ua_fn_nickname VARCHAR(30) NOT NULL,
  ua_fn_nombreFuncion VARCHAR(50) NOT NULL,
  ua_fn_fechaRegistro DATETIME NOT NULL,
  
  PRIMARY KEY (ua_fn_nickname, ua_fn_nombreFuncion),
  FOREIGN KEY (ua_fn_nickname) REFERENCES artistas (ua_nickname) ON DELETE CASCADE,
  FOREIGN KEY (ua_fn_nombreFuncion) REFERENCES funciones (fn_nombre) ON DELETE CASCADE
);

CREATE TABLE espectadores_funciones (
  ue_fn_nickname VARCHAR(30) NOT NULL,
  ue_fn_nombreFuncion VARCHAR(50) NOT NULL,
  ue_fn_canjeado BOOLEAN NOT NULL,
  ue_fn_costo DOUBLE NOT NULL,
  ue_fn_fechaRegistro DATETIME NOT NULL,
  
  PRIMARY KEY (ue_fn_nickname, ue_fn_nombreFuncion),
  FOREIGN KEY (ue_fn_nickname) REFERENCES espectadores (ue_nickname) ON DELETE CASCADE,
  FOREIGN KEY (ue_fn_nombreFuncion) REFERENCES funciones (fn_nombre) ON DELETE CASCADE
);