package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.E_EstadoEspectaculo;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.EspectaculoNuevoEstadoDTO;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AceptarRechazarEspectaculo extends  JInternalFrame {
    private JPanel mainPanel;
    private JTable tablaEspectaculos;
    private JButton rechazarButton;
    private JButton aceptarButton;
    private JButton button1;

    Map<String, EspectaculoDTO> espectaculosIngresados;
    public AceptarRechazarEspectaculo(String title){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        createUIComponents();
        cargarTablaEspectaculo();
        this.aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(tablaEspectaculos.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(mainPanel, "Error: ningun espectaculo seleccionado");
                }else{
                    EspectaculoDTO seleccionado=espectaculosIngresados.get(tablaEspectaculos.getValueAt(tablaEspectaculos.getSelectedRow(), 0).toString()+"-"+tablaEspectaculos.getValueAt(tablaEspectaculos.getSelectedRow(), 1).toString());
                    int respuesta = JOptionPane.showConfirmDialog(mainPanel,"Deseas aceptar el espectaculo?","Confirmar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==0){
                        try{
                            EspectaculoNuevoEstadoDTO espectaculoNuevoEstadoDTO = new EspectaculoNuevoEstadoDTO();
                            espectaculoNuevoEstadoDTO.setNombreEspectaculo(seleccionado.getNombre());
                            espectaculoNuevoEstadoDTO.setNombrePlataforma(seleccionado.getPlataforma().getNombre());
                            espectaculoNuevoEstadoDTO.setNuevoEstado(E_EstadoEspectaculo.ACEPTADO);
                            Fabrica.getInstance().getIEspectaculo().cambiarEstadoEspectaculo(espectaculoNuevoEstadoDTO);
                            JOptionPane.showMessageDialog(mainPanel,"Se acepto el espectaculo con exito!");
                            limpiartablaEspectaculos();
                            cargarTablaEspectaculo();
                            //dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(mainPanel,ex);
                        }
                    }
                    else if(respuesta==1){
                        JOptionPane.showMessageDialog(mainPanel, "Aviso: has cancelado la operacion");
                    }
                }


            }
        });

        this.rechazarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(tablaEspectaculos.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(mainPanel, "Error: ningun espectaculo seleccionado");
                }else {
                    EspectaculoDTO seleccionado=espectaculosIngresados.get(tablaEspectaculos.getValueAt(tablaEspectaculos.getSelectedRow(), 0).toString()+"-"+tablaEspectaculos.getValueAt(tablaEspectaculos.getSelectedRow(), 1).toString());
                    int respuesta = JOptionPane.showConfirmDialog(mainPanel, "Deseas rechazar el espectaculo?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta==0){
                        try{
                            EspectaculoNuevoEstadoDTO espectaculoNuevoEstadoDTO = new EspectaculoNuevoEstadoDTO();
                            espectaculoNuevoEstadoDTO.setNombreEspectaculo(seleccionado.getNombre());
                            espectaculoNuevoEstadoDTO.setNombrePlataforma(seleccionado.getPlataforma().getNombre());
                            espectaculoNuevoEstadoDTO.setNuevoEstado(E_EstadoEspectaculo.RECHAZADO);
                            Fabrica.getInstance().getIEspectaculo().cambiarEstadoEspectaculo(espectaculoNuevoEstadoDTO);
                            JOptionPane.showMessageDialog(mainPanel,"Se ha rechazado el espectaculo!");
                            limpiartablaEspectaculos();
                            cargarTablaEspectaculo();
                            //dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(mainPanel,ex);
                        }
                    }
                    else if(respuesta==1){
                        JOptionPane.showMessageDialog(mainPanel, "Aviso: has cancelado la operacion");
                    }
                }
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        tablaEspectaculos.setModel(new DefaultTableModel(null,new String[]{"Nombre", "Plataforma"} ) {
                                    @Override
                                    public boolean isCellEditable(int row, int column) {
                                        return false;
                                    }
                                }
        );
        tablaEspectaculos.getTableHeader().setReorderingAllowed(false);
        tablaEspectaculos.getTableHeader().setResizingAllowed(false);

    }

    private void cargarTablaEspectaculo() {

        DefaultTableModel model = (DefaultTableModel) tablaEspectaculos.getModel();
        try {
            this.espectaculosIngresados = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorEstado( E_EstadoEspectaculo.INGRESADO);

            for (EspectaculoDTO entry : this.espectaculosIngresados.values()) {
                model.addRow(new Object[]{entry.getNombre(),entry.getPlataforma().getNombre()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    public void limpiartablaEspectaculos() {
        DefaultTableModel temp = (DefaultTableModel) tablaEspectaculos.getModel();
        int filas = tablaEspectaculos.getRowCount();

        for (int a = 0; filas > a; a++) {
            temp.removeRow(0);
        }
    }
}
