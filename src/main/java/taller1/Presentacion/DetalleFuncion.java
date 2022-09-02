package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Espectador;
import main.java.taller1.Logica.Clases.EspectadorRegistradoAFuncion;
import main.java.taller1.Logica.Clases.Funcion;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class DetalleFuncion extends JFrame{
    private JPanel mainPanel;
    private JTextArea textArea1;
    private JTextArea nombreContenido;
    private JTextArea espectaculoContenido;
    private JTextArea fechaHoraDeInicioContenido;
    private JTextArea fechaDeRegistroContenido;
    private JTextArea espectaculoTextArea;
    private JTextArea fechaYHoraDeTextArea;
    private JTextArea fechaDeRegistroTextArea;
    private JTextArea detalleDeFuncionTextArea;
    private JTable table1;
    private JTextArea espectadoresTextArea;
    private JScrollPane scrollPane1;
    private JButton registrarEspectadoresButton;

    Funcion funcion;

    Map<String, EspectadorRegistradoAFuncion> espectadoresDeFuncion;
    public DetalleFuncion(String title,Funcion funcion){
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        this.funcion=funcion;
        nombreContenido.setText(this.funcion.getNombre());
        espectaculoContenido.setText(this.funcion.getEspectaculo().getNombre());
        fechaHoraDeInicioContenido.setText(this.funcion.getFechaHoraInicio().toString());
        fechaDeRegistroContenido.setText(this.funcion.getFechaRegistro().toString());
        createUIComponents();
        cargarTabla();
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Espectador espectador=espectadoresDeFuncion.get(table1.getValueAt(table1.getSelectedRow(), 0).toString()).getEspectador();
                    JFrame detalleEspectador= new DetalleUsuario("Detalle Usuario",espectador);
                }
            }
        });
        registrarEspectadoresButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame formularioRegistroEspectador = new FormularioRegistroEspectadorAFuncion("Formulario de registro de espectador a funcion",funcion);
            }
        });
    }
    public  JPanel getMainPanel(){return mainPanel;}

    public static void crearDetalleFuncion(String title, Funcion funcion){

        DetalleFuncion detalleFuncion = new DetalleFuncion("Detalle Funcion",funcion);
        JPanel rootPanel= detalleFuncion.getMainPanel();
        JFrame frame = new JFrame("Detalle Funcion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        table1.setModel(new DefaultTableModel(null, new String[]{"Nickname","Correo"}) {
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
            this.espectadoresDeFuncion = Fabrica.getInstance().getIUsuario().obtenerEspectadoresRegistradosAFuncion(this.funcion.getNombre());
            for (Map.Entry<String, EspectadorRegistradoAFuncion> entry : espectadoresDeFuncion.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getEspectador().getNickname(),entry.getValue().getEspectador().getCorreo()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
}



