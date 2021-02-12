    package edu.escuelaing.arsw.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.escuelaing.arsw.webserver.persistence.BDConnection;

/**
 * 
 *@author ceseg
 */
public class MyNanoSpark {

    
    /**
     *  Metodo que se encarga de iniciar el socket del servidor y del cliente 
     * 
     * @param args direccion en el browser http://localhost:35001/archivo.extension
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(getPort());
        System.out.println("Listo para recibir ...");
        
        ExecutorService pool = Executors.newCachedThreadPool();
        BDConnection bd = new BDConnection();
        while (true) {
            Socket socket = serverSocket.accept();
            HttpServer req = new HttpServer(socket,bd);
            pool.execute(req);
        }
    }

    /**
     * Devuelve el puerto que escuchará el servidor
     * 
     * @return El puerto de despliegue o 35000 si es local
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 30000;
    }
}
