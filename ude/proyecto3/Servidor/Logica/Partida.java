package ude.proyecto3.Servidor.Logica;

import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Persistencia.DAOJugador;

public class Partida {

	//private Jugador jug;
	//, jPes;
	private Jugador jPat, jPes;
	private DAOJugador daoj;
	private long Id;
	private int ptosJPat, ptosJPes, combusJPes, combusJPat, cantPeces, Tiempo;
	private EstadoPartida Estado;
	//private OPV;
	public Partida(String nomUsu, String bando, long id, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException {
		if (bando == "Pesquero" ){
			jPes = setJugadorPatrullero(nomUsu);
		} 
		else {
			jPat = setJugadorPatrullero(nomUsu);
		}
		//jPes = setJugadorPatrullero(nomPes);
		ptosJPat = ptosJPes = 0;
		
		id = -1;
		
		estado = EstadoPartida.CREADA;
	}	// Partida
	
	public Jugador setJugadorPatrullero(String j) throws SQLException {
		if (j != null) {
			return daoj.encontrar(null, j);
		}	// if
		else return null;
	}	// setJugadorPatrullero
	
	public Jugador setJugadorPesqueros(String j) throws SQLException {
		if (j != null) {
			return daoj.encontrar(null, j);
		}	// if
		else return null;
	}	// setJugadorPesquero
	
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
	
//	public int  getJId(String bando) {
//		if (bando == "Pesquero" ){
//			return jPes.getId();
//		} 
//		else {
//			return jPat.getId();
//		}
//		
//	}
	
//	public int getJPesId() {
//		return jPes.getId();
//	}
	public Jugador getJpat() {
		return jPat;	// if
	}
	
	public Jugador getJpes() {
		return jPes;	// if
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
