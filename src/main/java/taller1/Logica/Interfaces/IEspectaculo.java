package main.java.taller1.Logica.Interfaces;

import java.util.Map;
import java.util.Optional;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaEspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoNuevoEstadoDTO;

public interface IEspectaculo {
    void altaEspectaculo(AltaEspectaculoDTO nuevoEspectaculo);
    Map<String, EspectaculoDTO> obtenerEspectaculos();
    Optional<EspectaculoDTO> obtenerEspectaculo(String nombrePlataforma, String nombre);
    Map<String, EspectaculoDTO> obtenerEspectaculosPorPlataforma(String nombrePlataforma);
    Map<String, EspectaculoDTO> obtenerEspectaculosPorArtista(String nickname);
    Map<String, EspectaculoDTO> obtenerEspectaculosPorEstado(E_EstadoEspectaculo estado);
    Map<String, EspectaculoDTO> obtenerEspectaculosPorPaquete(String nombrePaquete);
    Map<String, EspectaculoDTO> obtenerEspectaculosPorPlataformaYEstado(String nombrePlataforma, E_EstadoEspectaculo estado);
    Map<String, EspectaculoDTO> obtenerEspectaculosPorArtistaYEstado(String nickname, E_EstadoEspectaculo estado);
    Map<String, EspectaculoDTO> obtenerEspectaculosFavoritosDeEspectador(String nickname);
    void cambiarEstadoEspectaculo(EspectaculoNuevoEstadoDTO espectaculoNuevoEstadoDTO);
}
