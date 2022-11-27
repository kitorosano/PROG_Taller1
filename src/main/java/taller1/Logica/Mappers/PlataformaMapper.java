package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.DTOs.PlataformaDTO;

public class PlataformaMapper {
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
