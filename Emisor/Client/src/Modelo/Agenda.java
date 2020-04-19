package Modelo;

import java.io.IOException;

import java.util.ArrayList;

public class Agenda {
    private AgendaPersistencia agendaPersistencia;
    private ArrayList<Usuario> destinatarios;

    public Agenda() {
        agendaPersistencia = new AgendaPersistencia();
    }

    public void actualizarDestinatariosArchivo() throws IOException {
        destinatarios = agendaPersistencia.obtenerDestinatarios();
    }

    /**
     * Metodo que devuelve un arraylist de los usuarios que haya conectados al momento de llamarlo.<br>
     * @return
     * ArrayList de usuarios si hay receptores en la lista.<br>
     * <i>null<i> si no los hay.
     */
    public ArrayList<Usuario> getDestinatarios() {
        return destinatarios;
    }

    /**
     * @param lista
     */
    public ArrayList<Usuario> actualizarDestinatarios(String lista) {
        ArrayList<Usuario> aux = new ArrayList<Usuario>();
        ArrayList<String[]> listaString = RearmadorLista.obtenerDestinatarios(lista);
        
        for (String[] arrString : listaString) {
            aux.add(new Usuario(arrString[0], arrString[1]));
        }
        destinatarios = aux;
        return destinatarios;
    }
}
