package main.java.taller1.Logica.DTOs;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {
    private String nombre;
    public CategoriaDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
