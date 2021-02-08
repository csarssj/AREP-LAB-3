package edu.escuelaing.arep.nanosparkapp;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 *@author ceseg
 */
public class App {
	/**
     *  Metodo que se encarga de iniciar el socket del servidor y del cliente 
     * 
     * @param args direccion en el browser http://localhost:35001/archivo.extension
     * @throws IOException 
     */
    public static void main( String[] args )
    {
        HttpServer httpServer = new HttpServer();
        try {
            httpServer.startServer();
        }catch(IOException e){

        }
    }
}
