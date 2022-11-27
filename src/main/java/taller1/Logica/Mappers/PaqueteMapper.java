package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.DTOs.PaqueteDTO;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PaqueteMapper {

    //ResultSet -> Paquete
    public static Paquete toModel(ResultSet rs){
        try {
            if (!rs.next()) return null;

            Paquete paquete = new Paquete();

            paquete.setNombre(rs.getString("paq_nombre"));
            paquete.setDescripcion(rs.getString("paq_descripcion"));
            paquete.setDescuento(rs.getDouble("paq_descuento"));
            paquete.setFechaExpiracion(rs.getTimestamp("paq_fechaExpiracion").toLocalDateTime());
            paquete.setFechaRegistro(rs.getTimestamp("paq_fechaRegistro").toLocalDateTime());
            paquete.setImagen(rs.getString("paq_imagen"));

            return paquete;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a Paquete", e);
        }
    }

    //ResultSet -> Map<Paquete>
    public static Map<String,Paquete> toModelMap(ResultSet rs) {
        try {
            Map<String,Paquete> paquetes= new HashMap<>();
            Paquete paquete = toModel(rs);
            while (paquete != null) {
                paquetes.put(paquete.getNombre(),paquete);
                paquete = toModel(rs);
            }
            return paquetes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a PaquetesMap", e);
        }
    }

    //Paquete -> DTO
    public static PaqueteDTO toDTO(Paquete paquete){
        PaqueteDTO paqueteDTO = new PaqueteDTO();
        try {
            paqueteDTO.setNombre(paquete.getNombre());
            paqueteDTO.setDescripcion(paquete.getDescripcion());
            paqueteDTO.setDescuento(paquete.getDescuento());
            paqueteDTO.setFechaExpiracion(paquete.getFechaExpiracion());
            paqueteDTO.setFechaRegistro(paquete.getFechaRegistro());
            paqueteDTO.setImagen(paquete.getImagen());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear Paquete a PaqueteDTO", e);
        }
        return paqueteDTO;
    }


    //Map<Paquete> -> Map<PaqueteDTO>

    public static Map<String,PaqueteDTO> toDTOMap(Map<String,Paquete> paquetes) {
        Map<String,PaqueteDTO> paquetesDTOMap = new HashMap<>();
        try {
            for (Paquete paquete : paquetes.values()) {
                PaqueteDTO paqueteDTO= toDTO(paquete);
                paquetesDTOMap.put(paqueteDTO.getNombre(),paqueteDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear PaqueteMap a PaqueteDTOMap", e);
        }

        return paquetesDTOMap;
    }
}
