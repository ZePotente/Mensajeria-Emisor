package Controladores;

import Excepciones.NoConexionException;

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

    public ControladorVentanaMensaje(InterfazVistaMensaje vista) {
        this.vista = vista;
        this.sistema = Sistema.getInstancia();
        sistema.addObserver(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getActionCommand().equals(InterfazVistaMensaje.ENVIAR)) {
            boolean usuarioOffline = false;
            String asunto = vista.getAsunto();
            String descripcion = vista.getDescripcion();
            ArrayList<Usuario> destinatarios = vista.getDestinatarios();
            for (Usuario destinatario: destinatarios) {
                if (!destinatario.getEstado()) {
                    usuarioOffline = true;
                    break;
                }
            }
            if (usuarioOffline) {
                vista.mostrarMensajeError("Alguno de los usuarios seleccionados esta offline. Por favor solo seleccione usuarios online.");
            } else {
                if (verificarCamposCorrectos(asunto, descripcion, destinatarios)) {
                    enviarMensaje(asunto,descripcion,destinatarios);
                }
            }
            
        } else if (evento.getActionCommand().equals(InterfazVistaMensaje.ACTUALIZAR)) {
            try {
                ArrayList<Usuario> destinatarios = sistema.requestDestinatarios();
            } catch (NoConexionException e) {
                vista.mostrarMensajeError("Error al conectar con el directorio");
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
