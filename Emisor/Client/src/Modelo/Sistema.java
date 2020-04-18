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
    private static final String NRO_IP_DIRECTORIO = "192.168.0.195";//leer desde archivo de configuracion
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
    
    public void requestDestinatarios() throws IOException {
        String lista = null;
        try {
            lista = internetManager.requestDestinatarios(NRO_IP_DIRECTORIO, NRO_PUERTO_DIRECTORIO);
            System.out.println("La lista es la siguiente: " + lista);
            // llamar al que la rearme y que se actualice
        } catch (IOException e) {
            System.out.println("Hubo un error al actualizar");
            throw new IOException(e); //porque la captura el controlador, que no deberia
            // TODO Informar un error al actualizar
        }
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

