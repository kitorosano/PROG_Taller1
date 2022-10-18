package main.java.taller1.Logica.Controladores;

import main.java.taller1.Logica.Interfaces.IDatabase;
import main.java.taller1.Persistencia.ConexionDB;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController implements IDatabase {
    private static DatabaseController instance = null;

    private DatabaseController() {}

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }
  
  @Override
  public void vaciarDatos(){
    Connection connection = null;
    Statement statement = null;
    String deletePlataformas = "DELETE FROM plataformas";
    String deletePaquetes = "DELETE FROM paquetes";
    String deleteArtistas = "DELETE FROM artistas";
    String deleteEspectadores = "DELETE FROM espectadores";
    try {
      connection = ConexionDB.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(deletePlataformas);
      statement.executeUpdate(deletePaquetes);
      statement.executeUpdate(deleteArtistas);
      statement.executeUpdate(deleteEspectadores);
      System.out.println("Todos los datos relacionados a los usuarios (artistas, espectadores) y espectaculos (plataforma, funciones, paquetes y registros) han sido eliminados.");
      ScriptRunner sr = new ScriptRunner(connection);
      Reader reader = new BufferedReader(new FileReader("src/main/resources/creation.sql"));
      sr.runScript(reader);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al conectar con la base de datos", e);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException("Error al vaciar los datos de los espectaculos", e);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
      }
    }
  }
  
  @Override
  public void cargarDatos(){
    Connection connection = null;
    try {
      connection = ConexionDB.getConnection();
      ScriptRunner sr = new ScriptRunner(connection);
      Reader reader = new BufferedReader(new FileReader("src/main/resources/artistas.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/espectadores.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/plataformas.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/paquetes.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/espectaculos.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/funciones.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/espectaculos_paquetes.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/espectadores_funciones.sql"));
      sr.runScript(reader);
      reader = new BufferedReader(new FileReader("src/main/resources/artistas_funciones.sql"));
      sr.runScript(reader);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (RuntimeException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException("Error al insertar los datos de prueba de usuarios y espectaculos", ex);
    } finally {
      try {
        if (connection != null) connection.close();
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        throw new RuntimeException("Error al cerrar la conexión a la base de datos", ex);
      }
    }
  }
  
}
