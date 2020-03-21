package ude.proyecto3.Servidor;

import javax.websocket.Session;

public class ParSesiones {
	private Session sPes, sPat;
	
	public ParSesiones() {
		sPes = sPat = null;
	}	// ParSesiones
	
	public Session getSesionPat() {
		return sPat;
	}	// getSesionPat
	
	public Session getSesionPes() {
		return sPes;
	}	// getSesionPes

	public void setSesionPat(Session s) {
		sPat = s;
	}	// setSesionPat
	
	public void setSesionPes(Session s) {
		sPes = s;
	}	// setSesionPes
	
}	/* ParSesiones */
