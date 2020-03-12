package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.IDAOJugador;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;
import ude.proyecto3.Servidor.Persistencia.IFabrica;
import ude.proyecto3.Servidor.Persistencia.IPoolConexiones;

import ude.proyecto3.Servidor.Logica.IFachada;

public class FachadaSQLite implements IFachada {
	private String cataHome, db_driver, db_factory, db_url, dirIP;
	private IDAOJugador daoJugador;
	private IDAOPartida daoPartida;
	private IPoolConexiones ipool;
	
	
	public FachadaSQLite () {
		// Parametros del sistema.
		cataHome = System.getProperty("catalina.home");
		InetAddress addr = InetAddress.getLocalHost();
		dirIP = addr.getHostAddress();
		
		// Parametros configurados por nos.
		Properties configuracion = new Properties();
		configuracion.load (new FileInputStream (cataHome + "/webapps/servidor/WEB-INF/classes/servidor.config"));
		db_driver = configuracion.getProperty("db_driver");
		db_url = configuracion.getProperty("db_url");
		db_factory = configuracion.getProperty("db_factory");
		
		IFabrica fabPartida = (IFabrica) Class.forName(db_factory).newInstance();
	}	// FachadaSQLite
	
	/*
	 * crearPartida.
	 * Crear una partida nueva a partir de datos en mensaje JSON.
	 * @param nom Nombre de la partida.
	 * @param bando Bando que toma el jugador que crea la partida.
	 */
	public void crearPartida(String nom, String bando, long id, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		part = new Partida(nom, bando, id, ptosJPat, ptosJPes, estado, combusJPes, combusJPat, tiempo);
		daoPartida.guardarPartida(con, part);
		partidas.put(part.getId(), part);
		ipool.liberarConexion(con, true);
	}	// crear Partida
	
	public void guardarPartida(Partida part) throws FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		//Partida part;
		//Jugador jPat, jPes;
		//part = part.
		
		daoPartida.guardarPartida(con, part);
		ipool.liberarConexion(con, true);
	}	// guardarPartida
	
	public void iniciarPartida(int id, String estado) throws SQLException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		part = daoPartida.encontrar(con, id);
		part.setEstadoPartida(EstadoPartida.INICIADA);
		ipool.liberarConexion(con, true);
	}	// iniciarPartida
	
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException { //String nom, String estado
		IConexion con = ipool.obtenerConexion(true);
		daoPartida.guardarPartida(con, part);
		//De alguna manera necesito acceder a la pantalla de inicio
		ipool.liberarConexion(con, true);
	}	// pausarPartida
	
	/*
	 * terminarPartida
	 * Termina la partida de identificador id y estado est. Calcula los
	 * puntajes de la partida, y declara al ganador.
	 * @param id  El identificador de la partida a terminar.
	 * @param est Estado actual de la partida a terminar.
	 */
	public void terminarPartida(int id, String est) throws SQLException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		Jugador jPat, jPes;
		
		part = daoPartida.encontrar(con, id);
		jPat = part.getJpat();
		jPes = part.getJpes();
		jPat.sumarPuntos(part.getPtosJPat()); 
		jPes.sumarPuntos(part.getPtosJPes());
		part.setEstadoPartida(EstadoPartida.TERMINADA);
		partidas.remove(part.getId());
		ipool.liberarConexion(con, true);
	}	// terminarPartida

}	/* FachadaSQLite */
