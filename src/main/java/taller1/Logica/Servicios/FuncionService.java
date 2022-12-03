package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.Mappers.FuncionMapper;
import main.java.taller1.Logica.Mappers.UsuarioMapper;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FuncionService {
  
  
  public void altaFuncion(FuncionDTO funcionDTO) {
    Connection connection = null;
    Statement statement = null;
    String insertFuncion = "INSERT INTO funciones (fn_nombre, fn_espectaculoAsociado, fn_plataformaAsociada, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen) " +
        "VALUES ('" + funcionDTO.getNombre() + "', '" + funcionDTO.getEspectaculo().getNombre() + "', '" + funcionDTO.getEspectaculo().getPlataforma().getNombre()+ "', '"+ funcionDTO.getFechaHoraInicio() + "', '" + funcionDTO.getFechaRegistro() + "', '" + funcionDTO.getImagen() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectFunciones);
      if(resultSet.next()) funciones.putAll(FuncionMapper.toModelMap(resultSet));
  
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
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectFuncion);
      if(resultSet.next()) funcion = FuncionMapper.toModel(resultSet);
      
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
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectFunciones);
      if(resultSet.next()) funciones.putAll(FuncionMapper.toModelMap(resultSet));
      
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
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectFunciones);
      if(resultSet.next()) funciones.putAll(FuncionMapper.toModelMap(resultSet));
      
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
  
  public Map<String, Usuario> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {
    Map<String, Usuario> artistas = new HashMap<>();
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
      
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectArtistas);
      if(resultSet.next()) artistas.putAll(UsuarioMapper.toModelMap(resultSet));
      
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
      
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectFunciones);
      if(resultSet.next()) funciones.putAll(FuncionMapper.toModelMap(resultSet));
  
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
