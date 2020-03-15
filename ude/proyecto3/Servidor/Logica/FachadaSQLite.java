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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Servidor;
import ude.proyecto3.Servidor.Logica.HashConSal;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.IDAOJugador;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;
import ude.proyecto3.Servidor.Persistencia.IDAOOPVLigero;
import ude.proyecto3.Servidor.Persistencia.IDAOOPVPesado;
import ude.proyecto3.Servidor.Persistencia.IDAOPesqueroFabrica;
import ude.proyecto3.Servidor.Persistencia.IDAOPesqueroLigero;
import ude.proyecto3.Servidor.Persistencia.IFabrica;
import ude.proyecto3.Servidor.Persistencia.IPoolConexiones;
import ude.proyecto3.Servidor.Persistencia.PoolConexSQLite;

import ude.proyecto3.Servidor.Logica.IFachada;

public class FachadaSQLite implements IFachada {
	Logger logger = Logger.getLogger(FachadaSQLite.class.getName());

	private String cataHome, db_driver, db_factory, db_url, dirIP;
	private IDAOJugador daoJugador;
	private IDAOOPVPesado daoOPVPesado;
	private IDAOOPVLigero daoOPVLigero;
	private IDAOPesqueroFabrica daoPesqFabrica;
	private IDAOPesqueroLigero  daoPesqLigero;
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
		
//		/* Para probar sin compilar el proyecto. */
//		db_driver  = "jdbc:sqlite";
//		db_factory = "ude.proyecto3.Servidor.Persistencia.FabricaSQLite";
//		db_url = "/home/nconde/eclipse-workspace/servidor/base.db3";
		
		logger.log(Level.INFO, db_driver + ":" + cataHome + "/" + db_url);
		ipool = PoolConexSQLite.getPoolConexiones(cataHome + "/" + db_url, "", "", 32, db_driver);

		IFabrica fabJuego = (IFabrica) Class.forName(db_factory).newInstance();
    	logger.log(Level.INFO, "Fachada IFabrica\n");
		daoJugador = fabJuego.crearDAOJugador();
		logger.log(Level.INFO, "Fachada DAO Jugador");
		daoPartida = fabJuego.crearDAOPartida();
		logger.log(Level.INFO, "Fachada DAO Partida");
		daoOPVLigero = fabJuego.crearDAOOPVLigero();
		logger.log(Level.INFO, "Fachada DAO OPV Lig");
		daoOPVPesado = fabJuego.crearDAOOPVPesado();
		logger.log(Level.INFO, "Fachada DAO OPV Pes");
		daoPesqFabrica = fabJuego.crearDAOPesqueroFabrica();
		logger.log(Level.INFO, "Fachada Pes Fab");
		daoPesqLigero = fabJuego.crearDAOPesqueroLigero();
		logger.log(Level.INFO, "Fachada Pes Lig");
	}	// FachadaSQLite
	
	/* De Partida. */
	
	/**
	 * Crear una partida nueva a partir de datos en mensaje JSON.
	 * @param nom Nombre de la partida.
	 * @param bando Bando que toma el jugador que crea la partida.
	 */
	@Override
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
	@Override
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
	@Override
	public void iniciarPartida(String id, String estado) throws SQLException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		part = daoPartida.encontrar(con, id);
		part.setEstadoPartida(EstadoPartida.INICIADA);
		ipool.liberarConexion(con, true);
	}	// iniciarPartida
	
	@Override
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
	@Override
	public void terminarPartida(String id, String est) throws SQLException {
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
	@Override
	public String crearJugador(String nom, String correo, String csenia) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		logger.log(Level.INFO, "ipool.obtenerConexion\n");
		Jugador j;
		logger.log(Level.INFO, "Jugador\n");
		String id = UUID.randomUUID().toString();
		String sal = HashConSal.getSalt(32);
		String hCsenia = HashConSal.generateSecurePassword(csenia, sal);
		
		logger.log(Level.INFO, "crearJugador\n");
		j = new Jugador(id, nom, correo, hCsenia, sal);
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
	@Override
	public void actPuntajeJugador(String id, int ptosJugador) throws SQLException {
		
	}	// actPuntajeJugador
	
	/**
	 * Login de un jugador.
	 * @throws SQLException 
	 */
	@Override
	public boolean loginJugador(String nom) throws SQLException {
		IConexion icon = ipool.obtenerConexion(true);
		boolean res = false;
		
		res = daoJugador.miembro(icon, nom);
		
		return res;
	}	// loginJugador
	
	/**
	 * Agregar un pesquero fábrica.
	 */
	public String crearPesqueroFabrica(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		PesqueroFabrica p;
		String id = UUID.randomUUID().toString();
		
		p = new PesqueroFabrica(id, a, r, x, y, e);
		daoPesqFabrica.guardar(icon, p);
		ipool.liberarConexion(icon, true);
		
		return id;		
	}	// crearPesqueroFabrica
	
	/**
	 * Agregar un pesquero ligero.
	 */
	public String crearPesqueroLigero(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		PesqueroLigero p;
		String id = UUID.randomUUID().toString();
		
		p = new PesqueroLigero(id, a, r, x, y, e);
		daoPesqLigero.guardar(icon, p);
		ipool.liberarConexion(icon, true);
		
		return id;
	}	// crearPesqueroLigero
	
	/**
	 * Agregar un patrullero ligero.
	 */
	public String crearOPVLigero(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		OPVLigero o;
		String id = UUID.randomUUID().toString();
		
		o = new OPVLigero(id, a, r, x, y, e);
		daoOPVLigero.guardar(icon, o);
		ipool.liberarConexion(icon, true);
		
		return id;
	}	// crearOPVLigero
	
	/**
	 * Agregar un patrullero pesado.
	 */
	public String crearOPVPesado(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		OPVPesado o;
		String id = UUID.randomUUID().toString();
		
		o = new OPVPesado(id, a, r, x, y, e);
		daoOPVPesado.guardar(icon, o);
		ipool.liberarConexion(icon, true);
		
		return id;		
	}	// crearPesqueroFabrica

}	/* FachadaSQLite */
