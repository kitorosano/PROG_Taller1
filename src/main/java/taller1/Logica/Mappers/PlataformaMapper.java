package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.DTOs.PlataformaDTO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PlataformaMapper {
    //Dto2model
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
    //model to dto
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
    //rs2model
    public static Plataforma toModel(ResultSet rs) {
        try {
            if (!rs.next()) return null;

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
    //rs2modelmap
    public static HashMap<String,Plataforma> toModelMap(ResultSet rs) {
        try {
            HashMap<String,Plataforma> plataformas = new HashMap<>();
            Plataforma plataforma = toModel(rs);
            while (plataforma != null) {
                plataformas.put(plataforma.getNombre(),plataforma);
                plataforma = toModel(rs);
            }
            return plataformas;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a UserList", e);
        }
    }
    //modelmap2ToDtoMap
    public static HashMap<String,PlataformaDTO> toDTOMap(HashMap<String,Plataforma> plataformas) {
        HashMap<String,PlataformaDTO> plataformaDTOMap = new HashMap<>();

        try {
            for (Map.Entry<String, Plataforma> entry : plataformas.entrySet()) {
                plataformaDTOMap.put(entry.getValue().getNombre(),toDTO(entry.getValue()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear PlataformaMap a PlataformaDTOMap", e);
        }

        return plataformaDTOMap;
    }


}
