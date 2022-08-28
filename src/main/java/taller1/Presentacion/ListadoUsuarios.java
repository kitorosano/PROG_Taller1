package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ListadoUsuarios extends JFrame {
    private DefaultListModel<String> model = new DefaultListModel<String>();

    private JList listaUsuarios;
    private JPanel Panel;

    public ListadoUsuarios(String title){
        super(title);
        setContentPane(Panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        listaUsuarios.setModel(model);
        cargarLista();
        listaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount()==2){
                    llamarDetalle();
                }
            }
        });
    }

    private void cargarLista(){
        Map<String, Usuario> usuarios = new HashMap<String, Usuario>();
        model.clear();
        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
                model.addElement(entry.getValue().getNickname());           //guardo el nickname en la lista
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
    }


    private void llamarDetalle(){
        Map<String, Usuario> usuarios = new HashMap<String, Usuario>();

        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            Usuario usuario = usuarios.get(listaUsuarios.getSelectedValue());  //Guardo el usuario seleccionado buscando en la lista por su nickname
            JFrame detalle = new DetalleUsuario("Detalle usuario", usuario);
            detalle.setVisible(true);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al llamar al detalle usuario" + e.toString());
        }
    }
}


