package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.AltaEspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.Mappers.EspectadorRegistradoAFuncionMapper;
import main.java.taller1.Logica.Mappers.UsuarioMapper;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class EspectadorRegistradoAFuncionService {
  
  
  public void registrarEspectadorAFuncion(AltaEspectadorRegistradoAFuncionDTO altaEspectadorRegistradoAFuncion) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectadorFunciones="";
    if(altaEspectadorRegistradoAFuncion.getPaquete()==null) {
      insertEspectadorFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada, ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) " +
          " VALUES ('" + altaEspectadorRegistradoAFuncion.getEspectador() + "', '" + altaEspectadorRegistradoAFuncion.getFuncion() + "', '" + altaEspectadorRegistradoAFuncion.getEspectaculo() + "', '" + altaEspectadorRegistradoAFuncion.getPlataforma() + "', " + null + "," + altaEspectadorRegistradoAFuncion.isCanjeado() + ", " + altaEspectadorRegistradoAFuncion.getCosto() + ", '" + LocalDateTime.now() + "')";
    } else{
      insertEspectadorFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion, ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada, ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) " +
          " VALUES ('" + altaEspectadorRegistradoAFuncion.getEspectador() + "', '" + altaEspectadorRegistradoAFuncion.getFuncion() + "', '" + altaEspectadorRegistradoAFuncion.getEspectaculo() + "', '" + altaEspectadorRegistradoAFuncion.getPlataforma() + "', '" + altaEspectadorRegistradoAFuncion.getPaquete() + "', " + altaEspectadorRegistradoAFuncion.isCanjeado() + ", " + altaEspectadorRegistradoAFuncion.getCosto() + ", '" + LocalDateTime.now() + "')";
    }
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
  
  public void registrarEspectadoresAFunciones(Map<String, AltaEspectadorRegistradoAFuncionDTO> espectadoresFunciones) {
    Connection connection = null;
    Statement statement = null;
    String insertEspectadoresFunciones = "INSERT INTO espectadores_funciones(ue_fn_nickname, ue_fn_nombreFuncion,ue_fn_espectaculoAsociado, ue_fn_plataformaAsociada,ue_fn_nombrePaquete, ue_fn_canjeado, ue_fn_costo, ue_fn_fechaRegistro) VALUES ";
    for (AltaEspectadorRegistradoAFuncionDTO espectadorFuncion : espectadoresFunciones.values()) {
      if(espectadorFuncion.getPaquete()==null){
        insertEspectadoresFunciones += "('" + espectadorFuncion.getEspectador() + "', '" + espectadorFuncion.getFuncion() + "', '"+espectadorFuncion.getEspectaculo() +"','" +espectadorFuncion.getPlataforma() +"',"+ null + ", " + espectadorFuncion.isCanjeado() + ", " + espectadorFuncion.getCosto() + ", '" + espectadorFuncion.getFechaRegistro() + "'), ";
      }else{
        insertEspectadoresFunciones += "('" + espectadorFuncion.getEspectador() + "', '" + espectadorFuncion.getFuncion() + "', "+espectadorFuncion.getEspectaculo() +"','" +espectadorFuncion.getPlataforma() +"','"+ espectadorFuncion.getPaquete() + "', " + espectadorFuncion.isCanjeado() + ", " + espectadorFuncion.getCosto() + ", '" + espectadorFuncion.getFechaRegistro() + "'), ";
      }
      
    }
    insertEspectadoresFunciones = insertEspectadoresFunciones.substring(0, insertEspectadoresFunciones.length() - 2); // Eliminamos la última coma y espacio
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
  
  public Map<String, EspectadorRegistradoAFuncionDTO> obtenerFuncionesRegistradasDelEspectador(String nickname) {
    Map<String, EspectadorRegistradoAFuncionDTO> funcionesRegistradas = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectFunciones = "SELECT * " +
        "FROM espectadores_funciones as UE_FN " +
        " LEFT JOIN funciones AS FN ON UE_FN.ue_fn_nombreFuncion = FN.fn_nombre AND UE_FN.ue_fn_espectaculoAsociado = FN.fn_espectaculoAsociado AND UE_FN.ue_fn_plataformaAsociada = FN.fn_plataformaAsociada " +
        " LEFT JOIN paquetes AS PAQ ON UE_FN.ue_fn_nombrePaquete = PAQ.paq_nombre " +
        " LEFT JOIN espectaculos AS ES ON FN.fn_espectaculoAsociado = ES.es_nombre AND FN.fn_plataformaAsociada = ES.es_plataformaAsociada " +
        " LEFT JOIN plataformas AS PL ON ES.es_plataformaAsociada = PL.pl_nombre " +
        " LEFT JOIN artistas AS UA ON ES.es_artistaOrganizador = UA.ua_nickname " +
        " LEFT JOIN usuarios AS U ON UA.ua_nickname = U.u_nickname " +
        "WHERE UE_FN.ue_fn_nickname = '" + nickname + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectFunciones);
      funcionesRegistradas.putAll(EspectadorRegistradoAFuncionMapper.toDTOMap(resultSet));
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
  
  public Map<String, Usuario> obtenerEspectadoresRegistradosAFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {
    Map<String, Usuario> espectadoresRegistrados = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectEspectadores = "SELECT *" +
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
        "  AND FN.fn_nombre = '" + nombreFuncion + "' " +
        "  AND FN.fn_espectaculoAsociado = '" + nombreEspectaculo + "' " +
        " AND FN.fn_plataformaAsociada = '" + nombrePlataforma + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectEspectadores);
      espectadoresRegistrados.putAll(UsuarioMapper.toModelMap(resultSet));
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
