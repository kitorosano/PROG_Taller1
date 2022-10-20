package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IPaquete;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PaqueteController implements IPaquete {
  private static PaqueteController  instance;
  
  private PaqueteController () {
  }
  
  public static PaqueteController  getInstance() {
    if (instance == null) {
      instance = new PaqueteController ();
    }
    return instance;
  }
  
  @Override
  public void altaPaquete(Paquete nuevoPaquete) {
    Connection connection = null;
    Statement statement = null;
    String insertPaquete = "INSERT INTO paquetes (paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro, paq_imagen)\n" +
        "VALUES ('" + nuevoPaquete.getNombre() + "', '" + nuevoPaquete.getFechaExpiracion() + "', '" + nuevoPaquete.getDescripcion() + "', " + nuevoPaquete.getDescuento() + ", '" + nuevoPaquete.getFechaRegistro() + ", '" + nuevoPaquete.getImagen() + "')'";
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
  
  @Override
  public Map<String, Paquete> obtenerPaquetes() {
    Map<String, Paquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetes = "SELECT * FROM paquetes ORDER BY paq_fechaRegistro DESC";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquetes);
      while (resultSet.next()) {
        String paq_nombre = resultSet.getString("paq_nombre");
        LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        String paq_descripcion = resultSet.getString("paq_descripcion");
        double paq_descuento = resultSet.getDouble("paq_descuento");
        LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        String paq_imagen = resultSet.getString("paq_imagen");
        Paquete paquete = new Paquete(paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro, paq_imagen);
        
        paquetes.put(paq_nombre, paquete);
      }
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
  
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete) {
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculosByPaquete = "SELECT *\n" +
        "FROM espectaculos_paquetes ES_PAQ, espectaculos ES, artistas UA, plataformas PL\n" +
        "WHERE ES_PAQ.es_paq_nombreEspectaculo = ES.es_nombre \n" +
        "AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
        "AND ES.es_plataformaAsociada = PL.pl_nombre\n" +
        "AND ES_PAQ.es_paq_nombrePaquete = '" + nombrePaquete + "'\n" +
        "ORDER BY ES.es_fechaRegistro DESC";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculosByPaquete);
      while (resultSet.next()) {
        String ua_nickname = resultSet.getString("ua_nickname");
        String ua_nombre = resultSet.getString("ua_nombre");
        String ua_apellido = resultSet.getString("ua_apellido");
        String ua_correo = resultSet.getString("ua_correo");
        LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
        String ua_contrasenia = resultSet.getString("ua_contrasenia");
        String ua_imagen = resultSet.getString("ua_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artistaOrganizador = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasenia, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        Double es_duracion = resultSet.getDouble("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        double es_costo = resultSet.getDouble("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artistaOrganizador);
        
        espectaculos.put(es_nombre, espectaculo);
      }
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
  
  @Override
  public Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo) {
    Map<String, Paquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetesByEspectaculo = "SELECT *\n" +
        "FROM espectaculos_paquetes ES_PAQ, paquetes PAQ\n" +
        "WHERE ES_PAQ.es_paq_nombrePaquete = PAQ.paq_nombre \n" +
        "AND ES_PAQ.es_paq_nombreEspectaculo = '" + nombreEspectaculo + "'\n" +
        "ORDER BY PAQ.paq_fechaRegistro DESC";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquetesByEspectaculo);
      while (resultSet.next()) {
        String paq_nombre = resultSet.getString("paq_nombre");
        LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        String paq_descripcion = resultSet.getString("paq_descripcion");
        double paq_descuento = resultSet.getDouble("paq_descuento");
        LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        String paq_imagen = resultSet.getString("paq_imagen");
        Paquete paquete = new Paquete(paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro, paq_imagen);
        
        paquetes.put(paq_nombre, paquete);
      }
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
  
  @Override
  public void altaEspectaculosAPaquete(Map<String, Espectaculo> espectaculos, String nombrePaquete) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculosPaquetes = "INSERT INTO espectaculos_paquetes (es_paq_nombreEspectaculo, es_paq_nombrePaquete) VALUES ";
    for (Espectaculo espectaculo : espectaculos.values()) {
      insertEspectaculosPaquetes += "('" + espectaculo.getNombre() + "', '" + nombrePaquete + "'), ";
    }
    insertEspectaculosPaquetes = insertEspectaculosPaquetes.substring(0, insertEspectaculosPaquetes.length() - 2);
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
}
