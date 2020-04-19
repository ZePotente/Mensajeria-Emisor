package Main;

import Modelo.RearmadorLista;

import java.util.ArrayList;

public class Prueba {
    public Prueba() {
        super();
    }

    public static void main(String[] args) {
        ArrayList<String[]> arr = new ArrayList<String[]>();
        arr = RearmadorLista.obtenerDestinatarios("ElPibe_IP1-ElPibe2_IP2");
        System.out.println(arr.size());
        if (arr.size() != 0) {
            for (String[] str : arr) {
                System.out.println("Nombre: " + str[0] + " " + "IP: " + str[1]);
            }
        }
        else
            System.out.println("Es null");
    }
}
