package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaEspectaculoAPaqueteDTO;
import main.java.taller1.Logica.DTOs.EspectadorPaqueteDTO;
import main.java.taller1.Logica.DTOs.PaqueteDTO;
import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Interfaces.IPaquete;
import main.java.taller1.Logica.Servicios.PaqueteService;

import java.util.Map;
import java.util.Optional;

public class PaqueteController implements IPaquete {
  private static PaqueteController  instance;
  private PaqueteService servicio;
  
  private PaqueteController () {
    servicio = new PaqueteService();
  }
  
  public static PaqueteController  getInstance() {
    if (instance == null) {
      instance = new PaqueteController ();
    }
    return instance;
  }
  
  /**
   * Crea un paquete
   * @param nuevoPaquete Objeto de tipo Paquete con los datos del paquete a crear
   */
  @Override
  public void altaPaquete(PaqueteDTO nuevoPaquete) {
    servicio.altaPaquete(nuevoPaquete);
  }
  
  /**
   * Obtiene todos los paquetes
   * @return Mapa con todos los paquetes
   */
  @Override
  public Map<String, Paquete> obtenerPaquetes() {
    return servicio.obtenerPaquetes();
  }
  
  /**
   * Obtiene un paquete
   * @param nombrePaquete Nombre del paquete a obtener
   * @return Objeto de tipo Paquete con los datos del paquete
   */
  @Override
  public Optional<Paquete> obtenerPaquete(String nombrePaquete){
    return servicio.obtenerPaquete(nombrePaquete);
  }
  
  /**
   * Obtiene todos los paquetes de un espectáculo
   * @param nombreEspectaculo Nombre del espectáculo
   *                          del que se quieren obtener los paquetes
   * @param nombrePlataforma Nombre de la plataforma
   *                         del que se quieren obtener los paquetes
   * @return Mapa con todos los paquetes del espectáculo
   */
  @Override
  public Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo, String nombrePlataforma) {
    return servicio.obtenerPaquetesDeEspectaculo(nombreEspectaculo, nombrePlataforma);
  }
  
  /**
   * Obtiene todos los paquetes comprados de un espectador
   * @param nickname Nickname del espectador
   *                          del que se quieren obtener los paquetes
   * @return Mapa con todos los paquetes comprados del espectador
   */
  @Override
  public Map<String, Paquete> obtenerPaquetesPorEspectador(String nickname){
    return servicio.obtenerPaquetesPorEspectador(nickname);
  }
  
  /**
   * Obtiene todos los espectadores que compraron un paquete
   * @param nombrePaquete Nombre del paquete
   *                          del que se quieren obtener los espectadores
   * @return Mapa con todos los espectadores que compraron el paquete
   */
  @Override
  public Map<String, Usuario> obtenerEspectadoresDePaquete(String nombrePaquete){
    return servicio.obtenerEspectadoresDePaquete(nombrePaquete);
  }
  
  /**
   * Ingresa un espectaculo a un paquete
   * @param altaEspectaculoAPaqueteDTO Objeto de tipo AltaEspectaculoAPaqueteDTO
   *                                   con los datos del espectaculo, el paquete y la plataforma a ingresar
   */
  @Override
  public void altaEspectaculoAPaquete(AltaEspectaculoAPaqueteDTO altaEspectaculoAPaqueteDTO) {
    servicio.altaEspectaculoAPaquete(altaEspectaculoAPaqueteDTO);
  }
  
  /**
   * Ingresa un espectador a un paquete
   * @param espectadorPaquete Objeto de tipo EspectadorPaqueteDTO
   *                          con los datos del espectador, el paquete y la fecha de compra(fechaRegistro)
   */
  @Override
  public void altaEspectadorAPaquete(EspectadorPaqueteDTO espectadorPaquete) {
    servicio.altaEspectadorAPaquete(espectadorPaquete);
  }
}
