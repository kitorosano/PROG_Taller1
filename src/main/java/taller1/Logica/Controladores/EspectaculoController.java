package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IEspectaculo;

import java.util.HashMap;
import java.util.Map;

public class EspectaculoController implements IEspectaculo {
    private static EspectaculoController instance;

    private EspectaculoController() {
    }

    public static EspectaculoController getInstance() {
        if (instance == null) {
            instance = new EspectaculoController();
        }
        return instance;
    }

    @Override
    public Map<String, Plataforma> obtenerPlataformas() {
        return new HashMap<>();
    }

    @Override
    public void altaPlataforma(Plataforma nuevaPlataforma) {

    }

    @Override
    public void altaEspectaculo(String nombrePlataforma, Espectaculo nuevoEspectaculo) {

    }

    @Override
    public void altaFuncion(String nombrePlataforma, String nombreEspectaculo, Funcion nuevaFuncion) {

    }

    @Override
    public void registrarEspectadoresAFunciones(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion, Map<String, Espectador> espectadores) {

    }

    @Override
    public Map<String, Paquete> obtenerPaquetes() {
        return new HashMap<>();
    }

    @Override
    public Map<String, Espectaculo> obtenerEspectaculosPaquete(String nombrePaquete) {
        return new HashMap<>();
    }
}
