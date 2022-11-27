package main.java.taller1.Logica;

import main.java.taller1.Logica.Controladores.*;
import main.java.taller1.Logica.Interfaces.*;

public class Fabrica {
    private static Fabrica instance;

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
    
    public IFuncion getIFuncion() {
        FuncionController funcionController = FuncionController.getInstance();
        return funcionController;
    }
    
    
    public IEspectadorRegistradoAFuncion getIEspectadorRegistradoAFuncion() {
        EspectadorRegistradoAFuncionController espectadorRegistradoAFuncionController = EspectadorRegistradoAFuncionController.getInstance();
        return espectadorRegistradoAFuncionController;
    }
    
    public IPaquete getIPaquete() {
        PaqueteController paqueteController = PaqueteController.getInstance();
        return paqueteController;
    }
    
    public ICategoria getICategoria() {
        CategoriaController categoriaController = CategoriaController.getInstance();
        return categoriaController;
    }
    
    public IPlataforma getIPlataforma() {
        PlataformaController plataformaController = PlataformaController.getInstance();
        return plataformaController;
    }
    
    public IDatabase getIDatabase() {
        DatabaseController databaseController = DatabaseController.getInstance();
        return databaseController;
    }
}

