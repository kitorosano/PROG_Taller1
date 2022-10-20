package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.List;
import java.util.Map;

public interface IEspectaculo {
    void altaEspectaculo(Espectaculo nuevoEspectaculo);
    Map<String, Espectaculo> obtenerEspectaculos(String nombrePlataforma);
    
    Map<String, Espectaculo> obtenerEspectaculosArtista(String nickname);
}
