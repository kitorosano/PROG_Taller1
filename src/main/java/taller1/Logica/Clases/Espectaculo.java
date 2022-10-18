package main.java.taller1.Logica.Clases;

import java.time.LocalDateTime;

public class Espectaculo {
    private String nombre;
    private String descripcion;
    private double duracion;
    private int minEspectadores;
    private int maxEspectadores;
    private String url;
    private double costo;
    private E_EstadoEspectaculo estado;
    private LocalDateTime fechaRegistro;
    private String imagen;
    private Plataforma plataforma;
    private Artista artista;

    public Espectaculo() {
    }

    public Espectaculo(String nombre, String descripcion, double duracion, int minEspectadores, int maxEspectadores, String url, double costo, E_EstadoEspectaculo estado, LocalDateTime fechaRegistro, String imagen, Plataforma plataforma, Artista artistaOrganizador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.minEspectadores = minEspectadores;
        this.maxEspectadores = maxEspectadores;
        this.url = url;
        this.costo = costo;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.imagen = imagen;
        this.plataforma = plataforma;
        this.artista = artistaOrganizador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public int getMinEspectadores() {
        return minEspectadores;
    }

    public void setMinEspectadores(int minEspectadores) {
        this.minEspectadores = minEspectadores;
    }

    public int getMaxEspectadores() {
        return maxEspectadores;
    }

    public void setMaxEspectadores(int maxEspectadores) {
        this.maxEspectadores = maxEspectadores;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    public E_EstadoEspectaculo getEstado() {
        return estado;
    }
    public void setEstado(E_EstadoEspectaculo estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma nombrePlataforma) {
        this.plataforma = nombrePlataforma;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Espectaculo{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", duracion=" + duracion +
                ", minEspectadores=" + minEspectadores +
                ", maxEspectadores=" + maxEspectadores +
                ", url='" + url + '\'' +
                ", costo=" + costo +
                ", estado=" + estado +
                ", fechaRegistro=" + fechaRegistro +
                ", imagen='" + imagen + '\'' +
                ", plataforma=" + plataforma +
                ", arganizador=" + artista +
                '}';
    }
}
