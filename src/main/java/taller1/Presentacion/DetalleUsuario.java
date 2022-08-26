package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DetalleUsuario{

    private JPanel panel1;
    private JTextArea nicknameTextArea;
    private JTextArea nombreTextArea;
    private JTextArea apellidoTextArea;
    private JTextArea correoTextArea;
    private JTextArea fechaNacimientoTextArea;
    private JTextArea descripcionTextArea;
    private JTextArea nicknameContenido;
    private JTextArea nombreContenido;
    private JTextArea apellidoContenido;
    private JTable table1;
    private JTextArea biografiaTextArea;
    private JTextArea correoContenido;
    private JTextArea fechaNContenido;
    private JTextArea descripcionContenido;
    private JTextArea biografiaContenido;
    private JTextArea sitioWebTextArea;
    private JTextArea sitioWebContenido;

    public  JPanel getMainPanel(){return panel1;}
    public DetalleUsuario(Usuario usuario){

        Map<String,Espectaculo>espectaculos=new HashMap<>();




        nicknameContenido.setText(usuario.getNickname());
        nombreContenido.setText(usuario.getNombre());
        apellidoContenido.setText(usuario.getApellido());
        correoContenido.setText(usuario.getCorreo());
        fechaNContenido.setText(usuario.getFechaNacimiento().toString());

        if (usuario instanceof Artista){
            createUIComponents(1);
            cargarTablaArtista();
            descripcionContenido.setText(((Artista) usuario).getDescripcion());
            biografiaContenido.setText(((Artista) usuario).getBiografia());
            sitioWebContenido.setText(((Artista) usuario).getSitioWeb());

        }
        else{
            createUIComponents(2);
            cargarTablaEspectador();
            descripcionTextArea.setEnabled(false);
            descripcionTextArea.setVisible(false);
            descripcionContenido.setEnabled(false);
            descripcionContenido.setVisible(false);

            biografiaTextArea.setEnabled(false);
            biografiaTextArea.setVisible(false);
            biografiaContenido.setEnabled(false);
            biografiaContenido.setVisible(false);

            sitioWebTextArea.setEnabled(false);
            sitioWebTextArea.setVisible(false);
            sitioWebTextArea.setEnabled(false);
            sitioWebContenido.setVisible(false);

        }

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount()==2){
                    JOptionPane.showMessageDialog(panel1,"contenido seleccionado: "+table1.getValueAt(table1.getSelectedRow(),0));
                }
            }
        });
    }


    private void createUIComponents(int tipo) { //tipo 1:Artista , tipo 2:espectador
        // TODO: place custom component creation code here
            String[]espectaculo={"Nombre Espectaculo","Costo"};
            String[]funcion={"Nombre Funcion","Espectaculo Asociado"};
            String[]titulos={};
            if(tipo==1)
                titulos=espectaculo;
            else
                titulos=funcion;
            table1.setModel(new DefaultTableModel(null, titulos){
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false;
                                }
                            }
            );
            table1.getTableHeader().setReorderingAllowed(false);
            table1.getTableHeader().setResizingAllowed(false);

    }
    private void cargarTablaEspectador(){
        //Fabrica fabrica=  Fabrica.getInstance();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        LocalDateTime ldt = LocalDateTime.now();
        Map<String,Funcion>funciones=new HashMap<>();
        Funcion funcion1=new Funcion("Show_circo","circo",ldt,ldt);
        funciones.put(funcion1.getNombre(),funcion1);

        for (Map.Entry<String,Funcion> entry : funciones.entrySet()) {
            model.addRow(new Object[]{entry.getValue().getNombre(),entry.getValue().getEspectaculoAsociado()});
        }
    }
    private void cargarTablaArtista(){
        Fabrica fabrica=  Fabrica.getInstance();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        LocalDateTime ldt = LocalDateTime.now();
        Map<String,Espectaculo>espectaculos=new HashMap<>();
        Espectaculo circo= new Espectaculo("circo","muy buen espectaculo",2,4,10,"http//..",2500,ldt,"Plataforma","Duki");
        espectaculos.put(circo.getNombre(),circo);

        for (Map.Entry<String, Espectaculo> entry : espectaculos.entrySet()) {
            model.addRow(new Object[]{entry.getValue().getNombre(),entry.getValue().getCosto()});
        }
    }
}
