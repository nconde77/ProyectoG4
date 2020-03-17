package ude.proyecto3.Servidor.Logica;

import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Persistencia.DAOJugadorSQLite;

public class Partida {
	private String id, nombre, idPes1, idPes2, idPes3, idPes4;
	private String idJPes, idJPat, idPat1, idPat2, idHeli, idLan;
	private int combusJPes, combusJPat, cantPeces, tiempo;
	private EstadoPartida estado;
	
	/**
	 * Constructor.
	 * @param i
	 * @param nom
	 * @param idUsu
	 * @param bando
	 * @throws SQLException
	 */
	public Partida() throws SQLException {
		id = "-1";
		nombre = idPes1 = idPes2 = idPes3 = idPes4 = idJPes = idJPat = idPat1 = idPat2 = idHeli = idLan = null;
		combusJPes = combusJPat = cantPeces = tiempo = 0;
	}	// Partida
	
	/**
	 * Constructor.
	 * @param i
	 * @param nom
	 * @param idUsu
	 * @param bando
	 * @throws SQLException
	 */
	public Partida(String i, String nom, String idUsu, String bando) throws SQLException {
		inicializar(i, nom, idUsu, bando);
	}	// Partida
	
	
	public void inicializar(String i, String nom, String idUsu, String bando) throws SQLException {
		id = i;
		nombre = nom;
		
		if (bando == "Pesquero" ) {
			idJPes = idUsu;
			idJPat = null;
		} 
		else {
			idJPat = idUsu;
			idJPes = null;
		}	// if
		
		idPes1 = idPes2 = idPes3 = idPes4 = null;
		idPat1 = idPat2 = idLan = idHeli = null;
		estado = EstadoPartida.CREADA;
		combusJPes = combusJPat = 8000;
		cantPeces = 10000;
		tiempo = 150;
	}	// inicializar
	
	public void setJugadorPatrullero(String jId) throws SQLException {
		if (jId != null) {
			idJPat = jId;
		}	// if
	}	// setJugadorPatrullero
	
	
	public void setPes1(String id) throws SQLException {
		if (id != null) {
			idPes1 = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setPes2(String id) throws SQLException {
		if (id != null) {
			idPes2 = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setPes3(String id) throws SQLException {
		if (id != null) {
			idPes3 = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setPes4(String id) throws SQLException {
		if (id != null) {
			idPes4 = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setPat1(String id) throws SQLException {
		if (id != null) {
			idPat1 = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setPat2(String id) throws SQLException {
		if (id != null) {
			idPat2 = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setHeli(String id) throws SQLException {
		if (id != null) {
			idHeli = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setLancha(String id) throws SQLException {
		if (id != null) {
			idLan = id;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setJugadorPesquero(String jId) throws SQLException {
		if (jId != null) {
			idJPes = jId;
		}	// if
	}	// setJugadorPesquero
	
	
	public void setCombusJPes(int combus) {
		if (combus > -1) {
			combusJPes = combus;
		}	// if
	}	// setCombusJPes
	
	
	public void setCombusJPat(int combus) {
		if (combus > -1) {
			combusJPat = combus;
		}	// if
	}	// setCombusJPat
	
	
	public void setCantidadPeces(int cantpeces) {
		if (cantpeces != 0) {
			cantPeces = cantpeces;
		}	// if
	}	// setCantidadPeces
	
	
	public void setTiempo(int tiempo) {
		if (tiempo > -1) {
			tiempo = tiempo;
		}	// if
	}	// setJugadorPatrullero
	
	
	public void setEstado(EstadoPartida estado) {
		estado = estado;
	}	// setJugadorPatrullero
	
	
	public void iniciarPartida() {
		estado = EstadoPartida.INICIADA;
	}
	
	
	public String getId() {
		return id;
	}
	
	
	public String getNombre() {
		return nombre;
	}	// getNombre
	
	
	public String getEstado() {
		String est = null;
		
		switch (estado) {
			case CREADA:
				est = "CREADA";
				break;
			case INICIADA:
				est = "INICIADA";
				break;
			case PAUSADA:
				est = "PAUSADA";
				break;
			case TERMINADA:
				est = "TERMINADA";
				break;
			default:
				break;
		}	// switch
		
		return est;
	}	// getEstado
	
	
	public String getJPat() {
		return idJPat;
	}	// getJPat
	
	
	public String getJPes() {
		return idJPes;
	}	// idJPes
	
	
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
		return tiempo;
	}
	
	
	public String getPes1() {
		return idPes1;
	}	// getPes1
	
	
	public String getPes2() {
		return idPes2;
	}	// getPes2
	
	
	public String getPes3() {
		return idPes3;
	}	// getPes3
	
	
	public String getPes4() {
		return idPes4;
	}	// getPes4
	
	
	public String getPat1() {
		return idPat1;
	}	// getPat1
	
	public String getPat2() {
		return idPat2;
	}	// getPat2
	
	public String getLancha() {
		return idLan;
	}	// getLancha
	
	public String getHeli() {
		return idHeli;
	}	// getHeli
	
	public String enJSON() {
		String est, res = null;
		
		res = "{ \"id\": \"" + id + "\", \"nombre\": \"" + nombre + "\", \"idPes1\": \"" + idPes1 + "\", \"idPes2\": \"" + idPes2 +
			"\", \"idPes3\": \"" + idPes3 + "\", \"idPes4\": \"" + idPes4 + "\", \"idJPes\": \"" + idJPes + "\", \"idJPat\": \"" + idJPat +
			"\", \"idPat1\": \"" + idPat1 + "\", \"idPat2\": \"" + idPat2 + "\", \"idHeli\": \"" + idHeli + "\", \"idLan\": \"" + idLan +
			"\", \"combusJPes\": " + combusJPes + ", \"combusJPat\": " + combusJPat + ", \"cantPeces\": " + cantPeces +
			", \"tiempo\": " + tiempo + ", \"estado\": \"" + getEstado() + "\" }";
		
		return res;
	}	// toJSON

}	/* Partida */
