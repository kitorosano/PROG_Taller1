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
    String insertFuncion = "INSERT INTO funciones (fn_nombre, fn_espectaculoAsociado, fn_plataformaAsociada, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen) " +
        "VALUES ('" + nuevaFuncion.getNombre() + "', '" + nuevaFuncion.getEspectaculo().getNombre() + "', '" + nuevaFuncion.getEspectaculo().getPlataforma().getNombre()+ "', '"+ nuevaFuncion.getFechaHoraInicio() + "', '" + nuevaFuncion.getFechaRegistro() + "', '" + nuevaFuncion.getImagen() + "')";
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
  public Map<String, Funcion> obtenerFunciones() {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
  
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
  
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
        funciones.put(fn_nombre+"-"+es_nombre+"-"+pl_nombre, funcion);
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
  public Funcion obtenerFuncion(String nombrePlataforma, String nombreEspectador, String nombreFuncion) {
    Funcion funcion = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFuncion = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname" +
        " AND FN.fn_nombre = '" + nombreFuncion + "'" +
        " AND ES.es_nombre = '" + nombreEspectador + "'" +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFuncion);
      if (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
  
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
  
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
  
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener la función", e);
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
    return funcion;
  }
  
  @Override
  public Map<String, Funcion> obtenerFuncionesDeEspectaculo(String nombrePlataforma, String nombreEspectaculo) {
    Map<String, Funcion> funciones = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada" +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND ES.es_nombre = '" + nombreEspectaculo + "' " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
  
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
  
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
  
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
  
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
  public Map<String, Artista> obtenerArtistasInvitadosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {
    Map<String, Artista> artistas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectArtistas = "SELECT U.*, UA.* " +
        "FROM artistas_funciones as UA_FN, funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE UA_FN.ua_fn_nickname = ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND UA_FN.ua_fn_espectaculoAsociado = fn_espectaculoAsociado " +
        " AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND ES.es_nombre = '" + nombreEspectaculo + "' " +
        " AND UA_FN.ua_fn_plataformaAsociada = FN.fn_platafomaAsociada" +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'" +
        " AND UA_FN.ua_fn_nombreFuncion = FN.fn_nombre " +
        " AND FN.fn_nombre = '" + nombreFuncion + "' ";
    try {
      connection = ConexionDB.getConnection();
      
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectArtistas);
      while (resultSet.next()) {
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artista = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
        artistas.put(u_nickname, artista);
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
    String selectFunciones = "SELECT FN.*, ES.*, PL.* " +
        "FROM artistas_funciones as UA_FN, funciones as FN, espectaculos as ES, plataformas as PL, artistas as UA, usuarios as U " +
        "WHERE UA_FN.ua_fn_nombreFuncion = FN.fn_nombre " +
        " AND UA_FN.ua_fn_plataformaAsociada = FN.fn_platafomaAsociada" +
        " AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        " AND ES.es_plataformaAsociada = PL.pl_nombre " +
        " AND PL.pl_nombre = '" + nombrePlataforma + "'" +
        " AND UA_FN.ua_fn_espectaculoAsociado = fn_espectaculoAsociado " +
        " AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        " AND ES.es_nombre = '" + nombreEspectaculo + "' " +
        " AND ES.es_artistaOrganizador = UA.ua_nickname " +
        " AND UA.ua_nickname = U.u_nickname " +
        " AND UA_FN.ua_fn_nickname = '" + nombreArtista + "' ";
    try {
      connection = ConexionDB.getConnection();
      
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
        String pl_nombre = resultSet.getString("pl_nombre");
        String pl_descripcion = resultSet.getString("pl_descripcion");
        String pl_url = resultSet.getString("pl_url");
        Plataforma plataforma = new Plataforma(pl_nombre, pl_descripcion, pl_url);
        
        String u_nickname = resultSet.getString("u_nickname");
        String u_nombre = resultSet.getString("u_nombre");
        String u_apellido = resultSet.getString("u_apellido");
        String u_correo = resultSet.getString("u_correo");
        LocalDate u_fechaNacimiento = resultSet.getDate("u_fechaNacimiento").toLocalDate();
        String u_contrasenia = resultSet.getString("u_contrasenia");
        String u_imagen = resultSet.getString("u_imagen");
        String ua_descripcion = resultSet.getString("ua_descripcion");
        String ua_biografia = resultSet.getString("ua_biografia");
        String ua_sitioWeb = resultSet.getString("ua_sitioWeb");
        Artista artistaOrganizador = new Artista(u_nickname, u_nombre, u_apellido, u_correo, u_fechaNacimiento, u_contrasenia, u_imagen, ua_descripcion, ua_biografia, ua_sitioWeb);
  
        String es_nombre = resultSet.getString("es_nombre");
        String es_descripcion = resultSet.getString("es_descripcion");
        int es_duracion = resultSet.getInt("es_duracion");
        int es_minEspectadores = resultSet.getInt("es_minEspectadores");
        int es_maxEspectadores = resultSet.getInt("es_maxEspectadores");
        String es_url = resultSet.getString("es_url");
        int es_costo = resultSet.getInt("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artistaOrganizador);
  
        String fn_nombre = resultSet.getString("fn_nombre");
        LocalDateTime fn_fechaHoraInicio = resultSet.getTimestamp("fn_fechaHoraInicio").toLocalDateTime();
        LocalDateTime fn_fechaRegistro = resultSet.getTimestamp("fn_fechaRegistro").toLocalDateTime();
        String fn_imagen = resultSet.getString("fn_imagen");
        Funcion funcion = new Funcion(fn_nombre, es, fn_fechaHoraInicio, fn_fechaRegistro, fn_imagen);
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
  
  @Override
  public Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname) {
    Map<String, EspectadorRegistradoAFuncion> funcionesRegistradas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM espectadores_funciones as UE_FN, espectadores as UE, usuarios as U, funciones as FN, espectaculos as ES " +
        "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname " +
        "  AND UE.ue_nickname = U.u_nickname " +
        "  AND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre " +
        "  AND UE_FN.ue_fn_espectaculoAsociado = FN.fn_espectaculoAsociado " +
        "  AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        "  AND UE_FN.ue_fn_plataformaAsociada = FN.fn_plataformaAsociada " +
        "  AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        "  AND UE_FN.ue_fn_nickname = '" + nickname + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
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
        String es_plataformaAsociada = resultSet.getString("es_plataformaAsociada");
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(es_plataformaAsociada);
        String es_artistaOrganizador = resultSet.getString("es_artistaOrganizador");
        Artista artista = new Artista();
        artista.setNickname(es_artistaOrganizador);
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
        
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
        
        //String paq_nombre = resultSet.getString("paq_nombre");
        //String paq_descripcion = resultSet.getString("paq_descripcion");
        //Double paq_descuento = resultSet.getDouble("paq_descuento");
        //LocalDateTime paq_fechaExpiracion = resultSet.getTimestamp("paq_fechaExpiracion").toLocalDateTime();
        //LocalDateTime paq_fechaRegistro = resultSet.getTimestamp("paq_fechaRegistro").toLocalDateTime();
        //String paq_imagen = resultSet.getString("paq_imagen");
        //Paquete paq = new Paquete(paq_nombre, paq_descripcion, paq_descuento, paq_fechaExpiracion, paq_fechaRegistro, paq_imagen);
        Paquete paq=null;
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
        "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname " +
        "  AND UE.ue_nickname = U.u_nickname " +
        "  AND UE_FN.ue_fn_espectaculoAsociado = FN.fn_espectaculoAsociado " +
        "  AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        "  AND UE_FN.ue_fn_plataformaAsociada = FN.fn_plataformaAsociada " +
        "  AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        "  AND UE_FN.ue_fn_nombrePaquete = PAQ.paq_nombre " +
        "  AND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre " +
        "  AND FN.fn_nombre = '" + nombreFuncion + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectadores);
      while (resultSet.next()) {
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
        String es_plataformaAsociada = resultSet.getString("es_plataformaAsociada");
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(es_plataformaAsociada);
        String es_artistaOrganizador = resultSet.getString("es_artistaOrganizador");
        Artista artista = new Artista();
        artista.setNickname(es_artistaOrganizador);
        Espectaculo es = new Espectaculo(es_nombre, es_descripcion, es_duracion, es_minEspectadores, es_maxEspectadores, es_url, es_costo, es_estado, es_fechaRegistro, es_imagen, plataforma, artista);
  
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
