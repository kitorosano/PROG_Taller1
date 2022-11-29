package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaEspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoNuevoEstadoDTO;

public interface IEspectaculo {
    void altaEspectaculo(AltaEspectaculoDTO nuevoEspectaculo);
    Map<String, Espectaculo> obtenerEspectaculos();
    Optional<Espectaculo> obtenerEspectaculo(String nombrePlataforma, String nombre);
    Map<String, Espectaculo> obtenerEspectaculosPorPlataforma(String nombrePlataforma);
    Map<String, Espectaculo> obtenerEspectaculosPorArtista(String nickname);
    Map<String, Espectaculo> obtenerEspectaculosPorEstado(E_EstadoEspectaculo estado);
    Map<String, Espectaculo> obtenerEspectaculosPorPlataformaYEstado(String nombrePlataforma, E_EstadoEspectaculo estado);
    Map<String, Espectaculo> obtenerEspectaculosPorArtistaYEstado(String nickname, E_EstadoEspectaculo estado);
    void cambiarEstadoEspectaculo(EspectaculoNuevoEstadoDTO espectaculoNuevoEstadoDTO);
}
