package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Plataforma;

import java.util.Map;

public interface IPlataforma {
  void altaPlataforma(Plataforma nuevaPlataforma);
  Map<String, Plataforma> obtenerPlataformas();
  Plataforma obtenerPlataforma(String nombrePlataforma);
}
