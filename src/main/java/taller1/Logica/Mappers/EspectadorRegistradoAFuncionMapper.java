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
      if (!rs.next()) return null;
      
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
      Map<String,EspectadorRegistradoAFuncion> espectadorfunciones = new HashMap<String,EspectadorRegistradoAFuncion>();
      EspectadorRegistradoAFuncion espectadorfuncion = toModel(rs);
      while (espectadorfuncion != null) {
        espectadorfunciones.put(espectadorfuncion.getFuncion().getNombre()+"-"+espectadorfuncion.getFuncion().getEspectaculo().getNombre()+"-"+espectadorfuncion.getFuncion().getEspectaculo().getPlataforma().getNombre(), espectadorfuncion);
        espectadorfuncion = toModel(rs);
      }
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
      espectadorfuncionDTO.setEspectador(UsuarioMapper.toDTO(espectadorfuncion.getEspectador()));
      espectadorfuncionDTO.setFuncion(FuncionMapper.toDTO(espectadorfuncion.getFuncion()));
      espectadorfuncionDTO.setPaquete(PaqueteMapper.toDTO(espectadorfuncion.getPaquete()));
      espectadorfuncionDTO.setCanjeado(espectadorfuncion.isCanjeado());
      espectadorfuncionDTO.setCosto(espectadorfuncion.getCosto());
      espectadorfuncionDTO.setFechaRegistro(espectadorfuncion.getFechaRegistro());
      
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
        espectadorfuncionesDTOMap.put(dto.getFuncion().getNombre()+"-"+dto.getFuncion().getEspectaculo().getNombre()+"-"+dto.getFuncion().getEspectaculo().getPlataforma().getNombre(),dto);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error al mapear EspectadorRegistradoAFuncionMap a EspectadorRegistradoAFuncionDTOMap", e);
    }
    
    return espectadorfuncionesDTOMap;
  }
}
