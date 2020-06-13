package modelo_e.persistencia;

import java.io.IOException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerPersistencia implements IVerificadorMensajesPendientes {
    private IPersistidor persistidor;
    
    public SchedulerPersistencia(IPersistidor persistidor) {
        this.persistidor = persistidor;
    }

    @Override
    public void ejecutar() {
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
            es.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            persistidor.recuperarDatos();
                        } catch (IOException e) {
                        }
                    }
                }, 
            0, 10, TimeUnit.SECONDS);
    }
}
