package modelo_e.mensaje;

import excepciones.MensajeMalFormadoException;

import modelo_e.ArmableDesarmable;
import modelo_e.agenda.Usuario;

public class MensajeSimple extends Mensaje {
    public MensajeSimple(String asunto, String descripcion, Usuario destinatario, Usuario emisor) {
        super(asunto,descripcion,destinatario,emisor);
    }

    @Override
    public String desarmar() {
        return Mensaje.MENSAJE_SIMPLE + ArmableDesarmable.SEPARADOR +
               super.toString();
    }
}

