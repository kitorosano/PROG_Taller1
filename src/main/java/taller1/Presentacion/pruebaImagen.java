package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class pruebaImagen extends JInternalFrame {
    private JButton seleccionarImagenButton;
    private JPanel panel1;
    public pruebaImagen(String title){
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();


        seleccionarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file;
                JFileChooser j = new JFileChooser();
                int retorno = j.showOpenDialog(panel1);

                if(retorno == JFileChooser.APPROVE_OPTION){
                    file = j.getSelectedFile();
                    //uso de guardar imagen convirtiendo a input stram
                    try {
                        String res = Fabrica.getInstance().getIDatabase().guardarImagen(file);
                        System.out.println(res);
                    }
                    catch (RuntimeException exc) {
                        JOptionPane.showMessageDialog(null, "Error" + exc.getMessage());
                    }
                }
            }
        });
    }
}
