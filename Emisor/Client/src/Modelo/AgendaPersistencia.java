package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class AgendaPersistencia {
    private String filePath = "destinatarios.txt";
    
    public AgendaPersistencia() {}
    
    public ArrayList<Usuario> obtenerDestinatarios() throws IOException {
        String contenido = readFileAsString(filePath);
        return parsearContenidoDeArchivo(contenido);
    }
    
    private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead = reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
    
    private ArrayList<Usuario> parsearContenidoDeArchivo(String contenido) {
        ArrayList<Usuario> destinatarios = new ArrayList<Usuario>();
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            String[] tokens = linea.split("\t");
            // Esto asegura que la linea esta escrita correctamente
            if (tokens.length == 2) {
                Usuario destinatario = new Usuario(tokens[0],tokens[1]);
                destinatarios.add(destinatario);
            }
        }
        return destinatarios;
    }
}
