package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.AltaEspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;

import java.util.Map;

public interface IEspectadorRegistradoAFuncion {
  
  void registrarEspectadorAFuncion(AltaEspectadorRegistradoAFuncionDTO espectadorRegistradoAFuncion);
  void registrarEspectadoresAFunciones(Map<String, AltaEspectadorRegistradoAFuncionDTO> espectadoresFunciones);
  Map<String, EspectadorRegistradoAFuncionDTO> obtenerFuncionesRegistradasDelEspectador(String nickname);
  Map<String, Usuario> obtenerEspectadoresRegistradosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion);
}
