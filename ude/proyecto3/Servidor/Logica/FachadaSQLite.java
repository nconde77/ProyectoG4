package ude.proyecto3.Servidor.Logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

import ude.proyecto3.Servidor.Logica.HashConSal;
import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Logica.PartidaCreada;
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
	// El conjunto de partidas en el sistema, indizadas por Id.
	private HashMap<String, Partida> partidas = new HashMap<String, Partida>();
	
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
		///configuracion.load (new FileInputStream ("/usr/share/tomcat/webapps/servidor/WEB-INF/classes/servidor.config"));
		db_driver = configuracion.getProperty("db_driver");
		db_url = configuracion.getProperty("db_url");
		///db_url = "/usr/share/tomcat/webapps/servidor/base.db3";
		db_factory = configuracion.getProperty("db_factory");
		
		ipool = PoolConexSQLite.getPoolConexiones(cataHome + "/" + db_url, "", "", 32, db_driver);
		IFabrica fabJuego = (IFabrica) Class.forName(db_factory).newInstance();
		daoJugador = fabJuego.crearDAOJugador();
		daoPartida = fabJuego.crearDAOPartida();
		daoOPVLigero = fabJuego.crearDAOOPVLigero();
		daoOPVPesado = fabJuego.crearDAOOPVPesado();
		daoPesqFabrica = fabJuego.crearDAOPesqueroFabrica();
		daoPesqLigero = fabJuego.crearDAOPesqueroLigero();
	}	// FachadaSQLite
	
		//* De Partida. */
	
	/**
	 * Crear una partida nueva a partir de datos en mensaje JSON.
	 * @param nom Nombre de la partida.
	 * @param bando Bando que toma el jugador que crea la partida.
	 */
	@Override
	public String crearPartida(String nom, String usu, String bando) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		String id = null;
		Partida part = null;
		
		if (!daoPartida.miembro(icon, nom)) {
			id = UUID.randomUUID().toString();
			part = new Partida(id, nom, usu, bando);
			daoPartida.guardar(icon, part);
			partidas.put(id, part);
		}	// if
		
		ipool.liberarConexion(icon, true);
		
		return id;
	}	// crearPartida
	
	/**
	 * Guarda una partida cuando se pausa o termina.
	 * @param part El objeto &quot;Partida&quot; a guardar.
	 */
	@Override
	 public void guardarPartida(Partida part) throws FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		
		daoPartida.guardar(con, part);
		ipool.liberarConexion(con, true);
	}	// guardarPartida
	
	/**
	 * Unirse a partida creada.
	 * @throws SQLException 
	 */
	@Override
	public void unirseAPartida(String pid, String uid) throws SQLException {
		logger.log(Level.INFO, " unirseAPartida inicio.\n ==> pid: " + pid + "\n ==> uid: " + uid);
		IConexion con;
		Partida part = new Partida();
		
		part = partidas.get(pid);
		if (part != null) {
			logger.log(Level.INFO, " hay partida " + pid);
			if (part.getJPat().equals(null)) {
				logger.log(Level.INFO, " unirseAPartida seteo OPV.");
				part.setJugadorPatrullero(uid);
			}
			else {
				logger.log(Level.INFO, " unirseAPartida seteo Pesq.");
				part.setJugadorPesquero(uid);
			}	// if
		
			logger.log(Level.INFO, " unirseAPartida -> persistencia.");
			con = ipool.obtenerConexion(true);
			part = daoPartida.encontrar(con, pid);
			part.setEstado(EstadoPartida.INICIADA);
			partidas.replace(pid, part);
			ipool.liberarConexion(con, true);
			logger.log(Level.INFO, " unirseAPartida fin.");
		}
		else {
			logger.log(Level.INFO, " unirseAPartida part es NULL.");
		}	// if
	}	// unirseAPartida
	
	/**
	 * Iniciar una partida creada cuando se une el segundo jugador o cuando se saca de pausa.
	 * @param id El id de la partida.
	 * @param estado El estado previo de la partida, antes de (re)iniciarla.
	 */
	@Override
	public void iniciarPartida(String id, String estado) throws SQLException {
		IConexion con;
		Partida part;
		
		con = ipool.obtenerConexion(true);
		part = daoPartida.encontrar(con, id);
		part.setEstado(EstadoPartida.INICIADA);
		ipool.liberarConexion(con, true);
	}	// iniciarPartida
	
	@Override
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException { //String nom, String estado
		IConexion con = ipool.obtenerConexion(true);
		
		daoPartida.guardar(con, part);
		//De alguna manera necesito acceder a la pantalla de inicio
		ipool.liberarConexion(con, true);
	}	// pausarPartida
	
	/**
	 * Termina la partida de identificador id y estado est. Calcula los
	 * puntajes de la partida, y declara al ganador.
	 * @param id  El identificador de la partida a terminar.
	 * @param est Estado actual de la partida a terminar.
	 */
	@Override
	public void terminarPartida(String id, String est) throws SQLException {
		/*IConexion con = ipool.obtenerConexion(true);
		Partida part;
		Jugador jPat, jPes;
		
		part = daoPartida.encontrar(con, id);
		jPat = part.getJpat();
		jPes = part.getJpes();
		jPat.sumarPuntos(part.getPtosJPat()); 
		jPes.sumarPuntos(part.getPtosJPes());
		part.setEstado(EstadoPartida.TERMINADA);
		ipool.liberarConexion(con, true);*/
	}	// terminarPartida
	
	/**
	 * Listar partidas creadas que esperan al 2do. jugador.
	 * @throws SQLException 
	 */
	public String partidasCreadas() throws SQLException {
		IConexion icon = ipool.obtenerConexion(true);
		ArrayList<PartidaCreada> lista;
		Iterator<PartidaCreada> itrLista;
		PartidaCreada part;
		Partida p;
		String id, jNom, strLista = "{ \"partCreadas\": [ ";
		int largo;
		
		lista = daoPartida.partidasCreadas(icon);
		itrLista = lista.iterator();
		
		/* Iterar sobre las partidas y pasarlas a JSON. */
		while (itrLista.hasNext()) {
			part = itrLista.next();
			// Cambiar el id del creador por su nombre y agregar la partida al resultado
			id = part.getJugador();
			//jNom = daoJugador.encontrarId(icon, id).getNombre();
			//part.setJugador(jNom);
			strLista += part.enJSON() + ", ";
		}	// while
		
		// Quitar la última coma de la cadena si hay partidas.
		largo = strLista.length();
		if (largo > 4) {
			strLista = strLista.substring(0, largo - 2);
		}	// if
		
		strLista += " ] }";
		ipool.liberarConexion(icon, true);
		
		return strLista;
	}	// partidasCreadas
	
		//* De jugador. */
	
	/**
	 * @param nom Nombre del jugador.
	 * @param bando Bando que eligió: patrullero o pesquero.
	 */
	@Override
	public String crearJugador(String nom, String correo, String csenia) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		Jugador j;
		String id = null;
		String sal = HashConSal.getSalt(32);
		String hCsenia = HashConSal.generateSecurePassword(csenia, sal);
		
		if (!daoJugador.miembro(icon, nom)) {
			id = UUID.randomUUID().toString();
			j = new Jugador(id, nom, correo, hCsenia, sal, 0);
			daoJugador.guardar(icon, j);
		}	// if
		
		ipool.liberarConexion(icon, true);
		
		return id;
	}	// crearJugador
	
	/**
	 * Implementa la actualización del puntaje de un jugador al terminar una partida.
	 * @param id El identificador del jugador.
	 * @param ptosJugador El puntaje del jugador (entero).
	 */
	@Override
	public void actPuntajeJugador(String id, int ptosJugador) throws SQLException {
		IConexion icon = ipool.obtenerConexion(true);
		Jugador j = null;
		boolean res = false;
		
		j = daoJugador.encontrarId(icon, id);
		if (j != null) {
			daoJugador.actualizarPuntaje(icon, id, ptosJugador);
		}	// if
		
		ipool.liberarConexion(icon, true);
	}	// actPuntajeJugador
	
	/**
	 * Login de un jugador.
	 * @throws SQLException 
	 */
	@Override
	public boolean loginJugador(String nom, String cla) throws SQLException {
		IConexion icon = ipool.obtenerConexion(true);
		Jugador j = null;
		boolean res = false;
		
		j = daoJugador.encontrarNombre(icon, nom);
		if (j != null) {
			res = j.getContrasenia().equals(HashConSal.generateSecurePassword(cla, j.getSal()));
		}	// if
				
		return res;
	}	// loginJugador
	
	/**
	 * Dar el Id de un Jugador por nombre.
	 * @throws SQLException 
	 */
	@Override
	public String idJugador(String nom) throws SQLException {
		IConexion icon = ipool.obtenerConexion(true);
		Jugador j = null;
		String id = null;
		
		j = daoJugador.encontrarNombre(icon, nom);
		if (j != null) {
			id = j.getId();
		}	// if
		
		return id;
	}	// idJugador
	
	
	/**
	 * Devuelve la lista con los cant jugadores de mayor puntaje.
	 * @param cant La cantidad de jugadores del ranking
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public String topNJugadores(int cant) throws SQLException {
		IConexion icon = ipool.obtenerConexion(true);
		ArrayList<Jugador> lista;
		Iterator<Jugador> itrLista;
		Jugador jug;
		String id, jNom, strLista = "{ \"ranking\": [ ";
		int i, largo, ult;
		
		lista = daoJugador.topNJugadores(icon, cant);
		itrLista = lista.iterator();
		ult = lista.size();
		logger.log(Level.INFO, " Cantidad de jugadores: " + ult);
		
		/* Iterar sobre las partidas y pasarlas a JSON. */
		for (i = 0; i < ult; i++) {
			jug = lista.get(i);
			logger.log(Level.INFO, " topNJugadores: " + jug.enJSON());
			strLista += jug.enJSON() + ", ";
		}	// while

		logger.log(Level.INFO, "\n topNJugadores: strLista: " + strLista);

		// Quitar la última coma de la cadena si hay partidas.
		largo = strLista.length();
		if (largo > 4) {
			strLista = strLista.substring(0, largo - 2);
		}	// if
		
		strLista += " ] }";
		ipool.liberarConexion(icon, true);
		
		return strLista;
	}	// topNJugadores
	
	
		//* De Pesquero. */
	
	/**
	 * Agregar un pesquero fábrica.
	 */
	@Override
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
	@Override
	public String crearPesqueroLigero(int a, int r, int x, int y, int e) throws SQLException, FileNotFoundException, IOException {
		IConexion icon = ipool.obtenerConexion(true);
		PesqueroLigero p;
		String id = UUID.randomUUID().toString();
		
		p = new PesqueroLigero(id, a, r, x, y, e);
		daoPesqLigero.guardar(icon, p);
		ipool.liberarConexion(icon, true);
		
		return id;
	}	// crearPesqueroLigero
	
		//* De Patrullero. */
	
	/**
	 * Agregar un patrullero ligero.
	 */
	@Override
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
	@Override
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
