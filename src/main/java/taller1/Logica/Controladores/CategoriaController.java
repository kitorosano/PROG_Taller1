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
import java.util.Optional;

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
  
  /**
   * Metodo que permite crear una categoria
   * @param categoria Objeto Categoria que se desea crear
   */
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
  
  /**
   * Metodo que permite obtener todas las categorias
   * @return Mapa con todas las categorias
   */
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
  
  /**
   * Metodo que permite obtener una categoria
   * @param nombreCategoria Nombre de la categoria que se desea obtener
   * @return Objeto Categoria
   */
  @Override
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
      if (resultSet.next()) {
        String cat_nombre = resultSet.getString("cat_nombre");
        categoria = new Categoria(cat_nombre);
      }
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
  
  /**
   * Metodo que permite obtener todos los espectaculos de una categoria
   * @param nombreCategoria Nombre de la categoria de la que se desea obtener los espectaculos
   *                        que pertenecen a ella
   * @return Mapa con todos los espectaculos de la categoria
   */
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosDeCategoria(String nombreCategoria){
    Map<String, Espectaculo> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectaculosByPaquete = "SELECT * " +
        "FROM espectaculos_categorias ES_CAT, espectaculos ES, artistas UA, usuarios U, plataformas PL " +
        "WHERE ES_CAT.es_cat_nombreEspectaculo = ES.es_nombre " +
        "AND ES.es_artistaOrganizador = UA.ua_nickname " +
        "AND UA.ua_nickname = U.u_nickname " +
        "AND ES_CAT.es_cat_plataformaAsociada = ES.es_plataformaAsociada " +
        "AND ES.es_plataformaAsociada = PL.pl_nombre " +
        "AND ES_CAT.es_cat_nombreCategoria = '" + nombreCategoria + "' ";
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
  
  /**
   * Metodo que permite obtener todas las categorias de un espectaculo
   * @param nombreEspectaculo Nombre del espectaculo del que se desea obtener las categorias
   * @return Mapa con todas las categorias de la base de datos
   */
  @Override
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
  
  /**
   * Metodo que permite obtener todos los espectaculos de una categoria
   * @param nombreCategoria Nombre de la categoria del que se desea obtener los espectaculos
   *                        asociados
   * @param nombreEspectaculo Nombre del espectaculo del que se desea obtener las categorias
   *                          asociadas
   * @param nombrePlataforma Nombre de la plataforma del que se desea obtener los espectaculos
   *                         asociados
   * @return Mapa con todos los espectaculos de la base de datos
   */
  @Override
  public void altaCategoriaAEspectaculo(String nombreCategoria, String nombreEspectaculo, String nombrePlataforma){
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculoCategoria = "INSERT INTO espectaculos_categorias (es_cat_nombreEspectaculo,es_cat_plataformaAsociada, es_cat_nombreCategoria) " +
        "                           VALUES ('" + nombreEspectaculo + "', '" + nombrePlataforma + "','" + nombreCategoria + "')";
    
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
