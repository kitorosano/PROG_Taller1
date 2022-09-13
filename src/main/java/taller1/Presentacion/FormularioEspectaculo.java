package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Map;

public class FormularioEspectaculo extends JFrame {

    private JPanel panel1;
    private JComboBox cbPlataforma;
    private JComboBox cbArtista;
    private JTextField tfNombre;
    private JTextField tfDescripcion;
    private JFormattedTextField tfDuracion;
    private JTextField tfCosto;
    private JTextField tfURL;
    private JButton ingresarButton;
    private JButton cancelarButton;
    private JSpinner spEspMaximos;
    private JSpinner spEspMinimos;

    //TODO: QUE EL TITULO SE TRAIGA DE UNA PROPIEDAD STATIC DEL DASHBOARD, Y QUE DESDE ESTA PANTALLA SE AGREGUE " - NUEVO ESPECTACULO"
    public FormularioEspectaculo(String title) {
        super(title + " - Nuevo Espectaculo");
        setContentPane(panel1);
        setSize(500, 280);
        setResizable(false);
        setLocationRelativeTo(null); //Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        soloNumero(tfDuracion);
        soloNumero(tfCosto);
        cargarDatosComboBox();
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String plataforma = (String) cbPlataforma.getSelectedItem();
                if (!comprobarErrorEnCampos()) {
                    if (!comprobarNombreUnico(plataforma, tfNombre.getText())) {
                        try {
                            Fabrica.getInstance().getIEspectaculo().altaEspectaculo(crearEspectaculo());
                            JOptionPane.showMessageDialog(null, "Espectaculo agregado con exito");
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
                dispose();
            }
        });
    }

    public void cargarDatosComboBox() {
        // TODO: PONER EN UN TRY CATCH Y QUIZAS ¿GUARDARLO EN UNA VARIABLE PARA NO TENER QUE VOLVER A CONSULTARLOS?
        Map<String, Plataforma> plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
        Map<String, Usuario> usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();

        for (Plataforma p : plataformas.values()) {
            cbPlataforma.addItem(p.getNombre());
        }
        for (Usuario usu : usuarios.values()) {
            if (usu instanceof Artista) {
                cbArtista.addItem(usu.getNombre());
            }
        }
    }

    public boolean comprobarErrorEnCampos() {                //Devuelve true si hay error
        if (tfNombre.getText().isEmpty() || tfDuracion.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfCosto.getText().isEmpty() || tfURL.getText().isEmpty()) {    //Comprobar campos nulos
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            return true;
        } else if ((int) spEspMinimos.getValue() < 0) {                                                              //Comprueba el valor de los spiners de espectadores
            JOptionPane.showMessageDialog(null, "El valor de espectadores no puede ser menor a 0");
            return true;
        } else if ((int) spEspMinimos.getValue() >= (int) spEspMaximos.getValue()) {
            JOptionPane.showMessageDialog(null, "Los espectadores maximos deben ser mas que los minimos");
            return true;
        }
        return false;
    }

    public boolean comprobarNombreUnico(String nombrePlataforma, String nombreEspectaculo) {       //Devuelve true si hay error
        Map<String,Espectaculo> espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(nombrePlataforma);
        for (Espectaculo esp : espectaculos.values()) {
            if (esp.getNombre().equals(nombreEspectaculo)) {
                JOptionPane.showMessageDialog(null, "El nombre elegido ya existe en la plataforma");
                return true;
            }
        }
        return false;
    }

    public Espectaculo crearEspectaculo() {
        String nombre = tfNombre.getText(),
               descripcion = tfDescripcion.getText(),
               url = tfURL.getText();
        int minEspec = (int) spEspMinimos.getValue(),
            maxEspec = (int) spEspMaximos.getValue();
        double duracion = Double.parseDouble(tfDuracion.getText()),
               costo = Double.parseDouble(tfCosto.getText());
        Plataforma plataforma = null;
        Artista artista = null;
        Map<String, Plataforma> plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
        Map<String, Usuario> usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        for (Plataforma p : plataformas.values()) {
            if (p.getNombre().equals((String) cbPlataforma.getSelectedItem())) {
                plataforma = p;
                break;
            }
        }
        for (Usuario usu : usuarios.values()) {
            if (usu instanceof Artista) {
                if (usu.getNombre().equals((String) cbArtista.getSelectedItem())) {
                    artista = (Artista) usu;
                    break;
                }
            }
        }
        Espectaculo nuevo = new Espectaculo(nombre, descripcion, duracion, minEspec, maxEspec, url, costo, LocalDateTime.now(), plataforma, artista);
        return nuevo;
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
}
