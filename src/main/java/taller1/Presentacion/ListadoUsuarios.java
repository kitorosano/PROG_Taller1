package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.Usuario;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ListadoUsuarios extends JInternalFrame {
    private DefaultListModel<String> modelEspectadores = new DefaultListModel<String>();
    private DefaultListModel<String> modelArtistas = new DefaultListModel<String>();

    private JList listaEspectadores;
    private JPanel Panel;
    private JList listaArtistas;
    private JLabel lblArtista;
    private JLabel lblEspectadore;
    private JTextField txtEsp;
    private JTextField txtArt;
    private JButton btnAlta;

    public ListadoUsuarios(String title) {
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        listaEspectadores.setModel(modelEspectadores);
        listaArtistas.setModel(modelArtistas);
        cargarLista();
        setResizable(false );


        listaEspectadores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    llamarDetalle(0);           //LE INDICO QUE ES ESPECTADOR CON EL PARAMETRO INT = 0
                }
            }
        });
        listaArtistas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    llamarDetalle(1);       //LE INDICO QUE ES ARTISTA CON EL PARAMETRO INT = 1
                }
            }
        });
        txtEsp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {//Se ejecuta cuando se libera una tecla
                JTextField textField = (JTextField) e.getSource();
                //obtiene contenido del textfield
                String text = textField.getText();
                if (text.trim().length() > 0) {
                    //nuevo Model temporal
                    DefaultListModel<String> tmp = new DefaultListModel();
                    for (int i = 0; i < modelEspectadores.getSize(); i++) {//recorre Model original
                        //si encuentra coincidencias agrega a model temporal
                        if (modelEspectadores.getElementAt(i).toLowerCase().contains(text.toLowerCase())) {
                            tmp.addElement(modelEspectadores.getElementAt(i));
                        }
                    }
                    //agrega nuevo modelo a JList
                    listaEspectadores.setModel(tmp);
                } else {//si esta vacio muestra el Model original
                    listaEspectadores.setModel(modelEspectadores);
                }
            }
        });
        txtArt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {//Se ejecuta cuando se libera una tecla
                JTextField textField = (JTextField) e.getSource();
                //obtiene contenido del textfield
                String text = textField.getText();
                if (text.trim().length() > 0) {
                    //nuevo Model temporal
                    DefaultListModel<String> tmp = new DefaultListModel();
                    for (int i = 0; i < modelArtistas.getSize(); i++) {//recorre Model original
                        //si encuentra coincidencias agrega a model temporal
                        if (modelArtistas.getElementAt(i).toLowerCase().contains(text.toLowerCase())) {
                            tmp.addElement(modelArtistas.getElementAt(i));
                        }
                    }
                    //agrega nuevo modelo a JList
                    listaArtistas.setModel(tmp);
                } else {//si esta vacio muestra el Model original
                    listaArtistas.setModel(modelArtistas);
                }
            }
        });
        txtEsp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtEsp.setText("");
                listaEspectadores.setModel(modelEspectadores);
            }
        });
        txtArt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtArt.setText("");
                listaArtistas.setModel(modelArtistas);
            }
        });


        txtEsp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(txtEsp.getText().equals("")) {
                    txtEsp.setText("Buscar nickname...");
                    listaEspectadores.setModel(modelEspectadores);
                }
            }
        });
        txtArt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(txtArt.getText().equals("")) {
                    txtArt.setText("Buscar nickname...");
                    listaArtistas.setModel(modelArtistas);
                }
            }
        });
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame alta = new FormularioUsuario("Alta usuario");
                alta.setIconifiable(true);
                alta.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(alta);
                alta.setVisible(true);
            }
        });
    }

    private void cargarLista() {
        Map<String, Usuario> usuarios = new HashMap<String, Usuario>();
        modelEspectadores.clear();
        modelArtistas.clear();
        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
                if (entry.getValue() instanceof Espectador)
                    modelEspectadores.addElement(entry.getValue().getNickname());           //guardo el nickname en la lista
                else if (entry.getValue() instanceof Artista)
                    modelArtistas.addElement(entry.getValue().getNickname());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
    }


    private void llamarDetalle(int tipo) {
        Map<String, Usuario> usuarios = new HashMap<String, Usuario>();

        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            Usuario usuario = new Usuario();
            if (tipo == 0) {
                usuario = usuarios.get(listaEspectadores.getSelectedValue());  //Guardo el usuario seleccionado buscando en la lista por su nickname
            }else if (tipo ==1) {
                usuario = usuarios.get(listaArtistas.getSelectedValue());  //Guardo el usuario seleccionado buscando en la lista por su nickname
            }
            
            JInternalFrame detalle = new DetalleUsuario("Detalle usuario", usuario);
            detalle.setIconifiable(true);
            detalle.setClosable(true);
            
            Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
            detalle.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al llamar al detalle usuario" + e.toString());
        }
    }
}


