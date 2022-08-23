package main.java.taller1.Logica.Clases;

public class ArtistasInvitados {
    private String nickname;
    private String nombreFuncion;

    public ArtistasInvitados() {
    }

    public ArtistasInvitados(String nickname, String nombreFuncion) {
        this.nickname = nickname;
        this.nombreFuncion = nombreFuncion;
    }

    public String getArtista() {
        return nickname;
    }

    public void setArtista(String nickname) {
        this.nickname = nickname;
    }

    public String getFuncion() {
        return nombreFuncion;
    }

    public void setFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
    }

    @Override
    public String toString() {
        return "ArtistasInvitados{" +
                "nickname=" + nickname +
                ", nombreFuncion=" + nombreFuncion +
                '}';
    }
}
