package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

public class FormularioPlataforma extends JInternalFrame {
    private JPanel panel1;
    private JTextField tfNombre;
    private JTextField tfURL;
    private JTextField tfDescripcion;
    private JButton ingresarButton;
    private JButton cancelarButton;

    private String regexURL = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";

    public FormularioPlataforma(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setContentPane(panel1);
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!comprobarErrorEnCampos()) {
                    if (comprobarNombre(tfNombre.getText())) {
                        JOptionPane.showMessageDialog(null, "El nombre ingresado ya existe");
                    } else {
                        String nombre = tfNombre.getText(), descripcion = tfDescripcion.getText(), url = tfURL.getText();
                        try {
                            Fabrica.getInstance().getIPlataforma().altaPlataforma(new Plataforma(nombre, descripcion, url));
                            JOptionPane.showMessageDialog(null, "Plataforma agregada con exito");
                            dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }
                }
            }
        });
        cancelarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }


    public boolean comprobarErrorEnCampos() {                //Devuelve true si hay error
        if (tfNombre.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfURL.getText().isEmpty()) {    //Comprobar campos nulos
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            return true;
        }
        //validate url with regex
        if (!tfURL.getText().matches(regexURL)) {
            JOptionPane.showMessageDialog(null, "La URL no es valida");
            return true;
        }
        return false;
    }

    public boolean comprobarNombre(String nombre) {          //Devuelve true si el nombre ya existe
        Map<String, Plataforma> plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
        Set<String> keys = plataformas.keySet();
        for (String key : keys) {
            if (key.equals(nombre))
                return true;
        }
        return false;
    }

}
