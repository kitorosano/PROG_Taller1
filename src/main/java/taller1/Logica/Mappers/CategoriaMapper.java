package main.java.taller1.Logica.Mappers;

import main.java.taller1.Logica.Clases.Categoria;
import main.java.taller1.Logica.DTOs.CategoriaDTO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class CategoriaMapper {
    // ResultSet -> Model
    public static Categoria toModel(ResultSet rs) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(rs.getString("cat_nombre"));

            return categoria;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a Categoria", e);
        }
    }
    public static Map<String,Categoria> toModelMap(ResultSet rs) {
        try {
            Map<String,Categoria> categorias = new HashMap<>();
            do { // Mientras haya un siguiente elemento
                Categoria categoria = toModel(rs);
                categorias.put(categoria.getNombre(),categoria);
            } while (rs.next());
            return categorias;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear ResultSet a CategoriaMap", e);
        }
    }
    
    // Model -> DTO
    public static CategoriaDTO toDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        try {
            categoriaDTO.setNombre(categoria.getNombre());
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al mapear la categoria a categoriaDTO", e);
        }
        
        return categoriaDTO;
    }
    public static Map<String,CategoriaDTO> toDTOMap(Map<String,Categoria> categorias) {
        Map<String,CategoriaDTO> categoriaDTOMap = new HashMap<>();

        try {
            for (Map.Entry<String, Categoria> entry : categorias.entrySet()) {
                categoriaDTOMap.put(entry.getKey(),toDTO(entry.getValue()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear CategoriaMap a CategoriaDTOMap", e);
        }

        return categoriaDTOMap;
    }
    
    // DTO -> Model
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
