package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;

public interface IDAOPartida {
	public void guardar(IConexion icon, Partida p) throws FileNotFoundException, IOException;
	//Pausar Partida esto va en Fachada
	public Partida encontrar(IConexion icon, String id) throws SQLException;
	public void borrar(IConexion icon, String id) throws SQLException;
	public boolean esVacio(IConexion icon) throws SQLException;
}
