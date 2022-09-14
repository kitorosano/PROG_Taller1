package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.HashMap;
import java.util.Map;

public interface IUsuario {
    // CARGAR DATOS PRUEBA
    void cargarDatosPrueba();
    void vaciarDatos();

    // METODOS
    Map<String, Usuario> obtenerUsuarios();
    void altaUsuario(Usuario nuevoUsuario); //usar instanceof para verificar que el usuario es una instancia de Artista o Espectador
    void modificarUsuario(Usuario usuarioModificado);

    Map<String, Espectaculo> obtenerEspectaculosArtista(String nickname);
//    Map<String, Artista> obtenerArtistasInvitados(String nombreFuncion);
    Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname);
    Map<String, EspectadorRegistradoAFuncion> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion);
}
