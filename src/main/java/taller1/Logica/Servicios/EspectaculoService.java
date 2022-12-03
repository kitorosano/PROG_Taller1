package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.E_EstadoEspectaculo;
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
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
  
  public Map<String, EspectaculoDTO> obtenerEspectaculos()    {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet resultSet = statement.executeQuery(selectEspectaculos);
      if(!resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
  
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
  
  public Optional<EspectaculoDTO> obtenerEspectaculo(String nombrePlataforma, String nombre){
    EspectaculoDTO espectaculo = null;
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculo = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES.es_nombre = '" + nombre + "' " +
        "  AND PL.pl_nombre = '" + nombrePlataforma + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet resultSet = statement.executeQuery(selectEspectaculo);
      if(resultSet.next()) espectaculo = EspectaculoMapper.toDTO(resultSet);
      
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
  
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorEstado(E_EstadoEspectaculo estado) {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES.es_estado = '" + estado + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
  
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
  
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorPlataforma(String nombrePlataforma) {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES.es_plataformaAsociada = '" + nombrePlataforma + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
  
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
  
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorPlataformaYEstado(String nombrePlataforma, E_EstadoEspectaculo estado) {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES.es_plataformaAsociada = '" + nombrePlataforma + "' " +
        "  AND ES.es_estado = '" + estado + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
      
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
  
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorArtista(String nickname) {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE UA.ua_nickname='" + nickname + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
      
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
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorCategoria(String nombreCategoria){
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES_CAT.*, ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_espectaculoAsociado) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN espectaculos_categorias as ES_CAT ON ES_CAT.es_cat_nombreEspectaculo = ES.es_nombre AND ES_CAT.es_cat_plataformaAsociada = ES.es_plataformaAsociada " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES_CAT.es_cat_nombreCategoria = '" + nombreCategoria + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
      
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
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorPaquete(String nombrePaquete){
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES_PAQ.*, ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_espectaculoAsociado) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN espectaculos_paquetes as ES_PAQ ON ES_PAQ.es_paq_nombreEspectaculo = ES.es_nombre AND ES_PAQ.es_paq_plataformaAsociada = ES.es_plataformaAsociada " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES_PAQ.es_paq_nombrePaquete = '" + nombrePaquete + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
      
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
  
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorArtistaYEstado(String nickname, E_EstadoEspectaculo estado) {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE UA.ua_nickname='" + nickname + "'" +
        "   AND ES.es_estado='" + estado + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
  
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
  public Map<String, EspectaculoDTO> obtenerEspectaculosFavoritosDeEspectador(String nickname) {
    Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculos = "SELECT ES.*, PL.*, UA.*, U.*, COUNT(ES_FAV.es_fav_nickname) AS cantidad_favoritos " +
        "FROM espectaculos as ES " +
        " LEFT JOIN plataformas as PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas as UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios as U ON UA.ua_nickname = U.u_nickname " +
        " LEFT JOIN espectaculos_favoritos as ES_FAV ON ES.es_nombre = ES_FAV.es_fav_espectaculoAsociado AND ES.es_plataformaAsociada = ES_FAV.es_fav_plataformaAsociada " +
        "WHERE ES_FAV.es_fav_nickname='" + nickname + "' " +
        "GROUP BY ES.es_nombre, ES.es_plataformaAsociada";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectaculos);
      if(resultSet.next()) espectaculos.putAll(EspectaculoMapper.toDTOMap(resultSet));
  
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos favoritos del espectador", e);
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
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
