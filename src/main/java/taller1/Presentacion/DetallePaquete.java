package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Artista;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.Clases.Plataforma;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Map;

public class DetallePaquete extends JFrame{
    private JPanel mainPanel;
    private JTextArea nombreTextArea;
    private JTextArea descripcionTextArea;
    private JTextArea fechaDeExpiracionTextArea;
    private JTextArea descuentoTextArea;
    private JTextArea fechaDeRegistroTextArea;
    private JTextArea nombreContenido;
    private JTextArea descripcionContenido;
    private JTextArea fechaDeExpiracionContenido;
    private JTextArea descuentoContenido;
    private JTextArea fechaDeRegistroContenido;
    private JTable table1;
    private JTextArea detalleDePaqueteTextArea;
    private JTextArea espectaculosTextArea;

    Paquete paquete;

    Map<String, Espectaculo> EspectaculosPaquete;
    public  JPanel getMainPanel(){return mainPanel;}
    public DetallePaquete(String title,Paquete paquete){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        this.paquete=paquete;
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
                String valor;

                if (e.getClickCount() == 2) {
                    valor = table1.getValueAt(table1.getSelectedRow(), 0).toString();
                    Espectaculo espectaculo= EspectaculosPaquete.get(table1.getValueAt(table1.getSelectedRow(), 0).toString());
                    JFrame detalleEspectaculo= new DetalleEspectaculo("Detalle de espectaculo",espectaculo);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        table1.setModel(new DefaultTableModel(null,new String[]{"Nombre", "Descripcion", "Costo"} ) {
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
            this.EspectaculosPaquete = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPaquete(this.paquete.getNombre());
            this.EspectaculosPaquete.put("Show",new Espectaculo("Show", "descripcion del espectaculo",2500,2,3, "url",4000, LocalDateTime.now(),new Plataforma(),new Artista()));
            for (Map.Entry<String, Espectaculo> entry : this.EspectaculosPaquete.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getDescripcion(),entry.getValue().getCosto()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }

}
