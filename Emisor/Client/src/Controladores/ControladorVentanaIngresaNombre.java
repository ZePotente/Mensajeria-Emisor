package controladores;

import modelo_e.Sistema;

import modelo_e.agenda.Usuario;

import ventanas.VentanaMensaje;

import vistas.InterfazVistaIngresaNombre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import modelo_e.ILoginAuthenticator;

public class ControladorVentanaIngresaNombre implements ActionListener {
    private InterfazVistaIngresaNombre vista;
    private ILoginAuthenticator sistema;

    public ControladorVentanaIngresaNombre(InterfazVistaIngresaNombre vista) {
        this.vista = vista;
        this.sistema = Sistema.getInstancia();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(InterfazVistaIngresaNombre.CONTINUAR)) {
            String nombre = vista.getNombre();
            if (nombre != null && !nombre.isEmpty()) {
                VentanaMensaje ventana = new VentanaMensaje(nombre);
                ControladorVentanaMensaje controlador = new ControladorVentanaMensaje(ventana);
                ventana.setControlador(controlador);
                try {
                    String nroIP = InetAddress.getLocalHost().getHostAddress();
                    sistema.ingresar(new Usuario(nombre, nroIP));
                    ventana.abrir();
                    vista.cerrar();
                } catch (UnknownHostException e) {
                    System.out.println("Error al obtener el numero de IP");
                }
            } else {
                vista.mostrarMensaje("Ingrese un nombre valido");
            }
        }
    }
}
