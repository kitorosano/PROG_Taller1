package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IUsuario;
import main.java.taller1.Persistencia.ConexionDB;

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
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);

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
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, pl , ua);

                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro);

                String ue_nickname = resultSet.getString("ue_nickname");
                String ue_nombre = resultSet.getString("ue_nombre");
                String ue_apellido = resultSet.getString("ue_apellido");
                String ue_correo = resultSet.getString("ue_correo");
                LocalDate ue_fechaNacimiento = resultSet.getDate("ue_fechaNacimiento").toLocalDate();
                Espectador ue = new Espectador(ue_nickname, ue_nombre, ue_apellido, ue_correo, ue_fechaNacimiento);

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
                                    "    AND ES_FN.ue_fn_nombreFuncion = '" + nombreFuncion + "'";
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
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista ua = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                Double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, pl , ua);

                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                Funcion fn = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro);

                String ue_nickname = resultSet.getString("ue_nickname");
                String ue_nombre = resultSet.getString("ue_nombre");
                String ue_apellido = resultSet.getString("ue_apellido");
                String ue_correo = resultSet.getString("ue_correo");
                LocalDate ue_fechaNacimiento = resultSet.getDate("ue_fechaNacimiento").toLocalDate();
                Espectador ue = new Espectador(ue_nickname, ue_nombre, ue_apellido, ue_correo, ue_fechaNacimiento);

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
