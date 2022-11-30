package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.AltaCategoriaAEspectaculoDTO;
import main.java.taller1.Logica.DTOs.AltaEspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Mappers.PlataformaMapper;
import main.java.taller1.Logica.Mappers.UsuarioMapper;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private JList listCategoriaAgregar;
    private JList listCategoriaQuitar;
    private JButton btnQuitar;
    private JButton btnAgregar;

    private DefaultListModel<String> modelAgregar = new DefaultListModel<String>();

    private DefaultListModel<String> modelQuitar = new DefaultListModel<String>();

    private Map<String,Categoria>categorias;

    private Map<String, String>categoriasAgregar = new HashMap<String, String>();

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

        listCategoriaAgregar.setModel(modelAgregar);
        listCategoriaQuitar.setModel(modelQuitar);

        cargarDatosListas();

        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String plataforma = (String) cbPlataforma.getSelectedItem();
                if (!comprobarErrorEnCampos()) {
                    if (!comprobarNombreUnico(plataforma, tfNombre.getText())) {
                        try {
                            crearEspectaculo();

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
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String miCategoria = (String) listCategoriaAgregar.getSelectedValue();
                //Categoria cat = Fabrica.getInstance().getICategoria().obtenerCategoria(miCategoria).get();
                categoriasAgregar.put(miCategoria, miCategoria);

                String categoria=modelAgregar.getElementAt(listCategoriaAgregar.getSelectedIndex());
                modelQuitar.addElement(categoria);
                modelAgregar.remove(listCategoriaAgregar.getSelectedIndex());
            }
        });
        btnQuitar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                categoriasAgregar.remove((String)listCategoriaQuitar.getSelectedValue());
                String categoria=modelQuitar.getElementAt(listCategoriaQuitar.getSelectedIndex());
                modelAgregar.addElement(categoria);
                modelQuitar.remove(listCategoriaQuitar.getSelectedIndex());
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
                cbArtista.addItem(usu.getNickname());
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
        Map<String,EspectaculoDTO> espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(nombrePlataforma);
        for (EspectaculoDTO esp : espectaculos.values()) {
            if (esp.getNombre().equals(nombreEspectaculo)) {
                JOptionPane.showMessageDialog(null, "El nombre elegido ya existe en la plataforma");
                return true;
            }
        }
        return false;
    }

    public void crearEspectaculo() {
        String nombre = tfNombre.getText(), descripcion = tfDescripcion.getText(), url = tfURL.getText();
        int minEspec = (int) spEspMinimos.getValue(), maxEspec = (int) spEspMaximos.getValue();
        double duracion = Double.parseDouble(tfDuracion.getText()), costo = Double.parseDouble(tfCosto.getText());
        if(imagen==null){
            imagen="https://i.imgur.com/BeJ3HuS.png";
        }

        AltaEspectaculoDTO dto = new AltaEspectaculoDTO();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setDuracion(duracion);
        dto.setMinEspectadores(minEspec);
        dto.setMaxEspectadores(maxEspec);
        dto.setUrl(url);
        dto.setCosto(costo);
        dto.setEstado(E_EstadoEspectaculo.INGRESADO);
        dto.setFechaRegistro(LocalDateTime.now());
        dto.setImagen(imagen);
        dto.setPlataforma((String) cbPlataforma.getSelectedItem());
        dto.setArtista((String) cbArtista.getSelectedItem());
        Fabrica.getInstance().getIEspectaculo().altaEspectaculo(dto);

        for (String micategoria : categoriasAgregar.values()){
            AltaCategoriaAEspectaculoDTO altaCategoriaAEspectaculoDTO = new AltaCategoriaAEspectaculoDTO();
            altaCategoriaAEspectaculoDTO.setNombreCategoria(micategoria);
            altaCategoriaAEspectaculoDTO.setNombreEspectaculo(dto.getNombre());
            altaCategoriaAEspectaculoDTO.setNombrePlataforma(dto.getPlataforma());
            Fabrica.getInstance().getICategoria().altaCategoriaAEspectaculo(altaCategoriaAEspectaculoDTO);
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

    public void cargarDatosListas(){
        Map<String,EspectadorRegistradoAFuncion>invitados;
        modelAgregar.clear();
        modelQuitar.clear();
        try {
            categorias = Fabrica.getInstance().getICategoria().obtenerCategorias();
            for(Categoria c:categorias.values()){
                modelAgregar.addElement(c.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
    }

}
