package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.PesqueroFabrica;

public interface IDAOPesqueroFabrica {

	public void guardar(IConexion icon, PesqueroFabrica p) throws FileNotFoundException, IOException;
	public void borrar(IConexion icon, String id) throws SQLException;
	public PesqueroFabrica encontrar(IConexion icon, String id) throws SQLException;
	public boolean esVacio(IConexion icon) throws SQLException;

}	/* IDAOPesqueroFabrica */