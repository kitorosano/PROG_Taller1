package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Mappers.FuncionMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class DetalleEspectaculo extends JInternalFrame {
    private JPanel mainPanel;
    private JLabel descripcionLabel;
    private JLabel duracionLabel;
    private JLabel minEspectadoresLabel;
    private JLabel nombreContenido;
    private JLabel descripcionContenido;
    private JLabel duracionContenido;
    private JLabel minEspectadoresContenido;
    private JLabel maxEspectadoresLabel;
    private JLabel maxEspectadoresContenido;
    private JLabel urlLabel;
    private JLabel urlContenido;
    private JLabel costoLabel;
    private JLabel costoContenido;
    private JLabel fechaDeRegistroLabel;
    private JLabel fechaDeRegistroContenido;
    private JLabel nombreDePlataformaLabel;
    private JLabel plataformaContenido;
    private JLabel artistaOrganizadorLabel;
    private JLabel artistaOrganizadorContenido;
    private JLabel funcionesDelEspectaculoTitulo;
    private JLabel paquetesDelEspectaculoTitulo;
    private JButton agregarFuncionButton;
    private JTable tablaFunciones;
    private JTable tablaPaquetes;
    private JButton actualizarFuncionesButton;
    private JLabel imagen;
    private JTable tablaCategorias;
    private JScrollPane scrollPane3;

    Map<String, Paquete> PaquetesDelEspectaculo;
    Map<String, Funcion> FuncionesDelEspectaculo;

    Map<String,Categoria> categoriasDelEspectaculo;

    Espectaculo espectaculo;

    public DetalleEspectaculo(String title, Espectaculo espectaculo){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        this.espectaculo=espectaculo;
        Image image = null;
        try {
            URL url = new URL(this.espectaculo.getImagen());
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

        nombreContenido.setText("Nombre: "+this.espectaculo.getNombre());
        descripcionContenido.setText(this.espectaculo.getDescripcion());
        duracionContenido.setText(String.valueOf(this.espectaculo.getDuracion()));
        minEspectadoresContenido.setText(String.valueOf(this.espectaculo.getMinEspectadores()));
        maxEspectadoresContenido.setText(String.valueOf(this.espectaculo.getMaxEspectadores()));
        urlContenido.setText(strToHtml(this.espectaculo.getUrl()));
        costoContenido.setText(String.valueOf(this.espectaculo.getCosto()));
        fechaDeRegistroContenido.setText(this.espectaculo.getFechaRegistro().toString());
        plataformaContenido.setText(this.espectaculo.getPlataforma().getNombre());
        artistaOrganizadorContenido.setText(this.espectaculo.getArtista().getNickname());
        plataformaContenido.setText(this.espectaculo.getPlataforma().getNombre().toString());
        createUIComponents();
        cargarTablaFunciones();
        cargarTablaPaquetes();
        cargarTablaCategorias();
        tablaFunciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Funcion funcion=FuncionesDelEspectaculo.get(tablaFunciones.getValueAt(tablaFunciones.getSelectedRow(), 0).toString()+"-"+espectaculo.getNombre()+"-"+espectaculo.getPlataforma().getNombre());
                    System.out.println(FuncionesDelEspectaculo);
                    FuncionDTO funcionDTO = FuncionMapper.toDTO(funcion);
                    JInternalFrame detalle = new DetalleFuncion("Detalle de funcion",funcionDTO);
                    detalle.setIconifiable(true);
                    detalle.setClosable(true);
                    Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
                    detalle.setVisible(true);
                }
            }
        });
        tablaPaquetes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Paquete paquete=PaquetesDelEspectaculo.get(tablaPaquetes.getValueAt(tablaPaquetes.getSelectedRow(), 0).toString());
                    JInternalFrame detallePaquete = new DetallePaquete("Detalle de paquete",paquete);
                    detallePaquete.setIconifiable(true);
                    detallePaquete.setClosable(true);
                    Dashboard.getInstance().getDashboardJDesktopPane().add(detallePaquete);
                    detallePaquete.setVisible(true);
                }
            }
        });



        agregarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame formularioFuncion = new FormularioFuncion("Formulario de Funcion",espectaculo);
                formularioFuncion.setIconifiable(true);
                formularioFuncion.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(formularioFuncion);
                formularioFuncion.setVisible(true);
            }
        });

        actualizarFuncionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiartablaFunciones();
                cargarTablaFunciones();
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

        tablaCategorias.setModel(new DefaultTableModel(null,new String[]{"Nombre"} ) {
                                   @Override
                                   public boolean isCellEditable(int row, int column) {
                                       return false;
                                   }
                               }
        );
        tablaCategorias.getTableHeader().setReorderingAllowed(false);
        tablaCategorias.getTableHeader().setResizingAllowed(false);


    }
    private void cargarTablaFunciones() {

        DefaultTableModel model = (DefaultTableModel) tablaFunciones.getModel();
        try {
            this.FuncionesDelEspectaculo = Fabrica.getInstance().getIFuncion().obtenerFuncionesDeEspectaculo(this.espectaculo.getPlataforma().getNombre(),this.espectaculo.getNombre());

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
            this.PaquetesDelEspectaculo = Fabrica.getInstance().getIPaquete().obtenerPaquetesDeEspectaculo(this.espectaculo.getNombre(), this.espectaculo.getPlataforma().getNombre());


            for (Map.Entry<String,Paquete> entry : this.PaquetesDelEspectaculo.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(),entry.getValue().getFechaExpiracion()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    private void cargarTablaCategorias() {

        DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();
        try {
            this.categoriasDelEspectaculo = Fabrica.getInstance().getICategoria().obtenerCategoriasDeEspectaculo(espectaculo.getNombre());

            for (Map.Entry<String,Categoria> entry : this.categoriasDelEspectaculo.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    public void limpiartablaFunciones() {
        DefaultTableModel temp = (DefaultTableModel) tablaFunciones.getModel();
        int filas = tablaFunciones.getRowCount();

        for (int a = 0; filas > a; a++) {
            temp.removeRow(0);
        }
    }
    private String strToHtml(String text){
        return "<html><p>"+text +"</p></html>";
    }
}
