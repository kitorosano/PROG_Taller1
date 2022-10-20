package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IEspectaculo;
import main.java.taller1.Persistencia.ConexionDB;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class EspectaculoController implements IEspectaculo {
  private static EspectaculoController instance;
  
  private EspectaculoController() {
  }
  
  public static EspectaculoController getInstance() {
    if (instance == null) {
      instance = new EspectaculoController();
    }
    return instance;
  }
  
  @Override
  public void altaEspectaculo(Espectaculo nuevoEspectaculo) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculo = "INSERT INTO espectaculos (es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, es_plataformaAsociada, es_artistaOrganizador) " +
        "VALUES ('" + nuevoEspectaculo.getNombre() + "', '" + nuevoEspectaculo.getDescripcion() + "', '" + nuevoEspectaculo.getDuracion() + "', '" + nuevoEspectaculo.getMinEspectadores() + "', '" + nuevoEspectaculo.getMaxEspectadores() + "', '" + nuevoEspectaculo.getUrl() + "', '" + nuevoEspectaculo.getCosto() + "', '" + nuevoEspectaculo.getEstado() + "', '" + nuevoEspectaculo.getFechaRegistro() + "', '" + nuevoEspectaculo.getImagen() + "', '" + nuevoEspectaculo.getPlataforma().getNombre() + "', '" + nuevoEspectaculo.getArtista().getNickname() + "')";
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
  @Override
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
        String es_plataformaAsociada = resultSet.getString("es_plataformaAsociada");
        Espectaculo nuevoEspectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        espectaculos.put(es_nombre + "-" + es_plataformaAsociada, nuevoEspectaculo);
      }
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
  @Override
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
        Espectaculo espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        espectaculos.put(es_nombre, espectaculo);
      }
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
  @Override
  public Espectaculo obtenerEspectaculoPorNombre(String nombrePlataforma, String nombre){
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
        espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
      }
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
    return espectaculo;
  }
  @Override
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
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma pl = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
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
        Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasenia, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
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
        String es_plataformaAsociada = resultSet.getString("es_plataformaAsociada");
        Espectaculo nuevoEspectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, pl, ua);
        espectaculos.put(es_nombre + "-" + es_plataformaAsociada, nuevoEspectaculo);
      }
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
  
}
