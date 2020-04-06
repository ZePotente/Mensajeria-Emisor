package Modelo;

import java.io.IOException;

import java.util.ArrayList;

public class Agenda {
    private AgendaPersistencia agendaPersistencia;
    private ArrayList<Usuario> destinatarios;

    public Agenda() {
        agendaPersistencia = new AgendaPersistencia();
    }

    public ArrayList<Usuario> getDestinatarios() throws IOException {
        destinatarios = agendaPersistencia.obtenerDestinatarios();
        return destinatarios;
    }
}
