package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.io.*;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {
        Dotenv.configure()
                .filename(".env")
                .systemProperties()
                .load();

        JFrame frame = Dashboard.getInstance();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
