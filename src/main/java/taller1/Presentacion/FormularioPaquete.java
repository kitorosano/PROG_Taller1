package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Map;

public class FormularioPaquete extends JFrame{
    private JPanel panel1;
    private JTextField tfNombre;
    private JButton cancelarButton;
    private JButton ingresarButton;
    private JTextField tfDescripcion;
    private JTextField tfDescuento;
    private JTextField tfFechaLanz;
    private JTextField tfFechaVenc;

    public FormularioPaquete(String title) {
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        soloNumero(tfDescuento);
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!comprobarErrorEnCampos()){
                    if(!comprobarNombreUnico(tfNombre.getText())){
                        try{
                            Fabrica.getInstance().getIEspectaculo().altaPaquete(new Paquete(tfNombre.getText(), LocalDateTime.parse(tfFechaVenc.getText()+" 00:00:00"),
                                    tfDescripcion.getText(),Double.parseDouble(tfDescuento.getText()),LocalDateTime.now()));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }
                    }
                }
            }
        });
    }


    public boolean comprobarErrorEnCampos() {                //Devuelve true si hay error
        if (tfNombre.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfDescuento.getText().isEmpty() || tfFechaLanz.getText().isEmpty() || tfFechaVenc.getText().isEmpty()) {    //Comprobar campos nulos
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            return true;
        } else if (!validarFechas(tfFechaLanz.getText(),tfFechaVenc.getText())){
            return true;
        }
        return false;
    }

    public boolean comprobarNombreUnico(String nombrePaquete) {       //Devuelve true si hay error
        try {
            Map<String, Paquete> paquetes = Fabrica.getInstance().getIEspectaculo().obtenerPaquetes();
            for (Paquete paq : paquetes.values()) {
                if (paq.getNombre().equals(nombrePaquete)) {
                    JOptionPane.showMessageDialog(null, "El nombre elegido ya existe");
                    return true;
                }
            }
            return false;
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        }
    }

    private void soloNumero(JTextField tf) {         //Impide que se ingresen caracteres no numericos
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
                if (c == '.' && tf.getText().contains(".")) {
                    e.consume();
                }
            }
        });
    }

    public boolean validarFechas(String fechaInicio,String fechaFinal) {             //Devuelve true si la fecha es correcta
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatoFecha.setLenient(false);
            formatoFecha.parse(fechaInicio);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"Formato de fecha de inicio no valido");
            return false;
        }
        try {
            formatoFecha.setLenient(false);
            formatoFecha.parse(fechaFinal);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"Formato de fecha final no valido");
            return false;
        }

        if(fechaInicio.compareTo(fechaFinal)!=-1){
            JOptionPane.showMessageDialog(null,"La fecha de inicio no puede ser mayor que la de vencimiento");
            return false;
        }
        return true;
    }
}
