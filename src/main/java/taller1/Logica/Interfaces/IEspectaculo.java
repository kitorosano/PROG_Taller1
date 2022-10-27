package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.Map;
import java.util.Optional;

public interface IEspectaculo {
    void altaEspectaculo(Espectaculo nuevoEspectaculo);
    Map<String, Espectaculo> obtenerEspectaculos();
    Optional<Espectaculo> obtenerEspectaculo(String nombrePlataforma, String nombre);
    Map<String, Espectaculo> obtenerEspectaculosPorPlataforma(String nombrePlataforma);
    Map<String, Espectaculo> obtenerEspectaculosPorArtista(String nickname);
}
