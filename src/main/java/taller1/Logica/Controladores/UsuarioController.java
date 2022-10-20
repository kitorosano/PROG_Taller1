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
            "FROM prog_taller1.usuarios as U, artistas as UA " +
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
        String insertUsuario = "INSERT INTO usuarios (u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen) " +
            "VALUES ('" + usuario.getNickname() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getContrasenia() + "', '" + usuario.getImagen() + "')";
        
        if (usuario instanceof Artista)
            insertUsuario += "INSERT INTO artistas (ua_nickname, ua_descripcion, ua_biografia, ua_sitioWeb) " +
                "VALUES ('" + usuario.getNickname() + "', '" + ((Artista) usuario).getDescripcion() + "', '" + ((Artista) usuario).getBiografia() + "', '" + ((Artista) usuario).getSitioWeb() + "')";
        else if (usuario instanceof Espectador)
            insertUsuario += "INSERT INTO espectadores (ue_nickname) " +
                "VALUES ('" + usuario.getNickname() + "')";
        else throw new RuntimeException("Error al modificar el usuario, tipo no reconocido");
                
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
        String updateUsuario = "UPDATE usuarios " +
            "SET u_nombre = '" + usuario.getNombre() + "', u_apellido = '" + usuario.getApellido() + "', u_correo = '" + usuario.getCorreo() + "', u_fechaNacimiento = '" + usuario.getFechaNacimiento() + "', u_contrasenia = '" + usuario.getContrasenia() + "', u_imagen = '" + usuario.getImagen() + "' " +
            "WHERE u_nickname = '" + usuario.getNickname() + "'";
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
    
    
    // METODOS DE ESPECTADOR REGISTRADO A FUNCION
    @Override
    public Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname) {
        Map<String, EspectadorRegistradoAFuncion> funcionesRegistradas = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectFunciones = "SELECT * " +
                "FROM espectadores_funciones as UE_FN, espectadores as UE, usuarios as U, paquetes as PAQ, funciones as FN, espectaculos as ES " +
                "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname AND UE.ue_nickname = U.u_nickname " +
                "  AND UE_FN.ue_fn_nombrePaquete = PAQ.paq_nombre " +
                "  AND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre " +
                "  AND FN.fn_espectaculoAsociado = ES.es_nombre " +
                "  AND UE_FN.ue_fn_nickname = '" + nickname + "'";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectFunciones);
            while (resultSet.next()) {
                Plataforma es_plataformaAsociada = new Plataforma();
                es_plataformaAsociada.setNombre(resultSet.getString("es_plataformaAsociada"));
    
                Artista es_artistaOrganizador = new Artista();
                es_artistaOrganizador.setNickname(resultSet.getString("es_artistaAsociado"));
    
                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                String es_imagen = resultSet.getString("es_imagen");
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, es_plataformaAsociada, es_artistaOrganizador);
    
                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                String fn_imagen = resultSet.getString("fn_imagen");
                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
    
                String ue_nickname = resultSet.getString("ue_nickname");
                String u_nombre = resultSet.getString("u_nombre");
                String u_apellido = resultSet.getString("u_apellido");
                String u_correo = resultSet.getString("u_correo");
                LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                String u_contrasenia = resultSet.getString("u_contrasenia");
                String u_imagen = resultSet.getString("u_imagen");
                Espectador ue = new Espectador(ue_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen);
    
                String paq_nombre = resultSet.getString("paq_nombre");
                String paq_descripcion = resultSet.getString("paq_descripcion");
                Double paq_descuento = resultSet.getDouble("paq_descuento");
                LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
                LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
                String paq_imagen = resultSet.getString("paq_imagen");
                Paquete paq = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
    
                Boolean ue_fn_canjeado = resultSet.getBoolean("ue_fn_canjeado");
                Double ue_fn_costo = resultSet.getDouble("ue_fn_costo");
                LocalDateTime ue_fn_fechaRegistro = resultSet.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime();
                EspectadorRegistradoAFuncion es_fn = new EspectadorRegistradoAFuncion(ue, fn, paq, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro);

                funcionesRegistradas.put(fn_nombre, es_fn);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las funciones del espectador", e);
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
        return funcionesRegistradas;
    }

    @Override
    public Map<String, EspectadorRegistradoAFuncion> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion) {
        Map<String, EspectadorRegistradoAFuncion> espectadoresRegistrados = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectEspectadores = "SELECT * " +
                "FROM espectadores_funciones as UE_FN, espectadores as UE, usuarios as U, paquetes as PAQ, funciones as FN, espectaculos as ES " +
                "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname AND UE.ue_nickname = U.u_nickname " +
                "  AND UE_FN.ue_fn_nombrePaquete = PAQ.paq_nombre " +
                "  AND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre " +
                "  AND FN.fn_espectaculoAsociado = ES.es_nombre " +
                "  AND UE_FN.ue_fn_nombreFuncion = '" + nombreFuncion + "'";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectadores);
            while (resultSet.next()) {
    
                Plataforma es_plataformaAsociada = new Plataforma();
                es_plataformaAsociada.setNombre(resultSet.getString("es_plataformaAsociada"));
    
                Artista es_artistaOrganizador = new Artista();
                es_artistaOrganizador.setNickname(resultSet.getString("es_artistaAsociado"));
    
                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                String es_imagen = resultSet.getString("es_imagen");
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, es_plataformaAsociada, es_artistaOrganizador);
    
                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                String fn_imagen = resultSet.getString("fn_imagen");
                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
    
                String ue_nickname = resultSet.getString("ue_nickname");
                String u_nombre = resultSet.getString("u_nombre");
                String u_apellido = resultSet.getString("u_apellido");
                String u_correo = resultSet.getString("u_correo");
                LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
                String u_contrasenia = resultSet.getString("u_contrasenia");
                String u_imagen = resultSet.getString("u_imagen");
                Espectador ue = new Espectador(ue_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen);
    
                String paq_nombre = resultSet.getString("paq_nombre");
                String paq_descripcion = resultSet.getString("paq_descripcion");
                Double paq_descuento = resultSet.getDouble("paq_descuento");
                LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
                LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
                String paq_imagen = resultSet.getString("paq_imagen");
                Paquete paq = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
                
                Boolean ue_fn_canjeado = resultSet.getBoolean("ue_fn_canjeado");
                Double ue_fn_costo = resultSet.getDouble("ue_fn_costo");
                LocalDateTime ue_fn_fechaRegistro = resultSet.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime();
                EspectadorRegistradoAFuncion es_fn = new EspectadorRegistradoAFuncion(ue, fn, paq, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro);

                espectadoresRegistrados.put(ue_nickname, es_fn);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los espectadores registrados a la funcion", e);
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
        return espectadoresRegistrados;
    }
}
