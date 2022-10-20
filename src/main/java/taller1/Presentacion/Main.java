package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.io.*;

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
