package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.Map;

public interface IEspectaculo {
    Map<String, Plataforma> obtenerPlataformas();
    void altaPlataforma(Plataforma nuevaPlataforma);
    void altaEspectaculo(String nombrePlataforma, Espectaculo nuevoEspectaculo);
    void altaFuncion(String nombrePlataforma, String nombreEspectaculo, Funcion nuevaFuncion);
    void registrarEspectadoresAFunciones(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion,Map<String, Espectador> espectadores);
    Map<String, Paquete> obtenerPaquetes();
    Map<String, Espectaculo> obtenerEspectaculosPaquete(String nombrePaquete);
}
