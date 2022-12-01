package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.AltaEspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FormularioRegistroEspectadorAFuncion extends JInternalFrame {
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
    private JList listaRegistPrevios;

    private String plataformaSelect, espectaculoSelect;
    private String nombreFuncion;
    private Map<String, Funcion> funciones;
    private Map<String,Usuario>usuarios;

    private FuncionDTO funcionPredefinida;

    private int maximo;

    private Map<String, AltaEspectadorRegistradoAFuncionDTO> espectadores=new HashMap<>();

    private DefaultListModel<String> modelAInvitar = new DefaultListModel<String>();
    private DefaultListModel<String> modelInvitados = new DefaultListModel<String>();

    private DefaultListModel<String> modelRegistros = new DefaultListModel<String>();
    public FormularioRegistroEspectadorAFuncion(String title, FuncionDTO funcion){
        super(title);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        createUIComponents();
        funcionPredefinida=funcion;
        confirmarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Fabrica.getInstance().getIEspectadorRegistradoAFuncion().registrarEspectadoresAFunciones(espectadores);
                    JOptionPane.showMessageDialog(null,"Espectadores agregados");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
            }
        });
        cbPlataforma.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Map<String, EspectaculoDTO> espectaculos;
                if (e.getSource() == cbPlataforma && funcion==null) {
                    plataformaSelect = (String) cbPlataforma.getSelectedItem();
                    try{
                        cbEspectaculo.removeAllItems();
                        espectaculos = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(plataformaSelect);
                        for (EspectaculoDTO esp : espectaculos.values()) {
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
                    cargarDatosListas();
                }
            }
        });
        listaAinvitar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarListaRegistros();
            }
        });
        agregarEspectadorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(modelInvitados.getSize()>=maximo){
                    JOptionPane.showMessageDialog(null,"No se puede agregar, capacidad maxima alcanzada");
                }else{
                    AgregarEspectadorAMapa();
                    String invitado=modelAInvitar.getElementAt(listaAinvitar.getSelectedIndex());
                    modelInvitados.addElement(invitado);
                    modelAInvitar.remove(listaAinvitar.getSelectedIndex());
                }
            }
        });
        eliminarEspectadorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                espectadores.remove((String)listaInvitados.getSelectedValue());
                String invitadoE=modelInvitados.getElementAt(listaInvitados.getSelectedIndex());
                modelAInvitar.addElement(invitadoE);
                modelInvitados.remove(listaInvitados.getSelectedIndex());
            }
        });
        if(funcion==null){
            cargarDatosComboBox();
        }else{
            cbPlataforma.addItem(funcion.getEspectaculo().getPlataforma().getNombre());
            cbEspectaculo.addItem(funcion.getEspectaculo().getNombre());
            cargarTabla();
        }
        cancelarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }
    private void AgregarEspectadorAMapa(){
        Espectador nuevo;
        double costo;
        Funcion funcion=funciones.get(nombreFuncion+"-"+espectaculoSelect+"-"+plataformaSelect);
        Espectaculo espectaculo=funcion.getEspectaculo();
        usuarios=Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        nuevo= (Espectador) usuarios.get((String) listaAinvitar.getSelectedValue());
        if(listaRegistPrevios.getSelectedIndices().length == 3){
            costo=0;
        }else{
            costo=espectaculo.getCosto();
        }
        //FALTA VER COMO ACTUALIZAR LOS DATOS DE LOS ESPECTACULOS CANJEADOS
        AltaEspectadorRegistradoAFuncionDTO dto = new AltaEspectadorRegistradoAFuncionDTO();
        dto.setEspectador(nuevo.getNickname());
        dto.setFuncion(funcion.getNombre());
        dto.setEspectaculo(funcion.getEspectaculo().getNombre());
        dto.setPlataforma(funcion.getEspectaculo().getPlataforma().getNombre());
        dto.setPaquete(null);
        dto.setCanjeado(false);
        dto.setCosto(costo);
        dto.setFechaRegistro(LocalDateTime.now());
        espectadores.put(listaAinvitar.getSelectedValue().toString(), dto);
    }
    private void cargarDatosComboBox() {
        try {
            Map<String, Plataforma> plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
            for (Plataforma p : plataformas.values()) {
                cbPlataforma.addItem(p.getNombre());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createUIComponents() {
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        listaAinvitar.setModel(modelAInvitar);
        listaInvitados.setModel(modelInvitados);
        listaRegistPrevios.setModel(modelRegistros);
        listaRegistPrevios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void cargarTabla() {
        plataformaSelect = (String) cbPlataforma.getSelectedItem();
        espectaculoSelect = (String) cbEspectaculo.getSelectedItem();
        table1.setModel(new DefaultTableModel(null, new String[]{"Nombre", "Espectaculo","Fecha y Hora"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        if(funcionPredefinida==null) {
            try {
                funciones = Fabrica.getInstance().getIFuncion().obtenerFuncionesDeEspectaculo(plataformaSelect, espectaculoSelect);
                for (Map.Entry<String, Funcion> entry : funciones.entrySet()) {
                    model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getEspectaculo().getNombre(), entry.getValue().getFechaHoraInicio()});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }else{
            funciones = Fabrica.getInstance().getIFuncion().obtenerFuncionesDeEspectaculo(plataformaSelect, espectaculoSelect);
            model.addRow(new Object[]{funcionPredefinida.getNombre(),funcionPredefinida.getEspectaculo().getNombre(),funcionPredefinida.getFechaHoraInicio()});
        }
    }

    public void cargarDatosListas(){
        Map<String,Usuario>invitados;
        modelAInvitar.clear();
        modelInvitados.clear();
        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            invitados=Fabrica.getInstance().getIEspectadorRegistradoAFuncion().obtenerEspectadoresRegistradosAFuncion(nombreFuncion,espectaculoSelect,plataformaSelect);
            Espectaculo espectaculo=funciones.get(nombreFuncion+"-"+espectaculoSelect+"-"+plataformaSelect).getEspectaculo();
            maximo=espectaculo.getMaxEspectadores()-invitados.size();
            for(Usuario u:usuarios.values()){
                if(u instanceof Espectador && !invitados.containsKey(u.getNickname())){
                    modelAInvitar.addElement(u.getNickname());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
    }

    public void cargarListaRegistros(){
        Map<String,EspectadorRegistradoAFuncionDTO> registros;
        modelRegistros.clear();
        String invitado=modelAInvitar.getElementAt(listaAinvitar.getSelectedIndex());
        try {
            registros= Fabrica.getInstance().getIEspectadorRegistradoAFuncion().obtenerFuncionesRegistradasDelEspectador(invitado);
            for(EspectadorRegistradoAFuncionDTO esp: registros.values()){
                if(!esp.isCanjeado()){
                    modelRegistros.addElement(esp.getFuncion().getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
}