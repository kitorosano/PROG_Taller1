package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.DTOs.PlataformaDTO;
import main.java.taller1.Logica.Mappers.PlataformaMapper;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlataformaService {
  
  public void altaPlataforma(PlataformaDTO plataformadto) {
    Connection connection = null;
    Statement statement = null;
    String insertPlataforma = "INSERT INTO plataformas (pl_nombre, pl_descripcion, pl_url) VALUES ('" + plataformadto.getNombre() + "', '" + plataformadto.getDescripcion() + "', '" + plataformadto.getUrl() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      statement.executeUpdate(insertPlataforma);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al insertar la plataforma", e);
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
  public Map<String, Plataforma> obtenerPlataformas() {
    Map<String, Plataforma> plataformas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPlataformas = "SELECT * FROM plataformas";
    try {
      connection = ConexionDB.getConnection();
      
      // Obtenemos todas las plataformas de la base de datos
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectPlataformas);
      if(!resultSet.next()) return plataformas; // Si el result set está vacío retornamos null
  
      plataformas.putAll(PlataformaMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener las plataformas", e);
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
    return plataformas;
  }
  public Optional<Plataforma> obtenerPlataforma(String nombrePlataforma) {
    Plataforma plataforma = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPlataforma = "SELECT * FROM plataformas WHERE pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      
      // Obtenemos la plataforma de la base de datos
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectPlataforma);
      if(!resultSet.next()) return null; // Si el result set está vacío retornamos null
  
      plataforma = PlataformaMapper.toModel(resultSet);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener la plataforma", e);
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
    return Optional.ofNullable(plataforma);
  }
}
