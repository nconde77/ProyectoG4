package ude.proyecto3.Servidor.Logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


public interface IFachada {
	// De partida.
	public String crearPartida(String nom, String bando, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException;
	public void guardarPartida(Partida part) throws FileNotFoundException, IOException;
	public void iniciarPartida(int id, String estado) throws SQLException;
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException;
	public void terminarPartida(int id, String est) throws SQLException;
	
	// De jugador.
	public String crearJugador(String nom, String bando) throws SQLException, FileNotFoundException, IOException;
	public void actPuntajeJugador(long id, int ptosJPat) throws SQLException;
	
	// De vehiculos.
	
}	/* IFachada */
