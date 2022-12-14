package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.AltaEspectaculoAPaqueteDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;

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

    Map<String,EspectaculoDTO> espectaculosNuevos= new HashMap<>();
    public FormularioAgregarEspectaculoAPaquete(String title) {
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        lstEspectAingresar.setModel(modelAAgregar);
        lstEspectIngresados.setModel(modelAgregados);
        cargarDatosComboBox();
        cargarDatosListaIngresados();
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
//                    cargarDatosListaIngresados(); //Es necesario? Lo saque para podes agregar espectaculos de distintas plataformas en el mismo paquetes EN LA MISMA TRANSACCION
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
                Map<String, EspectaculoDTO>espectaculos;
                String elegido,plataforma;
                elegido=(String) lstEspectAingresar.getSelectedValue();
                plataforma=(String) cbPlataforma.getSelectedItem();
                espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(plataforma);
                EspectaculoDTO nuevo = espectaculos.get(elegido+"-"+plataforma);
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
                for (EspectaculoDTO espectaculo : espectaculosNuevos.values()) {
                    AltaEspectaculoAPaqueteDTO dto = new AltaEspectaculoAPaqueteDTO();
                    dto.setNombreEspectaculo(espectaculo.getNombre());
                    dto.setNombrePlataforma(espectaculo.getPlataforma().getNombre());
                    dto.setNombrePaquete((String)cbPaquete.getSelectedItem());
                    Fabrica.getInstance().getIPaquete().altaEspectaculoAPaquete(dto);
                }
                JOptionPane.showMessageDialog(null,"Espectaculos agregados con exito");
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
            Map<String, Plataforma> plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
            Map<String, Paquete> paquetes=Fabrica.getInstance().getIPaquete().obtenerPaquetes();
            for (Plataforma p : plataformas.values()) {
                cbPlataforma.addItem(p.getNombre());
            }
            for(Paquete paq: paquetes.values()){
                cbPaquete.addItem(paq.getNombre());
            }

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    public void cargarDatosListaAIngresar(){
        Map<String, EspectaculoDTO> espectaculos;
        modelAAgregar.clear();
        try {
            espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma((String)cbPlataforma.getSelectedItem());
            for (EspectaculoDTO esp : espectaculos.values()) {
                if(!modelAgregados.contains(esp.getNombre())) {
                    modelAAgregar.addElement(esp.getNombre());
                }
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.getMessage());
        }
    }

    public void cargarDatosListaIngresados(){
        Map<String, EspectaculoDTO>espectaculos;
        modelAgregados.clear();
        try {
            espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPaquete((String)cbPaquete.getSelectedItem());
            for (EspectaculoDTO esp : espectaculos.values()) {
                modelAgregados.addElement(esp.getNombre());
                if(modelAAgregar.contains(esp.getNombre())){
                    modelAAgregar.removeElement(esp.getNombre());
                }
                espectaculos.put(esp.getNombre(),esp);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.getMessage());
        }

    }
}
