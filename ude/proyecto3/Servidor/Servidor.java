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
import ude.proyecto3.Servidor.Logica.IFachada;


@ServerEndpoint("/Servidor")
public class Servidor {
	// Para poder loguear.
	Logger logger = Logger .getLogger(Servidor.class.getName());
	private static Set<Session> sesiones = Collections.synchronizedSet(new HashSet<Session>());
	private HashMap<Long, Partida> partidas = new HashMap<Long, Partida>(); 
	private IFachada fachada;
	
	static final int PE_MAX_COMB = 100;
	static final int PA_MAX_COMB = 100;
	static final int MAX_TIEMPO  = 180;	// 180 s = 3 min.
	
	
	/*
	 * Servidor.
	 * Constructor de la clase, sin parámetros.
	 */
	public Servidor() throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

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
