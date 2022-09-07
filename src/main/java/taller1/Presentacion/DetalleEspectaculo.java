package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Map;

public class DetalleEspectaculo extends JFrame {
    private JPanel mainPanel;
    private JTextArea nombreTextArea;
    private JTextArea descripcionTextArea;
    private JTextArea duracionTextArea;
    private JTextArea minEspectadoresTextArea;
    private JTextArea nombreContenido;
    private JTextArea descripcionContenido;
    private JTextArea duracionContenido;
    private JTextArea minEspectadoresContenido;
    private JTextArea maxEspectadoresTextArea;
    private JTextArea maxEspectadoresContenido;
    private JTextArea urlTextArea;
    private JTextArea urlContenido;
    private JTextArea costoTextArea;
    private JTextArea costoContenido;
    private JTextArea fechaDeRegistroTextArea;
    private JTextArea fechaDeRegistroContenido;
    private JTextArea plataformaTextArea;
    private JTextArea plataformaContenido;
    private JTextArea artistaOrganizadorTextArea;
    private JTextArea artistaOrganizadorContenido;
    private JTextArea funcionesDelEspectaculoTitulo;
    private JTextArea paquetesDelEspectaculoTitulo;
    private JButton agregarFuncionButton;
    private JTable tablaFunciones;
    private JTextArea detalleDelEspectaculoTitulo;
    private JTable tablaPaquetes;

    Map<String, Paquete> PaquetesDelEspectaculo;
    Map<String, Funcion> FuncionesDelEspectaculo;

    Espectaculo espectaculo;

    public DetalleEspectaculo(String title, Espectaculo espectaculo){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        this.espectaculo=espectaculo;

        nombreContenido.setText(this.espectaculo.getNombre());
        descripcionContenido.setText(this.espectaculo.getDescripcion());
        duracionContenido.setText(String.valueOf(this.espectaculo.getDuracion()));
        minEspectadoresContenido.setText(String.valueOf(this.espectaculo.getMinEspectadores()));
        maxEspectadoresContenido.setText(String.valueOf(this.espectaculo.getMaxEspectadores()));
        urlContenido.setText(this.espectaculo.getUrl());
        costoContenido.setText(String.valueOf(this.espectaculo.getCosto()));
        fechaDeRegistroContenido.setText(this.espectaculo.getFechaRegistro().toString());
        plataformaContenido.setText(this.espectaculo.getPlataforma().getNombre());
        artistaOrganizadorContenido.setText(this.espectaculo.getArtista().getNickname());
        plataformaContenido.setText(this.espectaculo.getPlataforma().getNombre().toString());
        createUIComponents();
        cargarTablaFunciones();
        cargarTablaPaquetes();
        tablaFunciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Funcion funcion=FuncionesDelEspectaculo.get(tablaFunciones.getValueAt(tablaFunciones.getSelectedRow(), 0).toString());
                    JFrame detalleFuncion = new DetalleFuncion("Detalle de funcion",funcion);
                    detalleFuncion.setVisible(true);
                }
            }
        });
        tablaPaquetes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Paquete paquete=PaquetesDelEspectaculo.get(tablaPaquetes.getValueAt(tablaPaquetes.getSelectedRow(), 0).toString());
                    JFrame detallePaquete = new DetallePaquete("Detalle de paquete",paquete);
                    detallePaquete.setVisible(true);
                }
            }
        });

        agregarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame formularioFuncion = new FormularioFuncion("Formulario de Funcion",espectaculo);
                formularioFuncion.setVisible(true);
            }
        });

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        tablaFunciones.setModel(new DefaultTableModel(null,new String[]{"Nombre", "Fecha y hora de inicio"} ) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        }
        );
        tablaFunciones.getTableHeader().setReorderingAllowed(false);
        tablaFunciones.getTableHeader().setResizingAllowed(false);

        tablaPaquetes.setModel(new DefaultTableModel(null,new String[]{"Nombre", "Fecha de expiracion"} ) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        }
        );
        tablaPaquetes.getTableHeader().setReorderingAllowed(false);
        tablaPaquetes.getTableHeader().setResizingAllowed(false);
    }
    private void cargarTablaFunciones() {

        DefaultTableModel model = (DefaultTableModel) tablaFunciones.getModel();
        try {
            this.FuncionesDelEspectaculo = Fabrica.getInstance().getIEspectaculo().obtenerFuncionesDeEspectaculo(this.espectaculo.getPlataforma().getNombre(),this.espectaculo.getNombre());

            for (Map.Entry<String, Funcion> entry : this.FuncionesDelEspectaculo.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(),entry.getValue().getFechaHoraInicio()});
            }
        } catch (Exception exc) {
           JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    private void cargarTablaPaquetes() {

        DefaultTableModel model = (DefaultTableModel) tablaPaquetes.getModel();
        try {
            this.PaquetesDelEspectaculo = Fabrica.getInstance().getIEspectaculo().obtenerPaquetesDeEspectaculo(this.espectaculo.getNombre());


            for (Map.Entry<String,Paquete> entry : this.PaquetesDelEspectaculo.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(),entry.getValue().getFechaExpiracion()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
}
