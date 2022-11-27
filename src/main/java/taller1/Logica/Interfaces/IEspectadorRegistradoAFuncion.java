package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;

import java.util.Map;

public interface IEspectadorRegistradoAFuncion {
  
  void registrarEspectadorAFuncion(EspectadorRegistradoAFuncion espectadorRegistradoAFuncion);
  void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones);
  Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname);
  Map<String, EspectadorRegistradoAFuncion> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion);
}
