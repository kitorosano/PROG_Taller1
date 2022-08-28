package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class Funcion {
    private String nombre;
    private Espectaculo espectaculo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaRegistro;

    public Funcion() {
    }

    public Funcion(String nombre, Espectaculo espectaculo, LocalDateTime fechaHoraInicio, LocalDateTime fechaRegistro) {
        this.nombre = nombre;
        this.espectaculo = espectaculo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaRegistro = fechaRegistro;
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

    @Override
    public String toString() {
        return "Funcion{" +
                "nombre='" + nombre + '\'' +
                ", espectaculo=" + espectaculo +
                ", fechaHoraInicio=" + fechaHoraInicio +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
