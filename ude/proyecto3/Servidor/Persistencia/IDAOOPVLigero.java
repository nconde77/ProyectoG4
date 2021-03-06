package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.OPVLigero;

public interface IDAOOPVLigero {

	void guardar(IConexion icon, OPVLigero p) throws FileNotFoundException, IOException, SQLException;

	OPVLigero encontrar(IConexion icon, int n) throws SQLException;

	void borrar(IConexion icon, int i) throws SQLException;

	boolean esVacio(IConexion icon) throws SQLException;

}
