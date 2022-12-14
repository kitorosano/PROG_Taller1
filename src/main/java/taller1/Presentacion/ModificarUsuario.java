package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

public class ModificarUsuario extends JInternalFrame {
    private JPanel mainPanel;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtFechaNac;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFecha_N;
    private JTextField txtDescripcion;
    private JTextField txtBiografia;
    private JTextField txtURL;
    private JLabel lblDescripcion;
    private JLabel lblBiografia;
    private JLabel lblURL;
    private JButton btnAplicar;
    private JButton btnCancelar;
    private JButton btnImagen;
    private JLabel lblImagen;

    String imagen=null;
    private String regexURL = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";

    public ModificarUsuario(String title, UsuarioDTO usuario){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        txtNickname.setText(usuario.getNickname());
        txtCorreo.setText(usuario.getCorreo());
        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtFechaNac.setText(usuario.getFechaNacimiento().toString());

        txtNickname.setEditable(false);
        txtCorreo.setEditable(false);

        createUIComponents(usuario);

        btnAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (comprobarCamposNulos(usuario)) {
                        JOptionPane.showMessageDialog(null, "Los campos obligatorios no pueden estar vacios");
                        return;
                    }
                    if (comprobarExistencia(usuario.getNickname(),usuario.getCorreo()) == false) {
                        JOptionPane.showMessageDialog(null, "El usuario que desea modificar no existe");
                        return;
                    }
                    if (!validarFecha(txtFechaNac.getText())){
                        JOptionPane.showMessageDialog(null, "La fecha no es valida. El formato es (a??o-mes-dia)");
                        return;
                    }
                    //validate url with regex
                    if ((!txtURL.getText().matches(regexURL)) && (usuario.isEsArtista())) {
                        JOptionPane.showMessageDialog(null, "La URL no es valida");
                        return;
                    }
                    ActualizarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario modificado.");
                    dispose();
                } catch (RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Error" + exc.getMessage());
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path;
                File file;
                JFileChooser j = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "imagenes JPG & PNG ", "jpg", "png");
                j.setFileFilter(filter);
                j.setAcceptAllFileFilterUsed(false);
                int retorno = j.showOpenDialog(mainPanel);

                if(retorno == JFileChooser.APPROVE_OPTION){
                    file= j.getSelectedFile();
                    try {
                        String res =null;

                        res = Fabrica.getInstance().getIDatabase().guardarImagen(file);
                        if(res.isEmpty()){
                            btnImagen.setBackground(Color.red.darker());
                        }
                        else{
                            float[] hsvals;
                            btnImagen.setBackground(Color.green.darker());
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

    private void createUIComponents(UsuarioDTO usuario) {
        // TODO: place custom component creation code here
        if (!usuario.isEsArtista()){
            txtDescripcion.setVisible(false);
            txtBiografia.setVisible(false);
            txtURL.setVisible(false);
            lblDescripcion.setVisible(false);
            lblBiografia.setVisible(false);
            lblURL.setVisible(false);
        }else{
            txtDescripcion.setText(usuario.getDescripcion());
            txtBiografia.setText(usuario.getBiografia());
            txtURL.setText(usuario.getSitioWeb());
        }
    }

    private void ActualizarUsuario(UsuarioDTO usuarioDTO){
        usuarioDTO.setNombre(txtNombre.getText());
        usuarioDTO.setApellido(txtApellido.getText());
        usuarioDTO.setCorreo(txtCorreo.getText());
        usuarioDTO.setFechaNacimiento(LocalDate.parse(txtFechaNac.getText()).toString());
        if (imagen != null)
            usuarioDTO.setImagen(imagen);
        if (usuarioDTO.isEsArtista()) {
            usuarioDTO.setDescripcion(txtDescripcion.getText());
            usuarioDTO.setBiografia(txtBiografia.getText());
            usuarioDTO.setSitioWeb(txtURL.getText());
            usuarioDTO.setEsArtista(true);
        }
        Fabrica.getInstance().getIUsuario().modificarUsuario(usuarioDTO);
    }

    public boolean comprobarCamposNulos(UsuarioDTO usuario) {                //Devuelve true si hay campos nulos

        if (txtNickname.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtFechaNac.getText().isEmpty()) {
            return true;
        } else if (usuario.isEsArtista() && txtDescripcion.getText().isEmpty()){
            return true;
        }
        return false;
    }

    public boolean comprobarExistencia(String nickname, String correo) { //Devuelve true si los datos ya existen
        Map<String, Usuario> usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        for (Usuario usu : usuarios.values()) {
            if (usu.getNickname().equals(nickname)) {
                return true;
            }
            if (usu.getCorreo().equals(correo)) {
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
