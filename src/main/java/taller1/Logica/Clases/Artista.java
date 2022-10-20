package main.java.taller1.Logica.Clases;

import java.time.LocalDate;

public class Artista extends Usuario {
    private String descripcion;
    private String biografia;
    private String sitioWeb;

    public Artista(){}
    
    public Artista(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String contrasenia, String imagen, String descripcion, String biografia, String sitioWeb) {
        super(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen);
        this.descripcion = descripcion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    @Override
    public String toString() {
        return super.toString() + "Artista{" +
                "descripcion='" + descripcion + '\'' +
                ", biografia='" + biografia + '\'' +
                ", sitioWeb='" + sitioWeb + '\'' +
                '}';
    }
}
