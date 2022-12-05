package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.EspectaculoFavoritoDTO;
import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Interfaces.IUsuario;
import main.java.taller1.Logica.Servicios.UsuarioService;

import java.util.Map;
import java.util.Optional;


public class UsuarioController implements IUsuario {
    private static UsuarioController instance;
    private UsuarioService servicio;

    private UsuarioController() {
        servicio = new UsuarioService();
    }

    public static UsuarioController getInstance() {
        if (instance == null) {
            instance = new UsuarioController();
        }
        return instance;
    }
    
    /**
     * Metodo que obtiene todos los usuarios registrados en la base de datos
     * @return Mapa con todos los usuarios registrados en la base de datos
     */
    @Override
    public Map<String, Usuario> obtenerUsuarios() {
        return servicio.obtenerUsuarios();
    }
    
    /**
     * Metodo que obtiene un usuario registrado en la base de datos a partir de su nickname
     * @param nickname Nickname del usuario a buscar
     * @return Optional con el usuario encontrado
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorNickname(String nickname){
        return servicio.obtenerUsuarioPorNickname(nickname);
    }
    
    /**
     * Metodo que obtiene un usuario registrado en la base de datos a partir de su correo
     * @param correo Correo del usuario a buscar
     * @return Optional con el usuario encontrado
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo){
        return servicio.obtenerUsuarioPorCorreo(correo);
    }

    /**
     * Metodo que ingresa un usuario a la base de datos
     * @param usuariodto Objeto usuariodto a ingresar
     */
    @Override
    public void altaUsuario(UsuarioDTO usuariodto) {
        servicio.altaUsuario(usuariodto);
    }

    /**
     * Metodo que modifica un usuario en la base de datos
     * @param usuariodto Objeto usuariodto con los datos a modificar
     */
    @Override
    public void modificarUsuario(UsuarioDTO usuariodto) {
        servicio.modificarUsuario(usuariodto);
    }

    public void marcarFavorito(EspectaculoFavoritoDTO espectaculoFavoritoDTO){
        servicio.marcarFavorito(espectaculoFavoritoDTO);
    }
    public void desmarcarFavorito(EspectaculoFavoritoDTO espectaculoFavoritoDTO){
        servicio.desmarcarFavorito(espectaculoFavoritoDTO);
    }
    public Map<String, String> obtenerEspectaculosFavoritos(String nickname){
        return servicio.obtenerEspectaculosFavoritos(nickname);
    }
}
