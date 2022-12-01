package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.*;
import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.DTOs.AltaEspectaculoAPaqueteDTO;

public interface IPaquete {
  
  void altaPaquete(PaqueteDTO nuevoPaquete);
  Map<String, Paquete> obtenerPaquetes();
  Optional<Paquete> obtenerPaquete(String nombrePaquete);
  Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo, String nombrePlataforma);
  Map<String, Paquete> obtenerPaquetesPorEspectador(String nickname);
  Map<String, Usuario> obtenerEspectadoresDePaquete(String nombrePaquete);
  void altaEspectaculoAPaquete(AltaEspectaculoAPaqueteDTO altaEspectaculoAPaqueteDTO);
  void altaEspectadorAPaquete(AltaEspectadorAPaqueteDTO nuevoEspectadorPaquete);

  
}
