package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.DTOs.FuncionDTO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class FuncionMapper {

    //ResultSet -> Funcion
    public static Funcion toModel(ResultSet rs){
        try {
            if(rs.isBeforeFirst()){
                rs.next();
            }

            Funcion funcion = new Funcion();
            funcion.setNombre(rs.getString("fn_nombre"));
            funcion.setFechaHoraInicio(rs.getTimestamp("fn_fechaHoraInicio").toLocalDateTime());
            funcion.setFechaRegistro(rs.getTimestamp("fn_fechaRegistro").toLocalDateTime());
            funcion.setImagen(rs.getString("fn_imagen"));

            funcion.setEspectaculo(EspectaculoMapper.toModel(rs));
            return funcion;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a Funcion", e);
        }
    }

    //ResultSet -> Map<Funcion>
    public static Map<String,Funcion> toModelMap(ResultSet rs) {
        try {
            Map<String,Funcion> funciones= new HashMap<String,Funcion>();

            while (rs.next()) {
                Funcion funcion = toModel(rs);
                funciones.put(funcion.getNombre()+"-"+funcion.getEspectaculo().getNombre()+"-"+funcion.getEspectaculo().getPlataforma().getNombre(),funcion);
            }
            return funciones;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a FuncionMap", e);
        }
    }

    //Funcion -> DTO
    public static FuncionDTO toDTO(Funcion funcion){
        FuncionDTO funcionDTO = new FuncionDTO();
        try {
            funcionDTO.setNombre(funcion.getNombre());
            funcionDTO.setFechaHoraInicio(funcion.getFechaHoraInicio());
            funcionDTO.setFechaRegistro(funcion.getFechaRegistro());
            funcionDTO.setImagen(funcion.getImagen());
            funcionDTO.setEspectaculo(EspectaculoMapper.toDTO(funcion.getEspectaculo()));
            
            return funcionDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear Funcion a FuncionDTO", e);
        }
    }

    //Map<Funcion> -> Map<FuncionDTO>
    public static Map<String,FuncionDTO> toDTOMap(Map<String,Funcion> funciones) {
        Map<String,FuncionDTO> funcionesDTOMap = new HashMap<>();
        try {
            for (Funcion funcion : funciones.values()) {
                FuncionDTO dtoFuncion= toDTO(funcion);
                funcionesDTOMap.put(dtoFuncion.getNombre(),dtoFuncion);
            }
            
            return funcionesDTOMap;
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear FuncionMap a FuncionDTOMap", e);
        }

    }

}
