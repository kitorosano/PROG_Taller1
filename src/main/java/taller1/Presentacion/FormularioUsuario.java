package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;
import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
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
    private JTextArea tfDescripcion;
    private JTextArea tfBiografia;
    private JTextField tfURL;
    private JComboBox<String> comboBoxTipo;
    private JButton cancelarButton;
    private JLabel lbDescripcion;
    private JLabel lblBiografia;
    private JLabel lblURL;
    private JButton btnConsulta;
    private JTextField tfContrasenia;
    private JTextField tfContrasenia2;
    private JButton seleccionarImagenButton;
    private JLabel estadoImagen;
    private JLabel LabelDescripcion;

    private String seleccionado;

    String imagen=null;

    private String regexURL = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";

    public FormularioUsuario(String title) {
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        tfDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tfBiografia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tfDescripcion.setLineWrap(true);
        tfBiografia.setLineWrap(true);
        comboBoxTipo.addItem("Artista");
        comboBoxTipo.addItem("Espectador");
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (comprobarCamposNulos()) {
                    JOptionPane.showMessageDialog(null, "Los campos obligatorios no pueden estar vacios");
                } else {
                    // comprobar que los datos sean unicos
                    if (!comprobarDatosUnicos(tfNickname.getText(), tfCorreo.getText())) {
                        seleccionado = (String) comboBoxTipo.getSelectedItem();
                        String nickname = tfNickname.getText(),
                            nombre = tfNombre.getText(),
                            apellido = tfApellido.getText(),
                            correo = tfCorreo.getText(),
                            contrasenia = tfContrasenia.getText();
                            //imagen = "";
                        
                        // validar fecha
                        if (!validarFecha(tfFechaNac.getText())) {
                            JOptionPane.showMessageDialog(null, "El formato de fecha no es valido");
                            return;
                        }
                        LocalDate fechanac = LocalDate.parse(tfFechaNac.getText());
                        //validate url with regex
                        if ((!tfURL.getText().matches(regexURL)) && ((String) comboBoxTipo.getSelectedItem() == "Artista")) {
                            JOptionPane.showMessageDialog(null, "La URL no es valida");
                            return;
                        }
                        
                        //validar contraseña
                        if (!tfContrasenia.getText().equals(tfContrasenia2.getText())) {
                            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                            return;
                        }
                        if(imagen==null){
                            imagen="https://i.imgur.com/e4W1PV0.png";
                        }
                        if (seleccionado.equals("Artista")) {
                            String descripcion = tfDescripcion.getText(),
                                    biografia = tfBiografia.getText(),
                                    url = tfURL.getText();
                            try {
                                UsuarioDTO usuarioDTO = new UsuarioDTO();
                                usuarioDTO.setNickname(nickname);
                                usuarioDTO.setNombre(nombre);
                                usuarioDTO.setApellido(apellido);
                                usuarioDTO.setCorreo(correo);
                                usuarioDTO.setContrasenia(contrasenia);
                                usuarioDTO.setFechaNacimiento(fechanac);
                                usuarioDTO.setImagen(imagen);
                                usuarioDTO.setDescripcion(descripcion);
                                usuarioDTO.setBiografia(biografia);
                                usuarioDTO.setSitioWeb(url);
                                usuarioDTO.setEsArtista(true);
                                Fabrica.getInstance().getIUsuario().altaUsuario(usuarioDTO);
                                JOptionPane.showMessageDialog(null, "Artista creado con exito");
                                dispose();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                        } else { // equal Espectador
                            try {
                                UsuarioDTO usuarioDTO = new UsuarioDTO();
                                usuarioDTO.setNickname(nickname);
                                usuarioDTO.setNombre(nombre);
                                usuarioDTO.setApellido(apellido);
                                usuarioDTO.setCorreo(correo);
                                usuarioDTO.setContrasenia(contrasenia);
                                usuarioDTO.setFechaNacimiento(fechanac);
                                usuarioDTO.setImagen(imagen);
                                Fabrica.getInstance().getIUsuario().altaUsuario(usuarioDTO);
                                JOptionPane.showMessageDialog(null, "Espectador creado con exito");
                                dispose();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
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
                    } else {
                        tfDescripcion.setVisible(true);
                        tfURL.setVisible(true);
                        tfBiografia.setVisible(true);
                        lblBiografia.setVisible(true);
                        lblURL.setVisible(true);
                        lbDescripcion.setVisible(true);
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
                JInternalFrame alta = new ListadoUsuarios("Listado usuarios");
                alta.setIconifiable(true);
                alta.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(alta);
                alta.setVisible(true);
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
                            float[] hsvals;
                            seleccionarImagenButton.setBackground(Color.green.darker());
                        }

                        System.out.println(res);
                        imagen=res;

                    }
                    catch (RuntimeException exc) {
                        JOptionPane.showMessageDialog(null, "Error" + exc.getMessage());
                    }
                }

            }
        });
    }

    public boolean comprobarCamposNulos() {                //Devuelve true si hay campos nulos
        String seleccionado = (String) comboBoxTipo.getSelectedItem();
        if (tfNickname.getText().isEmpty() || tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfCorreo.getText().isEmpty() || tfFechaNac.getText().isEmpty() || tfContrasenia.getText().isEmpty() || tfContrasenia2.getText().isEmpty()) {
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
