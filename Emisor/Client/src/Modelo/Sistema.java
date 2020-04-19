package Modelo;

import Modelo.Mensaje.Mensaje;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Sistema extends Observable implements Observer {
    // clase
    private static Sistema instancia;
    private static final int NRO_PUERTO_RECEPTOR = 123, NRO_PUERTO_DIRECTORIO = 100;
    private static final String ARCHIVO_CONFIG = "configuracion.txt";
    // instancia
    private String NRO_IP_DIRECTORIO = "";
    private Agenda agenda;
    private InternetManager internetManager;
    private Usuario emisor;

    private Sistema() {
        agenda = new Agenda();
        internetManager = new InternetManager();
        /* //colocar donde corresponda
        try {
            leerConfig(ARCHIVO_CONFIG);
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer.");
        }
        */
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
                                                mensaje.getDestinatario().getNumeroDeIP(), NRO_PUERTO_RECEPTOR, mensajeString);
    }
    
    public void requestDestinatarios() throws IOException {
        String lista = null;
        try {
            lista = internetManager.requestDestinatarios(NRO_IP_DIRECTORIO, NRO_PUERTO_DIRECTORIO);
            agenda.actualizarDestinatarios(lista);
            System.out.println("La lista es la siguiente: " + lista);
            // llamar al que la rearme y que se actualice
        } catch (IOException e) {
            System.out.println("Hubo un error al actualizar");
            throw new IOException(e); //porque la captura el controlador, que no deberia
            // TODO Informar un error al actualizar
        }
    }
    /* //es el getDestinatariosViejo
    public ArrayList<Usuario> getDestinatarios() {
        ArrayList<Usuario> aux = null;
        try {
            aux = agenda.getDestinatariosArchivo();
        } catch (IOException e) {
            // error en la lectura del archivo de destinatarioss
            // sin funcionalidad
        }
        return aux;
    }
    */
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
    
    private void leerConfig(String nombreArch) throws FileNotFoundException {
        FileInputStream arch = new FileInputStream(Sistema.ARCHIVO_CONFIG);       
        Scanner sc = new Scanner(arch);    
        
        this.NRO_IP_DIRECTORIO = sc.nextLine(); 
        System.out.println(this.NRO_IP_DIRECTORIO);
        sc.close();
    }
}

