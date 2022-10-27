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
import java.util.Optional;

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
  @Override
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
      while (resultSet.next()) {
        String paq_nombre = resultSet.getString("paq_nombre");
        LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        String paq_descripcion = resultSet.getString("paq_descripcion");
        double paq_descuento = resultSet.getDouble("paq_descuento");
        LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        String paq_imagen = resultSet.getString("paq_imagen");
        Paquete paquete = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
        
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
      if (resultSet.next()) {
        String paq_nombre = resultSet.getString("paq_nombre");
        LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        String paq_descripcion = resultSet.getString("paq_descripcion");
        double paq_descuento = resultSet.getDouble("paq_descuento");
        LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        String paq_imagen = resultSet.getString("paq_imagen");
        paquete = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
      }
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
  
  @Override
  public Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo) {
    Map<String, Paquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetesByEspectaculo = "SELECT * " +
        "FROM espectaculos_paquetes ES_PAQ, paquetes PAQ " +
        "WHERE ES_PAQ.es_paq_nombrePaquete = PAQ.paq_nombre  " +
        "AND ES_PAQ.es_paq_nombreEspectaculo = '" + nombreEspectaculo + "' ";
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
        Paquete paquete = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
        
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
  public Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete){
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculosByPaquete = "SELECT * " +
                "FROM espectaculos_paquetes as ES_PAQ, espectaculos as ES, artistas as UA, usuarios as U, plataformas as PL " +
                "WHERE ES_PAQ.es_paq_nombreEspectaculo = ES.es_nombre " +
                "AND ES.es_plataformaAsociada = PL.pl_nombre " +
                "AND ES.es_artistaOrganizador = UA.ua_nickname " +
                "AND UA.ua_nickname = U.u_nickname " +
                "AND ES_PAQ.es_paq_nombrePaquete = '" + nombrePaquete + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectaculosByPaquete);
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
        Artista artistaOrganizador = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
  
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
        Double es_costo = resultSet.getDouble("es_costo");
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
  public Map<String, EspectadorPaquete> obtenerPaquetesPorEspectador(String nickname){
    Map<String, EspectadorPaquete> paquetes = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPaquetesByEspectador = "SELECT * " +
                "FROM paquetes as PAQ, espectadores_paquetes as ES_PAQ, espectadores as ES, usuarios as U "+
                "WHERE ES_PAQ.ue_paq_nombrePaquete = PAQ.paq_nombre " +
                "AND ES_PAQ.ue_paq_nickname = ES.es_nickname " +
                "AND ES.es_nickname = U.u_nickname " +
                "AND U.u_nickname = '" + nickname + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPaquetesByEspectador);
      while (resultSet.next()) {
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        Espectador espectador = new Espectador(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen);
  
        String paq_nombre = resultSet.getString("paq_nombre");
        String paq_descripcion = resultSet.getString("paq_descripcion");
        Double paq_descuento = resultSet.getDouble("paq_descuento");
        LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        String paq_imagen = resultSet.getString("paq_imagen");
        Paquete paquete = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
        
        LocalDateTime ue_paq_fechaRegistro = resultSet.getTimestamp("ue_paq_fechaRegistro").toLocalDateTime();
        
        EspectadorPaquete espectadorPaquete = new EspectadorPaquete(espectador, paquete, ue_paq_fechaRegistro);
        paquetes.put(paq_nombre, espectadorPaquete);
      }
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
  
  @Override
  public Map<String, EspectadorPaquete> obtenerEspectadoresDePaquete(String nombrePaquete){
    Map<String, EspectadorPaquete> espectadores = new HashMap<>();
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
      while (resultSet.next()) {
  
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        Espectador espectador = new Espectador(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen);
  
        String paq_nombre = resultSet.getString("paq_nombre");
        String paq_descripcion = resultSet.getString("paq_descripcion");
        Double paq_descuento = resultSet.getDouble("paq_descuento");
        LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        String paq_imagen = resultSet.getString("paq_imagen");
        Paquete paquete = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
  
        LocalDateTime ue_paq_fechaRegistro = resultSet.getTimestamp("ue_paq_fechaRegistro").toLocalDateTime();
  
        EspectadorPaquete espectadorPaquete = new EspectadorPaquete(espectador, paquete, ue_paq_fechaRegistro);
        espectadores.put(u_nickname, espectadorPaquete);
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
    return espectadores;
  }
  @Override
  public void altaEspectaculoAPaquete(String nombreEspectaculo, String nombrePaquete) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculosPaquetes = "INSERT INTO espectaculos_paquetes (es_paq_nombreEspectaculo, es_paq_nombrePaquete) " +
        "                 VALUES ('" + nombreEspectaculo + "', '" + nombrePaquete + "') ";
    
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
  
  @Override
  public void altaEspectadorAPaquete(String nickname, String nombrePaquete){
    Connection connection = null;
    Statement statement = null;
    String insertEspectadoresPaquetes = "INSERT INTO espectadores_paquetes (ue_paq_nickname, ue_paq_nombrePaquete, ue_paq_fechaRegistro) " +
        "                 VALUES ('" + nickname + "', '" + nombrePaquete + "', '" + LocalDate.now() + "') ";
    
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
