package main.java.taller1.Logica.DTOs;

import main.java.taller1.Logica.Clases.E_EstadoEspectaculo;

import java.io.Serializable;

public class EspectaculoNuevoEstadoDTO implements Serializable {
    private String nombrePlataforma;
    private String nombreEspectaculo;
    private E_EstadoEspectaculo nuevoEstado;

    public EspectaculoNuevoEstadoDTO(){}

    public String getNombrePlataforma() {
        return nombrePlataforma;
    }

    public void setNombrePlataforma(String nombrePlataforma) {
        this.nombrePlataforma = nombrePlataforma;
    }

    public String getNombreEspectaculo() {
        return nombreEspectaculo;
    }

    public void setNombreEspectaculo(String nombreEspectaculo) {
        this.nombreEspectaculo = nombreEspectaculo;
    }

    public E_EstadoEspectaculo getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(E_EstadoEspectaculo nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
