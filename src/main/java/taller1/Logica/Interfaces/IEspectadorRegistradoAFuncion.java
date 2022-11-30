package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.AltaEspectadorRegistradoAFuncionDTO;

import java.util.Map;

public interface IEspectadorRegistradoAFuncion {
  
  void registrarEspectadorAFuncion(AltaEspectadorRegistradoAFuncionDTO espectadorRegistradoAFuncion);
  void registrarEspectadoresAFunciones(Map<String, AltaEspectadorRegistradoAFuncionDTO> espectadoresFunciones);
  Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname);
  Map<String, Usuario> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion);
}
