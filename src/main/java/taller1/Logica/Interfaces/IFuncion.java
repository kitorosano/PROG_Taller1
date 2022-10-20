package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Funcion;

import java.util.Map;

public interface IFuncion {
  
  void altaFuncion(Funcion nuevaFuncion);
  Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo);
  Map<String, Artista> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion);
  Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista);
  void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones);
}
