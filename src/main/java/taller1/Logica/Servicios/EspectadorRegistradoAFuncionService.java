package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class EspectadorRegistradoAFuncionService {
  
  
  public void registrarEspectadorAFuncion(EspectadorRegistradoAFuncion espectadorRegistradoAFuncion) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectadorFunciones="";
    if(espectadorRegistradoAFuncion.getPaquete()==null) {
      insertEspectadorFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada, ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) " +
          " VALUES ('" + espectadorRegistradoAFuncion.getEspectador().getNickname() + "', '" + espectadorRegistradoAFuncion.getFuncion().getNombre() + "', '" + espectadorRegistradoAFuncion.getFuncion().getEspectaculo().getNombre() + "', '" + espectadorRegistradoAFuncion.getFuncion().getEspectaculo().getPlataforma().getNombre() + "', " + null + "," + espectadorRegistradoAFuncion.isCanjeado() + ", " + espectadorRegistradoAFuncion.getCosto() + ", '" + LocalDateTime.now() + "')";
    } else{
      insertEspectadorFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada, ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) " +
          " VALUES ('" + espectadorRegistradoAFuncion.getEspectador().getNickname() + "', '" + espectadorRegistradoAFuncion.getFuncion().getNombre() + "', '" + espectadorRegistradoAFuncion.getFuncion().getEspectaculo().getNombre() + "', '" + espectadorRegistradoAFuncion.getFuncion().getEspectaculo().getPlataforma().getNombre() + "', '" + espectadorRegistradoAFuncion.getPaquete().getNombre() + "', " + espectadorRegistradoAFuncion.isCanjeado() + ", " + espectadorRegistradoAFuncion.getCosto() + ", '" + LocalDateTime.now() + "')";
    }
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertEspectadorFunciones);
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
  
  public void registrarEspectadoresAFunciones(Map<String, EspectadorRegistradoAFuncion> espectadoresFunciones) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectadoresFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion,ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada,ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) VALUES ";
    for (EspectadorRegistradoAFuncion espectadorFuncion : espectadoresFunciones.values()) {
      if(espectadorFuncion.getPaquete()==null){
        insertEspectadoresFunciones += "('" + espectadorFuncion.getEspectador().getNickname() + "', '" + espectadorFuncion.getFuncion().getNombre() + "', '"+espectadorFuncion.getFuncion().getEspectaculo().getNombre()+"','" +espectadorFuncion.getFuncion().getEspectaculo().getPlataforma().getNombre()+"',"+ null + ", " + espectadorFuncion.isCanjeado() + ", " + espectadorFuncion.getCosto() + ", '" + espectadorFuncion.getFechaRegistro() + "'), ";
      }else{
        insertEspectadoresFunciones += "('" + espectadorFuncion.getEspectador().getNickname() + "', '" + espectadorFuncion.getFuncion().getNombre() + "', "+espectadorFuncion.getFuncion().getEspectaculo().getNombre()+"','" +espectadorFuncion.getFuncion().getEspectaculo().getPlataforma().getNombre()+"','"+ espectadorFuncion.getPaquete().getNombre() + "', " + espectadorFuncion.isCanjeado() + ", " + espectadorFuncion.getCosto() + ", '" + espectadorFuncion.getFechaRegistro() + "'), ";
      }
      
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
  
  public Map<String, EspectadorRegistradoAFuncion> obtenerFuncionesRegistradasDelEspectador(String nickname) {
    Map<String, EspectadorRegistradoAFuncion> funcionesRegistradas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM espectadores_funciones as UE_FN, espectadores as UE, usuarios as U, funciones as FN, espectaculos as ES, plataformas as PL " +
        "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname " +
        "  AND UE.ue_nickname = U.u_nickname " +
        "  AND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre " +
        "  AND UE_FN.ue_fn_espectaculoAsociado = FN.fn_espectaculoAsociado " +
        "  AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        "  AND UE_FN.ue_fn_plataformaAsociada = FN.fn_plataformaAsociada " +
        "  AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        "  AND ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND UE_FN.ue_fn_nickname = '" + nickname + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectFunciones);
      while (resultSet.next()) {
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
        Double es_costo = resultSet.getDouble("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        
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
        Boolean ue_fn_canjeado = resultSet.getBoolean("ue_fn_canjeado");
        Double ue_fn_costo = resultSet.getDouble("ue_fn_costo");
        LocalDateTime ue_fn_fechaRegistro = resultSet.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime();
        EspectadorRegistradoAFuncion es_fn = new EspectadorRegistradoAFuncion(ue, fn, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro);
        
        funcionesRegistradas.put(fn_nombre+"-"+es_nombre+"-"+pl_nombre, es_fn);
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
  
  public Map<String, EspectadorRegistradoAFuncion> obtenerEspectadoresRegistradosAFuncion(String nombreFuncion) {
    Map<String, EspectadorRegistradoAFuncion> espectadoresRegistrados = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectadores = "SELECT * " +
        "FROM espectadores_funciones as UE_FN, espectadores as UE, usuarios as U, paquetes as PAQ, funciones as FN, espectaculos as ES, plataformas as PL " +
        "WHERE UE_FN.ue_fn_nickname = UE.ue_nickname " +
        "  AND UE.ue_nickname = U.u_nickname " +
        "  AND UE_FN.ue_fn_espectaculoAsociado = FN.fn_espectaculoAsociado " +
        "  AND FN.fn_espectaculoAsociado = ES.es_nombre " +
        "  AND UE_FN.ue_fn_plataformaAsociada = FN.fn_plataformaAsociada " +
        "  AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        "  AND ES.es_plataformaAsociada = PL.pl_nombre " +
        "  AND (UE_FN.ue_fn_nombrePaquete = PAQ.paq_nombre OR UE_FN.ue_fn_nombrePaquete IS NULL)" +
        "  AND UE_FN.ue_fn_nombreFuncion = FN.fn_nombre " +
        "  AND FN.fn_nombre = '" + nombreFuncion + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(selectEspectadores);
      while (resultSet.next()) {
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
        Double es_costo = resultSet.getDouble("es_costo");
        E_EstadoEspectaculo es_estado = E_EstadoEspectaculo.valueOf(resultSet.getString("es_estado"));
        LocalDateTime es_fechaRegistro = resultSet.getTimestamp("es_fechaRegistro").toLocalDateTime();
        String es_imagen = resultSet.getString("es_imagen");
        
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
