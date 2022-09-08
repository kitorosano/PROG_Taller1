package main.java.taller1.Presentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JPanel panelDashboard;

    private JMenuBar menuBar;
    private JMenu inicioJMenu;
    private JMenu altasJMenu;
    private JMenu registrosJMenu;
    private JMenu consultasJMenu;
    private JMenu agregarJMenu;
    private JMenu modificarJMenu;

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
    private JMenuItem modificarDatosDeUsuarioJMenuItem;
    private JDesktopPane dashboardJDesktopPane;





    public Dashboard(String title) {
        super(title);
        setContentPane(panelDashboard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();



        cargarDatosJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        cerrarJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        altaDeUsuarioJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        altaDeEspectaculoJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        altaDeFuncionJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        altaDePaqueteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        altaDePlataformaJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        consultaDeUsuarioJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        consultaDeEspectaculoJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        consultaDeFuncionJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        consultaDePaqueteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        registroAFuncionJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        agregarEspAPaqueteJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });

        modificarDatosDeUsuarioJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola mundo");
            }
        });
    }
}
