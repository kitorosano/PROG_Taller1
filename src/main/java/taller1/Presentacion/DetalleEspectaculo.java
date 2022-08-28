package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Funcion;

import javax.swing.*;

public class DetalleEspectaculo {
    private JPanel mainPanel;
    private JTextArea textArea1;

    public DetalleEspectaculo(Espectaculo espectaculo){
        textArea1.setText(espectaculo.getNombre());
    }
    public  JPanel getMainPanel(){return mainPanel;}

    public static void crearDetalleEspectaculo(Espectaculo espectaculo){

        DetalleEspectaculo detalleEspectaculo = new DetalleEspectaculo(espectaculo);
        JPanel rootPanel= detalleEspectaculo.getMainPanel();
        JFrame frame = new JFrame("Detalle Espectaculo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
