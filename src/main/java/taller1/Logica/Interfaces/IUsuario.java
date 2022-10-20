package main.java.taller1.Logica.Interfaces;

import main.java.taller1.Logica.Clases.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface IUsuario {
    Map<String, Usuario> obtenerUsuarios();
    Optional<Usuario> obtenerUsuarioPorNickname(String nickname);
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);
    void altaUsuario(Usuario nuevoUsuario); //usar instanceof para verificar que el usuario es una instancia de Artista o Espectador
    void modificarUsuario(Usuario usuarioModificado);
}
