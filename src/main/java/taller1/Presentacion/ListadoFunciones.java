package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class ListadoFunciones {
    private JPanel Panel;
    private JComboBox cmbPlataforma;
    private JComboBox cmbEspectaculo;
    private JList listaFunciones;

    private DefaultListModel<String> model = new DefaultListModel<String>();

    public ListadoFunciones(String title){
        /*
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        */
        cargarPlataformas();

        cmbPlataforma.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cargarEspectaculos();
            }
        });
        cmbEspectaculo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cargarFunciones();
            }
        });
        listaFunciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount()==2){
                    llamarDetalleFuncion();
                }
            }
        });
    }


    private void cargarPlataformas(){
        Map<String, Plataforma> plataformas = new HashMap<String, Plataforma>();

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

        try {
            //espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(cmbPlataforma.getSelectedItem().toString());
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
        try {
            //funciones = Fabrica.getInstance().getIEspectaculo().obtenerFuncionesDeEspectaculo(cmbPlataforma.getSelectedItem().toString(), cmbEspectaculo.getSelectedItem().toString());
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
            /*                  *DESCOMENTAR
            //funciones = Fabrica.getInstance().getIEspectaculo().obtenerFuncionesDeEspectaculo(cmbPlataforma.getSelectedItem().toString(), cmbEspectaculo.getSelectedItem().toString());
            //Espectaculo funcion = funciones.get(lista.getSelectedValue());  //Guardo la funcion seleccionada buscando en la lista por su nombre
            //JFrame detalle = new DetalleFuncion("Detalle funcion", funcion);
            //detalle.setVisible(true);
            */

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al llamar al detalle funcion" + e.toString());
        }
    }

}
