package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Interfaces.IPlataforma;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PlataformaController  implements IPlataforma {
  private static PlataformaController instance;
  
  private PlataformaController() {
  }
  
  public static PlataformaController getInstance() {
    if (instance == null) {
      instance = new PlataformaController();
    }
    return instance;
  }
  
  @Override
  public void altaPlataforma(Plataforma nuevaPlataforma) {
    Connection connection = null;
    Statement statement = null;
    String insertPlataforma = "INSERT INTO plataformas (pl_nombre, pl_descripcion, pl_url) VALUES ('" + nuevaPlataforma.getNombre() + "', '" + nuevaPlataforma.getDescripcion() + "', '" + nuevaPlataforma.getUrl() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
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
  
  @Override
  public Map<String, Plataforma> obtenerPlataformas() {
    Map<String, Plataforma> plataformas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectPlataformas = "SELECT * FROM plataformas order by pl_nombre";
    try {
      connection = ConexionDB.getConnection();
      
      // Obtenemos todas las plataformas de la base de datos
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectPlataformas);
      while (resultSet.next()) {
        String nombre = resultSet.getString("pl_nombre");
        String descripcion = resultSet.getString("pl_descripcion");
        String url = resultSet.getString("pl_url");
        
        Plataforma plataforma = new Plataforma(nombre, descripcion, url);
        plataformas.put(nombre, plataforma);
      }
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
}