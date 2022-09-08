package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Dotenv.configure()
                .filename(".env")
                .systemProperties()
                .load();

        JFrame frame = new FormularioUsuario("Corona Tickets UY");
        frame.setVisible(true);
    }
}
