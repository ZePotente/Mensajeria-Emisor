package Modelo;

import Excepciones.NoConexionException;
import Excepciones.NoLecturaConfiguracionException;

import Modelo.Mensaje.Mensaje;

import Modelo.agenda.Agenda;
import Modelo.agenda.Usuario;

import configuracion.Configuracion;
import configuracion.LectorConfiguracion;

import java.io.IOException;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Sistema extends Observable implements Observer {
    // clase
    private static Sistema instancia;
    private static final int NRO_PUERTO_DIRECTORIO = 100,
                             NRO_PUERTO_SERVIDORMENSAJES = 200,
                             NRO_PUERTO_NOTIFICACION_RECEPCION = 300;
    private static final String ARCHIVO_CONFIG = "configuracion.txt";
    // instancia
    private Configuracion config;
    private Agenda agenda;
    private InternetManager internetManager;
    private ServerRecepcion sv;
    private Usuario emisor;
    
    private Sistema() throws NoLecturaConfiguracionException, IOException {
        config = LectorConfiguracion.leerConfig(Sistema.ARCHIVO_CONFIG);
        agenda = new Agenda();
        internetManager = new InternetManager();
        sv = new ServerRecepcion(Sistema.NRO_PUERTO_NOTIFICACION_RECEPCION);
    }
    
    
    public void ingresarComoEmisor(Usuario usuario) {
        emisor = usuario;
        internetManager.addObserver(this);
    }
    
    public Usuario getEmisor() {
        return emisor;
    }
    
    public static Sistema getInstancia() {
        if (instancia == null) {
            try {
                instancia = new Sistema();
            } catch (NoLecturaConfiguracionException e) {
                //esto no deberia ir aca, pero bueno.
                System.out.println("Por favor reiniciar la aplicacion, error al leer el archivo de configuracion.");
            } catch (IOException e) {
                System.out.println("Por favor reiniciar la aplicacion, error al crear el servidor de recepcion de notificaciones.");
            }
        }
        return instancia;
    }

    public void enviarMensaje(Mensaje mensaje) throws UnknownHostException, IOException {
        String mensajeString = mensaje.desarmar();
        internetManager.enviarMensaje(this.config.getIPSvMensajes(),
                                      mensaje.getDestinatario().getNombre(),
                                      mensaje.getDestinatario().getNumeroDeIP(),
                                      NRO_PUERTO_SERVIDORMENSAJES, mensajeString);
    }
    
    public ArrayList<Usuario> requestDestinatarios() throws NoConexionException {
        try {
            System.out.println(this.config.getNroIPDirectorio());
            String lista = lista = internetManager.requestDestinatarios(this.config.getNroIPDirectorio(), NRO_PUERTO_DIRECTORIO);
            ArrayList<Usuario> destinatarios = agenda.actualizarDestinatarios(lista);
            return destinatarios;
            // llamar al que la rearme y que se actualice
        } catch (IOException e) {
            throw new NoConexionException(e); //porque la captura el controlador, que no deberia
        }
    }
    
    public ArrayList<Usuario> getDestinatarios() {
        return agenda.getDestinatarios();
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

