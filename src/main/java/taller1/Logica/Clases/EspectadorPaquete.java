package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class EspectadorPaquete {
  private Espectador espectador;
  private Paquete paquete;
  private LocalDateTime fechaRegistro;
  
  public EspectadorPaquete(){}
  
  public EspectadorPaquete(Espectador espectador, Paquete paquete, LocalDateTime fechaRegistro) {
    this.espectador = espectador;
    this.paquete = paquete;
    this.fechaRegistro = fechaRegistro;
  }
  
  public Espectador getEspectador() {
    return espectador;
  }
  public void setEspectador(Espectador espectador) {
    this.espectador = espectador;
  }
  
  public Paquete getPaquete() {
    return paquete;
  }
  public void setPaquete(Paquete paquete) {
    this.paquete = paquete;
  }
  
  public LocalDateTime getFechaRegistro() {
    return fechaRegistro;
  }
  public void setFechaRegistro(LocalDateTime fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }
  
  @Override
  public String toString() {
    return "EspectadorPaquete{" +
      "espectador=" + espectador +
      ", paquete=" + paquete +
      ", fechaRegistro=" + fechaRegistro +
      '}';
  }
}
