package ude.proyecto3.Servidor.Logica;

import java.sql.SQLException;

public class PartidaCreada {
	private String id, nombre, jugador, bando;
	
	public PartidaCreada() throws SQLException {
		id = "-1";
	}	// Partida
	
	public PartidaCreada(String i, String n, String iJ, String bn) throws SQLException {
		id = i;
		nombre = n;
		jugador = iJ;
		bando = bn;
	}	// Partida
	
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getJugador() {
		return jugador;
	}

	public String getBando() {
		return bando;
	}

	public void setId(String i) {
		id = i;
	}

	public void setNombre(String n) {
		nombre = n;
	}

	public void setJugador(String j) {
		jugador = j;
	}

	public void setBando(String bn) {
		bando = bn;
	}

	public String enJSON() {
		String res = null;
		
		res = "{ \"id\": \"" + id + "\", \"nombre\": \"" + nombre + "\", \"jugador\": \"" + jugador + "\", \"bando\": \"" + bando + "\" }";
		
		return res;
	}	// enJSON

}	/* PartidaCreada */
