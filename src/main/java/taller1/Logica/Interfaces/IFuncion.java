package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.FuncionDTO;

public interface IFuncion {
  
  void altaFuncion(FuncionDTO nuevafuncion);
  Map<String, Funcion> obtenerFunciones();
  Optional<Funcion> obtenerFuncion(String nombrePlataforma, String nombreEspectador, String nombreFuncion);
  Map<String, Funcion> obtenerFuncionesDePlataforma(String nombrePlataforma);
  Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo);
  Map<String, Usuario> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion);
  Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista);
  
}
