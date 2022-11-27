package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.ICategoria;
import main.java.taller1.Logica.Servicios.CategoriaService;
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
  private CategoriaService servicio;
  
  private CategoriaController() {
    servicio = new CategoriaService();
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
    servicio.altaCategoria(categoria);
  }
  
  /**
   * Metodo que permite obtener todas las categorias
   * @return Mapa con todas las categorias
   */
  @Override
  public Map<String, Categoria> obtenerCategorias(){
    return servicio.obtenerCategorias();
  }
  
  /**
   * Metodo que permite obtener una categoria
   * @param nombreCategoria Nombre de la categoria que se desea obtener
   * @return Objeto Categoria
   */
  @Override
  public Optional<Categoria> obtenerCategoria(String nombreCategoria){
    return servicio.obtenerCategoria(nombreCategoria);
  }
  
  /**
   * Metodo que permite obtener todos los espectaculos de una categoria
   * @param nombreCategoria Nombre de la categoria de la que se desea obtener los espectaculos
   *                        que pertenecen a ella
   * @return Mapa con todos los espectaculos de la categoria
   */
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosDeCategoria(String nombreCategoria){
    return servicio.obtenerEspectaculosDeCategoria(nombreCategoria);
  }
  
  /**
   * Metodo que permite obtener todas las categorias de un espectaculo
   * @param nombreEspectaculo Nombre del espectaculo del que se desea obtener las categorias
   * @return Mapa con todas las categorias de la base de datos
   */
  @Override
  public Map<String, Categoria> obtenerCategoriasDeEspectaculo(String nombreEspectaculo){
    return servicio.obtenerCategoriasDeEspectaculo(nombreEspectaculo);
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
    servicio.altaCategoriaAEspectaculo(nombreCategoria, nombreEspectaculo, nombrePlataforma);
  }
}
