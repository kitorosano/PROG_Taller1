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

    private JPanel mainPanel;
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

     Usuario usuario;

    Map<String,EspectadorRegistradoAFuncion>funcionesRegistradasDelEspectador;

    Map<String,Espectaculo>espectaculosArtista;


    public  JPanel getMainPanel(){return mainPanel;}
    public DetalleUsuario(Usuario usuario){

       this.usuario=usuario;




        nicknameContenido.setText(usuario.getNickname());
        nombreContenido.setText(usuario.getNombre());
        apellidoContenido.setText(usuario.getApellido());
        correoContenido.setText(usuario.getCorreo());
        fechaNContenido.setText(usuario.getFechaNacimiento().toString());

        if (this.usuario instanceof Artista){
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
                String valor;

                if(e.getClickCount()==2){

                    valor=table1.getValueAt(table1.getSelectedRow(),0).toString();
                    System.out.println(valor);
                    if(usuario instanceof Artista){

                        DetalleEspectaculo.crearDetalleEspectaculo(espectaculosArtista.get(table1.getValueAt(table1.getSelectedRow(),0).toString()));
                    }
                    else{
                        DetalleFuncion.crearDetalleFuncion(funcionesRegistradasDelEspectador.get(table1.getValueAt(table1.getSelectedRow(),1).toString()).getFuncion());
                    }


                }
            }
        });
    }


    private void createUIComponents(int tipo) { //tipo 1:Artista , tipo 2:espectador
        // TODO: place custom component creation code here
            String[]espectaculo={"Nombre Espectaculo","Costo"};
            String[]funcion={"Nickname Espectador","Nombre Funcion"};
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

        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            this.funcionesRegistradasDelEspectador=Fabrica.getInstance().getIUsuario().obtenerFuncionesRegistradasDelEspectador(this.usuario.getNickname());
            LocalDateTime ldt = LocalDateTime.now();
            Funcion funcion1=new Funcion("Show_circo","circo",ldt,ldt);
            Espectador espectador = ((Espectador) this.usuario);
            EspectadorRegistradoAFuncion erf= new EspectadorRegistradoAFuncion( espectador, funcion1, true,2500,ldt);

            funcionesRegistradasDelEspectador.put(funcion1.getNombre(),erf);
            for (Map.Entry<String,EspectadorRegistradoAFuncion> entry :this.funcionesRegistradasDelEspectador.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getEspectador().getNickname(),entry.getValue().getFuncion().getNombre()});
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    private void cargarTablaArtista(){

        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            this.espectaculosArtista=Fabrica.getInstance().getIUsuario().obtenerEspectaculosArtista(this.usuario.getNickname());
            LocalDateTime ldt = LocalDateTime.now();
            Espectaculo circo= new Espectaculo("circo","muy buen espectaculo",2,4,10,"http//..",2500,ldt,"Plataforma","Duki");
            espectaculosArtista.put(circo.getNombre(),circo);

            for (Map.Entry<String, Espectaculo> entry : espectaculosArtista.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getCosto()});
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }
    public static void crearDetalleUsuario(Usuario usuario){


        DetalleUsuario detalleUsuario = new DetalleUsuario(usuario);
        JPanel rootPanel= detalleUsuario.getMainPanel();
        JFrame frame = new JFrame("Detalle Usuario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
