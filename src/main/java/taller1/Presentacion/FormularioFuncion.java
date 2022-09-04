package main.java.taller1.Presentacion;

import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Fabrica;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FormularioFuncion extends JFrame {
    private DefaultListModel<String> modelAInvitar = new DefaultListModel<String>();
    private DefaultListModel<String> modelInvitados = new DefaultListModel<String>();
    private JPanel panel1;
    private JComboBox cbPlataforma;
    private JComboBox cbEspectaculo;
    private JTextField tfNombre;
    private JList listaAinvitar;
    private JButton agregarArtistaButton;
    private JButton eliminarInvitadoButton;
    private JButton agregarFuncionButton;
    private JButton cancelarButton;
    private JList listaInvitados;
    private JTextField tfFecha;
    private JTextField tfHora;

    private String seleccionado;

    public FormularioFuncion(String title) {
        super(title);
        System.out.println(LocalDateTime.now());
        setContentPane(panel1);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        cargarDatosComboBox();
        listaAinvitar.setModel(modelAInvitar);
        listaInvitados.setModel(modelInvitados);
        cargarDatosListas();

        agregarFuncionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!comprobarErrorencampos()){
                    try{
                        //Fabrica.getInstance().getIEspectaculo().altaFuncion(crearFuncion());
                        Fabrica.getInstance().getIEspectaculo().altaFuncion("a","a",crearFuncion());
                        JOptionPane.showMessageDialog(null,"Funcion creada con exito");
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }
                }
            }
        });
        cbPlataforma.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource()==cbPlataforma) {
                    seleccionado=(String)cbPlataforma.getSelectedItem();
                    try{
                        //Map<String,Espectaculo> espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(seleccionado);
                        Map<String,Espectaculo> espectaculos = new HashMap<>();
                        for (Espectaculo esp : espectaculos.values()) {
                            cbEspectaculo.addItem(esp.getNombre());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }
                }
            }
        });
        agregarArtistaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String invitado=modelAInvitar.getElementAt(listaAinvitar.getSelectedIndex());
                modelInvitados.addElement(invitado);
                modelAInvitar.remove(listaAinvitar.getSelectedIndex());
            }
        });
        eliminarInvitadoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String invitado=modelInvitados.getElementAt(listaInvitados.getSelectedIndex());
                modelAInvitar.addElement(invitado);
                modelInvitados.remove(listaInvitados.getSelectedIndex());
            }
        });
        cancelarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

    public void cargarDatosComboBox() {
        try{
            Map<String, Plataforma> plataformas = Fabrica.getInstance().getIEspectaculo().obtenerPlataformas();
            for (Plataforma p : plataformas.values()) {
                cbPlataforma.addItem(p.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void cargarDatosListas(){
        Map<String,Usuario>usuarios;
        modelAInvitar.clear();
        try {
            usuarios = Fabrica.getInstance().getIUsuario().obtenerUsuarios();
            for(Usuario u:usuarios.values()){
                if(u instanceof Artista){
                    modelAInvitar.addElement(u.getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista" + e.toString());
        }
        //HAY QUE VER COMO CARGAR LA LISTA DE INVITADOS
    }
    public boolean comprobarErrorencampos(){
        if(tfNombre.getText().isEmpty()||tfFecha.getText().isEmpty()||tfHora.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            return true;
        }

        //TODO:COMPROBAR NOMBRE
        if(!validarFecha(tfFecha.getText())){
            JOptionPane.showMessageDialog(null, "El formato de fecha no es valido");
            return true;
        }
        if(!validarHora(tfHora.getText())){
            JOptionPane.showMessageDialog(null, "El formato de hora no es valido");
            return true;
        }

        return false;
    }

    public boolean validarFecha(String fecha) {             //Devuelve true si la fecha es correcta
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean validarHora(String hora) {             //Devuelve true si la fecha es correcta
        try {
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            formatoHora.setLenient(false);
            formatoHora.parse(hora);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public Funcion crearFuncion(){
        String nombre=tfNombre.getText();
        LocalDateTime fechahora= LocalDateTime.of(LocalDate.parse(tfFecha.getText()), LocalTime.parse(tfHora.getText()));
        Espectaculo espectaculo = null;
        try {
            //Map<String,Espectaculo> espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculos(seleccionado);
            Map<String, Espectaculo> espectaculos = new HashMap<>();
            for (Espectaculo esp : espectaculos.values()) {
                if(esp.getNombre().equals((String)cbEspectaculo.getSelectedItem())){
                    espectaculo=esp;
                    break;
                }
            }
            return new Funcion(nombre,espectaculo,fechahora,LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}