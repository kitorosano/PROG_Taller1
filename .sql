CREATE DATABASE PROG_Taller1;

USE PROG_Taller1;

CREATE TABLE artistas (
  nickname VARCHAR(30) NOT NULL,
  nombre VARCHAR(30) NOT NULL,
  apellido VARCHAR(30) NOT NULL,
  correo VARCHAR(50) NOT NULL,
  fechaNacimiento DATE NOT NULL,
  descripcion VARCHAR(100) NOT NULL,
  biografia VARCHAR(200) NOT NULL,
  sitioWeb VARCHAR(50) NOT NULL,

  PRIMARY KEY (nickname, correo)
);

CREATE TABLE espectadores (
  nickname VARCHAR(30) NOT NULL,
  nombre VARCHAR(30) NOT NULL,
  apellido VARCHAR(30) NOT NULL,
  correo VARCHAR(50) NOT NULL,
  fechaNacimiento DATE NOT NULL,

  PRIMARY KEY (nickname, correo)
);

CREATE TABLE plataformas (
  nombre VARCHAR(30) NOT NULL,
  descripcion VARCHAR(100) NOT NULL,
  `url` VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (nombre)
);

CREATE TABLE espectaculos (
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(100) NOT NULL,
  duracion DOUBLE NOT NULL,
  minEspectadores INTEGER NOT NULL,
  maxEspectadores INTEGER NOT NULL,
  `url` VARCHAR(50) NOT NULL,
  costo DOUBLE NOT NULL,
  fechaRegistro DATETIME NOT NULL,
  nombrePlataforma VARCHAR(30) NOT NULL,
  artistaOrganizador VARCHAR(30) NOT NULL,

  PRIMARY KEY (nombre),
  FOREIGN KEY (nombrePlataforma) REFERENCES plataformas (nombre) ON DELETE CASCADE,
  FOREIGN KEY (artistaOrganizador) REFERENCES artistas (nickname) ON DELETE CASCADE
);
CREATE TABLE funciones (
  nombre VARCHAR(50) NOT NULL,
  espectaculoAsociado VARCHAR(50) NOT NULL,
  fechaHoraInicio DATETIME NOT NULL,
  fechaRegistro DATETIME NOT NULL,

  PRIMARY KEY (nombre),
  FOREIGN KEY (espectaculoAsociado) REFERENCES espectaculos (nombre) ON DELETE CASCADE
);

CREATE TABLE paquetes (
  nombre VARCHAR(50) NOT NULL,
  fechaExpiracion DATETIME NOT NULL,
  descripcion VARCHAR(100) NOT NULL,
  descuento DOUBLE NOT NULL,
  fechaRegistro DATETIME NOT NULL,

  PRIMARY KEY (nombre)
);

CREATE TABLE espectaculos_paquetes (
  nombrePaquete VARCHAR(50) NOT NULL,
  nombreEspectaculo VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (nombrePaquete, nombreEspectaculo),
  FOREIGN KEY (nombrePaquete) REFERENCES paquetes (nombre) ON DELETE CASCADE,
  FOREIGN KEY (nombreEspectaculo) REFERENCES espectaculos (nombre) ON DELETE CASCADE
);

CREATE TABLE artistas_funciones (
  nickname VARCHAR(30) NOT NULL,
  nombreFuncion VARCHAR(50) NOT NULL,
  fechaRegistro DATETIME NOT NULL,
  
  PRIMARY KEY (nickname, nombreFuncion),
  FOREIGN KEY (nickname) REFERENCES artistas (nickname) ON DELETE CASCADE,
  FOREIGN KEY (nombreFuncion) REFERENCES funciones (nombre) ON DELETE CASCADE
);

CREATE TABLE espectadores_funciones (
  nickname VARCHAR(30) NOT NULL,
  nombreFuncion VARCHAR(50) NOT NULL,
  canjeado BOOLEAN NOT NULL,
  costo DOUBLE NOT NULL,
  fechaRegistro DATETIME NOT NULL,
  
  PRIMARY KEY (nickname, nombreFuncion),
  FOREIGN KEY (nickname) REFERENCES espectadores (nickname) ON DELETE CASCADE,
  FOREIGN KEY (nombreFuncion) REFERENCES funciones (nombre) ON DELETE CASCADE
)