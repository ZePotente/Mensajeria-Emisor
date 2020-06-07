package main;

import controladores.ControladorVentanaIngresaNombre;
import controladores.ControladorVentanaMensaje;

import ventanas.VentanaIngresaNombre;
import ventanas.VentanaMensaje;

public class Main {
    public Main() {
        super();
    }

    public static void main(String[] args) {
        VentanaIngresaNombre ventana = new VentanaIngresaNombre("Elegir nombre");
        ControladorVentanaIngresaNombre controlador = new ControladorVentanaIngresaNombre(ventana);
        ventana.setControlador(controlador);
        ventana.abrir();
    }
}
