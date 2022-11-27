package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Interfaces.IEspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Servicios.EspectadorRegistradoAFuncionService;

import java.util.Map;

public class EspectadorRegistradoAFuncionController implements IEspectadorRegistradoAFuncion {
  private static EspectadorRegistradoAFuncionController instance;
  private EspectadorRegistradoAFuncionService servicio;
  
  private EspectadorRegistradoAFuncionController() {
    servicio = new EspectadorRegistradoAFuncionService();
  }
  
  public static EspectadorRegistradoAFuncionController getInstance() {
    if (instance == null) {
      instance = new EspectadorRegistradoAFuncionController();
    }
    return instance;
  }
  
  
  
  @Override
  public void registrarEspectadorAFuncion(EspectadorRegistradoAFuncion espectadorRegistradoAFuncion) {
    servicio.registrarEspectadorAFuncion(espectadorRegistradoAFuncion);
  }
  @Override
  public void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones) {
    servicio.registrarEspectadoresAFunciones(espectadoresFunciones);
  }
  
  @Override
  public Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname) {
    return servicio.obtenerFuncionesRegistradasDelEspectador(nickname);
  }
  
  @Override
  public Map<String, EspectadorRegistradoAFuncion> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion) {
    return servicio.obtenerEspectadoresRegistradosAFuncion(nombreFuncion);
  }
}
