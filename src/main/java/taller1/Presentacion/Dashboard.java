package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Persistencia.ConexionDB;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class Dashboard extends JFrame {
    private JPanel panelDashboard;
    private JDesktopPane dashboardJDesktopPane;

    private JMenuBar menuBar;
    private JMenu inicioJMenu;
    private JMenu altasJMenu;
    private JMenu consultasJMenu;
    private JMenu acercaDeJMenu;

    private JMenuItem cargarDatosJMenuItem;
    private JMenuItem cerrarJMenuItem;
    private JMenuItem altaDeUsuarioJMenuItem;
    private JMenuItem altaDeEspectaculoJMenuItem;
    private JMenuItem altaDeFuncionJMenuItem;
    private JMenuItem altaDePaqueteJMenuItem;
    private JMenuItem altaDePlataformaJMenuItem;
    private JMenuItem altaDeCategoriaJMenuItem;
    private JMenuItem consultaDeUsuarioJMenuItem;
    private JMenuItem consultaDeEspectaculoJMenuItem;
    private JMenuItem consultaDeFuncionJMenuItem;
    private JMenuItem consultaDePaqueteJMenuItem;
    private JMenuItem registroAFuncionJMenuItem;
    private JMenuItem agregarEspAPaqueteJMenuItem;
    private JMenuItem nosotrosJMenuItem;
    private JMenuItem githubJMenuItem;
    private JMenuItem registroTrabajoJMenuItem;
    private JMenuItem vaciarDatosJMenuItem;
    private JMenuItem aceptarRechazarEspectaculo;

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
        pack();

        /* INICIO*/
        vaciarDatosJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Fabrica.getInstance().getIDatabase().vaciarDatos();
                    JOptionPane.showMessageDialog(null, "Datos vaciados correctamente");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Error al vaciar los datos");
                }
            }
        });
        cargarDatosJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Fabrica.getInstance().getIDatabase().cargarDatos();
                    JOptionPane.showMessageDialog(null, "Datos cargados correctamente");
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        cerrarJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit( 0 );
            }
        });

        /* ALTAS*/
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
                iFrame.setSize(580,450);
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
                iFrame.setVisible(true);
            }
        });
        altaDeCategoriaJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new FormularioCategoria("Formulario de categoria");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                iFrame.setSize(500,120);
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

        aceptarRechazarEspectaculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame iFrame = new AceptarRechazarEspectaculo("Aceptar/Rechazar Espectaculo");
                iFrame.setIconifiable(true);
                iFrame.setClosable(true);
                iFrame.setSize(485,500);
                dashboardJDesktopPane.add(iFrame);
                iFrame.setVisible(true);
            }
        });

        /* CONSULTAS */
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

        /* ACERCA DE */
        nosotrosJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sistema de gestión de espectáculos y funciones desarrollado por el grupo 1\nde la materia Programación de Aplicaciones del 4to semestre de la carrera\nTecnólogo en Informatica de la UTEC.\n\n* Joaquin Maidana\n* Esteban Rosano\n* Sebastian Stelmaj\n* Paulo Ruiz", "Acerca de nosotros", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        githubJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/kitorosano/PROG_Taller1"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        registroTrabajoJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://docs.google.com/spreadsheets/d/1i7XLnFGHvmSshEgGcsPPKZafQhNJ_Iyd71NhYCn9I1E"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
