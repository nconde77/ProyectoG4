package ude.proyecto3.Servidor.Logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


public interface IFachada {
	// De partida.
	public String crearPartida(String nom, String bando, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException;
	public void guardarPartida(Partida part) throws FileNotFoundException, IOException;
	public void iniciarPartida(String id, String estado) throws SQLException;
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException;
	public void terminarPartida(String id, String est) throws SQLException;
	
	// De jugador.
	public String crearJugador(String nom, String cor, String pas) throws SQLException, FileNotFoundException, IOException;
	public void actPuntajeJugador(String id, int ptosJPat) throws SQLException;
	
	// De vehiculos.
	public String crearPesqueroFabrica(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException;
	public String crearPesqueroLigero(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException;
	public String crearOPVPesado(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException;
	public String crearOPVLigero(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException;
	
}	/* IFachada */
