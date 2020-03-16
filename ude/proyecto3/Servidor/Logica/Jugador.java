package ude.proyecto3.Servidor.Logica;


public class Jugador {
	private String id, nombre, correo, contrasenia, sal;
	int puntaje;
	
	public Jugador(String i, String n, String c, String p, String s) {
		id = i;
		nombre = n;
		correo = c;
		contrasenia = p;
		sal = s;
		puntaje = 0;
	}	// Jugador
	
	public String getId() {
		return id;
	}	// getId
	
	public String getNombre() {
		return nombre;
	}	// getNombre
	
	public String getCorreo() {
		return correo;
	}	// getCorreo
	
	public String getContrasenia() {
		return contrasenia;
	}	// getNombre
	
	public String getSal() {
		return sal;
	}	// getNombre
	
	public long getPuntaje() {
		return puntaje;
	}	// getPuntaje
	
	public void sumarPuntos(long p) {
		puntaje += p;
	}	// sumarPuntos
	
	public void setNombre(String n) {
		nombre = n;
	}	// getNombre
	
	public void setCorreo(String c) {
		correo = c;
	}	// getNombre
	
	public String enJSON() {
		return "{ \"nombre\": \"" + nombre + "\", \"correo\": \"" + correo + 
			"\", \"contraseña\": " + contrasenia + "\", \"puntaje\": " + puntaje + ", \"id\": " + id + " }";
	}	// toJson
	
}	/* Jugador */