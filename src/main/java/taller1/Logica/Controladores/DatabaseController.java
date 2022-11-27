package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Interfaces.IDatabase;
import main.java.taller1.Logica.Servicios.DatabaseService;
import main.java.taller1.Persistencia.ConexionDB;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.jdbc.ScriptRunner;
import sun.misc.IOUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController implements IDatabase {
    private static DatabaseController instance = null;
    private DatabaseService servicio;

    private DatabaseController() {
      servicio = new DatabaseService();
    }

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }
  
  /**
   * Metodo que permite vaciar los datos que existian en base de datos y crear toda la estructura nuevamente
   */
  @Override
  public void vaciarDatos(){
    servicio.vaciarDatos();
  }
  
  /**
   * Metodo que permite cargar los datos de prueba a la base de datos
   */
  @Override
  public void cargarDatos(){
    servicio.cargarDatos();
  }
  
  /**
   * Metodo que permite guardar una imagen en la base de datos
   * @param imagen de tipo FileInputStream
   *               imagen que se desea guardar en la base de datos
   * @return String
   */
  @Override
  public String guardarImagen(FileInputStream imagen){
    return servicio.guardarImagen(imagen);
  }
  
  /**
   * Metodo que permite obtener una imagen de la base de datos
   * @param imagen de tipo File
   *                de la imagen que se guardar en la base de datos
   * @return String
   */
  @Override
  public String guardarImagen(File imagen){
    return servicio.guardarImagen(imagen);
  }
  
}
