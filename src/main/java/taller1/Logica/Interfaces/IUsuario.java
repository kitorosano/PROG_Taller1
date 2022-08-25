package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Clases.Usuario;

import java.util.Map;

public interface IUsuario {
    Map<String, Usuario> obtenerUsuarios();
    void altaUsuario(Usuario nuevoUsuario); //usar instanceof para verificar que el usuario es una instancia de Artista o Espectador
    void modificarUsuario(Usuario usuarioModificado);

    Map<String, Espectaculo> obtenerEspectaculosArtista(String nickname);
    Map<String, Funcion> obtenerFuncionesEspectador(String nickname);
    Map<String, Espectador> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion);
}
