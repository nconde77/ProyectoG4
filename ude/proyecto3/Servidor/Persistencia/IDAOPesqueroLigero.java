package ude.proyecto3.Servidor.Persistencia;

import java.sql.SQLException;
import ude.proyecto3.Servidor.Logica.PesqueroLigero;

public interface IDAOPesqueroLigero {

	boolean esVacio(IConexion icon) throws SQLException;

	PesqueroLigero encontrar(IConexion icon, int n) throws SQLException;

	void borrar(IConexion icon, int s) throws SQLException;

}
