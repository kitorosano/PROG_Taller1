package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class Paquete {
    private String nombre;
    private LocalDateTime fechaExpiracion;
    private String descripcion;
    private double descuento;
    private LocalDateTime fechaRegistro;

    public Paquete() {
    }

    public Paquete(String nombre, LocalDateTime fechaExpiracion, String descripcion, double descuento, LocalDateTime fechaRegistro) {
        this.nombre = nombre;
        this.fechaExpiracion = fechaExpiracion;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "nombre='" + nombre + '\'' +
                ", fechaExpiracion=" + fechaExpiracion +
                ", descripcion='" + descripcion + '\'' +
                ", descuento=" + descuento +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
