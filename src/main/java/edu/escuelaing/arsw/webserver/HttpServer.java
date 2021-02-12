/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.webserver;

/**
 *
 * @author ceseg
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.escuelaing.arsw.webserver.tools.getFiles;
import edu.escuelaing.arsw.webserver.tools.getImage;
import edu.escuelaing.arsw.webserver.persistence.BDConnection;
import edu.escuelaing.arsw.webserver.tools.getFile;

public class HttpServer implements Runnable  {
    private final Socket clientSock;
    private final BDConnection bd;
    /**
     * Crea un nuevo hilo ClientSock para el socket proporcionado
     *
     * @param clientSocket el socket para el cliente.
     */
    public HttpServer (final Socket clientSocket, final BDConnection bd) {
        this.clientSock = clientSocket;
        this.bd = bd;
    }
    @Override
    public void run() {
        try{
                PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                String inputLine, outputLine, path = null;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.matches("(GET)+.*")) {
                        path = inputLine.split(" ")[1];
                    }
                    if (!in.ready()) {
                        break;
                    }
                }
                if(path.startsWith("/App")) {
                	 handler getA = (request) ->  clientSock.getOutputStream().write((
                             "HTTP/1.1 200 OK\r\n"+
                             "Access-Control-Allow-Origin: *\r\n"+
                             "Content-Type: text/html\r\n\r\n"+
                             "<html><head>"+
                             "<title>App</title>"+
                             "</head><body>"+
                             "<h1>"+ bd.getPaises().toString()+
                             "</h1>"+
                             "</body></html>").getBytes());
                     getA.get(path);
                	
                }
                else{getFiles(path,clientSock);}
                out.close();
                in.close();
        } catch (IOException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    		outputLine = "HTTP/1.1 404 Not Found\r\n"
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

}
