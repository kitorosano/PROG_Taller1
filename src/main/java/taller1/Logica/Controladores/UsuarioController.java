package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IUsuario;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
        Map<String, Usuario> usuarios = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectEspectadores = "SELECT * FROM espectadores ORDER BY nickname";
        String selectArtistas = "SELECT * FROM artistas ORDER BY nickname";
        try {
            connection = ConexionDB.getConnection();

            // Obtenemos los espectadores
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectadores);
            while (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String correo = resultSet.getString("correo");
                LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();

                Usuario usuario = new Espectador(nickname, nombre, apellido, correo, fechaNacimiento);
                usuarios.put(nickname, usuario);
            }

            // Obtenemos los artistas
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectArtistas);
            while (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String correo = resultSet.getString("correo");
                LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
                String descripcion = resultSet.getString("descripcion");
                String biografia = resultSet.getString("biografia");
                String sitioWeb = resultSet.getString("sitioWeb");

                Usuario artista = new Artista(nickname, nombre, apellido, correo, fechaNacimiento, descripcion, biografia, sitioWeb);
                usuarios.put(nickname, artista);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
        return usuarios;
    }

    @Override
    public void altaUsuario(Usuario usuario) {
        Connection connection = null;
        Statement statement = null;
        String insertUsuario = "";
        if (usuario instanceof Espectador)
            insertUsuario = "INSERT INTO espectadores (nickname, nombre, apellido, correo, fechaNacimiento) VALUES ('" + usuario.getNickname() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getFechaNacimiento() + "')";
        else if (usuario instanceof Artista)
            insertUsuario = "INSERT INTO artistas (nickname, nombre, apellido, correo, fechaNacimiento, descripcion, biografia, sitioWeb) VALUES ('" + usuario.getNickname() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getFechaNacimiento() + "', '" + ((Artista) usuario).getDescripcion() + "', '" + ((Artista) usuario).getBiografia() + "', '" + ((Artista) usuario).getSitioWeb() + "')";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertUsuario);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al insertar el usuario", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
    }

    @Override
    public void modificarUsuario(Usuario usuario) {
        Connection connection = null;
        Statement statement = null;
        String updateUsuario = "";
        if (usuario instanceof Espectador)
            updateUsuario = "UPDATE espectadores SET nombre = '" + usuario.getNombre() + "', apellido = '" + usuario.getApellido() + "', correo = '" + usuario.getCorreo() + "', fechaNacimiento = '" + usuario.getFechaNacimiento() + "' WHERE nickname = '" + usuario.getNickname() + "'";
        else if (usuario instanceof Artista)
            updateUsuario = "UPDATE artistas SET nombre = '" + usuario.getNombre() + "', apellido = '" + usuario.getApellido() + "', correo = '" + usuario.getCorreo() + "', fechaNacimiento = '" + usuario.getFechaNacimiento() + "', descripcion = '" + ((Artista) usuario).getDescripcion() + "', biografia = '" + ((Artista) usuario).getBiografia() + "', sitioWeb = '" + ((Artista) usuario).getSitioWeb() + "' WHERE nickname = '" + usuario.getNickname() + "'";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(updateUsuario);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al actualizar el usuario", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
    }

    @Override
    public Map<String, Espectaculo> obtenerEspectaculosArtista(String nickname) {
        return new HashMap<>();
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
