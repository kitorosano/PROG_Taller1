package main.java.taller1.Logica.Clases;

import java.time.LocalDate;

public class Espectador extends Usuario{

    public Espectador() {
    }

    public Espectador(String nickname, String nombre, String correo, LocalDate fechaNacimiento) {
        super(nickname, nombre, correo, fechaNacimiento);
    }
}
