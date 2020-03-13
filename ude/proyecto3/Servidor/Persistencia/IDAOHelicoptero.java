package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.Helicoptero;

public interface IDAOHelicoptero {

	void borrar(IConexion icon, int i) throws SQLException;

	Helicoptero encontrar(IConexion icon, int n) throws SQLException;

	void guardar(IConexion icon, Helicoptero p) throws FileNotFoundException, IOException, SQLException;

	boolean esVacio(IConexion icon) throws SQLException;

}
