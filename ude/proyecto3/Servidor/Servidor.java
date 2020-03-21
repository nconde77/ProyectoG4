package ude.proyecto3.Servidor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import ude.proyecto3.Servidor.Logica.FachadaSQLite;
import ude.proyecto3.Servidor.Logica.IFachada;
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
	
	private HashMap<String, ParSesiones> partidas = new HashMap<String, ParSesiones>();
	private static Set<Session> sesiones = Collections.synchronizedSet(new HashSet<Session>());
	private IFachada facha;
	
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
		facha = new FachadaSQLite();
	}	// Servidor
	
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
		int c;
		
		logger.log(Level.INFO, "Mensaje " + mensaje + " de " + sesion.getId() + ".\n");
		/* Se cambió de clase para parsear a json-simple en vez de Gson
		 * porque si no revienta como chinche.
		 */
		jObj = (JSONObject) jParser.parse(mensaje);
		
		switch ((String) jObj.get("tipo")) {
			case "CAPTURAP1":   case "CAPTURAP2":
			case "CAPTURAP3":   case "CAPTURAP4":
			case "COMBUSTIBLE": case "DISPARO":
			case "MOVIMIENTO":  case "PAUSA":
			case "PECES":       case "AVISOS":
			case "REANUDAR":    case "TORMENTA":
			case "TIEMPO":
				// Solamente reenvio el mensaje al otro jugador.
				for (Session s : sesiones) {
					logger.log(Level.FINER, "mesaje: " + mensaje + "a sesión " + s);
					if (!s.equals(sesion)) {
						s.getBasicRemote().sendText(mensaje);
					}	//
				}	//	
				break;
			case "CREA_PART":
				crearPartida((String) jObj.get("nombre"), (String) jObj.get("jugador"),
						(String) jObj.get("bando"), sesion);
				break;
			case "EN_ESP":
				ponerEnEspera((String) jObj.get("uid"), (String) jObj.get("pid"),
						(String) jObj.get("bando"), sesion);
				break;
			case "UNI_PART":
				unirseAPartida((String) jObj.get("pid"), (String) jObj.get("uid"), sesion);
				break;
			case "PAU_PART":
				break;
			case "LIS_PART":
				listarPartidasCreadas(sesion);
				break;
			case "FIN_PART":
				c = ((Long) jObj.get("puntaje")).intValue();
				terminarPartida((String) jObj.get("uid"), c, sesion );
				break;
			case "ALTA_USU":
				altaUsuario((String) jObj.get("nombre"), (String) jObj.get("correo"),
					(String) jObj.get("contrasena"), sesion);
				break;
			case "LOGIN":
				loginUsuario((String) jObj.get("nombre"), (String) jObj.get("contrasena"), sesion);
				break;
			case "INI_JUEGO":
				iniciarJuego((String) jObj.get("pid"), (String) jObj.get("bando"), sesion);
				break;
			case "TOP_N":
				c = ((Long) jObj.get("cant")).intValue();
				topNJugadores(c, sesion);
				break;
			default:
				logger.log(Level.WARNING, "Mensaje desconocido " + mensaje + ".\n");
				break;
		}	// switch
	}	// cuandoMensaje
	
	
	@OnError
	public void cuandoError(Throwable t) {
		logger.log(Level.SEVERE, t.getMessage());
	}	// cuandoError
	
	
	@OnClose
	public void alCerrar(Session sesion, CloseReason cr) {
		logger.log(Level.INFO, "Cerrando sesión " + sesion.getId() + " por " + cr.toString() + ".\n");
		sesiones.remove(sesion);
	}	// alCerrar
	
	
	/*
	 * Crear una partida nueva a partir de datos en mensaje JSON.
	 * @param nom Nombre de la partida.
	 * @param bando Bando que toma el jugador que crea la partida.
	 */
	public void crearPartida(String nom, String jug, String bando, Session s)
			throws SQLException, FileNotFoundException, IOException {
		ParSesiones paSe = new ParSesiones();
		String pid = null;
		String resp = "{ \"pid\": \"";
		
		pid = facha.crearPartida(nom, jug, bando);
		
		if (pid.equals(null)) {
			resp += "-1\" }";
		}
		else {	// Hay partida
			resp += pid + "\" }";
			partidas.put(pid, paSe);
		}	// if
		
		s.getBasicRemote().sendText(resp);
	}	// crearPartida
	
	
	/**
	 * Poner a esperar una partida creada.
	 * @param uid
	 * @param pid
	 * @param bando
	 * @throws IOException 
	 */
	public void ponerEnEspera(String uid, String pid, String bando, Session s) throws IOException {
		ParSesiones paSe = new ParSesiones();
		
		if (bando.equals("Patrullero")) {
			logger.log(Level.INFO, " setear sesión OPV... " + s);
			paSe.setSesionPat(s);
		}
		else {
			logger.log(Level.INFO, " setear sesión Pes... " + s);
			paSe.setSesionPes(s);
		}	// if
		logger.log(Level.INFO, " hecho.\n" +
			" jug. en espera, bando: " + bando + ", sesión: " + s + " partida: " + pid);
		partidas.replace(pid, paSe);
	}	// ponerEnEspera
	
	/**
	 * Unir el usuario uid a una partida creada (pid) en el bando que falta.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void unirseAPartida(String pid, String uid, Session s) throws SQLException, IOException {
		ParSesiones paSe = new ParSesiones();
		String mArrancar = "{ \"tipo\": \"ARRANCAR\" }";
		
		//facha.unirseAPartida(pid, uid);
		logger.log(Level.INFO, " part. unirse, despues de facha: " + pid);
		
//		paSe = partidas.get(pid);
//		// Guardar la sesión del nuevo y avisar al que esperaba que empieza el juego.
//		if (paSe.getSesionPat() == null) {
//			logger.log(Level.INFO, " part. unirse, Pat 1: " + pid);
//			paSe.setSesionPat(s);
//			logger.log(Level.INFO, " part. unirse, Pat 2: " + pid);
//		}
//		else {
//			logger.log(Level.INFO, " part. unirse, Pes 1: " + pid);
//			paSe.setSesionPes(s);
//			logger.log(Level.INFO, " part. unirse, Pes 2: " + pid);
//		}	// if
		
		logger.log(Level.INFO, " part. unirse, mensajes de arranque: " + pid);
		paSe.getSesionPat().getBasicRemote().sendText(mArrancar);
		paSe.getSesionPes().getBasicRemote().sendText(mArrancar);
		logger.log(Level.INFO, " part. unirse, mensajes de arranque enviados: " + pid);
		
		partidas.replace(pid, paSe);
	}	// unirseAPartida
	
	/**
	 * Devuelve una cadena con un array JSON conteniendo las partidas creadas a las
	 * que se puede unir.
	 * @return String
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void listarPartidasCreadas(Session s) throws SQLException, FileNotFoundException, IOException {
		String lista = null;
		
		lista = facha.partidasCreadas();
		logger.log(Level.INFO, " partidasCreadas:\n" + lista);
		
		s.getBasicRemote().sendText(lista);
	}	//listarPartida
	
	public void guardarPartida(Partida part) throws FileNotFoundException, IOException {
	}	// guardarPartida
	
	
	
	/**
	 * Pausar una partida iniciada.
	 * @param part
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void pausarPartida(Partida part) throws FileNotFoundException, IOException { //String nom, String estado
	}	// pausarPartida
	
	/**
	 * Termina la partida de identificador id y estado est. Calcula los
	 * puntajes de la partida, y declara al ganador.
	 * @param id  El identificador de la partida a terminar.
	 * @param est Estado actual de la partida a terminar.
	 */
	public void terminarPartida(String uid, int p, Session s) throws SQLException {
		facha.actPuntajeJugador(uid, p);
	}	// terminarPartida
	
	/**
	 * Dar de alta un usuario si no está registrado ya.
	 * @param nom
	 * @param cor
	 * @param pas
	 * @param s
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void altaUsuario(String nom, String cor, String pas, Session s) throws FileNotFoundException, SQLException, IOException {
		String id = null;
		String resp = "{ \"registro\": ";
		
		id = facha.crearJugador(nom, cor, pas);
		
		if (id.equals(null)) {
			resp += "false }";
		}
		else {
			resp += "true }";
		}	// if
		
		s.getBasicRemote().sendText(resp);
	}	// altaUsuario
	
	private void loginUsuario(String nom, String cla, Session s) throws IOException, SQLException {
		String resp = "{ \"login\": ";
		String uid = facha.idJugador(nom);
		
		if (facha.loginJugador(nom, cla)) {
			resp += "true, \"uid\": \"" + uid + "\" }";
		}
		else {
			resp += "false, \"uid\": \"-1\" }";
		}	// if
		
		s.getBasicRemote().sendText(resp);
	}	// loginUsuario
	
	public void iniciarJuego(String pid, String bando, Session s) throws IOException {
		ParSesiones paSe = new ParSesiones();
		String arranca = "{ \"tipo\": \"ARRANCAR\" }";
		
//		paSe = partidas.get(pid);
//		paSe.getSesionPat().getBasicRemote().sendText(arranca);
//		paSe.getSesionPes().getBasicRemote().sendText(arranca);
	}	// iniciarJuego
	
	/**
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void topNJugadores(Integer cant, Session s) throws SQLException, IOException {
		String resp = null;
		logger.log(Level.FINER, "topNJugadores: " + cant.intValue());
		
		resp = facha.topNJugadores(cant.intValue());
		
		s.getBasicRemote().sendText(resp);
	}	// topNJugadores

}	/* Servidor */
