package modelo_e;

import Excepciones.NoConexionException;
import Excepciones.NoLecturaConfiguracionException;

import modelo_e.mensaje.Mensaje;

import modelo_e.agenda.Agenda;
import modelo_e.agenda.Usuario;

import configuracion.Configuracion;
import configuracion.LectorConfiguracion;

import java.io.IOException;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import modelo_e.persistencia.EnvioMensajeDelegate;
import modelo_e.persistencia.IPersistidor;
import modelo_e.persistencia.IVerificadorMensajesPendientes;
import modelo_e.persistencia.PersistenciaMensaje;
import modelo_e.persistencia.SchedulerPersistencia;

public class Sistema extends Observable implements Observer, ILoginAuthenticator, EnvioMensajeDelegate {
    private static Sistema instancia;
    private static final int NRO_PUERTO_DIRECTORIO = 100,
                             NRO_PUERTO_SERVIDORMENSAJES = 200,
                             NRO_PUERTO_NOTIFICACION_RECEPCION = 300;
    private static final String ARCHIVO_CONFIG = "configuracion.txt";
    private Configuracion config;
    private Agenda agenda;
    private InternetManager internetManager;
    private ServerRecepcion sv;
    private Usuario emisor;
    private IVerificadorMensajesPendientes verificadorMensajesPendientes;
    private IPersistidor persistidor;
    
    private Sistema() throws NoLecturaConfiguracionException, IOException {
        config = LectorConfiguracion.leerConfig(Sistema.ARCHIVO_CONFIG);
        agenda = new Agenda();
        internetManager = new InternetManager();
        sv = new ServerRecepcion(Sistema.NRO_PUERTO_NOTIFICACION_RECEPCION);
        persistidor = new PersistenciaMensaje();
        verificadorMensajesPendientes = new SchedulerPersistencia(persistidor, this);
    }
    
    public void ingresar(Usuario usuario) {
        emisor = usuario;
        internetManager.addObserver(this);
        verificadorMensajesPendientes.ejecutar();
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

    public boolean enviarMensaje(Mensaje mensaje) {
        String mensajeString = mensaje.desarmar();
         if (!internetManager.enviarMensaje(this.config.getIPSvMensajes(),
                                            mensaje.getDestinatario().getNombre(),
                                            mensaje.getDestinatario().getNumeroDeIP(),
                                            NRO_PUERTO_SERVIDORMENSAJES,
                                            mensajeString)) {
                                                persistidor.guardarDatos(mensaje);
                                                return false;
         } else {
             return true;
         }
    }
    
    public ArrayList<Usuario> requestDestinatarios() throws NoConexionException {
        try {
            System.out.println(this.config.getNroIPDirectorio());
            String lista = lista = internetManager.requestDestinatarios(this.config.getNroIPDirectorio(), NRO_PUERTO_DIRECTORIO);
            ArrayList<Usuario> destinatarios = agenda.actualizarDestinatarios(lista);
            return destinatarios;
        } catch (IOException e) {
            throw new NoConexionException(e);
        }
    }
    
    public ArrayList<Usuario> getDestinatarios() {
        return agenda.getDestinatarios();
    }

    @Override
    public void update(Observable observable, Object object) {
        setChanged();
        notifyObservers(object);
    }
}
