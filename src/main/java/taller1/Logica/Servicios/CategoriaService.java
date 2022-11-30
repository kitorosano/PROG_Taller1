package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaCategoriaAEspectaculoDTO;
import main.java.taller1.Logica.DTOs.CategoriaDTO;
import main.java.taller1.Logica.Mappers.CategoriaMapper;
import main.java.taller1.Logica.Mappers.EspectaculoMapper;
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

public class CategoriaService {
  
  public void altaCategoria(CategoriaDTO categoriadto) {
    Connection connection = null;
    Statement statement = null;
    String insertCategoria = "INSERT INTO categorias (cat_nombre) VALUES ('" + categoriadto.getNombre() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertCategoria);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al dar de alta la categoría", e);
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
  public Map<String, Categoria> obtenerCategorias(){
    Map<String, Categoria> categorias = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectCategorias = "SELECT * FROM categorias";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectCategorias);
      categorias.putAll(CategoriaMapper.toModelMap(resultSet));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener las categorías", e);
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
    return categorias;
  }
  public Optional<Categoria> obtenerCategoria(String nombreCategoria){
    Categoria categoria = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectCategoria = "SELECT * FROM categorias WHERE cat_nombre = '" + nombreCategoria + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectCategoria);
      categoria = CategoriaMapper.toModel(resultSet);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener la categoría", e);
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
    return Optional.ofNullable(categoria);
  }
  
  public Map<String, Categoria> obtenerCategoriasDeEspectaculo(String nombreEspectaculo){
    Map<String, Categoria> categorias = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectCategoriasByEspectaculo = "SELECT * " +
        "FROM espectaculos_categorias ES_CAT, categorias CAT " +
        "WHERE ES_CAT.es_cat_nombreCategoria = CAT.cat_nombre  " +
        "AND ES_CAT.es_cat_nombreEspectaculo = '" + nombreEspectaculo + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectCategoriasByEspectaculo);
      categorias = CategoriaMapper.toModelMap(resultSet);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener las categorías del espectáculo", e);
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
    return categorias;
  }
  public void altaCategoriaAEspectaculo(AltaCategoriaAEspectaculoDTO altaCategoriaAEspectaculoDTO){
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculoCategoria = "INSERT INTO espectaculos_categorias (es_cat_nombreEspectaculo, es_cat_plataformaAsociada, es_cat_nombreCategoria) " +
        " VALUES ('" + altaCategoriaAEspectaculoDTO.getNombreEspectaculo() + "', '" + altaCategoriaAEspectaculoDTO.getNombrePlataforma() + "','" + altaCategoriaAEspectaculoDTO.getNombreCategoria() + "')";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertEspectaculoCategoria);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al agregar las categorías al espectáculo", e);
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
