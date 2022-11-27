package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.DTOs.PlataformaDTO;

public interface IPlataforma {
  void altaPlataforma(PlataformaDTO nuevaPlataforma);
  Map<String, Plataforma> obtenerPlataformas();
  Optional<Plataforma> obtenerPlataforma(String nombrePlataforma);
}
