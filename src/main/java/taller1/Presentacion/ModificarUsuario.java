package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Usuario;

import javax.swing.*;

public class ModificarUsuario extends JInternalFrame {
    private JPanel mainPanel;

    public ModificarUsuario(String title, Usuario usuario){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }
}
