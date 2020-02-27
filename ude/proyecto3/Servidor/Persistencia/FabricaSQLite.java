package ude.proyecto3.Servidor.Persistencia;

import ude.proyecto3.Servidor.Persistencia.DAOJugador;
import ude.proyecto3.Servidor.Persistencia.IDAOJugador;
import ude.proyecto3.Servidor.Persistencia.DAOPartida;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;


public class FabricaSQLite implements IFabrica {

	@Override
	public IDAOJugador crearDAOJugador() {
		return new DAOJugador();
	}

	@Override
	public IDAOPartida crearDAOPartida() {
		return new DAOPartida();
	}

}
