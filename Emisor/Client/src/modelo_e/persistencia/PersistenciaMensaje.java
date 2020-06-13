package modelo_e.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

import modelo_e.mensaje.Mensaje;

public class PersistenciaMensaje implements IPersistidor {
    private final String fileName = "mensajesPendientes.txt";
    public PersistenciaMensaje() {
        super();
    }

    @Override
    public void guardarDatos(Object object) {
        FileOutputStream file;
        try {
            file = new FileOutputStream(new File(fileName));
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(object);
            output.close();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
                
        }
        
    }

    @Override
    public Object recuperarDatos() {
        ArrayList<Mensaje> mensajesLeidos = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            boolean cont = true;
            while (cont) {
                try (ObjectInputStream input = new ObjectInputStream(file)) {
                    Mensaje mensaje = (Mensaje)input.readObject();
                    if (mensaje != null) {
                        mensajesLeidos.add(mensaje);
                    } else {
                        cont = false;
                    }
                    input.close();
                } catch (ClassNotFoundException e) {
                    System.out.println("Error de casteo");
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Otro error");
        }
        return mensajesLeidos;
    }
}
