package ude.proyecto3.Servidor.Persistencia;

import java.sql.Connection;
import ude.proyecto3.Servidor.Persistencia.IConexion;


public class ConexionSQLite implements IConexion {
	Connection con;
	
	public ConexionSQLite(Connection connection) {
		con = connection;
	}	// ConexionSQLite
	
	public Connection getConexion() {
		return con;
	}	// getConexion
	
}	/* ConexionSQLite */
