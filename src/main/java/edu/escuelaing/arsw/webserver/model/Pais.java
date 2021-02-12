package edu.escuelaing.arsw.webserver.model;

public class Pais {
	private int id;
	private String pais;
	private String acronimo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getAcronimo() {
		return acronimo;
	}
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
	@Override
	public String toString() {
		return "{\"ID\":"+getId()+",\"Pais\":\""+getPais()+ ",\"Acronimo\":\""+getAcronimo()+"}";
	}	
	
}
