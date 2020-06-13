package vistas;

import modelo_e.agenda.Usuario;

import java.util.ArrayList;

public interface InterfazVistaMensaje extends InterfazVista {
    static final String ENVIAR = "EnviarClickeado";
    static final String MENSAJESIMPLE = "Simple";
    static final String MENSAJEALARMA = "Alarma";
    static final String MENSAJERECEPCION = "Recepcion";
    static final String ACTUALIZAR = "ActualizarClickeado";
    
    public String getAsunto();
    public String getDescripcion();
    public ArrayList<Usuario> getDestinatarios();
    public String getTipoDeMensaje();
    public void agregarNotification(Usuario receptor);
    public void mostrarMensaje(String mensaje);
    public void actualizarListaDestinatarios(ArrayList<Usuario> destinatarios);
    public void limpiarCampos();
}
