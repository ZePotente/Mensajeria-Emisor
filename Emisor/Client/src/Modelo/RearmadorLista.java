package Modelo;

import java.util.ArrayList;

public class RearmadorLista {
    private static final String SEPARADOR_ATRIBUTOS = "_", SEPARADOR_USUARIOS = "-";
    
    public static ArrayList<String[]> obtenerDestinatarios(String lista) {
        ArrayList<String[]> arr = new ArrayList<String[]>();
        String[] sLista = separarDestinatarios(lista);
        int cant = sLista.length;
        for (int i = 0; i < cant; i++) {
            arr.add(sLista[i].split(SEPARADOR_ATRIBUTOS));
        }
        return arr;
    }
    
    private static String[] separarDestinatarios(String lista) {
        return lista.split(SEPARADOR_USUARIOS);
    }
}
