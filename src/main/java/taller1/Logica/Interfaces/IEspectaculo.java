package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.List;
import java.util.Map;

public interface IEspectaculo {
    // METODOS
    void altaPlataforma(Plataforma nuevaPlataforma);
    Map<String, Plataforma> obtenerPlataformas();

    void altaEspectaculo(Espectaculo nuevoEspectaculo);
    Map<String, Espectaculo> obtenerEspectaculos(String nombrePlataforma);

    void altaFuncion(Funcion nuevaFuncion);
    Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo);
    Map<String, Artista> obtenerArtistasInvitados(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion);
    Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista);
    void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones);

    void altaPaquete(Paquete nuevoPaquete);
    Map<String, Paquete> obtenerPaquetes();
    Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete);
    Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo);
    void altaEspectaculosAPaquete(Map<String, Espectaculo> espectaculos, String nombrePaquete);
    
    void altaCategoria(Categoria nuevaCategoria);
    Map<String, Categoria> obtenerCategorias();
    Map<String, Espectaculo> obtenerEspectaculosDeCategoria(String nombreCategoria);
    Map<String, Categoria> obtenerCategoriasDeEspectaculo(String nombreEspectaculo);
    void altaCategoriasAEspectaculo(Map<String, Categoria> categorias, String nombreEspectaculo);
}
