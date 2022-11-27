package main.java.taller1.Presentacion;

import main.java.taller1.Logica.DTOs.CategoriaDTO;
import main.java.taller1.Logica.Fabrica;
import main.java.taller1.Logica.Clases.Categoria;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class FormularioCategoria extends JInternalFrame {
  private JPanel panel1;
  private JTextField tfNombre;
  private JLabel lblNombre;
  private JButton btnIngresar;
  private JButton btnCancelar;
  
  public FormularioCategoria(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    pack();
    setContentPane(panel1);
    
    btnIngresar.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        String nombre = tfNombre.getText();
        
        if (camposInvalidos(nombre)) {
          JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio");
          return;
        }
        if (nombreExistente(nombre)) {
          JOptionPane.showMessageDialog(null, "El nombre ingresado ya existe");
          return;
        }
  
        try {
          Fabrica.getInstance().getICategoria().altaCategoria(new CategoriaDTO(nombre));
          JOptionPane.showMessageDialog(null, "Categoria agregada con exito");
          limpiarCampos();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex);
        }
      }
    });
    btnCancelar.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        dispose();
      }
    });
  }
  
  
  private boolean camposInvalidos(String nombre) {                //Devuelve false si es invalido aka son vacios
    return nombre.isEmpty();
  }
  
  private boolean nombreExistente(String nombre) {
    try {
      Optional<Categoria> categoria = Fabrica.getInstance().getICategoria().obtenerCategoria(nombre);
      return categoria.isPresent();
    } catch (Exception e) {
      return false;
    }
  }
  
  private void limpiarCampos() {
    tfNombre.setText("");
  }
  
}
