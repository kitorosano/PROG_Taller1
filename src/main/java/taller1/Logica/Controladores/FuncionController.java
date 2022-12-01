package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.Interfaces.IFuncion;
import main.java.taller1.Logica.Servicios.FuncionService;
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

public class FuncionController implements IFuncion {
  private static FuncionController instance;
  private FuncionService servicio;
  
  private FuncionController() {
    servicio = new FuncionService();
  }
  
  public static FuncionController getInstance() {
    if (instance == null) {
      instance = new FuncionController();
    }
    return instance;
  }
  
  @Override
  public void altaFuncion(FuncionDTO nuevaFuncion) {
    servicio.altaFuncion(nuevaFuncion);
  }
  @Override
  public Map<String, Funcion> obtenerFunciones() {
    return servicio.obtenerFunciones();
  }
  
  @Override
  public Optional<Funcion> obtenerFuncion(String nombrePlataforma, String nombreEspectador, String nombreFuncion) {
    return servicio.obtenerFuncion(nombrePlataforma, nombreEspectador, nombreFuncion);
  }
  @Override
  public Map<String, Funcion> obtenerFuncionesDePlataforma(String nombrePlataforma) {
    return servicio.obtenerFuncionesDePlataforma(nombrePlataforma);
  }
  
  @Override
  public Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo) {
    return servicio.obtenerFuncionesDeEspectaculo(nombrePlataforma, nombreEspectaculo);
  }
  @Override
  public Map<String, Usuario> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {
    return servicio.obtenerArtistasInvitadosAFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion);
  }
  
  @Override
  public Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista) {
    return servicio.obtenerFuncionesDeArtista(nombrePlataforma, nombreEspectaculo, nombreArtista);
  }
  
}
