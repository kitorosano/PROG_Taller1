package main.java.taller1.Logica.DTOs;

public class EspectaculoAPaqueteDTO {
    private String nombrePaquete;
    private String nombreEspectaculo;
    private  String nombrePlataforma;

    public EspectaculoAPaqueteDTO() {
    }

    public EspectaculoAPaqueteDTO(String nombrePaquete, String nombreEspectaculo, String nombrePlataforma) {
        this.nombrePaquete = nombrePaquete;
        this.nombreEspectaculo = nombreEspectaculo;
        this.nombrePlataforma = nombrePlataforma;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public void setNombrePaquete(String nombrePaquete) {
        if (nombrePaquete == null || nombrePaquete.isEmpty())
            throw new IllegalArgumentException("El nombre del paquete no puede ser vacio");
        this.nombrePaquete = nombrePaquete;
    }

    public String getNombreEspectaculo() {
        return nombreEspectaculo;
    }

    public void setNombreEspectaculo(String nombreEspectaculo) {
        if (nombreEspectaculo == null || nombreEspectaculo.isEmpty())
            throw new IllegalArgumentException("El nombre del espectaculo no puede ser vacio");
        this.nombreEspectaculo = nombreEspectaculo;
    }

    public String getNombrePlataforma() {
        return nombrePlataforma;
    }

    public void setNombrePlataforma(String nombrePlataforma) {
        if (nombrePlataforma == null || nombrePlataforma.isEmpty())
            throw new IllegalArgumentException("El nombre de la plataforma no puede ser vacio");
        this.nombrePlataforma = nombrePlataforma;
    }
}

