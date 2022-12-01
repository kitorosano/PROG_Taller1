package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.DTOs.UsuarioDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UsuarioMapper {
  
  
  // ResultSet -> Usuario
  public static Usuario toModel(ResultSet rs) {
    try {
      Usuario user;
      if(existeColumnaEnResultSet("ua_descripcion", rs) && rs.getString("ua_descripcion") != null) {
        user = new Artista();
        ((Artista) user).setDescripcion(rs.getString("ua_descripcion"));
        ((Artista) user).setBiografia(rs.getString("ua_biografia"));
        ((Artista) user).setSitioWeb(rs.getString("ua_sitioWeb"));
      } else {
        user = new Espectador();
      }
      
      user.setNickname(rs.getString("u_nickname"));
      user.setNombre(rs.getString("u_nombre"));
      user.setApellido(rs.getString("u_apellido"));
      user.setCorreo(rs.getString("u_correo"));
      user.setFechaNacimiento(rs.getDate("u_fechaNacimiento").toLocalDate());
      user.setFechaNacimiento(rs.getDate("u_contrasenia").toLocalDate());
      user.setImagen(rs.getString("u_imagen"));
      
      return user;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a Usuario", e);
    }
  }
  public static Map<String, Usuario> toModelMap(ResultSet rs) {
    try {
      Map<String, Usuario> users = new HashMap<>();
      do {
        Usuario user = toModel(rs);
        users.put(user.getNickname(), user);
      } while (rs.next());
      return users;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a UsuarioMap", e);
    }
  }
  
  
  // Usuario -> UsuarioDTO
  public static UsuarioDTO toDTO(Usuario user){
    UsuarioDTO userDTO = new UsuarioDTO();
    try {
      userDTO.setNickname(user.getNickname());
      userDTO.setNombre(user.getNombre());
      userDTO.setApellido(user.getApellido());
      userDTO.setCorreo(user.getCorreo());
      userDTO.setFechaNacimiento(user.getFechaNacimiento());
      userDTO.setContrasenia(user.getContrasenia());
      userDTO.setImagen(user.getImagen());
      if(user instanceof Artista) {
        userDTO.setDescripcion(((Artista) user).getDescripcion());
        userDTO.setBiografia(((Artista) user).getBiografia());
        userDTO.setSitioWeb(((Artista) user).getSitioWeb());
        userDTO.setEsArtista(true);
      } else {
        userDTO.setEsArtista(false);
      }
      return userDTO;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear Usuario a UsuarioDTO", e);
    }
  }
  public static Map<String, UsuarioDTO> toDTOMap(Map<String, Usuario> users) {
    Map<String, UsuarioDTO> userDTOMap = new HashMap<>();
    
    try {
      for (Usuario user : users.values()) {
        userDTOMap.put(user.getNickname(), toDTO(user));
      }
    } catch (Exception e) {
      throw new RuntimeException("Error al mapear UsuarioMap a UsuarioDTOMap", e);
    }
    
    return userDTOMap;
  }

  private static boolean existeColumnaEnResultSet(String nombreCol, ResultSet rs){
    try{
      rs.findColumn(nombreCol);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
}
