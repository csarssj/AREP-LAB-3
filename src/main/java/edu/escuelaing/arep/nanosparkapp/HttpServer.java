package edu.escuelaing.arep.nanosparkapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.escuelaing.arep.nanosparkapp.resources.getFiles;
import edu.escuelaing.arep.nanosparkapp.resources.concrete.getFile;
import edu.escuelaing.arep.nanosparkapp.resources.concrete.getImage;

/**
 * 
 *@author ceseg
 */
public class HttpServer
{
    public HttpServer(){
        super();
    }
    public void startServer() throws IOException{
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
	        Socket clientSocket = null;
	        try {
	            System.out.println("Listo para recibir ...");
	            clientSocket = serverSocket.accept();
	            PrintWriter out = new PrintWriter(
		                clientSocket.getOutputStream(), true);
		        BufferedReader in = new BufferedReader(
		                new InputStreamReader(clientSocket.getInputStream()));
		        String inputLine, outputLine, path = null;
		        while ((inputLine = in.readLine()) != null) {
		            System.out.println("Recibí: " + inputLine);
		            if(inputLine.contains("GET")) {
		            	 path = inputLine.split(" ")[1];
		            	 System.out.println("path:" + path);
		            }
		            if (!in.ready()) {break; }
		        }
		        getFiles(path,clientSocket);
		        out.close();
		        in.close();
		        clientSocket.close();
	        } catch (IOException e) {
	            System.err.println("Accept failed.");
	            System.exit(1);
	        }
	        
        }
	    serverSocket.close();
    }
    public void getFiles(String path,Socket clientSocket) throws IOException {
    	getFiles file;
    	String outputLine;
    	if(path.contains(".html")||path.contains(".css")||path.contains(".js")) {
    		file = new getFile();
    		file.getFiles(clientSocket, path);
    	}else if(path.contains(".png")) {
    		file = new getImage();
    		file.getFiles(clientSocket, path);
    	}else {
    		PrintWriter out = new PrintWriter(
	                clientSocket.getOutputStream(), true);
    		outputLine = "HTTP/1.1 200 OK\r\n"
	                + "Content-Type: text/html\r\n"
	                 + "\r\n"
	                 + "<!DOCTYPE html>\n"
	                 + "<html>\n"
	                 + "<head>\n"
	                 + "<meta charset=\"UTF-8\">\n"
	                 + "<title>Pagina ERROR</title>\n"
	                 + "</head>\n"
	                 + "<body>\n"
	                 + "<h1>Archivo no encontrado</h1>\n"
	                 + "</body>\n"
	                 + "</html>\n";
	        out.println(outputLine);
	        out.close();
    	}
    	
    }
    /**
     * Devuelve el puerto que escuchará el servidor
     * 
     * @return El puerto de despliegue o 36000 si es local
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }
}
