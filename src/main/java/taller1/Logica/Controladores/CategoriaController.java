package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.ICategoria;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CategoriaController implements ICategoria {
  
  private static CategoriaController instance;
  
  private CategoriaController() {
  }
  
  public static CategoriaController getInstance() {
    if (instance == null) {
      instance = new CategoriaController();
    }
    return instance;
  }
  
  @Override
  public void altaCategoria(Categoria categoria) {
    Connection connection = null;
    Statement statement = null;
    String insertCategoria = "INSERT INTO categorias (cat_nombre) VALUES ('" + categoria.getNombre() + "')";
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
  
  @Override
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
      while (resultSet.next()) {
        String cat_nombre = resultSet.getString("cat_nombre");
        Categoria categoria = new Categoria(cat_nombre);
        categorias.put(cat_nombre, categoria);
      }
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
  
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosDeCategoria(String nombreCategoria){
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculosByPaquete = "SELECT *\n" +
        "FROM espectaculos_categprias ES_CAT, espectaculos ES, artistas UA, plataformas PL\n" +
        "WHERE ES_CAT.es_cat_nombreEspectaculo = ES.es_nombre \n" +
        "AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
        "AND ES.es_plataformaAsociada = PL.pl_nombre\n" +
        "AND ES_CAT.es_cat_nombreCategoria = '" + nombreCategoria + "'\n" +
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
  public Map<String, Categoria> obtenerCategoriasDeEspectaculo(String nombreEspectaculo){
    Map<String, Categoria> categorias = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectCategoriasByEspectaculo = "SELECT *\n" +
        "FROM espectaculos_categprias ES_CAT, categorias CAT\n" +
        "WHERE ES_CAT.es_cat_nombreCategoria = CAT.cat_nombre \n" +
        "AND ES_CAT.es_cat_nombreEspectaculo = '" + nombreEspectaculo + "'\n" +
        "ORDER BY CAT.cat_nombre";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectCategoriasByEspectaculo);
      while (resultSet.next()) {
        String cat_nombre = resultSet.getString("cat_nombre");
        Categoria categoria = new Categoria(cat_nombre);
        
        categorias.put(cat_nombre, categoria);
      }
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
  
  @Override
  public void altaCategoriasAEspectaculo(Map<String, Categoria> categorias, String nombreEspectaculo){
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculoCategoria = "INSERT INTO espectaculos_categorias (es_cat_nombreEspectaculo, es_cat_nombreCategoria) VALUES ";
    for (Map.Entry<String, Categoria> entry : categorias.entrySet()) {
      insertEspectaculoCategoria += "('" + nombreEspectaculo + "', '" + entry.getKey() + "'), ";
    }
    insertEspectaculoCategoria = insertEspectaculoCategoria.substring(0, insertEspectaculoCategoria.length() - 2);
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
