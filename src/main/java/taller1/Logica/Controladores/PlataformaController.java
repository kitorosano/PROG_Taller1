package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Interfaces.IPlataforma;
import main.java.taller1.Logica.Servicios.PlataformaService;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlataformaController  implements IPlataforma {
  private static PlataformaController instance;
  private PlataformaService servicio;
  
  private PlataformaController() {
    servicio = new PlataformaService();
  }
  
  public static PlataformaController getInstance() {
    if (instance == null) {
      instance = new PlataformaController();
    }
    return instance;
  }
  
  /**
   * Metodo que permite crear una plataforma
   * @param nuevaPlataforma Objeto Plataforma que se desea crear
   */
  @Override
  public void altaPlataforma(Plataforma nuevaPlataforma) {
    servicio.altaPlataforma(nuevaPlataforma);
  }
  
  /**
   * Metodo que permite obtener todas las plataformas
   * @return Mapa con todas las plataformas
   */
  @Override
  public Map<String, Plataforma> obtenerPlataformas() {
    return servicio.obtenerPlataformas();
  }
  
  /**
   * Metodo que permite obtener una plataforma
   * @param nombrePlataforma Nombre de la plataforma que se desea obtener
   * @return Optional con la plataforma
   */
  @Override
  public Optional<Plataforma> obtenerPlataforma(String nombrePlataforma) {
    return servicio.obtenerPlataforma(nombrePlataforma);
  }
}
