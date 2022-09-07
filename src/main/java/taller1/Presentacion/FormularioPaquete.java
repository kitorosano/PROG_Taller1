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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class FormularioPaquete extends JFrame{
    private JPanel panel1;
    private JTextField tfNombre;
    private JButton cancelarButton;
    private JButton ingresarButton;
    private JTextField tfDescripcion;
    private JTextField tfDescuento;
    private JTextField tfFechaVenc; //TODO: Implementar JCalendar

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
                        LocalDateTime fechaVencimiento= LocalDateTime.of(LocalDate.parse(tfFechaVenc.getText()), LocalTime.parse("00:00:00"));
                        try{
                            Fabrica.getInstance().getIEspectaculo().altaPaquete(new Paquete(tfNombre.getText(), fechaVencimiento,
                                    tfDescripcion.getText(),Double.parseDouble(tfDescuento.getText()),LocalDateTime.now()));
                            JOptionPane.showMessageDialog(null,"Paquete agregado exitosamente");
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }
                    }
                }
            }
        });
        cancelarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }


    public boolean comprobarErrorEnCampos() {                //Devuelve true si hay error
        if (tfNombre.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfDescuento.getText().isEmpty() || tfFechaVenc.getText().isEmpty()) {    //Comprobar campos nulos
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            return true;
        } else if (!validarFechas(tfFechaVenc.getText())){
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

    public boolean validarFechas(String fechaFinal) {             //Devuelve true si la fecha es correcta
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatoFecha.setLenient(false);
            formatoFecha.parse(fechaFinal);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,"Formato de fecha final no valido");
            return false;
        }
        return true;
    }
}
