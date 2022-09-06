package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.Map;

public interface IEspectaculo {
    void altaPlataforma(Plataforma nuevaPlataforma);
    Map<String, Plataforma> obtenerPlataformas();

    void altaEspectaculo(Espectaculo nuevoEspectaculo);
    Map<String, Espectaculo> obtenerEspectaculos(String nombrePlataforma);

    void altaFuncion(Funcion nuevaFuncion);
    Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo);
    void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones);

    void altaPaquete(Paquete nuevoPaquete);
    Map<String, Paquete> obtenerPaquetes();
    Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete);
    Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo);
}
