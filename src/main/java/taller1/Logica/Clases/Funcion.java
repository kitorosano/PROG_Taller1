package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class Funcion {
    private String nombre;
    private Espectaculo espectaculo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaRegistro;
    private String imagen;

    public Funcion() {
    }

    public Funcion(String nombre, Espectaculo espectaculo, LocalDateTime fechaHoraInicio, LocalDateTime fechaRegistro, String imagen) {
        this.nombre = nombre;
        this.espectaculo = espectaculo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaRegistro = fechaRegistro;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Espectaculo getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "nombre='" + nombre + '\'' +
                ", espectaculo=" + espectaculo +
                ", fechaHoraInicio=" + fechaHoraInicio +
                ", fechaRegistro=" + fechaRegistro +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
