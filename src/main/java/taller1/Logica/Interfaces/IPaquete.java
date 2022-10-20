package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Paquete;

import java.util.Map;

public interface IPaquete {
  
  void altaPaquete(Paquete nuevoPaquete);
  Map<String, Paquete> obtenerPaquetes();
  Paquete obtenerPaquete(String nombrePaquete);
  Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo);
  Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete);
  Map<String, Paquete> obtenerPaquetesPorEspectador(String nickname);
  Map<String, Espectador> obtenerEspectadoresDePaquete(String nombrePaquete);
  void altaEspectaculoAPaquete(String nombreEspectaculo, String nombrePaquete);
  void altaEspectadorAPaquete(String nickname, String nombrePaquete);
  
}
