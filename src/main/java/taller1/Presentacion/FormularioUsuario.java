package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

public class FormularioUsuario extends JInternalFrame {
    private JPanel panel1;
    private JTextField tfNickname;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfCorreo;
    private JTextField tfFechaNac;  //TODO: Implementar JCalendar
    private JButton ingresarButton;
    private JTextField tfDescripcion;
    private JTextField tfBiografia;
    private JTextField tfURL;
    private JComboBox<String> comboBoxTipo;
    private JButton cancelarButton;
    private JLabel lbDescripcion;
    private JLabel lblBiografia;
    private JLabel lblURL;
    private JLabel LabelDescripcion;

    private String seleccionado;

    public FormularioUsuario(String title) {
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        comboBoxTipo.addItem("Artista");
        comboBoxTipo.addItem("Espectador");
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (comprobarCamposNulos()) {
                    JOptionPane.showMessageDialog(null, "Los campos obligatorios no pueden estar vacios");
                } else {
                    if (!comprobarDatosUnicos(tfNickname.getText(), tfCorreo.getText())) {
                        seleccionado = (String) comboBoxTipo.getSelectedItem();
                        String nickname = tfNickname.getText(), nombre = tfNombre.getText(), apellido = tfApellido.getText(), correo = tfCorreo.getText();
                        if (!validarFecha(tfFechaNac.getText())) {
                            JOptionPane.showMessageDialog(null, "El formato de fecha no es valido");
                        } else {
                            LocalDate fechanac = LocalDate.parse(tfFechaNac.getText());
                            if (seleccionado.equals("Artista")) {
                                String descripcion = tfDescripcion.getText(),
                                        biografia = tfBiografia.getText(),
                                        url = tfURL.getText();
                                try {
                                    Fabrica.getInstance().getIUsuario().altaUsuario(new Artista(nickname, nombre, apellido, correo, fechanac, descripcion, biografia, url));
                                    JOptionPane.showMessageDialog(null, "Artista creado con exito");
                                    dispose();
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            } else {
                                try {
                                    Fabrica.getInstance().getIUsuario().altaUsuario(new Espectador(nickname, nombre, apellido, correo, fechanac));
                                    JOptionPane.showMessageDialog(null, "Espectador creado con exito");
                                    dispose();
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            }
                        }
                    }
                }

            }
        });


        comboBoxTipo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == comboBoxTipo) {
                    seleccionado = (String) comboBoxTipo.getSelectedItem();
                    if (seleccionado.equals("Espectador")) {
                        tfDescripcion.setVisible(false);
                        tfURL.setVisible(false);
                        tfBiografia.setVisible(false);
                        lblBiografia.setVisible(false);
                        lblURL.setVisible(false);
                        lbDescripcion.setVisible(false);
                        LabelDescripcion.setVisible(false);
                    } else {
                        tfDescripcion.setVisible(true);
                        tfURL.setVisible(true);
                        tfBiografia.setVisible(true);
                        lblBiografia.setVisible(true);
                        lblURL.setVisible(true);
                        lbDescripcion.setVisible(true);
                        LabelDescripcion.setVisible(true);
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

    public boolean comprobarCamposNulos() {                //Devuelve true si hay campos nulos
        String seleccionado = (String) comboBoxTipo.getSelectedItem();
        if (tfNickname.getText().isEmpty() || tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfCorreo.getText().isEmpty() || tfFechaNac.getText().isEmpty()) {
            return true;
        } else {
            if (seleccionado.equals("Artista") && tfDescripcion.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarDatosUnicos(String nickname, String correo) { //Devuelve true si los datos ya existen
        Map<String, Usuario> usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        for (Usuario usu : usuarios.values()) {
            if (usu.getNickname().equals(nickname)) {
                JOptionPane.showMessageDialog(null, "El nickname ingresado ya existe");
                return true;
            }
            if (usu.getCorreo().equals(correo)) {
                JOptionPane.showMessageDialog(null, "El correo ingresado ya existe");
                return true;
            }
        }
        return false;
    }


    public boolean validarFecha(String fecha) {             //Devuelve true si la fecha es correcta
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
