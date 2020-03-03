package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import ude.proyecto3.Servidor.Logica.Partida;

public interface IDAOPartida {
	public void guardarPartida(Partida p) throws FileNotFoundException, IOException;
	//Pausar Partida esto va en Facahda
	//
}
