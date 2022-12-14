package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Mappers.UsuarioMapper;

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

public class DetalleFuncion extends JInternalFrame {
    private JPanel mainPanel;
    private JLabel nombreContenido;
    private JLabel espectaculoContenido;
    private JLabel fechaHoraDeInicioContenido;
    private JLabel fechaDeRegistroContenido;
    private JLabel nombreDeEspectaculoLabel;
    private JLabel fechaYHoraDeLabel;
    private JLabel fechaDeRegistroLabel;
    private JTable table1;
    private JLabel espectadoresLabel;
    private JScrollPane scrollPane1;
    private JButton registrarEspectadoresButton;
    private JButton actualizarEspectadoresButton;
    private JLabel imagen;

    FuncionDTO funcion;
    Map<String, Usuario> espectadoresDeFuncion;

    public DetalleFuncion(String title, FuncionDTO funcion) {
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        this.funcion = funcion;

        nombreContenido.setText("Nombre: "+this.funcion.getNombre());
        espectaculoContenido.setText(this.funcion.getEspectaculo().getNombre());
        fechaHoraDeInicioContenido.setText(this.funcion.getFechaHoraInicio().toString());
        fechaDeRegistroContenido.setText(this.funcion.getFechaRegistro().toString());
        Image image = null;
        try {
            URL url = new URL(funcion.getImagen());
            image = ImageIO.read(url);
        }
        catch (IOException e) {
        }
        if(image != null) {
            Icon icon = new ImageIcon(image.getScaledInstance(150, 180, Image.SCALE_DEFAULT));
            imagen.setIcon(icon);
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: no se pudo obtener la imagen!");
        }
        createUIComponents();
        cargarTabla();

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Usuario espectador=espectadoresDeFuncion.get(table1.getValueAt(table1.getSelectedRow(), 0).toString());
                    UsuarioDTO espectadorDTO = UsuarioMapper.toDTO(espectador);
                    JInternalFrame detalle= new DetalleUsuario("Detalle Usuario",espectadorDTO);
                    detalle.setIconifiable(true);
                    detalle.setClosable(true);
                    Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
                    detalle.setVisible(true);
                }
            }
        });
        registrarEspectadoresButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JInternalFrame formularioRegistroEspectador = new FormularioRegistroEspectadorAFuncion("Formulario de registro de espectador a funcion",funcion);
                formularioRegistroEspectador.setClosable(true);
                formularioRegistroEspectador.setIconifiable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(formularioRegistroEspectador);
                formularioRegistroEspectador.setVisible(true);

            }
        });
        actualizarEspectadoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiartablaEspectadores();
                cargarTabla();
            }
        });
    }

    private void createUIComponents() {
        table1.setModel(new DefaultTableModel(null, new String[]{"Nickname", "Correo"}) {
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
            String nombreFuncion = funcion.getNombre();
            String nombreEspectaculo = funcion.getEspectaculo().getNombre();
            String nombrePlataforma = funcion.getEspectaculo().getPlataforma().getNombre();
            this.espectadoresDeFuncion = Fabrica.getInstance().getIEspectadorRegistradoAFuncion().obtenerEspectadoresRegistradosAFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion);

            for (Usuario entry : espectadoresDeFuncion.values()) {
                model.addRow(new Object[]{entry.getNickname(), entry.getCorreo()});
            }
        } catch (RuntimeException exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.getMessage());
        }
    }

    private void limpiartablaEspectadores() {
        DefaultTableModel temp = (DefaultTableModel) table1.getModel();
        int filas = table1.getRowCount();

        for (int a = 0; filas > a; a++) {
            temp.removeRow(0);
        }
    }
}

