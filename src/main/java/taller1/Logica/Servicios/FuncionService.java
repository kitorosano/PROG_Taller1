package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FuncionService {
  
  
  public void altaFuncion(Funcion nuevaFuncion) {
    Connection connection = null;
    Statement statement = null;
    String insertFuncion = "INSERT INTO funciones (fn_nombre, fn_espectaculoAsociado, fn_plataformaAsociada, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen) " +
        "VALUES ('" + nuevaFuncion.getNombre() + "', '" + nuevaFuncion.getEspectaculo().getNombre() + "', '" + nuevaFuncion.getEspectaculo().getPlataforma().getNombre()+ "', '"+ nuevaFuncion.getFechaHoraInicio() + "', '" + nuevaFuncion.getFechaRegistro() + "', '" + nuevaFuncion.getImagen() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertFuncion);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al insertar la funcion", e);
    } finally {
      try {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
  }
  
  public Map<String, Funcion> obtenerFunciones() {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
        funciones.put(fn_nombre+"-"+es_nombre+"-"+pl_nombre, funcion);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener las funciones", e);
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return funciones;
  }
  
  public Optional<Funcion> obtenerFuncion(String nombrePlataforma, String nombreEspectador, String nombreFuncion) {
    Funcion funcion = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFuncion = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname" +
        " AND FN.fn_nombre = '" + nombreFuncion + "'" +
        " AND ES.es_nombre = '" + nombreEspectador + "'" +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFuncion);
      if (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener la función", e);
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return Optional.ofNullable(funcion);
  }
  
  public Map<String, Funcion> obtenerFuncionesDePlataforma(String nombrePlataforma) {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
        
        funciones.put(fn_nombre, funcion);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener las funciones", e);
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return funciones;
  }
  
  
  public Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo) {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND ES.es_nombre = '" + nombreEspectaculo + "' " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
        
        funciones.put(fn_nombre+"-"+es_nombre+"-"+pl_nombre, funcion);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener las funciones", e);
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return funciones;
  }
  
  public Map<String, Artista> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {
    Map<String, Artista> artistas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectArtistas = "SELECT U.*, UA.* " +
        "FROM artistas_funciones as UA_FN, funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE UA_FN.ua_fn_nickname = ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND UA_FN.ua_fn_espectaculoAsociado = fn_espectaculoAsociado " +
        " AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND ES.es_nombre = '" + nombreEspectaculo + "' " +
        " AND UA_FN.ua_fn_plataformaAsociada = FN.fn_platafomaAsociada" +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'" +
        " AND UA_FN.ua_fn_nombreFuncion = FN.fn_nombre " +
        " AND FN.fn_nombre = '" + nombreFuncion + "' ";
    try {
      connection = ConexionDB.getConnection();
      
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectArtistas);
      while (resultSet.next()) {
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        artistas.put(u_nickname, artista);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los artistas invitados", e);
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return artistas;
  }
  
  public Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista) {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT FN.*, ES.*, PL.* " +
        "FROM artistas_funciones as UA_FN, funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE UA_FN.ua_fn_nombreFuncion = FN.fn_nombre " +
        " AND UA_FN.ua_fn_plataformaAsociada = FN.fn_platafomaAsociada" +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'" +
        " AND UA_FN.ua_fn_espectaculoAsociado = fn_espectaculoAsociado " +
        " AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND ES.es_nombre = '" + nombreEspectaculo + "' " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND UA_FN.ua_fn_nickname = '" + nombreArtista + "' ";
    try {
      connection = ConexionDB.getConnection();
      
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artistaOrganizador = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artistaOrganizador);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
        funciones.put(fn_nombre+"-"+es_nombre+"-"+pl_nombre, funcion);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los artistas invitados", e);
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    
    return funciones;
  }
}
