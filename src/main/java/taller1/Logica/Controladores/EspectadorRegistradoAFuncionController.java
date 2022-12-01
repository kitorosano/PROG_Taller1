package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.AltaEspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;
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
  public void registrarEspectadorAFuncion(AltaEspectadorRegistradoAFuncionDTO espectadorRegistradoAFuncion) {
    servicio.registrarEspectadorAFuncion(espectadorRegistradoAFuncion);
  }
  @Override
  public void registrarEspectadoresAFunciones(Map<String, AltaEspectadorRegistradoAFuncionDTO> espectadoresFunciones) {
    servicio.registrarEspectadoresAFunciones(espectadoresFunciones);
  }
  
  @Override
  public Map<String, EspectadorRegistradoAFuncionDTO> obtenerFuncionesRegistradasDelEspectador(String nickname) {
    return servicio.obtenerFuncionesRegistradasDelEspectador(nickname);
  }
  
  @Override
  public Map<String, Usuario> obtenerEspectadoresRegistradosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {
    return servicio.obtenerEspectadoresRegistradosAFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion);
  }
}
