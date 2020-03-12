package ude.proyecto3.Servidor.Logica;

public interface IFachada {
	// De partida.
	public void crearPartida(String nom, String bando, long id, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException;
	public void guardarPartida(Partida part) throws FileNotFoundException, IOException;
	public void iniciarPartida(int id, String estado) throws SQLException;
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException;
	public void terminarPartida(int id, String est) throws SQLException;
	
	// De jugador.
	public void crearJugador(String nom, String bando, long id, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException;
	public void actPuntajeJugador(long id, int ptosJPat) throws SQLException;
	
	// De vehiculos.
	
}	/* IFachada */
