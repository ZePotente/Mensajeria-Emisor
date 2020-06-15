package modelo_e.persistencia;

import java.io.IOException;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import modelo_e.mensaje.Mensaje;

public class SchedulerPersistencia implements IVerificadorMensajesPendientes {
    private IPersistidor persistidor;
    private EnvioMensajeDelegate delegado;
    
    public SchedulerPersistencia(IPersistidor persistidor, EnvioMensajeDelegate delegado) {
        this.persistidor = persistidor;
        this.delegado = delegado;
    }

    @Override
    public void ejecutar() {
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
            es.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Mensaje> mensajesPendientes = (ArrayList<Mensaje>) persistidor.recuperarDatos();
                        ArrayList<Mensaje> mensajesEnvioFallido = new ArrayList<>();
                        for (Mensaje mensaje: mensajesPendientes) {
                            if (!delegado.enviarMensaje(mensaje)) {
                                mensajesEnvioFallido.add(mensaje); //?
                            }
                        }
                    }
                }, 
            0, 15, TimeUnit.SECONDS);
    }
}
