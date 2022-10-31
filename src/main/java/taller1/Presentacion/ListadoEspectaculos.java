package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Plataforma;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ListadoEspectaculos extends JInternalFrame {
    private JPanel Panel;
    private JComboBox cmbBox;
    private JList lista;
    private JLabel lblPlataforma;
    private JLabel lblEspectaculos;
    private JTextField txtEspectaculo;
    private JButton btnAlta;

    private DefaultListModel<String> model = new DefaultListModel<String>();

    public ListadoEspectaculos(String title){
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        cargarCmbBox();
        lista.setModel(model);
        setResizable(false);
        cargarLista();

        lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount()==2){
                    llamarDetalleEspectaculo();
                }
            }
        });
        cmbBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarLista();
            }
        });
        txtEspectaculo.addKeyListener(new KeyAdapter() {
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
                    lista.setModel(tmp);
                } else {//si esta vacio muestra el Model original
                    lista.setModel(model);
                }
            }
        });
        txtEspectaculo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtEspectaculo.setText("");
            }
        });
        txtEspectaculo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(txtEspectaculo.getText().equals("")) {
                    txtEspectaculo.setText("Buscar espectaculo...");
                }
            }
        });
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame alta = new FormularioEspectaculo("Alta espectaculo");
                alta.setIconifiable(true);
                alta.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(alta);
                alta.setVisible(true);
            }
        });
    }

    void cargarCmbBox(){
        Map<String, Plataforma> plataformas = new HashMap<String, Plataforma>();

        try {
            plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
            for (Map.Entry<String, Plataforma> entry : plataformas.entrySet()) {
                cmbBox.addItem(entry.getValue().getNombre());           //guardo la plataforma en el combo box
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al obtener las plataformas" + e.toString());
        }
    }

    private void cargarLista(){
        Map<String, Espectaculo> espectaculos = new HashMap<String, Espectaculo>();
        model.clear();
        try {
            espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(cmbBox.getSelectedItem().toString());
            for (Map.Entry<String, Espectaculo> entry : espectaculos.entrySet()) {
                model.addElement(entry.getValue().getNombre());
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al obtener los espectaculos" + e.toString());
        }
    }

    private void llamarDetalleEspectaculo(){
        Map<String, Espectaculo> espectaculos = new HashMap<String, Espectaculo>();

        try {
            espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(cmbBox.getSelectedItem().toString());
            Espectaculo espectaculo = espectaculos.get(lista.getSelectedValue());  //Guardo el espectaculo seleccionado buscando en la lista por su nickname
            JInternalFrame detalle = new DetalleEspectaculo("Detalle espectaculo", espectaculo);
            detalle.setIconifiable(true);
            detalle.setClosable(true);
            Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
            detalle.setVisible(true);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al llamar al detalle espectaculo" + e.toString());
        }
    }
}
