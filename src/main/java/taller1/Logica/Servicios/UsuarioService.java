package main.java.taller1.Logica.Servicios;

import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoFavoritoDTO;
import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Mappers.EspectaculoMapper;
import main.java.taller1.Logica.Mappers.FuncionMapper;
import main.java.taller1.Logica.Mappers.UsuarioMapper;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsuarioService {
  
  public Map<String, Usuario> obtenerUsuarios(){
    Map<String, Usuario> usuarios = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectUsuarios = "SELECT * " +
        "FROM usuarios as U " +
        "  LEFT JOIN espectadores as UE ON UE.ue_nickname = U.u_nickname " +
        "  LEFT JOIN artistas as UA ON UA.ua_nickname = U.u_nickname " +
        "ORDER BY u_nickname";
    try {
      connection = ConexionDB.getConnection();
      
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectUsuarios);
      if(resultSet.next()) usuarios.putAll(UsuarioMapper.toModelMap(resultSet));
      
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
  public Optional<Usuario> obtenerUsuarioPorNickname(String nickname){
    Usuario usuario = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectUsuario = "SELECT * " +
        "FROM usuarios as U " +
        "  LEFT JOIN artistas as UA ON UA.ua_nickname = U.u_nickname " +
        "WHERE U.u_nickname = '" + nickname + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectUsuario);
      if(resultSet.next()) usuario = UsuarioMapper.toModel(resultSet);
  
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
  
  public Optional<Usuario> obtenerUsuarioPorCorreo(String correo){
    Usuario usuario = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String selectUsuario = "SELECT * " +
        "FROM usuarios as U " +
        "  LEFT JOIN artistas as UA ON UA.ua_nickname = U.u_nickname " +
        "WHERE U.u_correo = '" + correo + "'";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(selectUsuario);
      if(resultSet.next()) usuario = UsuarioMapper.toModel(resultSet);
      
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
  
  public void altaUsuario(UsuarioDTO usuariodto) {
    Connection connection = null;
    Statement statement = null;
    String insertUsuario = "INSERT INTO `usuarios` (`u_nickname`, `u_nombre`, `u_apellido`, `u_correo`, `u_fechaNacimiento`, `u_contrasenia`, `u_imagen`) " +
        "VALUES ('" + usuariodto.getNickname() + "', '" + usuariodto.getNombre() + "', '" + usuariodto.getApellido() + "', '" + usuariodto.getCorreo() + "', '" + usuariodto.getFechaNacimiento() + "', '" + usuariodto.getContrasenia() + "', '" + usuariodto.getImagen() + "'); ";
    
    String insertUsuario2;
    if (usuariodto.isEsArtista())
      insertUsuario2 = "INSERT INTO `artistas` (`ua_nickname`, `ua_descripcion`, `ua_biografia`, `ua_sitioWeb`) " +
          "VALUES ('" + usuariodto.getNickname() + "', '" + usuariodto.getDescripcion() + "', '" + usuariodto.getBiografia() + "', '" + usuariodto.getSitioWeb() + "')";
    else
      insertUsuario2 = "INSERT INTO `espectadores` (`ue_nickname`) " +
          "VALUES ('" + usuariodto.getNickname() + "')";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
  
  public void modificarUsuario(UsuarioDTO usuariodto) {
    Connection connection = null;
    Statement statement = null;
    String updateUsuario = "UPDATE usuarios " +
        "SET u_nombre = '" + usuariodto.getNombre() + "', u_apellido = '" + usuariodto.getApellido() + "', u_correo = '" + usuariodto.getCorreo() + "', u_fechaNacimiento = '" + usuariodto.getFechaNacimiento() + "', u_contrasenia = '" + usuariodto.getContrasenia() + "', u_imagen = '" + usuariodto.getImagen() + "' " +
        "WHERE u_nickname = '" + usuariodto.getNickname() + "'; ";
    
    String updateUsuario2 = "";
    if (usuariodto.isEsArtista())
      updateUsuario2 = "UPDATE artistas " +
          "SET ua_descripcion = '" + usuariodto.getDescripcion() + "', ua_biografia = '" + usuariodto.getBiografia() + "', ua_sitioWeb = '" + usuariodto.getSitioWeb() + "' " +
          "WHERE ua_nickname = '" + usuariodto.getNickname() + "'";
    
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      statement.executeUpdate(updateUsuario);
      if (usuariodto.isEsArtista()) {
        statement.executeUpdate(updateUsuario2);
      }
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

  public void marcarFavorito (EspectaculoFavoritoDTO espectaculoFavoritoDTO){
    Connection connection = null;
    Statement statement = null;
    String insertEspectaculo = "INSERT INTO espectaculos_favoritos (`es_fav_nickname`, `es_fav_espectaculoAsociado`, `es_fav_plataformaAsociada`) " +
            "VALUES ('" + espectaculoFavoritoDTO.getNickname() + "', '" + espectaculoFavoritoDTO.getNombreEspectaculo() + "', '" + espectaculoFavoritoDTO.getNombrePlataforma() + "')";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(insertEspectaculo);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al narcar espectaculo favorito", e);
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

  public void desmarcarFavorito (EspectaculoFavoritoDTO espectaculoFavoritoDTO){
    Connection connection = null;
    Statement statement = null;
    String deleteEspectaculo = "DELETE FROM espectaculos_favoritos WHERE es_fav_nickname = '" + espectaculoFavoritoDTO.getNickname() + "' " +
            "AND es_fav_espectaculoAsociado = '" + espectaculoFavoritoDTO.getNombreEspectaculo() + "' " +
            "AND es_fav_plataformaAsociada = '" + espectaculoFavoritoDTO.getNombrePlataforma() + "'";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.execute(deleteEspectaculo);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al desmarcar espectaculo favorito", e);
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

  public Map<String, String> obtenerEspectaculosFavoritos(String nickname){
    Map<String, String> espectaculos = new HashMap<>();
    Connection connection = null;
    Statement statement = null;
    String selectEspectaculo = "SELECT es_fav_espectaculoAsociado " +
            "FROM `espectaculos_favoritos`" +
            "WHERE es_fav_nickname = '" + nickname + "' ";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet resultSet = statement.executeQuery(selectEspectaculo);
      while(resultSet.next()){
          espectaculos.put(resultSet.getString(1), resultSet.getString(1));
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al obtener los espectaculos", e);
    } finally {
      try {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
    return espectaculos;
  }

}
