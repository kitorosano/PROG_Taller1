package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Categoria;
import main.java.taller1.Logica.DTOs.CategoriaDTO;

public class CategoriaMapper {
    public static Categoria toModel(CategoriaDTO categoriadto) {
        try {
            Categoria categoria = new Categoria();
            if(categoriadto.getNombre() != null) categoria.setNombre(categoriadto.getNombre());
            return categoria;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear CategoriaDTO a Categoria", e);
        }
    }
}
