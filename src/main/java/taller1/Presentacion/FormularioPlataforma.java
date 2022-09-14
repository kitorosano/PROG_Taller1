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

    public FormularioPlataforma(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setContentPane(panel1);
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tfNombre.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfURL.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
                } else {
                    if (comprobarNombre(tfNombre.getText())) {
                        JOptionPane.showMessageDialog(null, "El nombre ingresado ya existe");
                    } else {
                        String nombre = tfNombre.getText(), descripcion = tfDescripcion.getText(), url = tfURL.getText();
                        try {
                            Fabrica.getInstance().getIEspectaculo().altaPlataforma(new Plataforma(nombre, descripcion, url));
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

    public boolean comprobarNombre(String nombre) {          //Devuelve true si el nombre ya existe
        Map<String, Plataforma> plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
        Set<String> keys = plataformas.keySet();
        for (String key : keys) {
            if (key.equals(nombre))
                return true;
        }
        return false;
    }

}
