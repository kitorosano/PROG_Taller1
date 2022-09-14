package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;
import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Interfaces.IUsuario;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Dotenv.configure()
                .filename(".env")
                .systemProperties()
                .load();

//        try {
            JFrame frame = Dashboard.getInstance();
            frame.setResizable(false);
            frame.setVisible(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
