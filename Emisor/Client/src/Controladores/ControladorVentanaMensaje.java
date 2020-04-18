package Controladores;

import Modelo.Mensaje.Mensaje;

import Modelo.Mensaje.MensajeAlarma;
import Modelo.Mensaje.MensajeRecepcion;
import Modelo.Mensaje.MensajeSimple;

import Modelo.Sistema;
import Modelo.Usuario;

import Vista.InterfazVistaMensaje;

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
    private Usuario emisor;

    public ControladorVentanaMensaje(InterfazVistaMensaje vista) {
        this.vista = vista;
        this.sistema = Sistema.getInstancia();
        this.sistema.ingresarComoEmisor();
        sistema.addObserver(this);
        setEmisorIP();
        
    }
    
    private void setEmisorIP() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            emisor = new Usuario("Emisor", ip);
        } catch (UnknownHostException e) {
        }
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
            try {
                sistema.requestDestinatarios();
            } catch (IOException e) { // no deberia ser el controlador el que capture la excepcion
                vista.mostrarMensajeError(e.getMessage());
            }
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
            if (mensaje != null && emisor != null) {
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
        switch (vista.getTipoDeMensaje()) {
        case InterfazVistaMensaje.MENSAJESIMPLE:
            return new MensajeSimple(asunto, cuerpo, destinatario, emisor);
        case InterfazVistaMensaje.MENSAJEALARMA:
            return new MensajeAlarma(asunto, cuerpo, destinatario, emisor);
        case InterfazVistaMensaje.MENSAJERECEPCION:
            return new MensajeRecepcion(asunto, cuerpo, destinatario, emisor);
        }
        return null;
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
