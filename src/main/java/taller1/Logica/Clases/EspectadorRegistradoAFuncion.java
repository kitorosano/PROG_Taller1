package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class EspectadorRegistradoAFuncion {
    private String nickname;
    private String nombreFuncion;
    private boolean canjeado;
    private double costo;
    private LocalDateTime fechaRegistro;

    public EspectadorRegistradoAFuncion() {
    }

    public EspectadorRegistradoAFuncion(String nickname, String nombreFuncion, boolean canjeado, double costo, LocalDateTime fechaRegistro) {
        this.nickname = nickname;
        this.nombreFuncion = nombreFuncion;
        this.canjeado = canjeado;
        this.costo = costo;
        this.fechaRegistro = fechaRegistro;
    }

    public String getEspectador() {
        return nickname;
    }

    public void setEspectador(String nickname) {
        this.nickname = nickname;
    }

    public String getFuncion() {
        return nombreFuncion;
    }

    public void setFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
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
                "nickname=" + nickname +
                ", nombreFuncion=" + nombreFuncion +
                ", canjeado=" + canjeado +
                ", costo=" + costo +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
