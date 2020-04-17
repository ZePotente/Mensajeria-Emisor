package Modelo;

import Modelo.Mensaje.Mensaje;

import java.io.IOException;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Sistema extends Observable implements Observer {
    // clase
    private static Sistema instancia;
    private static final int NRO_PUERTO_RECEPTOR = 123, NRO_PUERTO_DIRECTORIO = 100;
    // instancia
    private Agenda agenda;
    private InternetManager internetManager;

    private Sistema() {
        agenda = new Agenda();
        internetManager = new InternetManager();
    }
    
    
    public void ingresarComoEmisor() {
        internetManager.addObserver(this);
    }
    
    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public void enviarMensaje(Mensaje mensaje) throws UnknownHostException, IOException {
        String mensajeString = mensaje.desarmar();
        internetManager.enviarMensaje(mensaje.getDestinatario().getNombre(),
                                                mensaje.getDestinatario().getNumeroDeIP(), NRO_PUERTO_RECEPTOR, mensajeString);
    }
    
    public void requestDestinatarios(String nroIP) throws IOException  {
        internetManager.requestDestinatarios(nroIP, NRO_PUERTO_RECEPTOR);
    }

    public ArrayList<Usuario> getDestinatarios() {
        ArrayList<Usuario> aux = null;
        try {
            aux = agenda.getDestinatarios();
        } catch (IOException e) {
            // error en la lectura del archivo de destinatarioss
            // sin funcionalidad
        }
        return aux;
    }
    
    // los tres metodos que siguen son para manejo de recepcion de mensajes en el IMR

    public void conexionExitosa() {
    }
    
    public void errorConexion(String error) {
    }

    public void finConexion() {
    }

    @Override
    public void update(Observable observable, Object object) {
        setChanged();
        notifyObservers(object);
    }
}

