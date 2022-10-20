package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Paquete;

import java.util.Map;

public interface IPaquete {
  
  void altaPaquete(Paquete nuevoPaquete);
  Map<String, Paquete> obtenerPaquetes();
  Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete);
  Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo);
  void altaEspectaculosAPaquete(Map<String, Espectaculo> espectaculos, String nombrePaquete);
  
}
