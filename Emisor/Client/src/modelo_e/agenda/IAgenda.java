package modelo_e.agenda;

import java.util.ArrayList;

public interface IAgenda {
    public ArrayList<Usuario> getDestinatarios();
    public void actualizarDestinatarios(String lista);
}
