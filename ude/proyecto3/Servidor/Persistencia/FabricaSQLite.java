package ude.proyecto3.Servidor.Persistencia;

import ude.proyecto3.Servidor.Persistencia.IDAOJugador;
import ude.proyecto3.Servidor.Persistencia.DAOJugadorSQLite;
import ude.proyecto3.Servidor.Persistencia.IDAOOPVLigero;
import ude.proyecto3.Servidor.Persistencia.DAOOPVLigeroSQLite;
import ude.proyecto3.Servidor.Persistencia.IDAOOPVPesado;
import ude.proyecto3.Servidor.Persistencia.DAOOPVPesadoSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOPartidaSQLite;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;


public class FabricaSQLite implements IFabrica {

	@Override
	public IDAOJugador crearDAOJugador() {
		return new DAOJugadorSQLite();
	}

	@Override
	public IDAOOPVLigero crearDAOOPVLigero() {
		return new DAOOPVLigeroSQLite();
	}
	
	@Override
	public IDAOOPVPesado crearDAOOPVPesado() {
		return new DAOOPVPesadoSQLite();
	}
	
	@Override
	public IDAOPesqueroLigero crearDAOPesqueroLigero() {
		return new DAOPesqueroLigeroSQLite();
	}
	
	@Override
	public IDAOPesqueroFabrica crearDAOPesqueroFabrica() {
		return new DAOPesqueroFabricaSQLite();
	}
	
	@Override
	public IDAOPartida crearDAOPartida() {
		return new DAOPartidaSQLite();
	}

}	/* FabricaSQLite */
