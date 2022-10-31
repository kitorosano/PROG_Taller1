package main.java.taller1.Logica.Interfaces;

import java.io.File;
import java.io.FileInputStream;

public interface IDatabase {
    void cargarDatos();
    void vaciarDatos();
    String guardarImagen(FileInputStream imagen);
    String guardarImagen(File imagen);
    
}
