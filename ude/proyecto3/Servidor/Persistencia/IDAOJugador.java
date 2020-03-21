package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import ude.proyecto3.Servidor.Logica.Jugador;


public interface IDAOJugador {
	public void guardar(IConexion icon, Jugador j) throws FileNotFoundException, IOException, SQLException;
	public boolean miembro(IConexion icon, String s) throws SQLException;
	Jugador encontrarNombre(IConexion icon, String n) throws SQLException;
	Jugador encontrarId(IConexion icon, String i) throws SQLException;
	public ArrayList<Jugador> topNJugadores(IConexion icon, int cant) throws SQLException;
	public void borrar(IConexion icon, String s) throws SQLException;
	public boolean esVacio(IConexion icon) throws SQLException;
	void actualizarPuntaje(IConexion icon, String uid, int p) throws SQLException;
}	/* IDAOJugador */
