package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class EspectadorRegistradoAFuncionMapper {
  
  //ResultSet -> EspectadorRegistradoAFuncion
  public static EspectadorRegistradoAFuncion toModel(ResultSet rs){
    try {
      EspectadorRegistradoAFuncion espectadorfuncion = new EspectadorRegistradoAFuncion();

      espectadorfuncion.setEspectador((Espectador) UsuarioMapper.toModel(rs));
      espectadorfuncion.setFuncion(FuncionMapper.toModel(rs));
      espectadorfuncion.setPaquete(PaqueteMapper.toModel(rs));
      
      espectadorfuncion.setCanjeado(rs.getBoolean("ue_fn_canjeado"));
      espectadorfuncion.setCosto(rs.getDouble("ue_fn_costo"));
      espectadorfuncion.setFechaRegistro(rs.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime());
      return espectadorfuncion;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectadorRegistradoAFuncion", e);
    }
  }
  
  //ResultSet -> Map<EspectadorRegistradoAFuncion>
  public static Map<String,EspectadorRegistradoAFuncion> toModelMap(ResultSet rs) {
    try {
      Map<String,EspectadorRegistradoAFuncion> espectadorfunciones = new HashMap<>();
      do { // Mientras haya un siguiente elemento en el ResultSet
        EspectadorRegistradoAFuncion espectadorfuncion = toModel(rs);
        String nombre_funcion = espectadorfuncion.getFuncion().getNombre();
        String nombre_espectaculo = espectadorfuncion.getFuncion().getEspectaculo().getNombre();
        String nombre_plataforma = espectadorfuncion.getFuncion().getEspectaculo().getPlataforma().getNombre();
        espectadorfunciones.put(nombre_funcion+"-"+nombre_espectaculo+"-"+nombre_plataforma, espectadorfuncion);
      } while (rs.next());
      return espectadorfunciones;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectadorRegistradoAFuncionMap", e);
    }
  }
  
  //EspectadorRegistradoAFuncion -> DTO
  public static EspectadorRegistradoAFuncionDTO toDTO(EspectadorRegistradoAFuncion espectadorfuncion){
    EspectadorRegistradoAFuncionDTO espectadorfuncionDTO = new EspectadorRegistradoAFuncionDTO();
    try {
      espectadorfuncionDTO.setEspectador(espectadorfuncion.getEspectador().getNombre());
      espectadorfuncionDTO.setFuncion(FuncionMapper.toDTO(espectadorfuncion.getFuncion()));
      espectadorfuncionDTO.setPaquete(PaqueteMapper.toDTO(espectadorfuncion.getPaquete()));
      espectadorfuncionDTO.setCanjeado(espectadorfuncion.isCanjeado());
      espectadorfuncionDTO.setCosto(espectadorfuncion.getCosto());
      espectadorfuncionDTO.setFechaRegistro(espectadorfuncion.getFechaRegistro().toString());
      
      return espectadorfuncionDTO;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear EspectadorRegistradoAFuncion a EspectadorRegistradoAFuncionDTO", e);
    }
  }
  
  //Map<EspectadorRegistradoAFuncion> -> Map<EspectadorRegistradoAFuncionDTO>
  public static Map<String,EspectadorRegistradoAFuncionDTO> toDTOMap(Map<String,EspectadorRegistradoAFuncion> espectadorfunciones) {
    Map<String,EspectadorRegistradoAFuncionDTO> espectadorfuncionesDTOMap = new HashMap<>();
    try {
      for (EspectadorRegistradoAFuncion espectadorfuncion : espectadorfunciones.values()) {
        EspectadorRegistradoAFuncionDTO dto = toDTO(espectadorfuncion);
        String nombre_funcion = espectadorfuncion.getFuncion().getNombre();
        String nombre_espectaculo = espectadorfuncion.getFuncion().getEspectaculo().getNombre();
        String nombre_plataforma = espectadorfuncion.getFuncion().getEspectaculo().getPlataforma().getNombre();
        espectadorfuncionesDTOMap.put(nombre_funcion+"-"+nombre_espectaculo+"-"+nombre_plataforma, dto);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error al mapear EspectadorRegistradoAFuncionMap a EspectadorRegistradoAFuncionDTOMap", e);
    }
    
    return espectadorfuncionesDTOMap;
  }
  
  
  //ResultSet -> EspectadorRegistradoAFuncionDTO
  public static EspectadorRegistradoAFuncionDTO toDTO(ResultSet rs){
    try {
      EspectadorRegistradoAFuncionDTO dto = new EspectadorRegistradoAFuncionDTO();
      
      dto.setEspectador(rs.getString("ue_fn_nickname"));
      dto.setFuncion(FuncionMapper.toDTO(FuncionMapper.toModel(rs)));
      
      if(rs.getString("ue_fn_nombrePaquete") != null)
        dto.setPaquete(PaqueteMapper.toDTO(PaqueteMapper.toModel(rs)));
      
      dto.setCanjeado(rs.getBoolean("ue_fn_canjeado"));
      dto.setCosto(rs.getDouble("ue_fn_costo"));
      dto.setFechaRegistro(rs.getTimestamp("ue_fn_fechaRegistro").toLocalDateTime().toString());
      return dto;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectadorRegistradoAFuncionDTO", e);
    }
  }
  
  //ResultSet -> Map<String, EspectadorRegistradoAFuncionDTO>
  public static Map<String,EspectadorRegistradoAFuncionDTO> toDTOMap(ResultSet rs) {
    try {
      Map<String,EspectadorRegistradoAFuncionDTO> espectadorfunciones = new HashMap<>();
      do { // Mientras haya un siguiente elemento en el ResultSet
        EspectadorRegistradoAFuncionDTO espectadorfuncion = toDTO(rs);
        String nombre_funcion = espectadorfuncion.getFuncion().getNombre();
        String nombre_espectaculo = espectadorfuncion.getFuncion().getEspectaculo().getNombre();
        String nombre_plataforma = espectadorfuncion.getFuncion().getEspectaculo().getPlataforma().getNombre();
        espectadorfunciones.put(nombre_funcion+"-"+nombre_espectaculo+"-"+nombre_plataforma, espectadorfuncion);
      } while (rs.next());
      return espectadorfunciones;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al mapear ResultSet a EspectadorRegistradoAFuncionDTO", e);
    }
  }
}
