package main.java.taller1.Logica.Interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public interface IDatabase {
    void cargarDatos();
    void vaciarDatos();
    String guardarImagen(InputStream imagen) throws IOException;
}
