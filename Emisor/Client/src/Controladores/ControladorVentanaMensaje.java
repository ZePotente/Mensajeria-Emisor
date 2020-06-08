package controladores;

import excepciones.NoConexionException;

import modelo_e.mensaje.Mensaje;

import modelo_e.mensaje.MensajeAlarma;
import modelo_e.mensaje.MensajeRecepcion;
import modelo_e.mensaje.MensajeSimple;

import modelo_e.MensajesFactory;
import modelo_e.Sistema;
import modelo_e.agenda.Usuario;

import vistas.InterfazVistaMensaje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.net.InetAddress;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ControladorVentanaMensaje implements ActionListener, Observer {
    private InterfazVistaMensaje vista;
    private Sistema sistema;
    private MensajesFactory factoryMensajes = new MensajesFactory();

    public ControladorVentanaMensaje(InterfazVistaMensaje vista) {
        this.vista = vista;
        this.sistema = Sistema.getInstancia();
        sistema.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getActionCommand().equals(InterfazVistaMensaje.ENVIAR)) {
            String asunto = vista.getAsunto();
            String descripcion = vista.getDescripcion();
            ArrayList<Usuario> destinatarios = vista.getDestinatarios();
            if (verificarCamposCorrectos(asunto, descripcion, destinatarios)) {
                enviarMensaje(asunto,descripcion,destinatarios);
            }
        } else if (evento.getActionCommand().equals(InterfazVistaMensaje.ACTUALIZAR)) {
            actualizarlistaDestinatarios();
        }
    }
    
    public void actualizarlistaDestinatarios() {
        try {
            ArrayList<Usuario> destinatarios = sistema.requestDestinatarios();
            vista.actualizarListaDestinatarios(destinatarios);
        } catch (NoConexionException e) {
            vista.mostrarMensajeError("Error al conectar con el directorio");
        }
    }
    
    private boolean verificarCamposCorrectos(String asunto, String descripcion, ArrayList<Usuario> destinatarios) {
        if (asunto == null || asunto.isEmpty()) {
            vista.mostrarMensajeError("El asunto no debe estar vacio");
            return false;
        } else if (descripcion == null || descripcion.isEmpty()) {
            vista.mostrarMensajeError("La descripcion no debe estar vacia");
            return false;
        } else if (destinatarios == null || destinatarios.isEmpty()) {
            vista.mostrarMensajeError("Debe elegir algun destinatario de la lista");
            return false;
        } else {
            return true;
        }
    }

    private void enviarMensaje(String asunto, String cuerpo, ArrayList<Usuario> destinatarios) {
        for (Usuario destinatario: destinatarios) {
            Mensaje mensaje;
            mensaje = crearMensaje(asunto, cuerpo, destinatario);
            if (mensaje != null) {
                try {
                    sistema.enviarMensaje(mensaje);
                } catch (Exception e) {
                    vista.mostrarMensajeError("Error al enviar el mensaje");
                }
            } else {
                vista.mostrarMensajeError("Error al enviar el mensaje");
            }
        }
    }
    
    private Mensaje crearMensaje(String asunto, String cuerpo, Usuario destinatario) {
        Usuario emisor = sistema.getEmisor();
        return factoryMensajes.crearMensaje(vista.getTipoDeMensaje(), asunto, cuerpo, destinatario, emisor);
    }
    
    public ArrayList<Usuario> getDestinatarios() {
        return sistema.getDestinatarios();
    }

    @Override
    public void update(Observable observable, Object object) {
        String mensaje = (String) object;
        if (mensaje != null) {
            vista.agregarNotification(new Usuario(mensaje,"123"));
        }
    }
}
