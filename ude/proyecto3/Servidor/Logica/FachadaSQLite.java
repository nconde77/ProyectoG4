package ude.proyecto3.Servidor.Logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.websocket.Session;

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
	
	
	public FachadaSQLite () throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
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
		
		IFabrica fabJuego = (IFabrica) Class.forName(db_factory).newInstance();
		daoJugador = fabJuego.crearDAOJugador();
		daoPartida = fabJuego.crearDAOPartida();
	}	// FachadaSQLite
	
	/* De Partida. */
	
	/**
	 * Crear una partida nueva a partir de datos en mensaje JSON.
	 * @param nom Nombre de la partida.
	 * @param bando Bando que toma el jugador que crea la partida.
	 */
	public String crearPartida(String nom, String bando, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		String id = UUID.randomUUID().toString();
		Partida part = new Partida(id, nom, bando, ptosJPat, ptosJPes, estado, combusJPes, combusJPat, tiempo);
		
		daoPartida.guardar(con, part);
		ipool.liberarConexion(con, true);
		
		return id;
	}	// crear Partida
	
	/**
	 * Guarda una partida cuando se pausa o termina.
	 * @param part El objeto &quot;Partida&quot; a guardar.
	 */
	 public void guardarPartida(Partida part) throws FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		//Partida part;
		//Jugador jPat, jPes;
		//part = part.
		
		daoPartida.guardar(con, part);
		ipool.liberarConexion(con, true);
	}	// guardarPartida
	
	/**
	 * Iniciar una partida creada cuando se une el segundo jugador o cuando se saca de pausa.
	 * @param id El id de la partida.
	 * @param estado El estado previo de la partida, antes de (re)iniciarla.
	 */
	
	public void iniciarPartida(int id, String estado) throws SQLException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		part = daoPartida.encontrar(con, id);
		part.setEstadoPartida(EstadoPartida.INICIADA);
		ipool.liberarConexion(con, true);
	}	// iniciarPartida
	
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException { //String nom, String estado
		IConexion con = ipool.obtenerConexion(true);
		daoPartida.guardar(con, part);
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
		ipool.liberarConexion(con, true);
	}	// terminarPartida
	
	/* De jugador. */
	
	/**
	 * @param nom Nombre del jugador.
	 * @param bando Bando que eligió: patrullero o pesquero.
	 */
	public String crearJugador(String nom, String correo) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		Jugador j;
		String id = UUID.fromString(nom).toString();
		
		j = new Jugador(id, nom, correo);
		daoJugador.guardar(icon, j);
		ipool.liberarConexion(icon, true);
		
		return id;
	}	// crearJugador
	
	/*
	 * actPuntajeJugador
	 * Implementa la actualización del puntaje de un jugador al terminar una partida.
	 * @param id El identificador del jugador.
	 * @param ptosJugador El puntaje del jugador (entero).
	 */
	public void actPuntajeJugador(String id, int ptosJugador) throws SQLException {
		
	}	// actPuntajeJugador

	@Override
	public void actPuntajeJugador(long id, int ptosJPat) throws SQLException {
		// TODO Auto-generated method stub
		
	}	// actPuntajeJugador

}	/* FachadaSQLite */
