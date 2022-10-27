package main.java.taller1.Logica.Interfaces;

import java.io.FileInputStream;

public interface IDatabase {
    void cargarDatos();
    void vaciarDatos();
    String guardarImagen(FileInputStream imagen);
}
