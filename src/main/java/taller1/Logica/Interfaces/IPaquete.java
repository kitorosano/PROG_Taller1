package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.EspectadorPaquete;
import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.DTOs.AltaEspectaculoAPaqueteDTO;
import main.java.taller1.Logica.DTOs.PaqueteDTO;

public interface IPaquete {
  
  void altaPaquete(PaqueteDTO nuevoPaquete);
  Map<String, Paquete> obtenerPaquetes();
  Optional<Paquete> obtenerPaquete(String nombrePaquete);
  Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo, String nombrePlataforma);
  Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete);
  Map<String, EspectadorPaquete> obtenerPaquetesPorEspectador(String nickname);
  Map<String, EspectadorPaquete> obtenerEspectadoresDePaquete(String nombrePaquete);
  void altaEspectaculoAPaquete(AltaEspectaculoAPaqueteDTO altaEspectaculoAPaqueteDTO);
  void altaEspectadorAPaquete(String nombrePaquete, String nickname);
  
}
