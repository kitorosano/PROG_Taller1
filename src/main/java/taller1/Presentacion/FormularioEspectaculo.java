package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

public class FormularioEspectaculo extends JInternalFrame {

    private JPanel panel1;
    private JComboBox cbPlataforma;
    private JComboBox cbArtista;
    private JTextField tfNombre;
    private JTextArea tfDescripcion;
    private JFormattedTextField tfDuracion;
    private JTextField tfCosto;
    private JTextField tfURL;
    private JButton ingresarButton;
    private JButton cancelarButton;
    private JSpinner spEspMaximos;
    private JSpinner spEspMinimos;
    private JButton btnConsulta;
    private JButton seleccionarImagenButton;

    String imagen=null;

    private String regexURL = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";

    public FormularioEspectaculo(String title) {
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        tfDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tfDescripcion.setLineWrap(true);

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
        btnConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame consulta = new ListadoEspectaculos("Listado espectaculo");
                consulta.setIconifiable(true);
                consulta.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(consulta);
                consulta.setVisible(true);
            }
        });
        seleccionarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path;
                File file;
                JFileChooser j = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "imagenes JPG & PNG ", "jpg", "png");
                j.setFileFilter(filter);
                j.setAcceptAllFileFilterUsed(false);
                int retorno = j.showOpenDialog(panel1);

                if(retorno == JFileChooser.APPROVE_OPTION){
                    file= j.getSelectedFile();
                    try {
                        String res =null;
                        res = Fabrica.getInstance().getIDatabase().guardarImagen(file);
                        if(res.isEmpty()){

                            seleccionarImagenButton.setBackground(Color.red.darker());
                        }
                        else{

                            seleccionarImagenButton.setBackground(Color.green.darker());
                        }
                        System.out.println(res);
                        imagen=res;
                    }
                    catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Error" + exc.toString());
                    }
                }
            }
        });
    }

    public void cargarDatosComboBox() {
        // TODO: PONER EN UN TRY CATCH Y QUIZAS ¿GUARDARLO EN UNA VARIABLE PARA NO TENER QUE VOLVER A CONSULTARLOS?
        Map<String, Plataforma> plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
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
        }
        if ((int) spEspMinimos.getValue() < 0) {                                                              //Comprueba el valor de los spiners de espectadores
            JOptionPane.showMessageDialog(null, "El valor de espectadores no puede ser menor a 0");
            return true;
        }
        if ((int) spEspMinimos.getValue() >= (int) spEspMaximos.getValue()) {
            JOptionPane.showMessageDialog(null, "Los espectadores maximos deben ser mas que los minimos");
            return true;
        }
        //validate url with regex
        if (!tfURL.getText().matches(regexURL)) {
            JOptionPane.showMessageDialog(null, "La URL no es valida");
            return true;
        }
        return false;
    }

    public boolean comprobarNombreUnico(String nombrePlataforma, String nombreEspectaculo) {       //Devuelve true si hay error
        Map<String,Espectaculo> espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(nombrePlataforma);
        for (Espectaculo esp : espectaculos.values()) {
            if (esp.getNombre().equals(nombreEspectaculo)) {
                JOptionPane.showMessageDialog(null, "El nombre elegido ya existe en la plataforma");
                return true;
            }
        }
        return false;
    }

    public Espectaculo crearEspectaculo() {
        String nombre = tfNombre.getText(), descripcion = tfDescripcion.getText(), url = tfURL.getText();
        int minEspec = (int) spEspMinimos.getValue(), maxEspec = (int) spEspMaximos.getValue();
        double duracion = Double.parseDouble(tfDuracion.getText()), costo = Double.parseDouble(tfCosto.getText());
        Plataforma plataforma = null;
        Artista artista = null;
        Map<String, Plataforma> plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
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
        Espectaculo nuevo = new Espectaculo(nombre, descripcion, duracion, minEspec, maxEspec, url, costo, E_EstadoEspectaculo.INGRESADO, LocalDateTime.now(), imagen,plataforma, artista);
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
