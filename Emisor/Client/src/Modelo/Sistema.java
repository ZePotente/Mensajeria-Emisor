package Modelo;

import Excepciones.NoConexionException;
import Excepciones.NoLecturaConfiguracionException;

import Modelo.Mensaje.Mensaje;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Sistema extends Observable implements Observer {
    // clase
    private static Sistema instancia;
    private static final int NRO_PUERTO_SERVIDORMENSAJES = 200, NRO_PUERTO_DIRECTORIO = 100;
    private static final String ARCHIVO_CONFIG = "configuracion.txt";
    // instancia
    private String NRO_IP_DIRECTORIO = "";
    private Agenda agenda;
    private InternetManager internetManager;
    private Usuario emisor;

    private Sistema() {
        agenda = new Agenda();
        internetManager = new InternetManager();
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
            instancia = new Sistema();
        }
        return instancia;
    }

    public void enviarMensaje(Mensaje mensaje) throws UnknownHostException, IOException {
        String mensajeString = mensaje.desarmar();
        internetManager.enviarMensaje(mensaje.getDestinatario().getNombre(),
                                                mensaje.getDestinatario().getNumeroDeIP(), NRO_PUERTO_SERVIDORMENSAJES, mensajeString);
    }
    
    public ArrayList<Usuario> requestDestinatarios() throws NoConexionException {
        try {
            String lista = lista = internetManager.requestDestinatarios(NRO_IP_DIRECTORIO, NRO_PUERTO_DIRECTORIO);
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

    /**
     * @param nombreArch
     * Lee el archivo de configuracion.txt
     * y asigna la IP leida a la variable local que la contiene.
     * 
     * @throws FileNotFoundException
     * Si ocurre un error con la lectura del archivo de configuracion.
     */
    //llamarlo como leerConfig(Sistema.ARCHIVO_CONFIG)
    private void leerConfig(String nombreArch) throws NoLecturaConfiguracionException {
        try {
            FileInputStream arch;
            arch = new FileInputStream(nombreArch);
            Scanner sc = new Scanner(arch);    
            
            this.NRO_IP_DIRECTORIO = sc.nextLine(); 
            sc.close();
        } catch (FileNotFoundException e) {
            throw new NoLecturaConfiguracionException(e);
        }  
    }
}

