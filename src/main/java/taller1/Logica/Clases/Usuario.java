package main.java.taller1.Logica.Clases;

import java.time.LocalDate;

public class Usuario {
    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private LocalDate fechaNacimiento;

    public Usuario() {
    }

    public Usuario(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nickname='" + nickname + '\'' + ", nombre='" + nombre + '\'' + ", apellido='" + apellido + '\'' + ", correo='" + correo + '\'' + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}
