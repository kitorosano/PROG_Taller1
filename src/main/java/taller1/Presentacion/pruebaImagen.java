package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

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
                String path;
                JFileChooser j = new JFileChooser();
                int retorno = j.showOpenDialog(panel1);

                if(retorno == JFileChooser.APPROVE_OPTION){
                    path= j.getSelectedFile().getAbsolutePath();
                    //uso de guardar imagen convirtiendo a input stram
                    System.out.println(path);
                    try {
                        FileInputStream fileInput = new FileInputStream(path);
                        System.out.println(Fabrica.getInstance().getIDatabase().guardarImagen(fileInput));
                    }
                    catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Error" + exc.toString());
                    }

                }
            }
        });
    }
}
