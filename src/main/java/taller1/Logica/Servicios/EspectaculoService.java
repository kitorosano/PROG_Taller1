package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.E_EstadoEspectaculo;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.DTOs.AltaEspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoNuevoEstadoDTO;
import main.java.taller1.Logica.Mappers.EspectaculoMapper;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EspectaculoService {
  
  
  public void altaEspectaculo(AltaEspectaculoDTO nuevoEspectaculo) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculo = "INSERT INTO espectaculos (es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, es_plataformaAsociada, es_artistaOrganizador) " +
        "VALUES ('" + nuevoEspectaculo.getNombre() + "', '" + nuevoEspectaculo.getDescripcion() + "', '" + nuevoEspectaculo.getDuracion() + "', '" + nuevoEspectaculo.getMinEspectadores() + "', '" + nuevoEspectaculo.getMaxEspectadores() + "', '" + nuevoEspectaculo.getUrl() + "', '" + nuevoEspectaculo.getCosto() + "', '" + nuevoEspectaculo.getEstado() + "', '" + nuevoEspectaculo.getFechaRegistro() + "', '" + nuevoEspectaculo.getImagen() + "', '" + nuevoEspectaculo.getPlataforma() + "', '" + nuevoEspectaculo.getArtista() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertEspectaculo);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al insertar el espectaculo", e);
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
  
  public Map<String, Espectaculo> obtenerEspectaculos() {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculos = "SELECT * " +
        "FROM espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "  AND UA.ua_nickname = U.u_nickname";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(selectEspectaculos);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos", e);
    } finally {
      try {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return espectaculos;
  }
  
  public Optional<Espectaculo> obtenerEspectaculo(String nombrePlataforma, String nombre){
    Espectaculo espectaculo = null;
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculo = "SELECT * " +
        "FROM espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "  AND UA.ua_nickname = U.u_nickname " +
        "  AND ES.es_nombre = '" + nombre + "' " +
        "  AND PL.pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(selectEspectaculo);
      espectaculo = EspectaculoMapper.toModel(resultSet);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener el espectaculo", e);
    } finally {
      try {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return Optional.ofNullable(espectaculo);
  }
  
  public Map<String, Espectaculo> obtenerEspectaculosPorEstado(E_EstadoEspectaculo estado) {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculos = "SELECT * " +
        "FROM espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "  AND UA.ua_nickname = U.u_nickname" +
        "  AND ES.es_estado = '" + estado + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(selectEspectaculos);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos", e);
    } finally {
      try {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return espectaculos;
  }
  
  public Map<String, Espectaculo> obtenerEspectaculosPorPlataforma(String nombrePlataforma) {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT * " +
        "FROM espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "  AND UA.ua_nickname = U.u_nickname " +
        "  AND ES.es_plataformaAsociada = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculos);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos", e);
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
  
  public Map<String, Espectaculo> obtenerEspectaculosPorPlataformaYEstado(String nombrePlataforma, E_EstadoEspectaculo estado) {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT * " +
        "FROM espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "  AND UA.ua_nickname = U.u_nickname " +
        "  AND ES.es_plataformaAsociada = '" + nombrePlataforma + "'" +
        "  AND ES.es_estado = '" + estado + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculos);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos", e);
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
  
  public Map<String, Espectaculo> obtenerEspectaculosPorArtista(String nickname) {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT *  " +
        "FROM espectaculos as ES, artistas as UA, usuarios as U, plataformas as PL " +
        "WHERE ES.es_plataformaAsociada=PL.pl_nombre " +
        "   AND ES.es_artistaOrganizador=UA.ua_nickname" +
        "   AND UA.ua_nickname=U.u_nickname " +
        "   AND UA.ua_nickname='" + nickname + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculos);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos del artista", e);
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
  
  public Map<String, Espectaculo> obtenerEspectaculosPorArtistaYEstado(String nickname, E_EstadoEspectaculo estado) {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT *  " +
        "FROM espectaculos as ES, artistas as UA, usuarios as U, plataformas as PL " +
        "WHERE ES.es_plataformaAsociada=PL.pl_nombre " +
        "   AND ES.es_artistaOrganizador=UA.ua_nickname" +
        "   AND UA.ua_nickname=U.u_nickname " +
        "   AND UA.ua_nickname='" + nickname + "'" +
        "   AND ES.es_estado='" + estado + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculos);
      espectaculos.putAll(EspectaculoMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos del artista", e);
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
  
  public void cambiarEstadoEspectaculo(EspectaculoNuevoEstadoDTO espectaculoNuevoEstadoDTO){
    Connection connection = null;
    Statement statement = null;
    String updateEspectaculo = "UPDATE espectaculos " +
        "SET es_estado='" + espectaculoNuevoEstadoDTO.getNuevoEstado() + "' " +
        "WHERE es_nombre='" + espectaculoNuevoEstadoDTO.getNombreEspectaculo() + "' " +
        "AND es_plataformaAsociada='" + espectaculoNuevoEstadoDTO.getNombrePlataforma() + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(updateEspectaculo);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al cambiar el estado del espectaculo", e);
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
