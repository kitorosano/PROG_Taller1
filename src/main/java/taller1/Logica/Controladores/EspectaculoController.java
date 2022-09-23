package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IEspectaculo;
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

public class EspectaculoController implements IEspectaculo {
    private static EspectaculoController instance;

    private EspectaculoController() {
    }

    public static EspectaculoController getInstance() {
        if (instance == null) {
            instance = new EspectaculoController();
        }
        return instance;
    }

    @Override
    public void vaciarDatos(){
        Connection connection = null;
        Statement statement = null;
        String deleteQuery1 = "DELETE FROM plataformas;";
        String deleteQuery2 = "DELETE FROM paquetes;";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(deleteQuery1);
            statement.executeUpdate(deleteQuery2);
            System.out.println("Todos los datos relacionados a los espectaculos (plataforma, funciones, paquetes y registros) han sido eliminados.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al vaciar los datos de los espectaculos", e);
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
            Reader reader = new BufferedReader(new FileReader("src/main/resources/plataformas.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/paquetes.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/espectaculos.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/funciones.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/espectaculos_paquetes.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/espectadores_funciones.sql"));
            sr.runScript(reader);
            reader = new BufferedReader(new FileReader("src/main/resources/artistas_funciones.sql"));
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

    @Override
    public void altaPlataforma(Plataforma nuevaPlataforma) {
        Connection connection = null;
        Statement statement = null;
        String insertPlataforma = "INSERT INTO plataformas (pl_nombre, pl_descripcion, pl_url) VALUES ('" + nuevaPlataforma.getNombre() + "', '" + nuevaPlataforma.getDescripcion() + "', '" + nuevaPlataforma.getUrl() + "')";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertPlataforma);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al insertar la plataforma", e);
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
    public Map<String, Plataforma> obtenerPlataformas() {
        Map<String, Plataforma> plataformas = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectPlataformas = "SELECT * FROM plataformas order by pl_nombre";
        try {
            connection = ConexionDB.getConnection();

            // Obtenemos todas las plataformas de la base de datos
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectPlataformas);
            while (resultSet.next()) {
                String nombre = resultSet.getString("pl_nombre");
                String descripcion = resultSet.getString("pl_descripcion");
                String url = resultSet.getString("pl_url");

                Plataforma plataforma = new Plataforma(nombre, descripcion, url);
                plataformas.put(nombre, plataforma);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las plataformas", e);
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
        return plataformas;
    }

    @Override
    public void altaEspectaculo(Espectaculo nuevoEspectaculo) {
        Connection connection = null;
        Statement statement = null;
        String insertEspectaculo = "INSERT INTO espectaculos (es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, es_nombrePlataforma, es_artistaOrganizador)\n" +
                "VALUES ('" + nuevoEspectaculo.getNombre() + "', '" + nuevoEspectaculo.getDescripcion() + "', '" + nuevoEspectaculo.getDuracion() + "', '" + nuevoEspectaculo.getMinEspectadores() + "', '" + nuevoEspectaculo.getMaxEspectadores() + "', '" + nuevoEspectaculo.getUrl() + "', '" + nuevoEspectaculo.getCosto() + "', '" + nuevoEspectaculo.getFechaRegistro() + "', '" + nuevoEspectaculo.getPlataforma().getNombre() + "', '" + nuevoEspectaculo.getArtista().getNickname() + "')";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertEspectaculo);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al insertar el espectaculo", e);
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
    public Map<String, Espectaculo> obtenerEspectaculos(String nombrePlataforma) {
        Map<String, Espectaculo> espectaculos = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectEspectaculos = "SELECT *\n" +
                "FROM espectaculos as ES, artistas as UA, plataformas as PL\n" +
                "WHERE ES.es_nombrePlataforma = PL.pl_nombre\n" +
                "  AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
                "  AND es_nombrePlataforma = '" + nombrePlataforma + "'";
        try {
            connection = ConexionDB.getConnection();

            // Obtenemos todas las plataformas de la base de datos
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectaculos);
            while (resultSet.next()) {
                String pl_nombre = resultSet.getString("pl_nombre");
                String pl_descripcion = resultSet.getString("pl_descripcion");
                String pl_url = resultSet.getString("pl_url");
                Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);

                String ua_nickname = resultSet.getString("ua_nickname");
                String ua_nombre = resultSet.getString("ua_nombre");
                String ua_apellido = resultSet.getString("ua_apellido");
                String ua_correo = resultSet.getString("ua_correo");
                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista artista = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, plataforma, artista);

                espectaculos.put(es_nombre, espectaculo);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los espectaculos", e);
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
    public void altaFuncion(Funcion nuevaFuncion) {
        Connection connection = null;
        Statement statement = null;
        String insertFuncion = "INSERT INTO funciones (fn_nombre, fn_espectaculoAsociado, fn_fechaHoraInicio, fn_fechaRegistro)\n" +
                "VALUES ('" + nuevaFuncion.getNombre() + "', '" + nuevaFuncion.getEspectaculo().getNombre() + "', '" + nuevaFuncion.getFechaHoraInicio() + "', '" + nuevaFuncion.getFechaRegistro() + "')";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertFuncion);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al insertar la funcion", e);
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
    public Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo) {
        Map<String, Funcion> funciones = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectFunciones = "SELECT *\n" +
                "FROM funciones as FN, espectaculos as ES, artistas as UA, plataformas as PL\n" +
                "WHERE FN.fn_espectaculoAsociado = ES.es_nombre\n" +
                "  AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
                "  AND ES.es_nombrePlataforma = PL.pl_nombre\n" +
                "  AND ES.es_nombrePlataforma = '" + nombrePlataforma + "'\n" +
                "  AND ES.es_nombre = '" + nombreEspectaculo + "' ";
        try {
            connection = ConexionDB.getConnection();

            // Obtenemos todas las plataformas de la base de datos
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectFunciones);
            while (resultSet.next()) {
                String pl_nombre = resultSet.getString("pl_nombre");
                String pl_descripcion = resultSet.getString("pl_descripcion");
                String pl_url = resultSet.getString("pl_url");
                Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);

                String ua_nickname = resultSet.getString("ua_nickname");
                String ua_nombre = resultSet.getString("ua_nombre");
                String ua_apellido = resultSet.getString("ua_apellido");
                String ua_correo = resultSet.getString("ua_correo");
                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista artista = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, plataforma, artista);

                String fn_nombre = resultSet.getString("fn_nombre");
                LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
                LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
                Funcion funcion = new Funcion(fn_nombre, espectaculo, fn_fechaHoraInicio, fn_fechaRegistro);

                funciones.put(fn_nombre, funcion);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las funciones", e);
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
        return funciones;
    }

