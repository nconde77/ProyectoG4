package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.EstadoPartida;

public class Partida {

	private Jugador jPat, jPes;
	private long id, ptosJPat, ptosJPes;
	private EstadoPartida estado;
	
	public Partida() {
		jPat = jPes = null;
		ptosJPat = ptosJPes = 0;
		id = -1;
		estado = EstadoPartida.CREADA;
	}	// Partida
	
	public void setJugadorPatrullero(Jugador j) {
		if (j != null) {
			jPat = j;
		}	// if
	}	// setJugadorPatrullero
	
	public void setJugadorPesqueros(Jugador j) {
		if (j != null) {
			jPes = j;
		}	// if
	}	// setJugadorPatrullero
	
	public void iniciarPartida() {
		estado = EstadoPartida.INICIADA;
	}
}
