package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Categoria;
import main.java.taller1.Logica.Clases.Espectaculo;

import java.util.Map;

public interface ICategoria {
  
  void altaCategoria(Categoria nuevaCategoria);
  Map<String, Categoria> obtenerCategorias();
  Categoria obtenerCategoria(String nombreCategoria);
  Map<String, Espectaculo> obtenerEspectaculosDeCategoria(String nombreCategoria);
  Map<String, Categoria> obtenerCategoriasDeEspectaculo(String nombreEspectaculo);
  void altaCategoriaAEspectaculo(String nombreCategoria, String nombreEspectaculo);
}
