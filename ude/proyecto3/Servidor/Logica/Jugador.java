package ude.proyecto3.Servidor.Logica;


public class Jugador {
	private String id, nombre, correo, contrasenia, sal;
	int puntaje;
	
	public Jugador() {
		id = "-1";
	}	// Jugador

	public Jugador(String i, String n, String c, String pa, String s, int pu) {
		inicializar(i, n, c, pa, s, pu);
	}	// Jugador
	
	public void inicializar(String i, String n, String c, String pa, String s, int pu) {
		if (i.equals(null)) {
			id = "-1";
		}
		else {
			id = i;
			nombre = n;
			correo = c;
			contrasenia = pa;
			sal = s;
			puntaje = pu;
		}	// id
	}	// inicializar
	
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
	
	public int getPuntaje() {
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
		return "{ \"id\": \"" + id + "\", \"nombre\": \"" + nombre + "\", \"correo\": \"" + correo + "\", \"contraseña\": " +
				contrasenia + "\", \"sal\": \"" + sal + "\", \"puntaje\": " + puntaje + " }";
	}	// toJson
	
}	/* Jugador */