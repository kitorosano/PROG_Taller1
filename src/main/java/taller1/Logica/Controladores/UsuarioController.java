package main.java.taller1.Logica.Controladores;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Interfaces.IUsuario;

import java.util.HashMap;
import java.util.Map;


public class UsuarioController implements IUsuario {
    private static UsuarioController instance;

    private UsuarioController() {
    }

    public static UsuarioController getInstance() {
        if (instance == null) {
            instance = new UsuarioController();
        }
        return instance;
    }

    @Override
    public Map<String, Usuario> obtenerUsuarios() {
        return new HashMap<>();
    }

    @Override
    public void altaUsuario(Usuario nuevoUsuario) {

    }

    @Override
    public void modificarUsuario(Usuario usuarioModificado) {

    }

    @Override
    public Map<String, Funcion> obtenerFuncionesEspectador(String nickname) {
        return new HashMap<>();
    }

    @Override
    public Map<String, Espectador> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion) {
        return new HashMap<>();
    }
}
