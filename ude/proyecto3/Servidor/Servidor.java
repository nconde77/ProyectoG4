package ude.proyecto3.Servidor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.websocket.*;
import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Persistencia.DAOPartida;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.IPoolConexiones;
import ude.proyecto3.Servidor.TipoMensaje;


@ServerEndpoint("/Servidor")
public class Servidor {
	private static Set<Session> sesiones = Collections.synchronizedSet(new HashSet<Session>());
	private String cataHome, db_driver, db_url;
	private IPoolConexiones ipool;
	private IDAOPartida partPersistencia;
	
	public Servidor() throws FileNotFoundException, IOException {
		cataHome = System.getProperty("catalina.home");
		Properties configuracion = new Properties();
		configuracion.load (new FileInputStream (cataHome + "/webapps/servidor/WEB-INF/classes/servidor.config"));
		db_driver = configuracion.getProperty("db_driver");
		db_url = configuracion.getProperty("db_url");
	}

	@OnOpen
	public void alAbrir(Session sesion) {
		System.out.println("Abriendo sesión " + sesion.getId() + ".");
		System.err.println("Abriendo sesión " + sesion.getId() + ".");
		sesiones.add(sesion);
	}	// alAbrir
	
	@OnMessage
	public void cuandoMensaje(Session sesion, String mensaje) {
		//System.out.println("Mensaje de " + sesion.getId() + ". Texto: " + mensaje);
		JsonParser jParse = new JsonParser();
		JsonObject jObj; //= new JsonElement();
		
		try {
/*			for (Session s : sesiones) {
				if (!sesion.equals(s)) {
					s.getBasicRemote().sendText(mensaje);
				}	// if
			}	// for */
			jObj = jParse.parse(mensaje).getAsJsonObject();
			switch (jObj.get("tipo").getAsString()) {
				case "CREAR_PART":
					break;
				default:
					System.err.println("Mensaje desconocido " + mensaje + ".\n");
					break;
			}	// switch
		}	// try
		catch (IOException e) {
			e.printStackTrace();
		}
	}	// cuandoMensaje
	
	//long id, int ptosJPat, int ptosJPes, EstadoPartida estado, int combusJPes, int combusJPat, int tiempo
	public void crearPartida(String nom, String bando,long id, int ptosJPat, int ptosJPes, EstadoPartida estado,int combusJPes, int combusJPat, int tiempo) throws SQLException, FileNotFoundException, IOException {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		part = new Partida(nom, bando, id, ptosJPat, ptosJPes, estado, combusJPes, combusJPat, tiempo);
		partPersistencia.guardarPartida(con, part);
		ipool.liberarConexion(con, true);
	}	// crear Partida
	
	public void guardarPartida(String nom1, String nom2,  String estado, int ptosJ1, int ptosJ2) {
		ipool.obtenerConexion(true);
		
	}	// guarda la Partida
	
	public void iniciarPartida(String nom, String estado) {
		ipool.obtenerConexion(true);
		
	}	// Inicia la Partida
	
	public void pausarPartida(String nom, String estado) {
		ipool.obtenerConexion(true);
		
	}	// Pausa la Partida
	
	public void terminarPartida(int id, String estado) {
		IConexion con = ipool.obtenerConexion(true);
		Partida part;
		Jugador jPat, jPes;
		part = partPersistencia.encontrar(con, id);
		jPat = part.getJpat();
		jPes = part.getJpat();		
		jPat.sumarPuntos(part.getPtosJPat()); 
		jPes.sumarPuntos(part.getPtosJPes()); 
	}	// Termina la Partida
	
	@OnError
	public void cuandoError(Throwable t) {
		System.out.println("Error:" + t.getMessage());
	}	// cuandoError
	
	@OnClose
	public void alCerrar(Session sesion) {
		System.out.println("Cerrando sesiÃ³n " + sesion.getId() + ".");
		sesiones.remove(sesion);
	}	// alCerrar
	
}
