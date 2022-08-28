package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Interfaces.IUsuario;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Dotenv.configure()
                .filename(".env")
                .systemProperties()
                .load();

        JFrame frame = new Dashboard("Corona Tickets UY");
        frame.setVisible(true);
    }
}
