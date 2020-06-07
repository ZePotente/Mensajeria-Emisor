package modelo;

import modelo.mensaje.Mensaje;
import modelo.mensaje.MensajeAlarma;
import modelo.mensaje.MensajeRecepcion;
import modelo.mensaje.MensajeSimple;

import modelo.agenda.Usuario;

public class MensajesFactory {
    public MensajesFactory() {
        super();
    }
    
    
    public Mensaje crearMensaje(String tipo, String asunto, String cuerpo, Usuario destinatario, Usuario emisor) {
        if (tipo.equalsIgnoreCase("recepcion")) {
            return new MensajeRecepcion(asunto, cuerpo, destinatario, emisor);
        }
        if (tipo.equalsIgnoreCase("alarma")) {
            return new MensajeAlarma(asunto, cuerpo, destinatario, emisor);
        }
        if (tipo.equalsIgnoreCase("simple")) {
            return new MensajeSimple(asunto, cuerpo, destinatario, emisor);
        }
        return null;
    }
}
