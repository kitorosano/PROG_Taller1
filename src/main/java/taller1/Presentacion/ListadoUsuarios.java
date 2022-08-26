package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.Usuario;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;
import java.util.Map;

public class ListadoUsuarios {
    private DefaultListModel<String> model = new DefaultListModel<String>();

    private JList listaUsuarios;
    private JPanel Panel;

    public JPanel getPanel() {
        return Panel;
    }

    public ListadoUsuarios(){
        listaUsuarios.setModel(model);
        //cargarLista();                    Hay que descomentar esto una vez esté finalizado el obtenerUsuario()
        model.addElement("prueba1");        //borrar esto (es para probar)
        model.addElement("prueba2");        //borrar esto (es para probar)
        model.addElement("prueba3");        //borrar esto (es para probar)
        listaUsuarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //DetalleUsuario();             Hay que descomentar esto una vez esté finalizado el DetalleUsuario()
            }
        });
    }

    private void cargarLista(){
        Map<String, Usuario> usuarios = new HashMap<String, Usuario>();

        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }

        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            model.addElement(entry.getValue().getNickname());
        }
    }
}
