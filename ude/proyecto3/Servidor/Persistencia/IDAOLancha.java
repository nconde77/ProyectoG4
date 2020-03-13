package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.Lancha;

public interface IDAOLancha {

	void guardar(IConexion icon, Lancha p) throws FileNotFoundException, IOException, SQLException;

	Lancha encontrar(IConexion icon, int n) throws SQLException;

	void borrar(IConexion icon, int i) throws SQLException;

	boolean esVacio(IConexion icon) throws SQLException;

}
