package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.EstadoPartida;

public class Partida {

	private Jugador jPat, jPes;
	private long Id;
	private int ptosJPat, ptosJPes, combusJPes, combusJPat, cantPeces, Tiempo;
	private EstadoPartida Estado;
	//private OPV;
	public Partida(Jugador jPat, Jugador jPes, long id, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) {
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
	

	public void setPtosJPat(int ptos) {
		if (ptos != 0) {
			ptosJPat= ptos;
		}	// if
	}	// setPtosJPat
	
	public void setPtosJPes(int ptos) {
		if (ptos != 0) {
			ptosJPes = ptos;
		}	// if
	}	// setJugadorPesquero
	
	public void setCombusJPes(int combus) {
		if (combus != 0) {
			combusJPes = combus;
		}	// if
	}	// setCombustibleJPesquero
	
	public void setCombusJPat(int combus) {
		if (combus != 0) {
			combusJPat = combus;
		}	// if
	}	// setcombustibleJugadorPatrullero
	
	public void setCantidadPeses(int cantpeces) {
		if (cantpeces != 0) {
			cantPeces = cantpeces;
		}	// if
	}	// setCantidadPeces
	
	public void setTiempo(int tiempo) {
		if (tiempo != 0) {
			Tiempo = tiempo;
		}	// if
	}	// setJugadorPatrullero
	
	public void setEstadoPartida(EstadoPartida estado) {
		if (estado != null) {
			Estado = estado;
		}	// if
	}	// setJugadorPatrullero
	
	public void iniciarPartida() {
		Estado = EstadoPartida.EN_CURSO;
	}
	
	public int  getJPatId() {
		return jPat.getId();
	}
	public int getJPesId() {
		return jPes.getId();
	}
	public long getId() {
		return Id;
	}
	
	public int getPtosJPat() {
		return ptosJPat;
	}
	
	public int getPtosJPes() {
		return ptosJPes;
	}
	
	public int getCombusJPes() {
		return combusJPes;
	}
	
	public int getCombusJPat() {
		return combusJPat;
	}
	
	public int getCantPeces() {
		return cantPeces;
	}
	
	public int getTiempo() {
		return Tiempo;
	}
}
