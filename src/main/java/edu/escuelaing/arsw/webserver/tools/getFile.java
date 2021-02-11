/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.webserver.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author ceseg
 */
public class getFile implements getFiles{
	
	/**
     * 
     * 
     * @param path 
     * @param clientSocket 
     */
	public void getFiles(Socket clientSocket, String path) {
		String type = getType(path,clientSocket);
        try{
            String text = "HTTP/1.1 200 OK\r\n"
	                + "Content-Type: text/"+type+"\r\n"
	                 + "\r\n";
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ "/resources"+path));
            String infile = null;
            while ((infile = reader.readLine()) != null) {
                  text = text + infile;
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(text);
            out.close();
        }catch (IOException e) {
                e.printStackTrace();
        }
		
	}
	/**
	 * Devuelve el tipo de archivo correspondiente al final del path
	 * 
	 * @param path
	 * @param clientSocket
	 * @return formato del archivo
	 */
	private String getType(String path, Socket clientSocket) {
		if(path.contains(".html")) {
    		return "html";
    	}else if(path.contains(".css")) {
    		return "css";
    	}else if(path.contains(".js")) {
    		return "javascript";
    	}
		return path;
		
	}

}

