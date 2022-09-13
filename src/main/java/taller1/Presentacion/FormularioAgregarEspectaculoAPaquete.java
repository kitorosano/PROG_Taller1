package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class FormularioAgregarEspectaculoAPaquete extends JInternalFrame{
    private JPanel panel1;
    private JComboBox cbPaquete;
    private JComboBox cbPlataforma;
    private JList lstEspectAingresar;
    private JList lstEspectIngresados;
    private JButton cancelarButton;
    private JButton ingresarButton;
    private JButton agregarAlPaqueteButton;
    private JButton eliminarDelPaqueteButton;

    DefaultListModel<String> modelAAgregar = new DefaultListModel<String>();
    DefaultListModel<String> modelAgregados = new DefaultListModel<String>();

    Map<String,Espectaculo> espectaculosNuevos= new HashMap<>();
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
                    espectaculosNuevos.clear();
                    cargarDatosListaAIngresar();
                    cargarDatosListaIngresados();
                }
            }
        });
        agregarAlPaqueteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map<String, Espectaculo>espectaculos;
                String elegido;
                elegido=(String) lstEspectAingresar.getSelectedValue();
                espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos((String)cbPlataforma.getSelectedItem());
                Espectaculo nuevo = espectaculos.get(elegido);
                espectaculosNuevos.put(nuevo.getNombre(),nuevo);
                modelAgregados.addElement(elegido);
                modelAAgregar.remove(lstEspectAingresar.getSelectedIndex());
            }
        });
        eliminarDelPaqueteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                espectaculosNuevos.remove((String)lstEspectIngresados.getSelectedValue());
                modelAAgregar.addElement((String)lstEspectIngresados.getSelectedValue());
                modelAgregados.remove(lstEspectIngresados.getSelectedIndex());
            }
        });
        ingresarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //altaEspectaculosAPaquete(espectaculosNuevos, (String)cbPaquete.getSelectedItem());
                JOptionPane.showMessageDialog(null,"Espectadores agregados con exito");
                dispose();
            }
        });
        cancelarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
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
                espectaculos.put(esp.getNombre(),esp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }

    }
}
