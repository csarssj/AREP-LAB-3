/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.webserver.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;


/**
 *
 * 
 * @author ceseg
 */
public class getImage implements getFiles{

	/**
     * 
     * 
     * @param path Parametro que indica el tipo del encabezado de la pagina web. 
     * @param clientSocket Parametro que indica el despliegue de la imagen al cliente. 
     */
	@Override
	public void getFiles(Socket clientSocket, String path) {
		try{
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir")+ "/resources"+path));
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writeImg = new DataOutputStream(clientSocket.getOutputStream());
            ImageIO.write(image, "PNG", ArrBytes);
            writeImg.writeBytes("HTTP/1.1 200 OK \r\n");
            writeImg.writeBytes("Content-Type: image/png \r\n");
            writeImg.writeBytes("\r\n");
            writeImg.write(ArrBytes.toByteArray());
            System.out.println(System.getProperty("user.dir") + path);
        }catch(IOException e){
        }
	}

}
