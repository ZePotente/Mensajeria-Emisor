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
                vista.limpiarCampos();
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
            vista.mostrarMensaje("Error al conectar con el directorio");
        }
    }
    
    private boolean verificarCamposCorrectos(String asunto, String descripcion, ArrayList<Usuario> destinatarios) {
        if (asunto == null || asunto.isEmpty()) {
            vista.mostrarMensaje("El asunto no debe estar vacio");
            return false;
        } else if (descripcion == null || descripcion.isEmpty()) {
            vista.mostrarMensaje("La descripcion no debe estar vacia");
            return false;
        } else if (destinatarios == null || destinatarios.isEmpty()) {
            vista.mostrarMensaje("Debe elegir algun destinatario de la lista");
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
                boolean envioExitoso = sistema.enviarMensaje(mensaje);
                String mensajeAMostrar = envioExitoso ? "Mensaje enviado con exito" : "Error al enviar el mensaje";
                vista.mostrarMensaje(mensajeAMostrar);
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
