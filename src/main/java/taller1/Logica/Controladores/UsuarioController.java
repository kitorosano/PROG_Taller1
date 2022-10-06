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

    // DATOS PRUEBA

    @Override
    public void vaciarDatos(){
        Connection connection = null;
        Statement statement = null;
        String deleteArtistas = "DELETE FROM artistas";
        String deleteEspectadores = "DELETE FROM espectadores";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(deleteArtistas);
            System.out.println("Datos artistas vaciados");
            statement.executeUpdate(deleteEspectadores);
            System.out.println("Datos espectaculos vaciados");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al vaciar los usuarios", e);
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
    public void cargarDatosPrueba(){
        Connection connection = null;
        try {
            connection = ConexionDB.getConnection();
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("src/main/resources/artistas.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/espectadores.sql"));
            sr.runScript(reader);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error al insertar los datos de prueba de usuarios", ex);
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", ex);
            }
        }
    }


    // METODOS

    @Override
    public Map<String, Usuario> obtenerUsuarios() {
        Map<String, Usuario> usuarios = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectEspectadores = "SELECT * FROM espectadores ORDER BY ue_nickname";
        String selectArtistas = "SELECT * FROM artistas ORDER BY ua_nickname";
        try {
            connection = ConexionDB.getConnection();

            // Obtenemos los espectadores
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectadores);
            while (resultSet.next()) {
                String nickname = resultSet.getString("ue_nickname");
                String nombre = resultSet.getString("ue_nombre");
                String apellido = resultSet.getString("ue_apellido");
                String correo = resultSet.getString("ue_correo");
                LocalDate fechaNacimiento = resultSet.getDate("ue_fechaNacimiento").toLocalDate();
                String contrasena = resultSet.getString("ue_contrasena");
                String imagen = resultSet.getString("ue_imagen");

                Usuario espectador = new Espectador(nickname, nombre, apellido, correo, fechaNacimiento, contrasena, imagen);
                usuarios.put(nickname, espectador);
            }

            // Obtenemos los artistas
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectArtistas);
            while (resultSet.next()) {
                String nickname = resultSet.getString("ua_nickname");
                String nombre = resultSet.getString("ua_nombre");
                String apellido = resultSet.getString("ua_apellido");
                String correo = resultSet.getString("ua_correo");
                LocalDate fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String contrasena = resultSet.getString("ua_contrasena");
                String imagen = resultSet.getString("ua_imagen");
                String descripcion = resultSet.getString("ua_descripcion");
                String biografia = resultSet.getString("ua_biografia");
                String sitioWeb = resultSet.getString("ua_sitioWeb");
                
                Usuario artista = new Artista(nickname, nombre, apellido, correo, fechaNacimiento, contrasena, imagen, descripcion, biografia, sitioWeb);
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
        if (usuario instanceof Artista)
            insertUsuario = "INSERT INTO artistas (ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasena, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb) VALUES ('" + usuario.getNickname() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getContrasena() + "', '" + usuario.getImagen() + "', '" + ((Artista) usuario).getDescripcion() + "', '" + ((Artista) usuario).getBiografia() + "', '" + ((Artista) usuario).getSitioWeb() + "')";
        else if (usuario instanceof Espectador)
            insertUsuario = "INSERT INTO espectadores (ue_nickname, ue_nombre, ue_apellido, ue_correo, ue_fechaNacimiento, ue_contrasena, ue_imagen) VALUES ('" + usuario.getNickname() + "', '" + usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getFechaNacimiento() + "', '" + usuario.getContrasena() + "', '" + usuario.getImagen() + "')";
        else throw new RuntimeException("Error al insertar el usuario, tipo no reconocido");
        
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
        if (usuario instanceof Artista)
            updateUsuario = "UPDATE artistas SET ua_nombre = '" + usuario.getNombre() + "', ua_apellido = '" + usuario.getApellido() + "', ua_correo = '" + usuario.getCorreo() + "', ua_fechaNacimiento = '" + usuario.getFechaNacimiento() + "', ua_contrasena = '" + usuario.getContrasena() + "', ua_imagen = '" + usuario.getImagen() + "', ua_descripcion = '" + ((Artista) usuario).getDescripcion() + "', ua_biografia = '" + ((Artista) usuario).getBiografia() + "', ua_sitioWeb = '" + ((Artista) usuario).getSitioWeb() + "' WHERE ua_nickname = '" + usuario.getNickname() + "'";
        else if (usuario instanceof Espectador)
            updateUsuario = "UPDATE espectadores SET ue_nombre = '" + usuario.getNombre() + "', ue_apellido = '" + usuario.getApellido() + "', ue_correo = '" + usuario.getCorreo() + "', ue_fechaNacimiento = '" + usuario.getFechaNacimiento() + "', ue_contrasena = '" + usuario.getContrasena() + "', ue_imagen = '" + usuario.getImagen() + "' WHERE ue_nickname = '" + usuario.getNickname() + "'";
        else throw new RuntimeException("Error al modificar el usuario, tipo no reconocido");
        
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
        Map<String, Espectaculo> espectaculos = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectEspectaculos = "SELECT * \n" +
                "FROM espectaculos as ES, artistas as UA, plataformas as PL\n" +
                "WHERE ES.es_nombrePlataforma=PL.pl_nombre\n" +
                " \tAND ES.es_artistaOrganizador=UA.ua_nickname" +
                "   AND UA.ua_nickname='" + nickname + "'";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectaculos);
            while (resultSet.next()) {
                String pl_nombre = resultSet.getString("pl_nombre");
                String pl_descripcion = resultSet.getString("pl_descripcion");
                String pl_url = resultSet.getString("pl_url");
                Plataforma pl = new Plataforma(pl_nombre, pl_descripcion, pl_url);

                String ua_nickname = resultSet.getString("ua_nickname");
                String ua_nombre = resultSet.getString("ua_nombre");
                String ua_apellido = resultSet.getString("ua_apellido");
                String ua_correo = resultSet.getString("ua_correo");
                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String ua_contrasena = resultSet.getString("ua_contrasena");
                String ua_imagen = resultSet.getString("ua_imagen");
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasena, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, pl, ua);

                espectaculos.put(es_nombre, es);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los espectaculos del artista", e);
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
        return espectaculos;
    }

