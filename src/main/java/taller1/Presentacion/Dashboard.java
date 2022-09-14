package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Dashboard extends JFrame {
    private JPanel panelDashboard;

    private JMenuBar menuBar;
    private JMenu inicioJMenu;
    private JMenu altasJMenu;
    private JMenu registrosJMenu;
    private JMenu consultasJMenu;
    private JMenu agregarJMenu;

    private JMenuItem cargarDatosJMenuItem;
    private JMenuItem cerrarJMenuItem;
    private JMenuItem altaDeUsuarioJMenuItem;

    private JMenuItem altaDeEspectaculoJMenuItem;
    private JMenuItem altaDeFuncionJMenuItem;
    private JMenuItem altaDePaqueteJMenuItem;
    private JMenuItem altaDePlataformaJMenuItem;
    private JMenuItem consultaDeUsuarioJMenuItem;
    private JMenuItem consultaDeEspectaculoJMenuItem;
    private JMenuItem consultaDeFuncionJMenuItem;
    private JMenuItem consultaDePaqueteJMenuItem;
    private JMenuItem registroAFuncionJMenuItem;
    private JMenuItem agregarEspAPaqueteJMenuItem;
    private JDesktopPane dashboardJDesktopPane;

    public JDesktopPane getDashboardJDesktopPane(){
        return this.dashboardJDesktopPane;
    }
    private static Dashboard instance=null;
    public static Dashboard getInstance() {
        if (instance == null) {
            instance = new Dashboard("Corona Tickets UY");
        }
        return instance;
    }

    public Dashboard(String title) {
        super(title);
        setContentPane(panelDashboard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(6);
        pack();



        cargarDatosJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //pendiente
            }
        });

        cerrarJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit( 0 );
            }
        });

        altaDeUsuarioJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioUsuario("Formulario de Usuario");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        altaDeEspectaculoJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioEspectaculo("Formulario de Espectaculo");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                iFrame.setSize(580,360);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        altaDeFuncionJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioFuncion("Formulario de funcion",null);
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        altaDePaqueteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioPaquete("Alta de paquete");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        altaDePlataformaJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioPlataforma("Formulario de plataforma");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                iFrame.setSize(500,310);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setSize(600,400);
                iFrame.setVisible(true);
            }
        });

        consultaDeUsuarioJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new ListadoUsuarios("Consulta de usuario");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        consultaDeEspectaculoJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new ListadoEspectaculos("Consulta de espectaculo");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        consultaDeFuncionJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new ListadoFunciones("Consulta de funcion");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        consultaDePaqueteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new ListadoPaquetes("Consulta de paquete");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        registroAFuncionJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioRegistroEspectadorAFuncion("Registro de espectador a funcion",null);
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        agregarEspAPaqueteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioAgregarEspectaculoAPaquete("Agregar espectaculo a paquete");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });


    }
    public void crearDetalleUsuario(Usuario usuario){
        //JInternalFrame detalle = new DetalleUsuario("Detalle usuario", new Usuario("nickname","nombre","apellido","correo", LocalDate.now()));
        JInternalFrame detalle = new DetalleUsuario("Detalle usuario",usuario);
        detalle.setIconifiable(true);
        detalle.setClosable(true);
        dashboardJDesktopPane.add(detalle);
        detalle.setVisible(true);
    }
}
