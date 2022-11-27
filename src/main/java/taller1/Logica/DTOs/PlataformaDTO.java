package main.java.taller1.Logica.DTOs;

import main.java.taller1.Logica.Mappers.PlataformaMapper;

import javax.swing.*;

public class PlataformaDTO {
    private String nombre;
    private String descripcion;
    private String url;

    private String regexURL = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";


    public PlataformaDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser vacio");
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty())
            throw new IllegalArgumentException("la descripcion no puede ser vacia");
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url == null || url.isEmpty())
            throw new IllegalArgumentException("El url no puede ser vacio");
            //queda validar url
        if (url.matches(regexURL)) {
            throw new IllegalArgumentException("El url no es valido");
        }
        this.url = url;
    }
}