package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import ude.proyecto3.Servidor.Jugador;


public interface IDAOJugador {
	public Connection conectar() throws FileNotFoundException, IOException;
	public void insertarJugador(Jugador j) throws FileNotFoundException, IOException;
}
