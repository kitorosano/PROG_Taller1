package main.java.taller1.Logica;

import main.java.taller1.Logica.Controladores.DatabaseController;
import main.java.taller1.Logica.Controladores.EspectaculoController;
import main.java.taller1.Logica.Controladores.UsuarioController;
import main.java.taller1.Logica.Interfaces.IDatabase;
import main.java.taller1.Logica.Interfaces.IEspectaculo;
import main.java.taller1.Logica.Interfaces.IUsuario;

public class Fabrica {
    private static Fabrica instance;
    private IUsuario usuario;
    private IEspectaculo espectaculo;
    private IDatabase database;

    private Fabrica() {}

    public static Fabrica getInstance() {
        if (instance == null) {
            instance = new Fabrica();
        }
        return instance;
    }

    public IUsuario getIUsuario() {
        UsuarioController usuarioController = UsuarioController.getInstance();
        return usuarioController;
    }

    public IEspectaculo getIEspectaculo() {
        EspectaculoController espectaculoController = EspectaculoController.getInstance();
        return espectaculoController;
    }
    
    public IDatabase getIDatabase() {
        DatabaseController databaseController = DatabaseController.getInstance();
        return databaseController;
    }
}

