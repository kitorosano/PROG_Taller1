package main.java.taller1.Logica.Clases;

public class EspectaculoPaquetes {
    private String nombrePaquete;
    private String nombreEspectaculo;

    public EspectaculoPaquetes() {
    }

    public EspectaculoPaquetes(String nombrePaquete, String nombreEspectaculo) {
        this.nombrePaquete = nombrePaquete;
        this.nombreEspectaculo = nombreEspectaculo;
    }

    public String getPaquete() {
        return nombrePaquete;
    }

    public void setPaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    public String getEspectaculo() {
        return nombrePaquete;
    }

    public void setEspectaculo(String nombreEspectaculo) {
        this.nombreEspectaculo = nombreEspectaculo;
    }

    @Override
    public String toString() {
        return "EspectaculoPaquetes{" +
                "nombrePaquete=" + nombrePaquete +
                ", nombreEspectaculo=" + nombreEspectaculo +
                '}';
    }
}
