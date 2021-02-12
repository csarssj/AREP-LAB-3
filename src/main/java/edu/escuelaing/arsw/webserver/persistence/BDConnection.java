package edu.escuelaing.arsw.webserver.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.escuelaing.arsw.webserver.model.Pais;

public class BDConnection {
	
	private String host="jdbc:postgresql://ec2-52-7-168-69.compute-1.amazonaws.com:5432/d5g5qpv8n1o1h5?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	private String user="yagygnxlasincb";
	private String password= "7ea29c40a0b734c83cca9ac26622df9e9066dabb45b1adb07ce1cba9c0c0e182";
	private Connection connection = null;
	private static BDConnection BDConnection;
	
	public  BDConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(host,user, password);
			if (connection != null) {
				System.out.println("Se estableció la conexión :)");
		}
		} catch (Exception e) {
            System.out.println("Error al conectar a la base.");
			e.printStackTrace();
		}
	}
	/**
	 * Devuelve una lista de todos los paises que se encuentran en la base de datos
	 * 
	 * @return Lista de paises
	 */
	public ArrayList<Pais> getPaises()
	   {
			ArrayList<Pais> listapaises=new ArrayList<Pais>();
	      try
	      {
	    	 Statement st = connection.createStatement();
	         ResultSet rs = st.executeQuery("select * from paises" );
	         while (rs.next())
	         {
	        	 Pais pais = new Pais();
	        	 pais.setId(rs.getInt("Id"));
	        	 pais.setPais(rs.getString("pais"));
	        	 pais.setAcronimo(rs.getString("acronimo"));
	        	 listapaises.add(pais);
	         }
	         System.out.println(listapaises);
	         rs.close();
	         st.close();
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	      return listapaises;
	   }
}
