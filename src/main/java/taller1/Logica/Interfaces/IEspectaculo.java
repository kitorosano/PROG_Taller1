package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.Map;

public interface IEspectaculo {
    void altaEspectaculo(Espectaculo nuevoEspectaculo);
    Map<String, Espectaculo> obtenerEspectaculos();
    Espectaculo obtenerEspectaculoPorNombre(String nombrePlataforma, String nombre);
    Map<String, Espectaculo> obtenerEspectaculosPorPlataforma(String nombrePlataforma);
    Map<String, Espectaculo> obtenerEspectaculosPorArtista(String nickname);
}
