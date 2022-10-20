package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Interfaces.IFuncion;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FuncionController implements IFuncion {
  private static FuncionController instance;
  
  private FuncionController() {
  }
  
  public static FuncionController getInstance() {
    if (instance == null) {
      instance = new FuncionController();
    }
    return instance;
  }
  
  @Override
  public void altaFuncion(Funcion nuevaFuncion) {
    Connection connection = null;
    Statement statement = null;
    String insertFuncion = "INSERT INTO funciones (fn_nombre, fn_espectaculoAsociado, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen)\n" +
        "VALUES ('" + nuevaFuncion.getNombre() + "', '" + nuevaFuncion.getEspectaculo().getNombre() + "', '" + nuevaFuncion.getFechaHoraInicio() + "', '" + nuevaFuncion.getFechaRegistro() + "', '" + nuevaFuncion.getImagen() + "')";
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
        "  AND ES.es_plataformaAsociada = PL.pl_nombre\n" +
        "  AND ES.es_plataformaAsociada = '" + nombrePlataforma + "'\n" +
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
        String ua_contrasenia = resultSet.getString("ua_contrasenia");
        String ua_imagen = resultSet.getString("ua_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasenia, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        double es_duracion = resultSet.getDouble("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        double es_costo = resultSet.getDouble("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo espectaculo = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, espectaculo, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
        
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
  public Map<String, Artista> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion){
    Map<String, Artista> artistas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectArtistas = "SELECT *\n" +
        "FROM funciones as FN, espectaculos as ES, artistas as UA, plataformas as PL\n" +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre\n" +
        "  AND ES.es_artistaOrganizador = UA.ua_nickname\n" +
        "  AND ES.es_plataformaAsociada = PL.pl_nombre\n" +
        "  AND ES.es_plataformaAsociada = '" + nombrePlataforma + "'\n" +
        "  AND ES.es_nombre = '" + nombreEspectaculo + "' ";
    try {
      connection = ConexionDB.getConnection();
      
      // Obtenemos todas las plataformas de la base de datos
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectArtistas);
      while (resultSet.next()) {
        String ua_nickname = resultSet.getString("ua_nickname");
        String ua_nombre = resultSet.getString("ua_nombre");
        String ua_apellido = resultSet.getString("ua_apellido");
        String ua_correo = resultSet.getString("ua_correo");
        LocalDate ua_fechaNacimiento = resultSet.getDate("ua_fechaNacimiento").toLocalDate();
        String ua_contrasenia = resultSet.getString("ua_contrasenia");
        String ua_imagen = resultSet.getString("ua_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(ua_nickname, ua_nombre, ua_apellido, ua_correo, ua_fechaNacimiento, ua_contrasenia, ua_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        artistas.put(ua_nickname, artista);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los artistas invitados", e);
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
    return artistas;
  }
  
  @Override
  public Map<String, Funcion> obtenerFuncionesDeArtista(String nombrePlataforma, String nombreEspectaculo, String nombreArtista) {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = ""; //TODO: completar
    try {
      connection = ConexionDB.getConnection();
    
      // Obtenemos todas las plataformas de la base de datos
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        //TODO: completar
        Espectaculo espectaculo = new Espectaculo();
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, espectaculo, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
  
        funciones.put(fn_nombre, funcion);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los artistas invitados", e);
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
    String insertEspectadoresFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) VALUES ";
    for (EspectadorRegistradoAFuncion espectadorFuncion : espectadoresFunciones.values()) {
      insertEspectadoresFunciones += "('" + espectadorFuncion.getEspectador().getNickname() + "', '" + espectadorFuncion.getFuncion().getNombre() + "', " + espectadorFuncion.getPaquete().getNombre() + "', " + espectadorFuncion.isCanjeado() + ", " + espectadorFuncion.getCosto() + ", '" + espectadorFuncion.getFechaRegistro() + "'), ";
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
}
