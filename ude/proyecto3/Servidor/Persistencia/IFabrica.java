package ude.proyecto3.Servidor.Persistencia;

import ude.proyecto3.Servidor.Persistencia.IDAOJugador;
import ude.proyecto3.Servidor.Persistencia.IDAOPartida;


public interface IFabrica {
	public IDAOJugador crearDAOJugador();
	public IDAOOPVLigero crearDAOOPVLigero();
	public IDAOOPVPesado crearDAOOPVPesado();
	public IDAOPesqueroLigero crearDAOPesqueroLigero();
	public IDAOPesqueroFabrica crearDAOPesqueroFabrica();
	public IDAOPartida crearDAOPartida();
}	/* IFabrica */
