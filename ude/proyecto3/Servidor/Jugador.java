package ude.proyecto3.Servidor;

public class Jugador {
	private String nombre, correo;
	int id;
	
	public Jugador(String n, String c, int i) {
		nombre = n;
		correo = c;
		id = i;
	}
	
	public String getNombre() {
		return nombre;
	}	// getNombre
	
	public String getCorreo() {
		return correo;
	}	// getNombre
	
	public int getId() {
		return id;
	}	// getNombre
}	/* Jugador */