    @Override
    public void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones) {
        Connection connection = null;
        Statement statement = null;
        String insertEspectadoresFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) VALUES ";
        for (EspectadorRegistradoAFuncion espectadorFuncion : espectadoresFunciones.values()) {
            insertEspectadoresFunciones += "('" + espectadorFuncion.getEspectador().getNickname() + "', '" + espectadorFuncion.getFuncion().getNombre() + "', " + espectadorFuncion.isCanjeado() + ", " + espectadorFuncion.getCosto() + ", '" + espectadorFuncion.getFechaRegistro() + "'), ";
        }
        insertEspectadoresFunciones = insertEspectadoresFunciones.substring(0, insertEspectadoresFunciones.length() - 2); // Eliminamos la última coma y espacio
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertEspectadoresFunciones);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al registrar los espectadores a la función", e);
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
    public void altaPaquete(Paquete nuevoPaquete) {
        Connection connection = null;
        Statement statement = null;
        String insertPaquete = "INSERT INTO paquetes (paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro)\n" +
                "VALUES ('" + nuevoPaquete.getNombre() + "', '" + nuevoPaquete.getFechaExpiracion() + "', '" + nuevoPaquete.getDescripcion() + "', " + nuevoPaquete.getDescuento() + ", '" + nuevoPaquete.getFechaRegistro() + "')";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertPaquete);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al insertar el paquete", e);
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
    public Map<String, Paquete> obtenerPaquetes() {
        Map<String, Paquete> paquetes = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectPaquetes = "SELECT * FROM paquetes ORDER BY paq_fechaRegistro DESC";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectPaquetes);
            while (resultSet.next()) {
                String paq_nombre = resultSet.getString("paq_nombre");
                LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
                String paq_descripcion = resultSet.getString("paq_descripcion");
                double paq_descuento = resultSet.getDouble("paq_descuento");
                LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
                Paquete paquete = new Paquete(paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro);

                paquetes.put(paq_nombre, paquete);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los paquetes", e);
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
        return paquetes;
    }

    @Override
    public Map<String, Espectaculo> obtenerEspectaculosDePaquete(String nombrePaquete) {
        Map<String, Espectaculo> espectaculos = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectEspectaculosByPaquete = "SELECT *\n" +
                "FROM espectaculos_paquetes ES_PAQ, espectaculos ES, artistas UA, plataformas PL\n" +
                "WHERE ES_PAQ.es_paq_nombreEspectaculo = ES.es_nombre \n" +
                "AND ES.es_artistaOrganizador = UA.ua_nickname\n" +           //CAMBIAR ES.es_nombreArtista por ES.es_artistaOrganizador y UA.ua_nombre por nickname
                "AND ES.es_nombrePlataforma = PL.pl_nombre\n" +
                "AND ES_PAQ.es_paq_nombrePaquete = '" + nombrePaquete + "'\n" +
                "ORDER BY ES.es_fechaRegistro DESC";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectEspectaculosByPaquete);
            while (resultSet.next()) {
                String ua_nickname = resultSet.getString("ua_nickname");
                String ua_nombre = resultSet.getString("ua_nombre");
                String ua_apellido = resultSet.getString("ua_apellido");
                String ua_correo = resultSet.getString("ua_correo");
                LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
                String ua_descripcion = resultSet.getString("ua_descripcion");
                String ua_biografia = resultSet.getString("ua_biografia");
                String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
                Artista artistaOrganizador = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_descripcion, ua_biografia, ua_sitioWeb);

                String pl_nombre = resultSet.getString("pl_nombre");
                String pl_descripcion = resultSet.getString("pl_descripcion");
                String pl_url = resultSet.getString("pl_url");
                Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);

                String es_nombre = resultSet.getString("es_nombre");
                String es_descripcion = resultSet.getString("es_descripcion");
                Double es_duracion = resultSet.getDouble("es_duracion");
                int es_minEspectadores = resultSet.getInt("es_minEspectadores");
                int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
                String es_url = resultSet.getString("es_url");
                double es_costo = resultSet.getDouble("es_costo");
                LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
                Espectaculo espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_fechaRegistro, plataforma, artistaOrganizador);

                espectaculos.put(es_nombre, espectaculo);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los espectáculos del paquete", e);
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
    public Map<String, Paquete> obtenerPaquetesDeEspectaculo(String nombreEspectaculo) {
        Map<String, Paquete> paquetes = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectPaquetesByEspectaculo = "SELECT *\n" +
                "FROM espectaculos_paquetes ES_PAQ, paquetes PAQ\n" +
                "WHERE ES_PAQ.es_paq_nombrePaquete = PAQ.paq_nombre \n" +
                "AND ES_PAQ.es_paq_nombreEspectaculo = '" + nombreEspectaculo + "'\n" +
                "ORDER BY PAQ.paq_fechaRegistro DESC";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectPaquetesByEspectaculo);
            while (resultSet.next()) {
                String paq_nombre = resultSet.getString("paq_nombre");
                LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
                String paq_descripcion = resultSet.getString("paq_descripcion");
                double paq_descuento = resultSet.getDouble("paq_descuento");
                LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
                Paquete paquete = new Paquete(paq_nombre, paq_fechaExpiracion, paq_descripcion, paq_descuento, paq_fechaRegistro);

                paquetes.put(paq_nombre, paquete);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los paquetes del espectáculo", e);
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
        return paquetes;
    }

    @Override
    public void altaEspectaculosAPaquete(Map<String, Espectaculo> espectaculos, String nombrePaquete) {
        Connection connection = null;
        Statement statement = null;
        String insertEspectaculosPaquetes = "INSERT INTO espectaculos_paquetes (es_paq_nombreEspectaculo, es_paq_nombrePaquete) VALUES ";
        for (Espectaculo espectaculo : espectaculos.values()) {
            insertEspectaculosPaquetes += "('" + espectaculo.getNombre() + "', '" + nombrePaquete + "'), ";
        }
        insertEspectaculosPaquetes = insertEspectaculosPaquetes.substring(0, insertEspectaculosPaquetes.length() - 2);
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertEspectaculosPaquetes);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al dar de alta los espectáculos al paquete", e);
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
