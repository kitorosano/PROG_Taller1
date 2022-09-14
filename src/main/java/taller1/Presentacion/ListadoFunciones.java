package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ListadoFunciones extends JInternalFrame {
    private JPanel Panel;
    private JComboBox cmbPlataforma;
    private JComboBox cmbEspectaculo;
    private JList listaFunciones;
    private JLabel lblPlataforma;
    private JLabel lblEspectaculo;
    private JLabel lblFunciones;
    private JTextField txtFunciones;

    private DefaultListModel<String> model = new DefaultListModel<String>();

    public ListadoFunciones(String title){
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        cargarPlataformas();
        cargarEspectaculos();
        cargarFunciones();
        listaFunciones.setModel(model);
        setResizable(false);
        listaFunciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount()==2){
                    llamarDetalleFuncion();
                }
            }
        });
        cmbPlataforma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEspectaculos();
            }
        });
        cmbEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarFunciones();
            }
        });
        txtFunciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {//Se ejecuta cuando se libera una tecla
                JTextField textField = (JTextField) e.getSource();
                //obtiene contenido del textfield
                String text = textField.getText();
                if (text.trim().length() > 0) {
                    //nuevo Model temporal
                    DefaultListModel<String> tmp = new DefaultListModel();
                    for (int i = 0; i < model.getSize(); i++) {//recorre Model original
                        //si encuentra coincidencias agrega a model temporal
                        if (model.getElementAt(i).toLowerCase().contains(text.toLowerCase())) {
                            tmp.addElement(model.getElementAt(i));
                        }
                    }
                    //agrega nuevo modelo a JList
                    listaFunciones.setModel(tmp);
                } else {//si esta vacio muestra el Model original
                    listaFunciones.setModel(model);
                }
            }
        });
        txtFunciones.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtFunciones.setText("");
                listaFunciones.setModel(model);
            }
        });
        txtFunciones.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(txtFunciones.getText().equals("")) {
                    txtFunciones.setText("Buscar nickname...");
                    listaFunciones.setModel(model);
                }
            }
        });
    }

    private void cargarPlataformas(){
        Map<String, Plataforma> plataformas = new HashMap<String, Plataforma>();
        cmbPlataforma.removeAllItems();
        try {
            plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
            for (Map.Entry<String, Plataforma> entry : plataformas.entrySet()) {
                cmbPlataforma.addItem(entry.getValue().getNombre());           //guardo la plataforma en el combo box
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al obtener las plataformas" + e.toString());
        }
    }
    private void cargarEspectaculos(){
        Map<String, Espectaculo> espectaculos = new HashMap<String, Espectaculo>();
        cmbEspectaculo.removeAllItems();
        try {
            espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(cmbPlataforma.getSelectedItem().toString());
            for (Map.Entry<String, Espectaculo> entry : espectaculos.entrySet()) {
                cmbEspectaculo.addItem(entry.getValue().getNombre());           //guardo el espectaculo en el combo box
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al obtener los espectaculos" + e.toString());
        }
    }
    private void cargarFunciones(){
        Map<String, Funcion> funciones = new HashMap<String, Funcion>();
        model.clear();
        if (cmbEspectaculo.getSelectedItem()==null)
            return;
        try {
            funciones = Fabrica.getInstance().getIEspectaculo().obtenerFuncionesDeEspectaculo(cmbPlataforma.getSelectedItem().toString(), cmbEspectaculo.getSelectedItem().toString());
            for (Map.Entry<String, Funcion> entry : funciones.entrySet()) {
                model.addElement(entry.getValue().getNombre());           //guardo la funcion en la lista
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al obtener las funciones" + e.toString());
        }
    }

    private void llamarDetalleFuncion(){
        Map<String, Funcion> funciones = new HashMap<String, Funcion>();

        try {
            funciones = Fabrica.getInstance().getIEspectaculo().obtenerFuncionesDeEspectaculo(cmbPlataforma.getSelectedItem().toString(), cmbEspectaculo.getSelectedItem().toString());
            Funcion funcion = funciones.get(listaFunciones.getSelectedValue());  //Guardo la funcion seleccionada buscando en la lista por su nombre
            JInternalFrame detalle = new DetalleFuncion("Detalle funcion", funcion);
            detalle.setIconifiable(true);
            detalle.setClosable(true);
            Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
            detalle.setVisible(true);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al llamar al detalle funcion" + e.toString());
        }
    }
}
