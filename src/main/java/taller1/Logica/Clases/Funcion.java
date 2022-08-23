package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class Funcion {
    private String nombre;
    private String espectaculoAsociado;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaRegistro;

    public Funcion() {
    }

    public Funcion(String nombre, String espectaculoAsociado, LocalDateTime fechaHoraInicio, LocalDateTime fechaRegistro) {
        this.nombre = nombre;
        this.espectaculoAsociado = espectaculoAsociado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspectaculoAsociado() {
        return espectaculoAsociado;
    }

    public void setEspectaculoAsociado(String espectaculoAsociado) {
        this.espectaculoAsociado = espectaculoAsociado;
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
                ", espectaculoAsociado=" + espectaculoAsociado +
                ", fechaHoraInicio=" + fechaHoraInicio +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
