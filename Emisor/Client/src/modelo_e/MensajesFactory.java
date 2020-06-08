package modelo_e;

import modelo_e.mensaje.Mensaje;
import modelo_e.mensaje.MensajeAlarma;
import modelo_e.mensaje.MensajeRecepcion;
import modelo_e.mensaje.MensajeSimple;

import modelo_e.agenda.Usuario;

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
