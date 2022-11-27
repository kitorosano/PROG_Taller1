package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IEspectaculo;
import main.java.taller1.Logica.Servicios.EspectaculoService;
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

public class EspectaculoController implements IEspectaculo {
  private static EspectaculoController instance;
  private EspectaculoService servicio;
  
  private EspectaculoController() {
  }
  
  public static EspectaculoController getInstance() {
    if (instance == null) {
      instance = new EspectaculoController();
    }
    return instance;
  }
  
  @Override
  public void altaEspectaculo(Espectaculo nuevoEspectaculo) {
    servicio.altaEspectaculo(nuevoEspectaculo);
  }
  @Override
  public Map<String, Espectaculo> obtenerEspectaculos() {
    return servicio.obtenerEspectaculos();
  }
  @Override
  public Optional<Espectaculo> obtenerEspectaculo(String nombrePlataforma, String nombre){
    return servicio.obtenerEspectaculo(nombrePlataforma, nombre);
  }
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosPorEstado(E_EstadoEspectaculo estado) {
    return servicio.obtenerEspectaculosPorEstado(estado);
  }
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosPorPlataforma(String nombrePlataforma) {
    return servicio.obtenerEspectaculosPorPlataforma(nombrePlataforma);
  }
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosPorPlataformaYEstado(String nombrePlataforma, E_EstadoEspectaculo estado) {
    return servicio.obtenerEspectaculosPorPlataformaYEstado(nombrePlataforma, estado);
  }
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosPorArtista(String nickname) {
    return servicio.obtenerEspectaculosPorArtista(nickname);
  }
  @Override
  public Map<String, Espectaculo> obtenerEspectaculosPorArtistaYEstado(String nickname, E_EstadoEspectaculo estado) {
    return servicio.obtenerEspectaculosPorArtistaYEstado(nickname, estado);
  }
  @Override
  public void cambiarEstadoEspectaculo(String nombrePlataforma, String nombreEspectaculo, E_EstadoEspectaculo nuevoEstado){
    servicio.cambiarEstadoEspectaculo(nombrePlataforma, nombreEspectaculo, nuevoEstado);
  }
}
