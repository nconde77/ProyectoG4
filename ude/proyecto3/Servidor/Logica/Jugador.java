package ude.proyecto3.Servidor.Logica;


public class Jugador {
	private String id, nombre, correo;
	long puntaje;
	
	public Jugador(String i, String n, String c) {
		id = i;
		nombre = n;
		correo = c;
		puntaje = 0;
	}	// Jugador
	
	public String getNombre() {
		return nombre;
	}	// getNombre
	
	public String getCorreo() {
		return correo;
	}	// getNombre
	
	public long getPuntaje() {
		return puntaje;
	}	// getNombre
	
	public String getId() {
		return id;
	}	// getNombre
	
	public void sumarPuntos(long p) {
		puntaje += p;
	}	// sumarPuntos
	
	public void setNombre(String n) {
		nombre = n;
	}	// getNombre
	
	public void setCorreo(String c) {
		correo = c;
	}	// getNombre
	
	public String toJson() {
		return "{ \"nombre\": \"" + nombre + "\", \"correo\": \"" + correo + 
			"\", \"puntaje\": " + puntaje + ", \"id\": " + id + " }";
	}	// toJson
	
}	/* Jugador */