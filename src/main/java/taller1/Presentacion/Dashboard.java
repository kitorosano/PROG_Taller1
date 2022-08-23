package main.java.taller1.Presentacion;

import javax.swing.*;

public class Dashboard extends JFrame {
    private JPanel panelDashboard;
    private JToolBar barraNavegacion;

    public Dashboard(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
