import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/Servidor")
public class L350Servidor {
	
	@OnOpen
	public void alAbrir(Session sesion) {
		System.out.println("Abriendo sesión " + sesion.getId() + ".");
	}	// alAbrir
	
	@OnMessage
	public void cuandoMensaje(Session sesion, String mensaje) {
		System.out.println("Mensaje de " + sesion.getId() + ". Texto: " + mensaje);
		
		try {
			sesion.getBasicRemote().sendText("Hola Cliente " + sesion.getId() + "!");
		}
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
