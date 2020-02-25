package ude.proyecto3.Servidor;

public class Jugador {
	private String nombre, correo;
	long puntaje;
	int id;
	
	public Jugador(String n, String c, int i) {
		nombre = n;
		correo = c;
		puntaje = 0;
		id = i;
	}
	
	public String getNombre() {
		return nombre;
	}	// getNombre
	
	public String getCorreo() {
		return correo;
	}	// getNombre
	
	public long getPuntaje() {
		return puntaje;
	}	// getNombre
	
	public int getId() {
		return id;
	}	// getNombre
	
	public void sumarPuntos(long p) {
		puntaje += p;
	}	// sumarPuntos
	
}	/* Jugador */