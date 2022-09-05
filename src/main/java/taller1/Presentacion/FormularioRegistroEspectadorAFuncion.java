package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FormularioRegistroEspectadorAFuncion extends JFrame {
    private JPanel panel1;
    private JComboBox cbPlataforma;
    private JComboBox cbEspectaculo;
    private JTable table1;
    private JList listaAinvitar;
    private JList listaInvitados;
    private JButton agregarEspectadorButton;
    private JButton eliminarEspectadorButton;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JScrollPane tablaFunciones;

    private String plataformaSelect, espectaculoSelect, nombreFuncion;
    Map<String, Funcion> funciones;
    Map<String,Usuario>usuarios;

    private DefaultListModel<String> modelAInvitar = new DefaultListModel<String>();
    private DefaultListModel<String> modelInvitados = new DefaultListModel<String>();
    public FormularioRegistroEspectadorAFuncion(String title, Funcion funcion){
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        createUIComponents();
        cargarDatosComboBox();
        cargarDatosListas();
        confirmarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Fabrica.getInstance().getIEspectaculo().registrarEspectadoresAFunciones(AgregarEspectadores());
                    JOptionPane.showMessageDialog(null,"espectadores agregados");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cbPlataforma.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Map<String, Espectaculo> espectaculos;
                if (e.getSource() == cbPlataforma) {
                    plataformaSelect = (String) cbPlataforma.getSelectedItem();
                    try{
                        espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(plataformaSelect);
                        for (Espectaculo esp : espectaculos.values()) {
                            cbEspectaculo.addItem(esp.getNombre());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }
                }
            }
        });
        cbEspectaculo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == cbEspectaculo) {
                    cargarTabla();
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila;
                fila = table1.getSelectedRow();
                if (fila == -1) {
                    System.out.println("No se selecciono una fila");
                } else {
                    nombreFuncion = (String) table1.getValueAt(fila, 0);
                }
            }
        });
        agregarEspectadorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Espectaculo espectaculo=funciones.get(nombreFuncion).getEspectaculo();
                if(modelInvitados.capacity()>=espectaculo.getMaxEspectadores()){
                    JOptionPane.showMessageDialog(null,"No se puede agregar, capacidad maxima alcanzada");
                }else{
                    String invitado=modelAInvitar.getElementAt(listaAinvitar.getSelectedIndex());
                    modelInvitados.addElement(invitado);
                    modelAInvitar.remove(listaAinvitar.getSelectedIndex());
                }
            }
        });
        eliminarEspectadorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String invitadoE=modelInvitados.getElementAt(listaInvitados.getSelectedIndex());
                modelAInvitar.addElement(invitadoE);
                modelInvitados.remove(listaInvitados.getSelectedIndex());
            }
        });
    }

    private Map<String, EspectadorRegistradoAFuncion> AgregarEspectadores() {
        Map<String,EspectadorRegistradoAFuncion> espectadores=new HashMap<>();
        Espectador nuevo;
        Espectaculo espectaculo=funciones.get(nombreFuncion).getEspectaculo();
        Funcion funcion=funciones.get(nombreFuncion);
        usuarios=Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        for(int i=0; i<modelInvitados.getSize();i++){
            nuevo= (Espectador) usuarios.get(modelInvitados.get(i));
            espectadores.put(modelInvitados.get(i),new EspectadorRegistradoAFuncion(nuevo,funcion,false,espectaculo.getCosto(), LocalDateTime.now()));
        }
        return espectadores;
    }


    public void cargarDatosComboBox() {
        try {
            Map<String, Plataforma> plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
            for (Plataforma p : plataformas.values()) {
                cbPlataforma.addItem(p.getNombre());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createUIComponents() {
        table1.setModel(new DefaultTableModel(null, new String[]{"Nombre", "Espectaculo","Fecha y Hora"}) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
        });
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        listaAinvitar.setModel(modelAInvitar);
        listaInvitados.setModel(modelInvitados);
    }

    private void cargarTabla() {
        plataformaSelect = (String) cbPlataforma.getSelectedItem();
        espectaculoSelect = (String) cbEspectaculo.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            funciones=Fabrica.getInstance().getIEspectaculo().obtenerFuncionesDeEspectaculo(plataformaSelect, espectaculoSelect);
            for (Map.Entry<String, Funcion> entry : funciones.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getEspectaculo().getNombre(),entry.getValue().getFechaHoraInicio()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarDatosListas(){
        Map<String,EspectadorRegistradoAFuncion>invitados;
        modelAInvitar.clear();
        modelInvitados.clear();
        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            invitados=Fabrica.getInstance().getIUsuario().obtenerEspectadoresRegistradosAFuncion(nombreFuncion);
            for(EspectadorRegistradoAFuncion esp: invitados.values()){
                modelInvitados.addElement(esp.getEspectador().getNickname());
            }
            for(Usuario u:usuarios.values()){
                if(u instanceof Espectador && !modelInvitados.contains(u.getNickname())){
                    modelAInvitar.addElement(u.getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
    }
}
