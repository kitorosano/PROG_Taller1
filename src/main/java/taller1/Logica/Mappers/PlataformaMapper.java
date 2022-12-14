package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.DTOs.PlataformaDTO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PlataformaMapper {
    // ResultSet -> Model
    public static Plataforma toModel(ResultSet rs) {
        try {
            Plataforma plataforma = new Plataforma();
            plataforma.setNombre(rs.getString("pl_nombre"));
            plataforma.setDescripcion(rs.getString("pl_descripcion"));
            plataforma.setUrl(rs.getString("pl_url"));
            
            return plataforma;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a Plataforma", e);
        }
    }
    public static Map<String,Plataforma> toModelMap(ResultSet rs) {
        try {
            Map<String,Plataforma> plataformas = new HashMap<>();
            do { //Mientras haya un siguiente elemento
                Plataforma plataforma = toModel(rs);
                plataformas.put(plataforma.getNombre(),plataforma);
            } while (rs.next());
            return plataformas;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a UserList", e);
        }
    }
    
    // Model -> DTO
    public static PlataformaDTO toDTO(Plataforma plataforma){
        PlataformaDTO plataformaDTO = new PlataformaDTO();
        try {
            plataformaDTO.setNombre(plataforma.getNombre());
            plataformaDTO.setDescripcion(plataforma.getDescripcion());
            plataformaDTO.setUrl(plataforma.getUrl());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear la plataforma a plataformaDTO", e);
        }

        return plataformaDTO;
    }
    public static Map<String,PlataformaDTO> toDTOMap(Map<String,Plataforma> plataformas) {
        Map<String,PlataformaDTO> plataformaDTOMap = new HashMap<>();
        
        try {
            for (Map.Entry<String, Plataforma> entry : plataformas.entrySet()) {
                plataformaDTOMap.put(entry.getKey(),toDTO(entry.getValue()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear PlataformaMap a PlataformaDTOMap", e);
        }
        
        return plataformaDTOMap;
    }
    
    // DTO -> Model
    public static Plataforma toModel(PlataformaDTO plataformadto) {
        try {
            Plataforma plataforma = new Plataforma();
            if(plataformadto.getNombre() != null) plataforma.setNombre(plataformadto.getNombre());
            if(plataformadto.getDescripcion() != null) plataforma.setDescripcion(plataformadto.getDescripcion());
            if(plataformadto.getUrl() != null) plataforma.setUrl(plataformadto.getUrl());
            return plataforma;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear UserDTO a User", e);
        }
    }
}
