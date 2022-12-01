package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class EspectaculoMapper {
  
  // ResultSet -> Espectaculo
  public static Espectaculo toModel(ResultSet rs) {
    try {
      Espectaculo espectaculo = new Espectaculo();

      espectaculo.setNombre(rs.getString("es_nombre"));
      espectaculo.setDescripcion(rs.getString("es_descripcion"));
      espectaculo.setDuracion(rs.getInt("es_duracion"));
      espectaculo.setMinEspectadores(rs.getInt("es_minEspectadores"));
      espectaculo.setMaxEspectadores(rs.getInt("es_maxEspectadores"));
      espectaculo.setUrl(rs.getString("es_url"));
      espectaculo.setCosto(rs.getInt("es_costo"));
      espectaculo.setEstado(E_EstadoEspectaculo.valueOf(rs.getString("es_estado")));
      espectaculo.setFechaRegistro(rs.getTimestamp("es_fechaRegistro").toLocalDateTime());
      espectaculo.setImagen(rs.getString("es_imagen"));

      espectaculo.setPlataforma(PlataformaMapper.toModel(rs));
      
      espectaculo.setArtista((Artista) UsuarioMapper.toModel(rs));

      return espectaculo;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a Espectaculo", e);
    }
  }
  public static Map<String, Espectaculo> toModelMap(ResultSet rs) {
    try {
      Map<String, Espectaculo> espectaculos = new HashMap<>();

      do { //Mientras haya un siguiente elemento
        Espectaculo espectaculo = toModel(rs);
        espectaculos.put(espectaculo.getNombre() +"-"+ espectaculo.getPlataforma().getNombre(), espectaculo);
      } while (rs.next());
      return espectaculos;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectaculoMap", e);
    }
  }
  
  
  // Espectaculo -> EspectaculoDTO
  public static EspectaculoDTO toDTO(Espectaculo espectaculo){
    EspectaculoDTO espectaculoDTO = new EspectaculoDTO();
    try {
      espectaculoDTO.setNombre(espectaculo.getNombre());
      espectaculoDTO.setDescripcion(espectaculo.getDescripcion());
      espectaculoDTO.setDuracion(espectaculo.getDuracion());
      espectaculoDTO.setMinEspectadores(espectaculo.getMinEspectadores());
      espectaculoDTO.setMaxEspectadores(espectaculo.getMaxEspectadores());
      espectaculoDTO.setUrl(espectaculo.getUrl());
      espectaculoDTO.setCosto(espectaculo.getCosto());
      espectaculoDTO.setEstado(espectaculo.getEstado());
      espectaculoDTO.setFechaRegistro(espectaculo.getFechaRegistro());
      espectaculoDTO.setImagen(espectaculo.getImagen());
      espectaculoDTO.setPlataforma(PlataformaMapper.toDTO(espectaculo.getPlataforma()));
      espectaculoDTO.setArtista(UsuarioMapper.toDTO(espectaculo.getArtista()));
      
      return espectaculoDTO;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear a EspectaculoDTO", e);
    }
  }
  public static Map<String, EspectaculoDTO> toDTOMap(Map<String, Espectaculo> espectaculos) {
    Map<String, EspectaculoDTO> espectaculoDTOMap = new HashMap<>();
    
    try {
      for (Map.Entry<String, Espectaculo> entry : espectaculos.entrySet()) {
        espectaculoDTOMap.put(entry.getKey(), toDTO(entry.getValue()));
      }
    } catch (Exception e) {
      throw new RuntimeException("Error al mapear a EspectaculoDTOMap", e);
    }
    
    return espectaculoDTOMap;
  }
  
  // ResultSet -> EspectaculoDTO
  public static EspectaculoDTO toDTO(ResultSet rs) {
    try {
      if(rs.isBeforeFirst()) rs.next(); // Si el cursor esta antes del primer elemento, lo mueve al primero
  
      EspectaculoDTO espectaculoDTO = new EspectaculoDTO();
  
      espectaculoDTO.setNombre(rs.getString("es_nombre"));
      espectaculoDTO.setDescripcion(rs.getString("es_descripcion"));
      espectaculoDTO.setDuracion(rs.getInt("es_duracion"));
      espectaculoDTO.setMinEspectadores(rs.getInt("es_minEspectadores"));
      espectaculoDTO.setMaxEspectadores(rs.getInt("es_maxEspectadores"));
      espectaculoDTO.setUrl(rs.getString("es_url"));
      espectaculoDTO.setCosto(rs.getInt("es_costo"));
      espectaculoDTO.setEstado(E_EstadoEspectaculo.valueOf(rs.getString("es_estado")));
      espectaculoDTO.setFechaRegistro(rs.getTimestamp("es_fechaRegistro").toLocalDateTime());
      espectaculoDTO.setImagen(rs.getString("es_imagen"));
      espectaculoDTO.setCantidadFavoritos(rs.getInt("cantidad_favoritos"));
      
      espectaculoDTO.setPlataforma(PlataformaMapper.toDTO(PlataformaMapper.toModel(rs)));
      espectaculoDTO.setArtista(UsuarioMapper.toDTO(UsuarioMapper.toModel(rs)));
      return espectaculoDTO;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectaculoDTO", e);
    }
  }
  public static Map<String, EspectaculoDTO> toDTOMap(ResultSet rs) {
    try {
      Map<String, EspectaculoDTO> espectaculos = new HashMap<>();
  
      while (rs.next()) { // Mientras haya un siguiente elemento
        EspectaculoDTO espectaculoDTO = toDTO(rs);
        espectaculos.put(espectaculoDTO.getNombre() +"-"+ espectaculoDTO.getPlataforma().getNombre(), espectaculoDTO);
      }
      return espectaculos;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectaculoDTOMap", e);
    }
  }
}
