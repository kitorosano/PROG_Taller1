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

        //LocalDate fecha = LocalDate.now();
        //LocalDateTime ldt = LocalDateTime.now();
        //Usuario espectador =new Espectador("Ramiro11","Ramiro","Fernandez","ramiro11@mail.com",fecha);
        //Usuario artista =new Artista("Duki","Mauro","Lombardo","duki@mail.com",fecha,"un gran artista","bio","url");
        //DetalleUsuario.crearDetalleUsuario(espectador);
            Paquete paquete=new Paquete("Paquete1", LocalDateTime.now(),"Esto es una descrpcion",2500,LocalDateTime.now());
            DetallePaquete.crearDetallePaquete(paquete);
        //JFrame frame = new Dashboard("Corona Tickets UY");
        //frame.setVisible(true);
    }

}
