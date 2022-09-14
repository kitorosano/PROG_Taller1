package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ListadoPaquetes extends JInternalFrame{

    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JPanel Panel;
    private JList listaPaquetes;
    private JLabel lblPaquete;
    private JTextField txtPaquete;
    private JButton btnAlta;

    public ListadoPaquetes(String title) {
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        cargarLista();
        listaPaquetes.setModel(model);
        setResizable(false);

        listaPaquetes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount()==2){
                    llamarDetallePaquete();
                }
            }
        });
        txtPaquete.addKeyListener(new KeyAdapter() {
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
                    listaPaquetes.setModel(tmp);
                } else {//si esta vacio muestra el Model original
                    listaPaquetes.setModel(model);
                }
            }
        });

        txtPaquete.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                txtPaquete.setText("");
                listaPaquetes.setModel(model);
            }
        });
        txtPaquete.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(txtPaquete.getText().equals("")) {
                    txtPaquete.setText("Buscar paquete...");
                    listaPaquetes.setModel(model);
                }
            }
        });
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame alta = new FormularioPaquete("Alta paquete");
                alta.setIconifiable(true);
                alta.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(alta);
                alta.setVisible(true);
            }
        });
    }

    private void cargarLista() {
        Map<String, Paquete> paquetes = new HashMap<String, Paquete>();
        model.clear();
        try {
            paquetes = Fabrica.getInstance().getIEspectaculo().obtenerPaquetes();
        for (Map.Entry<String, Paquete> entry : paquetes.entrySet()) {
                model.addElement(entry.getValue().getNombre());           //guardo el paquete en la lista
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al obtener los paquetes" + e.toString());
        }
    }

    private void llamarDetallePaquete() {
        Map<String, Paquete> Paquetes = new HashMap<String, Paquete>();

        try {
            Paquetes = Fabrica.getInstance().getIEspectaculo().obtenerPaquetes();
            Paquete paquete = Paquetes.get(listaPaquetes.getSelectedValue());  //Guardo el paquete seleccionada buscando en la lista por su nombre
            JInternalFrame detalle = new DetallePaquete("Detalle paquete", paquete);
            detalle.setIconifiable(true);
            detalle.setClosable(true);
            Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
            detalle.setVisible(true);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al llamar al detalle paquete" + e.toString());
        }
    }
}
