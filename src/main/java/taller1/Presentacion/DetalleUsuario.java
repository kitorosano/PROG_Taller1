package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.EspectadorRegistradoAFuncionDTO;
import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.DTOs.UsuarioDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Mappers.FuncionMapper;

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
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DetalleUsuario extends JInternalFrame {

    private JPanel mainPanel;
    private JLabel nombreLabel;
    private JLabel apellidoLabel;
    private JLabel correoLabel;
    private JLabel fechaNacimientoLabel;
    private JLabel descripcionLabel;
    private JLabel nicknameContenido;
    private JLabel nombreContenido;
    private JLabel apellidoContenido;
    private JTable table1;
    private JLabel biografiaLabel;
    private JLabel correoContenido;
    private JLabel fechaNContenido;
    private JLabel descripcionContenido;
    private JLabel biografiaContenido;
    private JLabel sitioWebLabel;
    private JLabel sitioWebContenido;
    private JButton modificarUsuarioButton;
    private JLabel imagen;
    
    UsuarioDTO usuario;

    Map<String, EspectadorRegistradoAFuncion> funcionesRegistradasDelEspectador;

    Map<String, Espectaculo> espectaculosArtista;



    public DetalleUsuario(String title, UsuarioDTO usuario) {
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        this.usuario = usuario;
        
        nicknameContenido.setText(usuario.getNickname());
        nombreContenido.setText(usuario.getNombre());
        apellidoContenido.setText(usuario.getApellido());
        correoContenido.setText(usuario.getCorreo());
        fechaNContenido.setText(usuario.getFechaNacimiento().toString());
        Image image = null;
        try {
            URL url = new URL(usuario.getImagen());
            image = ImageIO.read(url);
        }
        catch (IOException e) {
        }
        if(image != null) {
            Icon icon = new ImageIcon(image.getScaledInstance(150, 170, Image.SCALE_DEFAULT));
            imagen.setIcon(icon);
        }
        else{
            JOptionPane.showMessageDialog(null, "Error: no se pudo obtener la imagen!");
        }
        if (this.usuario.isEsArtista()) {
            createUIComponents(1);
            cargarTablaArtista();
            descripcionContenido.setText(usuario.getDescripcion());
            biografiaContenido.setText(strToHtml(usuario.getBiografia()));
            sitioWebContenido.setText(usuario.getSitioWeb());

        } else {
            createUIComponents(2);
            cargarTablaEspectador();
            descripcionLabel.setEnabled(false);
            descripcionLabel.setVisible(false);
            descripcionContenido.setEnabled(false);
            descripcionContenido.setVisible(false);

            biografiaLabel.setEnabled(false);
            biografiaLabel.setVisible(false);
            biografiaContenido.setEnabled(false);
            biografiaContenido.setVisible(false);

            sitioWebLabel.setEnabled(false);
            sitioWebLabel.setVisible(false);
            sitioWebLabel.setEnabled(false);
            sitioWebContenido.setVisible(false);

        }

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2) {

                    if (usuario.isEsArtista()) {
                        String valor = table1.getValueAt(table1.getSelectedRow(), 0).toString();
                        String plataforma= table1.getValueAt(table1.getSelectedRow(), 2).toString();

                        Espectaculo espectaculo = espectaculosArtista.get(valor+"-"+plataforma);
                        System.out.println("el espectaculo es:"+espectaculo);
                        JInternalFrame detalleEspectaculo = new DetalleEspectaculo("Detalle Espectaculo", espectaculo);
                        detalleEspectaculo.setIconifiable(true);
                        detalleEspectaculo.setClosable(true);
                        Dashboard.getInstance().getDashboardJDesktopPane().add(detalleEspectaculo);
                        detalleEspectaculo.setVisible(true);
                    } else {
                        String nombre_funcion = table1.getValueAt(table1.getSelectedRow(), 0).toString();
                        String nombre_espectaculo = table1.getValueAt(table1.getSelectedRow(), 1).toString();
                        String nombre_plataforma = table1.getValueAt(table1.getSelectedRow(), 2).toString();

                        Funcion funcion = funcionesRegistradasDelEspectador.get(nombre_funcion+"-"+nombre_espectaculo+"-"+nombre_plataforma).getFuncion();

                        FuncionDTO funcionDTO = FuncionMapper.toDTO(funcion);
                        
                        JInternalFrame detalle = new DetalleFuncion("Detalle Funcion", funcionDTO);
                        detalle.setIconifiable(true);
                        detalle.setClosable(true);
                        Dashboard.getInstance().getDashboardJDesktopPane().add(detalle);
                        detalle.setVisible(true);
                    }


                }
            }
        });
        modificarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame modificar_usuario = new ModificarUsuario("Modificar Usuario", usuario);
                modificar_usuario.setIconifiable(true);
                modificar_usuario.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(modificar_usuario);
                modificar_usuario.setVisible(true);
            }
        });
    }


    private void createUIComponents(int tipo) { //tipo 1:Artista , tipo 2:espectador
        // TODO: place custom component creation code here
        String[] espectaculo = {"Nombre Espectaculo", "Costo","Plataforma"};
        String[] funcion = {"Funcion", "Espectaculo","Plataforma"};
        String[] titulos = {};
        if (tipo == 1) titulos = espectaculo;
        else titulos = funcion;
        table1.setModel(new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);

    }

    private void cargarTablaEspectador() {

        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            this.funcionesRegistradasDelEspectador = Fabrica.getInstance().getIEspectadorRegistradoAFuncion().obtenerFuncionesRegistradasDelEspectador(this.usuario.getNickname());
            for (Map.Entry<String, EspectadorRegistradoAFuncion> entry : this.funcionesRegistradasDelEspectador.entrySet()) {
                model.addRow(new Object[]{ entry.getValue().getFuncion().getNombre(),entry.getValue().getFuncion().getEspectaculo().getNombre(),entry.getValue().getFuncion().getEspectaculo().getPlataforma().getNombre()});
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }

    private void cargarTablaArtista() {

        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        try {
            this.espectaculosArtista = Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorArtista(this.usuario.getNickname());
            for (Map.Entry<String, Espectaculo> entry : espectaculosArtista.entrySet()) {
                model.addRow(new Object[]{entry.getValue().getNombre(), entry.getValue().getCosto(),entry.getValue().getPlataforma().getNombre()});
                System.out.println("claves del espectaculo cuando se agregan:"+entry.getValue().getNombre());
                System.out.println(" espectaculo cuando se agregan:"+entry.getValue().getNombre());
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error" + exc.toString());
        }
    }

    private String strToHtml(String text){
        return "<html><p>"+text +"</p></html>";
    }

}
