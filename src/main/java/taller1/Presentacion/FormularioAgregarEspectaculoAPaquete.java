package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class FormularioAgregarEspectaculoAPaquete extends JFrame{
    private JPanel panel1;
    private JComboBox cbPaquete;
    private JComboBox cbPlataforma;
    private JList lstEspectAingresar;
    private JList lstEspectIngresados;
    private JButton cancelarButton;
    private JButton ingresarButton;

    DefaultListModel<String> modelAAgregar = new DefaultListModel<String>();
    DefaultListModel<String> modelAgregados = new DefaultListModel<String>();
    public FormularioAgregarEspectaculoAPaquete(String title) {
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        lstEspectAingresar.setModel(modelAAgregar);
        lstEspectIngresados.setModel(modelAgregados);
        cargarDatosComboBox();
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        cbPlataforma.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource()==cbPlataforma) {
                    cargarDatosListaAIngresar();
                    cargarDatosListaIngresados();
                }
            }
        });
        cbPaquete.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource()==cbPaquete) {
                    cargarDatosListaAIngresar();
                    cargarDatosListaIngresados();
                }
            }
        });
    }

    public void cargarDatosComboBox() {
        try{
            Map<String, Plataforma> plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
            Map<String, Paquete> paquetes=Fabrica.getInstance().getIEspectaculo().obtenerPaquetes();
            for (Plataforma p : plataformas.values()) {
                cbPlataforma.addItem(p.getNombre());
            }
            for(Paquete paq: paquetes.values()){
                cbPaquete.addItem(paq.getNombre());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void cargarDatosListaAIngresar(){
        Map<String, Espectaculo>espectaculos;
        modelAAgregar.clear();
        try {
            espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos((String)cbPlataforma.getSelectedItem());
            for (Espectaculo esp : espectaculos.values()) {
                if(!modelAgregados.contains(esp.getNombre())) {
                    modelAAgregar.addElement(esp.getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
    }

    public void cargarDatosListaIngresados(){
        Map<String, Espectaculo>espectaculos;
        modelAgregados.clear();
        try {
            espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosDePaquete((String)cbPaquete.getSelectedItem());
            for (Espectaculo esp : espectaculos.values()) {
                modelAgregados.addElement(esp.getNombre());
                if(modelAAgregar.contains(esp.getNombre())){
                    modelAAgregar.removeElement(esp.getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }

    }
}
