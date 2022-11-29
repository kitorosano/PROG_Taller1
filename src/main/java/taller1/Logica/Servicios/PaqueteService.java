package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaEspectaculoAPaqueteDTO;
import main.java.taller1.Logica.DTOs.EspectadorPaqueteDTO;
import main.java.taller1.Logica.DTOs.PaqueteDTO;
import main.java.taller1.Logica.Mappers.EspectaculoMapper;
import main.java.taller1.Logica.Mappers.PaqueteMapper;
import main.java.taller1.Logica.Mappers.UsuarioMapper;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaqueteService {
  
  public void altaPaquete(PaqueteDTO nuevoPaquete) {
    Connection connection = null;
    Statement statement = null;
    String insertPaquete = "INSERT INTO paquetes (paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro, paq_imagen) " +
        "VALUES ('" + nuevoPaquete.getNombre() + "', '" + nuevoPaquete.getFechaExpiracion() + "', '" + nuevoPaquete.getDescripcion() + "', " + nuevoPaquete.getDescuento() + ", '" + nuevoPaquete.getFechaRegistro() + "', '" + nuevoPaquete.getImagen() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertPaquete);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al insertar el paquete", e);
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
  public Map<String, Paquete> obtenerPaquetes() {
    Map<String, Paquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetes = "SELECT * FROM paquetes";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquetes);
      paquetes.putAll(PaqueteMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los paquetes", e);
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
    return paquetes;
  }
  public Optional<Paquete> obtenerPaquete(String nombrePaquete){
    Paquete paquete = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquete = "SELECT * FROM paquetes WHERE paq_nombre = '" + nombrePaquete + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquete);
      paquete = PaqueteMapper.toModel(resultSet);

    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener el paquete", e);
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
    return Optional.ofNullable(paquete);
  }
  public Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo, String nombrePlataforma) {
    Map<String, Paquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetesByEspectaculo = "SELECT * " +
        "FROM espectaculos_paquetes ES_PAQ, paquetes PAQ " +
        "WHERE ES_PAQ.es_paq_nombrePaquete = PAQ.paq_nombre  " +
        "AND ES_PAQ.es_paq_nombreEspectaculo = '" + nombreEspectaculo + "' " +
        "AND ES_PAQ.es_paq_plataformaAsociada = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquetesByEspectaculo);
      paquetes.putAll(PaqueteMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los paquetes del espectáculo", e);
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
    return paquetes;
  }
  public Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete){
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculosByPaquete = "SELECT * " +
        "FROM espectaculos_paquetes as ES_PAQ, espectaculos as ES, artistas as UA, usuarios as U, plataformas as PL " +
        "WHERE ES_PAQ.es_paq_nombreEspectaculo = ES.es_nombre " +
        "AND ES_PAQ.es_paq_plataformaAsociada = ES.es_plataformaAsociada " +
        "AND ES.es_plataformaAsociada = PL.pl_nombre " +
        "AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "AND UA.ua_nickname = U.u_nickname " +
        "AND ES_PAQ.es_paq_nombrePaquete = '" + nombrePaquete + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculosByPaquete);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectáculos del paquete", e);
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
    return espectaculos;
  }
  public Map<String, Paquete> obtenerPaquetesPorEspectador(String nickname){
    Map<String, Paquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetesByEspectador = "SELECT * " +
        "FROM paquetes as PAQ, espectadores_paquetes as UE_PAQ, espectadores as UE, usuarios as U "+
        "WHERE UE_PAQ.ue_paq_nombrePaquete = PAQ.paq_nombre " +
        "AND UE_PAQ.ue_paq_nickname = UE.ue_nickname " +
        "AND UE.ue_nickname = U.u_nickname " +
        "AND U.u_nickname = '" + nickname + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquetesByEspectador);
      paquetes.putAll(PaqueteMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los paquetes del espectador", e);
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
    return paquetes;
  }
  public Map<String, Usuario> obtenerEspectadoresDePaquete(String nombrePaquete){
    Map<String, Usuario> espectadores = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectadoresByPaquete = "SELECT * " +
        "FROM espectadores_paquetes as UE_PAQ, espectadores as UE, usuarios as U " +
        "WHERE UE_PAQ.ue_paq_nombrePaquete = '" + nombrePaquete + "' " +
        "AND UE_PAQ.ue_paq_nicknameEspectador = UE.es_nickname " +
        "AND UE.es_nickname = U.us_nickname ";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectadoresByPaquete);
      espectadores.putAll(UsuarioMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectáculos del paquete", e);
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
    return espectadores;
  }
  
  public void altaEspectaculoAPaquete(AltaEspectaculoAPaqueteDTO altaEspectaculoAPaqueteDTO) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculosPaquetes = "INSERT INTO espectaculos_paquetes (es_paq_nombreEspectaculo, es_paq_plataformaAsociada, es_paq_nombrePaquete) " +
        "                 VALUES ('" + altaEspectaculoAPaqueteDTO.getNombreEspectaculo() + "', '" + altaEspectaculoAPaqueteDTO.getNombrePlataforma() + "', '" + altaEspectaculoAPaqueteDTO.getNombrePaquete() + "') ";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertEspectaculosPaquetes);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al dar de alta los espectáculos al paquete", e);
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
  public void altaEspectadorAPaquete(EspectadorPaqueteDTO espectadorPaquete) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectadoresPaquetes = "INSERT INTO espectadores_paquetes (ue_paq_nickname, ue_paq_nombrePaquete, ue_paq_fechaRegistro) " +
                          " VALUES ('" + espectadorPaquete.getEspectador().getNickname() + "', '" + espectadorPaquete.getPaquete().getNombre() + "', '" + espectadorPaquete.getFechaRegistro() + "') ";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertEspectadoresPaquetes);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al dar de alta los espectadores al paquete", e);
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
}
