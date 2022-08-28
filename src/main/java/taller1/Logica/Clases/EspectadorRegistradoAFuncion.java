package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class EspectadorRegistradoAFuncion {
    private Espectador espectador;
    private Funcion funcion;
    private boolean canjeado;
    private double costo;
    private LocalDateTime fechaRegistro;

    public EspectadorRegistradoAFuncion() {
    }

    public EspectadorRegistradoAFuncion(Espectador espectador, Funcion funcion, boolean canjeado, double costo, LocalDateTime fechaRegistro) {
        this.espectador = espectador;
        this.funcion = funcion;
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
                ", canjeado=" + canjeado +
                ", costo=" + costo +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
