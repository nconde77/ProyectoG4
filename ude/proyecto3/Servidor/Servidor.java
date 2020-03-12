package ude.proyecto3.Servidor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.IDAOJugador;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;
import ude.proyecto3.Servidor.Persistencia.IFabrica;
import ude.proyecto3.Servidor.Persistencia.IPoolConexiones;


@ServerEndpoint("/Servidor")
public class Servidor {
	private String cataHome, db_driver, db_factory, db_url, dirIP;
	// Para poder loguear.
	Logger logger = Logger .getLogger(Servidor.class.getName());
	private static Set<Session> sesiones = Collections.synchronizedSet(new HashSet<Session>());
	private HashMap<Long, Partida> partidas = new HashMap<Long, Partida>(); 
	private IPoolConexiones ipool;
	private IDAOJugador jugPersistencia;
	private IDAOPartida partPersistencia;
	
	static final int PE_MAX_COMB = 100;
	static final int PA_MAX_COMB = 100;
	static final int MAX_TIEMPO  = 180;	// 180 s = 3 min.
	
	
	/*
	 * Servidor.
	 * Constructor de la clase, sin parámetros.
	 */
	public Servidor() throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		cataHome = System.getProperty("catalina.home");
		InetAddress addr = InetAddress.getLocalHost();
		dirIP = addr.getHostAddress();
		Properties configuracion = new Properties();
		configuracion.load (new FileInputStream (cataHome + "/webapps/servidor/WEB-INF/classes/servidor.config"));
		db_driver = configuracion.getProperty("db_driver");
		db_url = configuracion.getProperty("db_url");
		db_factory = configuracion.getProperty("db_factory");
		
		IFabrica fabPartida = (IFabrica) Class.forName(db_factory).newInstance();
	}	/* Servidor */
	
	/*
	 * alAbrir
	 * Almacena una nueva conexión de websocket en cuanto se abre desde el cliente.
	 * @param sesion El objeto Session que identifica al cliente.
	 */
	@OnOpen
	public void alAbrir(Session sesion) {
		logger.log(Level.INFO, "Abriendo sesión " + sesion.getId() + ".");
		sesiones.add(sesion);
	}	// alAbrir
	
	/*
	 * cuandoMensaje
	 * Se ejecuta ante la llegada de cada mensaje de un cliente.
	 * @param sesion El objeto Session que identifica al cliente.
	 * @param mensaje Una cadena de texto que contiene el mensaje JSON del cliente con un tipo y los datos necesarios para procesarlo.
	 */
	@OnMessage
	public void cuandoMensaje(Session sesion, String mensaje) throws SQLException, ParseException, IOException {
		JSONParser jParser = new JSONParser();
		JSONObject jObj;
		
		logger.log(Level.INFO, "Mensaje " + mensaje + " de " + sesion.getId() + ".\n");
		
		/* Se cambió de clase para parsear a json-simple en vez de Gson
		 * porque si no revienta como chinche.
		 */
		jObj = (JSONObject) jParser.parse(mensaje);
		
		switch ((String) jObj.get("tipo")) {
			case "MOVIMIENTO":
				for (Session s : sesiones) {
					if (!s.equals(sesion)) {
						// logger.log(Level.INFO, "Sesión " + s.getId() + ".\n");
						s.getBasicRemote().sendText(mensaje);
					}	// if
				}	// for
				break;
			case "CREAR_PART":
				crearPartida((String) jObj.get("nombre"), (String) jObj.get("bando"), -1,
					0, 0, EstadoPartida.CREADA, PE_MAX_COMB, PA_MAX_COMB, MAX_TIEMPO);
				break;
			case "INI_PART":
				break;
			case "PAU_PART":
				break;
			case "FIN_PART":
				break;
			default:
				logger.log(Level.WARNING, "Mensaje desconocido " + mensaje + ".\n");
				break;
		}	// switch
	}	// cuandoMensaje
	
	
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
		partPersistencia.guardarPartida(con, part);
		partidas.put(part.getId(), part);
		ipool.liberarConexion(con, true);
	}	// crear Partida
	
	
	public void guardarPartida(Partida part) throws FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		//Partida part;
		//Jugador jPat, jPes;
		//part = part.
		
		partPersistencia.guardarPartida(con, part);
		ipool.liberarConexion(con, true);
	}	// guardarPartida
	
	public void iniciarPartida(int id, String estado) throws SQLException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		part = partPersistencia.encontrar(con, id);
		part.setEstadoPartida(EstadoPartida.INICIADA);
		ipool.liberarConexion(con, true);
	}	// iniciarPartida
	
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException { //String nom, String estado
		IConexion con = ipool.obtenerConexion(true);
		partPersistencia.guardarPartida(con, part);
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
		
		part = partPersistencia.encontrar(con, id);
		jPat = part.getJpat();
		jPes = part.getJpes();
		jPat.sumarPuntos(part.getPtosJPat()); 
		jPes.sumarPuntos(part.getPtosJPes());
		part.setEstadoPartida(EstadoPartida.TERMINADA);
		partidas.remove(part.getId());
		ipool.liberarConexion(con, true);
	}	// terminarPartida
		
	@OnError
	public void cuandoError(Throwable t) {
		logger.log(Level.SEVERE, "cuandoError: " + t.getMessage());
	}	// cuandoError
	
	
	@OnClose
	public void alCerrar(Session sesion, CloseReason cr) {
		logger.log(Level.INFO, "Cerrando sesión " + sesion.getId() + " por " + cr.toString() + ".\n");
		sesiones.remove(sesion);
	}	// alCerrar
	
}