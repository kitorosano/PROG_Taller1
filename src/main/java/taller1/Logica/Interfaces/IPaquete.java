package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.EspectadorPaquete;
import main.java.taller1.Logica.Clases.Paquete;

public interface IPaquete {
  
  void altaPaquete(Paquete nuevoPaquete);
  Map<String, Paquete> obtenerPaquetes();
  Optional<Paquete> obtenerPaquete(String nombrePaquete);
  Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo, String nombrePlataforma);
  Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete);
  Map<String, EspectadorPaquete> obtenerPaquetesPorEspectador(String nickname);
  Map<String, EspectadorPaquete> obtenerEspectadoresDePaquete(String nombrePaquete);
  void altaEspectaculoAPaquete(String nombrePaquete, String nombreEspectaculo, String nombrePlataforma);
  void altaEspectadorAPaquete(String nombrePaquete, String nickname);
  
}
