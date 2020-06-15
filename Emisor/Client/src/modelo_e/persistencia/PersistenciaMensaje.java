package modelo_e.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.PrintWriter;

import java.nio.file.Files;

import java.util.ArrayList;

import modelo_e.mensaje.Mensaje;

public class PersistenciaMensaje<T> implements IPersistidor {
    private final String fileName = "mensajesPendientes.txt";
    public PersistenciaMensaje() {
        super();
    }

    @Override
    public void guardarDatos(Object object) {
        synchronized(fileName) { //no se si funcione pero es la manera mas directa de que no se superpongan
            try {
                FileOutputStream file = new FileOutputStream(fileName, true);
                ObjectOutputStream output = new ObjectOutputStream(file);
                output.writeObject(object);
                output.flush();
                output.close();
                file.close();
            } catch (IOException e) {
                
            }
        }
    }

    @Override
    public Object recuperarDatos() {
        synchronized(fileName) {//no se si funcione pero es la manera mas directa de que no se superpongan
            ArrayList<Object> objectsList = new ArrayList<>();
            File file = new File(fileName);
            try {
                FileInputStream fileStream = new FileInputStream(file);
                boolean cont = true;
                while (cont) {
                    Object obj = null;
                    try {
                        ObjectInputStream input = new ObjectInputStream(fileStream);
                        obj = input.readObject();
                    } catch (ClassNotFoundException e) {
                    }
                    if (obj != null) {
                      objectsList.add(obj);
                    } else {
                      cont = false;
                    }
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
            try {
                new PrintWriter(fileName).close();
            } catch (FileNotFoundException e) {
            }
            return objectsList;
        }
    }
}