//    public Map<String, Artista> obtenerArtistasInvitados(String nombreFuncion) {
//        Map<String, Artista> artistasInvitados = new HashMap<>();
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        String selectEspectadores = "SELECT UA_FN.*, UA.*, FN.*, ES.*, PL.*, UA_INV.ua_nickname AS ua_inv_nickname, UA_INV.nombre AS ua_inv_nombre,  \n" +
//                "FROM artistas_funciones as UA_FN, artistas as UA, funciones as FN, espectaculos as ES, plataformas as PL, artistas UA_INV \n" +
//                "WHERE UA_FN.ua_fn_nickname = UA.ua_nickname \n" +
//                "AND UA_FN.ua_fn_nombreFuncion = FN.fn_nombre \n" +
//                "AND FN.fn_espectaculoAsociado = ES.es_nombre \n" +
//                "AND ES.es_nombrePlataforma = PL.pl_nombre \n" +
//                "AND ES.es_artistaOrganizador = UA_INV.ua_nickname \n" +
//                "AND UA_FN.ua_fn_nombreFuncion='" + nombreFuncion + "'";
//        try {
//            connection = ConexionDB.getConnection();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(selectEspectadores);
//            while (resultSet.next()) {
//                String pl_nombre = resultSet.getString("pl_nombre");
//                String pl_descripcion = resultSet.getString("pl_descripcion");
//                String pl_url = resultSet.getString("pl_url");
//                Plataforma pl = new Plataforma(pl_nombre, pl_descripcion, pl_url);
//
//                String ua_nickname = resultSet.getString("ua_nickname");
//                String ua_nombre = resultSet.getString("ua_nombre");
//                String ua_apellido = resultSet.getString("ua_apellido");
//                String ua_correo = resultSet.getString("ua_correo");
//                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
//                String ua_descripcion = resultSet.getString("ua_descripcion");
//                String ua_biografia = resultSet.getString("ua_biografia");
//                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
//                Artista ua_organizador = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);
//
//                String es_nombre = resultSet.getString("es_nombre");
//                String es_descripcion = resultSet.getString("es_descripcion");
//                Double es_duracion = resultSet.getDouble("es_duracion");
//                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
//                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
//                String es_url = resultSet.getString("es_url");
//                Double es_costo = resultSet.getDouble("es_costo");
//                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
//                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, pl, ua);
//
//                String fn_nombre = resultSet.getString("fn_nombre");
//                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
//                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
//                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro);
//
//                String ue_nickname = resultSet.getString("ue_nickname");
//                String ue_nombre = resultSet.getString("ue_nombre");
//                String ue_apellido = resultSet.getString("ue_apellido");
//                String ue_correo = resultSet.getString("ue_correo");
//                LocalDate ue_fechaNacimiento = resultSet.getDate("ue_fechaNacimiento").toLocalDate();
//                Espectador ue = new Espectador(ue_nickname, ue_nombre, ue_apellido, ue_correo, ue_fechaNacimiento);
//
//                Boolean ue_fn_canjeado = resultSet.getBoolean("ue_fn_canjeado");
//                Double ue_fn_costo = resultSet.getDouble("ue_fn_costo");
//                LocalDateTime ue_fn_fechaRegistro = resultSet.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime();
//                EspectadorRegistradoAFuncion es_fn = new EspectadorRegistradoAFuncion(ue, fn, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro);
//
//                espectadoresRegistrados.put(ue_nickname, es_fn);
//            }
//        } catch (RuntimeException e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException("Error al conectar con la base de datos", e);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException("Error al obtener los espectadores registrados a la funcion", e);
//        } finally {
//            try {
//                if (resultSet != null) resultSet.close();
//                if (statement != null) statement.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
//            }
//        }
//        return espectadoresRegistrados;
//    }

    @Override
    public Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname) {
        Map<String, EspectadorRegistradoAFuncion> funcionesRegistradas = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectFunciones = "SELECT *\n" +
                "FROM espectadores_funciones as UE_FN, espectadores as UE, funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA\n" +
                "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname\n" +
                "  \tAND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre\n" +
                "    AND FN.fn_espectaculoAsociado = ES.es_nombre\n" +
                "    AND ES.es_nombrePlataforma = PL.pl_nombre\n" +
                "    AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
                "    AND UE_FN.ue_fn_nickname = '" + nickname + "'";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectFunciones);
            while (resultSet.next()) {
                String pl_nombre = resultSet.getString("pl_nombre");
                String pl_descripcion = resultSet.getString("pl_descripcion");
                String pl_url = resultSet.getString("pl_url");
                Plataforma pl = new Plataforma(pl_nombre, pl_descripcion, pl_url);

                String ua_nickname = resultSet.getString("ua_nickname");
                String ua_nombre = resultSet.getString("ua_nombre");
                String ua_apellido = resultSet.getString("ua_apellido");
                String ua_correo = resultSet.getString("ua_correo");
                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String ua_contrasena = resultSet.getString("ua_contrasena");
                String ua_imagen = resultSet.getString("ua_imagen");
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasena, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, pl, ua);

                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro);

                String ue_nickname = resultSet.getString("ue_nickname");
                String ue_nombre = resultSet.getString("ue_nombre");
                String ue_apellido = resultSet.getString("ue_apellido");
                String ue_correo = resultSet.getString("ue_correo");
                LocalDate ue_fechaNacimiento = resultSet.getDate("ue_fechaNacimiento").toLocalDate();
                String ue_contrasena = resultSet.getString("ue_contrasena");
                String ue_imagen = resultSet.getString("ue_imagen");
                Espectador ue = new Espectador(ue_nickname, ue_nombre, ue_apellido, ue_correo, ue_fechaNacimiento, ue_contrasena, ue_imagen);

                Boolean ue_fn_canjeado = resultSet.getBoolean("ue_fn_canjeado");
                Double ue_fn_costo = resultSet.getDouble("ue_fn_costo");
                LocalDateTime ue_fn_fechaRegistro = resultSet.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime();
                EspectadorRegistradoAFuncion es_fn = new EspectadorRegistradoAFuncion(ue, fn, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro);

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
        String selectEspectadores = "SELECT *\n" +
                "FROM espectadores_funciones as UE_FN, espectadores as UE, funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA\n" +
                "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname\n" +
                "  \tAND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre\n" +
                "    AND FN.fn_espectaculoAsociado = ES.es_nombre\n" +
                "    AND ES.es_nombrePlataforma = PL.pl_nombre\n" +
                "    AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
                "    AND UE_FN.ue_fn_nombreFuncion = '" + nombreFuncion + "'";              //CAMBIAR ES_FN A UE_FN
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectadores);
            while (resultSet.next()) {
                String pl_nombre = resultSet.getString("pl_nombre");
                String pl_descripcion = resultSet.getString("pl_descripcion");
                String pl_url = resultSet.getString("pl_url");
                Plataforma pl = new Plataforma(pl_nombre, pl_descripcion, pl_url);

                String ua_nickname = resultSet.getString("ua_nickname");
                String ua_nombre = resultSet.getString("ua_nombre");
                String ua_apellido = resultSet.getString("ua_apellido");
                String ua_correo = resultSet.getString("ua_correo");
                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String ua_contrasena = resultSet.getString("ua_contrasena");
                String ua_imagen = resultSet.getString("ua_imagen");
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasena, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, pl, ua);

                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro);

                String ue_nickname = resultSet.getString("ue_nickname");
                String ue_nombre = resultSet.getString("ue_nombre");
                String ue_apellido = resultSet.getString("ue_apellido");
                String ue_correo = resultSet.getString("ue_correo");
                LocalDate ue_fechaNacimiento = resultSet.getDate("ue_fechaNacimiento").toLocalDate();
                String ue_contrasena = resultSet.getString("ue_contrasena");
                String ue_imagen = resultSet.getString("ue_imagen");
                Espectador ue = new Espectador(ue_nickname, ue_nombre, ue_apellido, ue_correo, ue_fechaNacimiento, ue_contrasena, ue_imagen);

                Boolean ue_fn_canjeado = resultSet.getBoolean("ue_fn_canjeado");
                Double ue_fn_costo = resultSet.getDouble("ue_fn_costo");
                LocalDateTime ue_fn_fechaRegistro = resultSet.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime();
                EspectadorRegistradoAFuncion es_fn = new EspectadorRegistradoAFuncion(ue, fn, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro);

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
