package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IPaquete;
import main.java.taller1.Logica.Servicios.PaqueteService;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
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
  public void altaPaquete(Paquete nuevoPaquete) {
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
   * Obtiene todos los espectáculos de un paquete
   * @param nombrePaquete Nombre del paquete
   *                      del que se quieren obtener los espectáculos
   * @return Mapa con todos los espectáculos del paquete
   */
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete){
    return servicio.obtenerEspectaculosDePaquete(nombrePaquete);
  }
  
  /**
   * Obtiene todos los paquetes comprados de un espectador
   * @param nickname Nickname del espectador
   *                          del que se quieren obtener los paquetes
   * @return Mapa con todos los paquetes comprados del espectador
   */
  @Override
  public Map<String, EspectadorPaquete> obtenerPaquetesPorEspectador(String nickname){
    return servicio.obtenerPaquetesPorEspectador(nickname);
  }
  
  /**
   * Obtiene todos los espectadores que compraron un paquete
   * @param nombrePaquete Nombre del paquete
   *                          del que se quieren obtener los espectadores
   * @return Mapa con todos los espectadores que compraron el paquete
   */
  @Override
  public Map<String, EspectadorPaquete> obtenerEspectadoresDePaquete(String nombrePaquete){
    return servicio.obtenerEspectadoresDePaquete(nombrePaquete);
  }
  
  /**
   * Ingresa un espectaculo a un paquete
   * @param nombreEspectaculo Nombre del espectaculo
   *                          que se quiere ingresar al paquete
   * @param nombrePlataforma Nombre de la plataforma
   *                         en la que se encuentra el espectaculo
   * @param nombrePaquete Nombre del paquete
   *                      al que se quiere ingresar el espectaculo
   */
  @Override
  public void altaEspectaculoAPaquete(String nombreEspectaculo, String nombrePlataforma, String nombrePaquete) {
    servicio.altaEspectaculoAPaquete(nombreEspectaculo, nombrePlataforma, nombrePaquete);
  }
  
  /**
   * Ingresa un espectador a un paquete
   * @param nombrePaquete Nombre del paquete
   *                      al que se quiere ingresar el espectador
   * @param nickname Nickname del espectador
   *                           que se quiere ingresar al paquete
   */
  @Override
  public void altaEspectadorAPaquete(String nombrePaquete, String nickname){
    servicio.altaEspectadorAPaquete(nombrePaquete, nickname);
  }
}
