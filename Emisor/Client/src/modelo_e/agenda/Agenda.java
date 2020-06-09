package modelo_e.agenda;

import modelo_e.AgendaPersistencia;
import modelo_e.RearmadorLista;

import java.io.IOException;

import java.util.ArrayList;

public class Agenda implements IAgenda {
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
     * ArrayList con size() == 0 si no hay receptores en la lista.
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
            Usuario usuario = new Usuario(arrString[0], arrString[1]);
            usuario.setEstado(arrString[2].equalsIgnoreCase("true"));
            aux.add(usuario);
        }
        destinatarios = aux;
        return destinatarios;
    }
}
