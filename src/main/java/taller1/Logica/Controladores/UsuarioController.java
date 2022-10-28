package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IUsuario;
import main.java.taller1.Persistencia.ConexionDB;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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
        String selectEspectadores = "SELECT * " +
            "FROM usuarios as U, espectadores as UE " +
            "WHERE UE.ue_nickname=U.u_nickname " +
            "ORDER BY ue_nickname";
        String selectArtistas = "SELECT * " +
            "FROM usuarios as U, artistas as UA " +
            "WHERE UA.ua_nickname=U.u_nickname " +
            "ORDER BY UA.ua_nickname";
        try {
            connection = ConexionDB.getConnection();

            // Obtenemos los espectadores
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectadores);
            while (resultSet.next()) {
                String nickname = resultSet.getString("u_nickname");
                String nombre = resultSet.getString("u_nombre");
                String apellido = resultSet.getString("u_apellido");
                String correo = resultSet.getString("u_correo");
                LocalDate fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                String contrasenia = resultSet.getString("u_contrasenia");
                String imagen = resultSet.getString("u_imagen");

                Usuario espectador = new Espectador(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen);
                usuarios.put(nickname, espectador);
            }

            // Obtenemos los artistas
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectArtistas);
            while (resultSet.next()) {
                String nickname = resultSet.getString("u_nickname");
                String nombre = resultSet.getString("u_nombre");
                String apellido = resultSet.getString("u_apellido");
                String correo = resultSet.getString("u_correo");
                LocalDate fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                String contrasenia = resultSet.getString("u_contrasenia");
                String imagen = resultSet.getString("u_imagen");
                String descripcion = resultSet.getString("ua_descripcion");
                String biografia = resultSet.getString("ua_biografia");
                String sitioWeb = resultSet.getString("ua_sitioWeb");
                
                Usuario artista = new Artista(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen, descripcion, biografia, sitioWeb);
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
    public Optional<Usuario> obtenerUsuarioPorNickname(String nickname){
        Usuario usuario = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectUsuario = "SELECT * " +
                                "FROM usuarios as U, artistas as UA " +
                                "WHERE UA.ua_nickname=U.u_nickname AND U.u_nickname = '" + nickname + "'";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectUsuario);
            if (resultSet.next()) { // Es un artista
                String nombre = resultSet.getString("u_nombre");
                String apellido = resultSet.getString("u_apellido");
                String correo = resultSet.getString("u_correo");
                LocalDate fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                String contrasenia = resultSet.getString("u_contrasenia");
                String imagen = resultSet.getString("u_imagen");
                String descripcion = resultSet.getString("ua_descripcion");
                String biografia = resultSet.getString("ua_biografia");
                String sitioWeb = resultSet.getString("ua_sitioWeb");
                
                usuario = new Artista(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen, descripcion, biografia, sitioWeb);
            } else { // Es un espectador
                selectUsuario = "SELECT * " +
                                "FROM usuarios as U, espectadores as UE " +
                                "WHERE UE.ue_nickname=U.u_nickname AND U.u_nickname = '" + nickname + "'";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectUsuario);
                if (resultSet.next()) {
                    String nombre = resultSet.getString("u_nombre");
                    String apellido = resultSet.getString("u_apellido");
                    String correo = resultSet.getString("u_correo");
                    LocalDate fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                    String contrasenia = resultSet.getString("u_contrasenia");
                    String imagen = resultSet.getString("u_imagen");
                    
                    usuario = new Espectador(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen);
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el usuario", e);
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
        
        return Optional.ofNullable(usuario);
    }
    
    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo){
        Usuario usuario = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectUsuario = "SELECT * " +
            "FROM usuarios as U, artistas as UA " +
            "WHERE UA.ua_nickname=U.u_nickname AND U.u_correo = '" + correo + "'";
        
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectUsuario);
            if (resultSet.next()) { // Es un artista
                String nickname = resultSet.getString("u_nickname");
                String nombre = resultSet.getString("u_nombre");
                String apellido = resultSet.getString("u_apellido");
                LocalDate fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                String contrasenia = resultSet.getString("u_contrasenia");
                String imagen = resultSet.getString("u_imagen");
                String descripcion = resultSet.getString("ua_descripcion");
                String biografia = resultSet.getString("ua_biografia");
                String sitioWeb = resultSet.getString("ua_sitioWeb");
                
                usuario = new Artista(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen, descripcion, biografia, sitioWeb);
            } else { // Es un espectador
                selectUsuario = "SELECT * " +
                    "FROM usuarios as U, espectadores as UE " +
                    "WHERE UE.ue_nickname=U.u_nickname AND U.u_correo = '" + correo + "'";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectUsuario);
                if (resultSet.next()) {
                    String nickname = resultSet.getString("u_nickname");
                    String nombre = resultSet.getString("u_nombre");
                    String apellido = resultSet.getString("u_apellido");
                    LocalDate fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                    String contrasenia = resultSet.getString("u_contrasenia");
                    String imagen = resultSet.getString("u_imagen");
                    
                    usuario = new Espectador(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen);
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el usuario", e);
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
        
        return Optional.ofNullable(usuario);
    }

    @Override
    public void altaUsuario(Usuario usuario) {
        Connection connection = null;
        Statement statement = null;
        String insertUsuario = "INSERT INTO `usuarios` (`u_nickname`, `u_nombre`, `u_apellido`, `u_correo`, `u_fechaNacimiento`, `u_contrasenia`, `u_imagen`) " +
            "VALUES ('" + usuario.getNickname() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getContrasenia() + "', '" + usuario.getImagen() + "'); ";
        
        String insertUsuario2;
        if (usuario instanceof Artista)
            insertUsuario2 = "INSERT INTO `artistas` (`ua_nickname`, `ua_descripcion`, `ua_biografia`, `ua_sitioWeb`) " +
                "VALUES ('" + usuario.getNickname() + "', '" + ((Artista) usuario).getDescripcion() + "', '" + ((Artista) usuario).getBiografia() + "', '" + ((Artista) usuario).getSitioWeb() + "')";
        else if (usuario instanceof Espectador)
            insertUsuario2 = "INSERT INTO `espectadores` (`ue_nickname`) " +
                "VALUES ('" + usuario.getNickname() + "')";
        else throw new RuntimeException("Error al insertar el usuario, tipo no reconocido");
                
        try {
            System.out.println(insertUsuario);
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertUsuario);
            statement.executeUpdate(insertUsuario2);
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
        String updateUsuario = "UPDATE usuarios " +
            "SET u_nombre = '" + usuario.getNombre() + "', u_apellido = '" + usuario.getApellido() + "', u_correo = '" + usuario.getCorreo() + "', u_fechaNacimiento = '" + usuario.getFechaNacimiento() + "', u_contrasenia = '" + usuario.getContrasenia() + "', u_imagen = '" + usuario.getImagen() + "' " +
            "WHERE u_nickname = '" + usuario.getNickname() + "'; ";
        if (usuario instanceof Artista)
            updateUsuario += "UPDATE artistas " +
                "SET ua_descripcion = '" + ((Artista) usuario).getDescripcion() + "', ua_biografia = '" + ((Artista) usuario).getBiografia() + "', ua_sitioWeb = '" + ((Artista) usuario).getSitioWeb() + "' " +
                "WHERE ua_nickname = '" + usuario.getNickname() + "'";
        
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
}
