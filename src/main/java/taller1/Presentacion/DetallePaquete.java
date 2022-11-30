package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Categoria;
import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Espectaculo;
import main.java.taller1.Logica.Clases.Paquete;
import main.java.taller1.Logica.Mappers.EspectaculoMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DetallePaquete extends JInternalFrame {
    private JPanel mainPanel;
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
    private JTable tablaCategoria;
    private JScrollPane scrollpaneCat;
    private JLabel imagen;

    Paquete paquete;

    Map<String, Espectaculo> EspectaculosPaquete;

    Map<String, Categoria> categoriasPaquete = new HashMap<>();

    public DetallePaquete(String title, Paquete paquete) {
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        this.paquete = paquete;
        Image image = null;
        try {
            URL url = new URL(paquete.getImagen());
            System.out.println(url);
            image = ImageIO.read(url);
        }
        catch (IOException e) {
        }
        if(image != null) {
            Icon icon = new ImageIcon(image.getScaledInstance(150, 170, Image.SCALE_DEFAULT));
            imagen.setText("");
            imagen.setIcon(icon);
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: no se pudo obtener la imagen!");
            imagen.setText("Imagen del espectaculo");
        }

        nombreContenido.setText("Nombre: "+paquete.getNombre());
        fechaDeExpiracionContenido.setText(paquete.getFechaExpiracion().toString());
        descripcionContenido.setText(paquete.getDescripcion());
        descuentoContenido.setText(String.valueOf(paquete.getDescuento()));
        fechaDeRegistroContenido.setText(paquete.getFechaRegistro().toString());
        createUIComponents();
        cargarTabla();
        cargarTablaCategorias();
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String valor = table1.getValueAt(table1.getSelectedRow(), 0).toString()+"-"+table1.getValueAt(table1.getSelectedRow(), 1).toString();
                    Espectaculo espectaculo = EspectaculosPaquete.get(valor);
                    EspectaculoDTO dto = EspectaculoMapper.toDTO(espectaculo);
                    JInternalFrame detalleEspectaculo = new DetalleEspectaculo("Detalle de espectaculo", dto);
                    detalleEspectaculo.setIconifiable(true);
                    detalleEspectaculo.setClosable(true);
                    detalleEspectaculo.setSize(1260,700);
                    Dashboard.getInstance().getDashboardJDesktopPane().add(detalleEspectaculo);
                    detalleEspectaculo.setVisible(true);
                }
            }
        });
    }

    private void createUIComponents() {
        table1.setModel(new DefaultTableModel(null, new String[]{"Nombre", "Plataforma", "Costo"}) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        }
        );
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);

        tablaCategoria.setModel(new DefaultTableModel(null, new String[]{"Nombre"}) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        }
        );
        tablaCategoria.getTableHeader().setReorderingAllowed(false);
        tablaCategoria.getTableHeader().setResizingAllowed(false);
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            this.EspectaculosPaquete = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPaquete(this.paquete.getNombre());
            for (Map.Entry<String, Espectaculo> entry : this.EspectaculosPaquete.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getPlataforma().getNombre(), entry.getValue().getCosto()});//se carga la lista de categorias mientras recorro los espectaculos
                try {
                    Map<String, Categoria> categoriasdelEspectaulo = Fabrica.getInstance().getICategoria().obtenerCategoriasDeEspectaculo(entry.getValue().getNombre());
                    for (Map.Entry<String, Categoria> entry2 : categoriasdelEspectaulo.entrySet()) {
                        categoriasPaquete.put(entry2.getValue().getNombre(),entry2.getValue());
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Error" + exc.toString());
                }
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    private void cargarTablaCategorias(){
        DefaultTableModel model = (DefaultTableModel) tablaCategoria.getModel();
        try {
            for (Map.Entry<String, Categoria> entry : this.categoriasPaquete.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }

}
