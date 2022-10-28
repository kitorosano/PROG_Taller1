package main.java.taller1.Logica.Clases;

import java.time.LocalDate;

public class Espectador extends Usuario{

    public Espectador() {
    }
    public Espectador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String contrasenia, String imagen) {
        super(nickname, nombre, apellido, correo, fechaNacimiento, contrasenia, imagen);
    }
    
    @Override
    public String toString() {
        return "Espectador|" + super.toString();
    }
}
