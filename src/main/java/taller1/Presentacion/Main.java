package main.java.taller1.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Interfaces.IUsuario;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Dotenv.configure()
                .filename(".env")
                .systemProperties()
                .load();

        JFrame frame = new Dashboard("Corona Tickets UY");
        frame.setVisible(true);

        Usuario nuevoUsuario = new Espectador("nickname", "nombre", "apellido", "correo", LocalDate.now());
        Fabrica.getInstance().getIUsuario().altaUsuario(nuevoUsuario);
        Map<String, Usuario> resultado = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        if(resultado.size() > 0) {
            System.out.println("Usuarios encontrados: " + resultado.size());
            for (Map.Entry<String, Usuario> entry : resultado.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        } else {
            System.out.println("No se encontraron usuarios");
        }

    }
}
