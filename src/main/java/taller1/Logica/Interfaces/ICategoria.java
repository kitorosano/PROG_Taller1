package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Categoria;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.DTOs.AltaCategoriaAEspectaculoDTO;
import main.java.taller1.Logica.DTOs.CategoriaDTO;

public interface ICategoria {
  
  void altaCategoria(CategoriaDTO nuevaCategoria);
  Map<String, Categoria> obtenerCategorias();
  Optional<Categoria> obtenerCategoria(String nombreCategoria);
  Map<String, Categoria> obtenerCategoriasDeEspectaculo(String nombreEspectaculo, String nombrePlataforma);
  void altaCategoriaAEspectaculo(AltaCategoriaAEspectaculoDTO altaCategoriaAEspectaculoDTO);
}
