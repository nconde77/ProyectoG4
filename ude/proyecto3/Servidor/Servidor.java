package ude.proyecto3.Servidor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/Servidor")
public class Servidor {
	private static Set<Session> sesiones = Collections.synchronizedSet(new HashSet<Session>());
	private String db_driver, db_url;
	
	public Servidor() throws FileNotFoundException, IOException {
		Properties configuracion = new Properties();
		configuracion.load (new FileInputStream ("./servidor.config"));
		db_driver = configuracion.getProperty(db_driver);
		db_url = configuracion.getProperty(db_url);
	}

	@OnOpen
	public void alAbrir(Session sesion) {
		System.out.println("Abriendo sesión " + sesion.getId() + ".");
	}	// alAbrir
	
	@OnMessage
	public void cuandoMensaje(Session sesion, String mensaje) {
		System.out.println("Mensaje de " + sesion.getId() + ". Texto: " + mensaje);
		
		try {
			sesion.getBasicRemote().sendText("Hola Cliente " + sesion.getId() + "!");
			for (Session s : sesiones) {
				// if (!sesion.equals(s)) {
					s.getBasicRemote().sendText(mensaje);
				// }	// if
			}	// for
		}	// try
		catch (IOException e) {
			e.printStackTrace();
		}
	}	// cuandoMensaje
	
	@OnError
	public void cuandoError(Throwable t) {
		System.out.println("Error:" + t.getMessage());
	}	// cuandoError
	
	@OnClose
	public void alCerrar(Session sesion) {
		System.out.println("Cerrando sesión " + sesion.getId() + ".");
	}	// alCerrar
	
}
