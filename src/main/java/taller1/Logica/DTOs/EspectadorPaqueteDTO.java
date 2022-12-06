package main.java.taller1.Logica.DTOs;

import java.io.Serializable;

public class EspectadorPaqueteDTO implements Serializable {
  private UsuarioDTO espectador;
  private PaqueteDTO paquete;
  private String fechaRegistro;
  
  public EspectadorPaqueteDTO(){}
  
  
  public UsuarioDTO getEspectador() {
    return espectador;
  }
  public void setEspectador(UsuarioDTO espectador) {
    this.espectador = espectador;
  }
  
  public PaqueteDTO getPaquete() {
    return paquete;
  }
  public void setPaquete(PaqueteDTO paquete) {
    this.paquete = paquete;
  }
  
  public String getFechaRegistro() {
    return fechaRegistro;
  }
  public void setFechaRegistro(String fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }
  
}
