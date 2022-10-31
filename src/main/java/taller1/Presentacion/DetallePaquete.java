package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Paquete;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class DetallePaquete extends JInternalFrame {
    private JPanel mainPanel;
    private JLabel nombreLabel;
    private JLabel descripcionLabel;
    private JLabel fechaDeExpiracionLabel;
    private JLabel descuentoLabel;
    private JLabel fechaDeRegistroLabel;
    private JLabel nombreContenido;
    private JLabel descripcionContenido;
    private JLabel fechaDeExpiracionContenido;
    private JLabel descuentoContenido;
    private JLabel fechaDeRegistroContenido;
    private JTable table1;
    private JLabel espectaculosLabel;

    Paquete paquete;

    Map<String, Espectaculo> EspectaculosPaquete;

    public DetallePaquete(String title, Paquete paquete) {
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        this.paquete = paquete;

        nombreContenido.setText(paquete.getNombre());
        fechaDeExpiracionContenido.setText(paquete.getFechaExpiracion().toString());
        descripcionContenido.setText(paquete.getDescripcion());
        descuentoContenido.setText(String.valueOf(paquete.getDescuento()));
        fechaDeRegistroContenido.setText(paquete.getFechaRegistro().toString());
        createUIComponents();
        cargarTabla();
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String valor = table1.getValueAt(table1.getSelectedRow(), 0).toString();
                    Espectaculo espectaculo = EspectaculosPaquete.get(valor);

                    JInternalFrame detalleEspectaculo = new DetalleEspectaculo("Detalle de espectaculo", espectaculo);
                    detalleEspectaculo.setIconifiable(true);
                    detalleEspectaculo.setClosable(true);
                    Dashboard.getInstance().getDashboardJDesktopPane().add(detalleEspectaculo);
                    detalleEspectaculo.setVisible(true);
                }
            }
        });
    }

    private void createUIComponents() {
        table1.setModel(new DefaultTableModel(null, new String[]{"Nombre", "Descripcion", "Costo"}) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        }
        );
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            this.EspectaculosPaquete = Fabrica.getInstance().getIPaquete().obtenerEspectaculosDePaquete(this.paquete.getNombre());
            for (Map.Entry<String, Espectaculo> entry : this.EspectaculosPaquete.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getDescripcion(), entry.getValue().getCosto()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }

}
