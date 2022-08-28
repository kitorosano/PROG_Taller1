package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Clases.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DetalleFuncion {
    private JPanel mainPanel;
    private JTextArea textArea1;

    public DetalleFuncion(Funcion funcion){
        textArea1.setText(funcion.getNombre());
    }
    public  JPanel getMainPanel(){return mainPanel;}

    public static void crearDetalleFuncion(Funcion funcion){

        DetalleFuncion detalleFuncion = new DetalleFuncion(funcion);
        JPanel rootPanel= detalleFuncion.getMainPanel();
        JFrame frame = new JFrame("Detalle Funcion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



