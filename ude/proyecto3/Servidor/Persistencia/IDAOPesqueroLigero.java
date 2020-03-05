package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import ude.proyecto3.Servidor.Logica.PesqueroLigero;

public interface IDAOPesqueroLigero {

	public boolean esVacio(IConexion icon) throws SQLException;
	public PesqueroLigero encontrar(IConexion icon, int n) throws SQLException;
	public void borrar(IConexion icon, int s) throws SQLException;
	public void guardar(IConexion icon, PesqueroLigero p) throws FileNotFoundException, IOException, SQLException;
	
}	/* IDAOPesqueroLigero */
