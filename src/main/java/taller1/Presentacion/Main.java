package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;
import main.java.taller1.Logica.Clases.Artista;
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
        crearDetalleUsuario();
      //  JFrame frame = new Dashboard("Corona Tickets UY");
      //  frame.setVisible(true);
    }
    public static void crearDetalleUsuario(){
        LocalDate fecha = LocalDate.now();
        LocalDateTime ldt = LocalDateTime.now();
        Usuario espectador =new Espectador("Ramiro11","Ramiro","Fernandez","ramiro11@mail.com",fecha);
        Usuario artista =new Artista("Duki","Mauro","Lombardo","duki@mail.com",fecha,"un gran artista","bio","url");

        DetalleUsuario detalleUsuario = new DetalleUsuario(artista);
        JPanel rootPanel= detalleUsuario.getMainPanel();
        JFrame frame = new JFrame("Detalle Usuario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
