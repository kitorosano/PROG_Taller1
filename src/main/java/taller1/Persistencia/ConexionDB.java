package main.java.taller1.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    public static Connection getConnection() {
        Connection connection = null;
        String url = System.getProperty("DB_URL");
        String user = System.getProperty("DB_USER");
        String password = System.getProperty("DB_PASS");
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null)
                System.out.println("Conexion a la base de datos establecida");

        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
        return connection;
    }

}