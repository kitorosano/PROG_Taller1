package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaEspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoNuevoEstadoDTO;
import main.java.taller1.Logica.Interfaces.IEspectaculo;
import main.java.taller1.Logica.Servicios.EspectaculoService;
import java.util.Map;
import java.util.Optional;

public class EspectaculoController implements IEspectaculo {
  private static EspectaculoController instance;
  private EspectaculoService servicio;
  
  private EspectaculoController() {
    servicio = new EspectaculoService();
  }
  
  public static EspectaculoController getInstance() {
    if (instance == null) {
      instance = new EspectaculoController();
    }
    return instance;
  }
  
  @Override
  public void altaEspectaculo(AltaEspectaculoDTO nuevoEspectaculo) {
    servicio.altaEspectaculo(nuevoEspectaculo);
  }
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculos() {
    return servicio.obtenerEspectaculos();
  }
  @Override
  public Optional<EspectaculoDTO> obtenerEspectaculo(String nombrePlataforma, String nombre){
    return servicio.obtenerEspectaculo(nombrePlataforma, nombre);
  }
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorEstado(E_EstadoEspectaculo estado) {
    return servicio.obtenerEspectaculosPorEstado(estado);
  }
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorPlataforma(String nombrePlataforma) {
    return servicio.obtenerEspectaculosPorPlataforma(nombrePlataforma);
  }
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorPlataformaYEstado(String nombrePlataforma, E_EstadoEspectaculo estado) {
    return servicio.obtenerEspectaculosPorPlataformaYEstado(nombrePlataforma, estado);
  }
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorArtista(String nickname) {
    return servicio.obtenerEspectaculosPorArtista(nickname);
  }
  
  /**
   * Metodo que permite obtener todos los espectaculos de una categoria
   * @param nombreCategoria Nombre de la categoria de la que se desea obtener los espectaculos
   *                        que pertenecen a ella
   * @return Mapa con todos los espectaculos de la categoria
   */
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorCategoria(String nombreCategoria){
    return servicio.obtenerEspectaculosPorCategoria(nombreCategoria);
  }
  
  /**
   * Obtiene todos los espect??culos de un paquete
   * @param nombrePaquete Nombre del paquete
   *                      del que se quieren obtener los espect??culos
   * @return Mapa con todos los espect??culos del paquete
   */
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorPaquete(String nombrePaquete){
    return servicio.obtenerEspectaculosPorPaquete(nombrePaquete);
  }
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosPorArtistaYEstado(String nickname, E_EstadoEspectaculo estado) {
    return servicio.obtenerEspectaculosPorArtistaYEstado(nickname, estado);
  }
  
  @Override
  public Map<String, EspectaculoDTO> obtenerEspectaculosFavoritosDeEspectador(String nickname) {
    return servicio.obtenerEspectaculosFavoritosDeEspectador(nickname);
  }
  
  @Override
  public void cambiarEstadoEspectaculo(EspectaculoNuevoEstadoDTO espectaculoNuevoEstadoDTO){
    servicio.cambiarEstadoEspectaculo(espectaculoNuevoEstadoDTO);
  }
}
