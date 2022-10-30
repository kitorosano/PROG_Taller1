package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Funcion;

public interface IFuncion {
  
  void altaFuncion(Funcion nuevaFuncion);
  Map<String, Funcion> obtenerFunciones();
  Optional<Funcion> obtenerFuncion(String nombrePlataforma, String nombreEspectador, String nombreFuncion);
  Map<String, Funcion> obtenerFuncionesDePlataforma(String nombrePlataforma);
  Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo);
  Map<String, Artista> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion);
  Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista);
  
  void registrarEspectadorAFuncion(EspectadorRegistradoAFuncion espectadorRegistradoAFuncion);
  void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones);
  Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname);
  Map<String, EspectadorRegistradoAFuncion> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion);
}
