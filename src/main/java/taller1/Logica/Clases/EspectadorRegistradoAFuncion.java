package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;


public class EspectadorRegistradoAFuncion {
    private Espectador espectador;
    private Funcion funcion;
    private Paquete paquete;
    private boolean canjeado;
    private double costo;
    private LocalDateTime fechaRegistro;

    public EspectadorRegistradoAFuncion() {
    }
    
    public EspectadorRegistradoAFuncion(Espectador espectador, Funcion funcion, double costo) {
        this.espectador = espectador;
        this.funcion = funcion;
        this.canjeado = false;
        this.costo = costo;
    }
    public EspectadorRegistradoAFuncion(Espectador espectador, Funcion funcion, boolean canjeado, double costo) {
        this.espectador = espectador;
        this.funcion = funcion;
        this.canjeado = canjeado;
        this.costo = costo;
    }
    
    public EspectadorRegistradoAFuncion(Espectador espectador, Funcion funcion, boolean canjeado, double costo, LocalDateTime fechaRegistro) {
        this.espectador = espectador;
        this.funcion = funcion;
        this.canjeado = canjeado;
        this.costo = costo;
        this.fechaRegistro = fechaRegistro;
    }
    
    public EspectadorRegistradoAFuncion(Espectador espectador, Funcion funcion, Paquete paquete, boolean canjeado, double costo) {
        this.espectador = espectador;
        this.funcion = funcion;
        this.paquete = paquete;
        this.canjeado = canjeado;
        this.costo = costo;
    }
    
    public EspectadorRegistradoAFuncion(Espectador espectador, Funcion funcion, Paquete paquete, boolean canjeado, double costo, LocalDateTime fechaRegistro) {
        this.espectador = espectador;
        this.funcion = funcion;
        this.paquete = paquete;
        this.canjeado = canjeado;
        this.costo = costo;
        this.fechaRegistro = fechaRegistro;
    }

    public Espectador getEspectador() {
        return espectador;
    }

    public void setEspectador(Espectador espectador) {
        this.espectador = espectador;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    public Paquete getPaquete() {
        return paquete;
    }
    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public boolean isCanjeado() {
        return canjeado;
    }

    public void setCanjeado(boolean canjeado) {
        this.canjeado = canjeado;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "EspectadorRegistradoAFuncion{" +
                "espectador=" + espectador +
                ", funcion=" + funcion +
                ", paquete=" + paquete +
                ", canjeado=" + canjeado +
                ", costo=" + costo +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}