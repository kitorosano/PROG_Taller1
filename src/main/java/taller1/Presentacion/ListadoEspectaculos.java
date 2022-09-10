package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;

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

    private DefaultListModel<String> model = new DefaultListModel<String>();

    public ListadoEspectaculos(String title){
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(400,500);
        cargarCmbBox();
        lista.setModel(model);
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
    }

    void cargarCmbBox(){
        Map<String, Plataforma> plataformas = new HashMap<String, Plataforma>();

        try {
            plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
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
            espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(cmbBox.getSelectedItem().toString());
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
            espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(cmbBox.getSelectedItem().toString());
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
