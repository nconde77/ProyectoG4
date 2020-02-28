package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import ude.proyecto3.Servidor.Jugador;


public interface IDAOJugador {
	public void insertar(IConexion icon, Jugador j) throws FileNotFoundException, IOException, SQLException;
	public boolean miembro(IConexion icon, String s) throws SQLException;
	// encontrar por nombre o correo-e.
	public Jugador encontrar(IConexion icon, String s) throws SQLException;
	// eliminar por nombre o correo-e.
	public void borrar(IConexion icon, String s) throws SQLException;
	public boolean esVacio(IConexion icon) throws SQLException;
}	/* IDAOJugador */