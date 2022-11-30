package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.EspectaculoDTO;
import main.java.taller1.Logica.DTOs.FuncionDTO;
import main.java.taller1.Logica.DTOs.PlataformaDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.*;
import main.java.taller1.Logica.Mappers.EspectaculoMapper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class FormularioFuncion extends JInternalFrame {
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
    private JTextField tfFecha; //TODO: Implementar JCalendar
    private JTextField tfHora;
    private JButton btnConsulta;
    private JButton seleccionarImagenButton;

    private String seleccionado;

    String imagen=null;

    public FormularioFuncion(String title, EspectaculoDTO elegido) {
        super(title);
        System.out.println(LocalDateTime.now());
        setContentPane(panel1);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        listaAinvitar.setModel(modelAInvitar);
        listaInvitados.setModel(modelInvitados);
        cargarDatosListas();

        agregarFuncionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!comprobarErrorencampos()){
                    try{
                        Fabrica.getInstance().getIFuncion().altaFuncion(crearFuncion());
                        JOptionPane.showMessageDialog(null,"Funcion creada con exito");
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }
                }
            }
        });
        if(elegido!=null){
            PlataformaDTO plataforma=elegido.getPlataforma();
            cbPlataforma.addItem(plataforma.getNombre());
            cbEspectaculo.addItem(elegido.getNombre());
            seleccionado=(String)cbPlataforma.getSelectedItem();
        }else{
            cargarDatosComboBox();
            cargarEspectaculos();
            cbPlataforma.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    cargarEspectaculos();
                }
            });
        }
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
        btnConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame alta = new ListadoFunciones("Listado funciones");
                alta.setIconifiable(true);
                alta.setClosable(true);
                Dashboard.getInstance().getDashboardJDesktopPane().add(alta);
                alta.setVisible(true);
            }
        });
        seleccionarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path;
                File file;
                JFileChooser j = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "imagenes JPG & PNG ", "jpg", "png");
                j.setFileFilter(filter);
                j.setAcceptAllFileFilterUsed(false);
                int retorno = j.showOpenDialog(panel1);

                if(retorno == JFileChooser.APPROVE_OPTION){
                    file= j.getSelectedFile();
                    try {
                        String res =null;
                        res = Fabrica.getInstance().getIDatabase().guardarImagen(file);
                        if(res.isEmpty()){
                            seleccionarImagenButton.setBackground(Color.red.darker());
                        }
                        else{
                            float[] hsvals;
                            seleccionarImagenButton.setBackground(Color.green.darker());
                        }
                        System.out.println(res);
                        imagen=res;
                    }
                    catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Error" + exc.toString());
                    }
                }
            }
        });
    }

    public void cargarDatosComboBox() {
        try{
            Map<String, Plataforma> plataformas = Fabrica.getInstance().getIPlataforma().obtenerPlataformas();
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
    public boolean comprobarErrorencampos(){                    //Devuelve true si hay error
        String plataforma=cbPlataforma.getSelectedItem().toString(),espectaculo=cbEspectaculo.getSelectedItem().toString();
        if(tfNombre.getText().isEmpty()||tfFecha.getText().isEmpty()||tfHora.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            return true;
        }
        if(!comprobarNombreUnico(plataforma,espectaculo,tfNombre.getText())){
            JOptionPane.showMessageDialog(null, "El nombre elegido ya existe");
            return true;
        }
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
    public boolean comprobarNombreUnico(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion) {       //Devuelve true si no hay error
        try {
            Map<String,Funcion> funciones = Fabrica.getInstance().getIFuncion().obtenerFuncionesDeEspectaculo(nombrePlataforma,nombreEspectaculo);
            for (Funcion fun : funciones.values()) {
                if (fun.getNombre().equals(nombreFuncion)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            return false;
        }
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
    public FuncionDTO crearFuncion(){
        String nombre=tfNombre.getText();
        LocalDateTime fechahora= LocalDateTime.of(LocalDate.parse(tfFecha.getText()), LocalTime.parse(tfHora.getText()));
        EspectaculoDTO espectaculo = null;
        try {
            Map<String,EspectaculoDTO> espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(seleccionado);
            for (EspectaculoDTO esp : espectaculos.values()) {
                if(esp.getNombre().equals((String)cbEspectaculo.getSelectedItem())){
                    espectaculo= esp;
                    break;
                }
            }
            if(imagen==null){
                imagen="https://i.imgur.com/EDotlnM.png";
            }
            FuncionDTO dto = new FuncionDTO();
            dto.setNombre(nombre);
            dto.setEspectaculo(espectaculo);
            dto.setFechaHoraInicio(fechahora);
            dto.setFechaRegistro(fechahora);
            dto.setImagen(imagen);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarEspectaculos(){
        seleccionado=(String)cbPlataforma.getSelectedItem();
        try{
            cbEspectaculo.removeAllItems();
            Map<String,EspectaculoDTO> espectaculos=Fabrica.getInstance().getIEspectaculo().obtenerEspectaculosPorPlataforma(seleccionado);
            for (EspectaculoDTO esp : espectaculos.values()) {
                    cbEspectaculo.addItem(esp.getNombre());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
}

