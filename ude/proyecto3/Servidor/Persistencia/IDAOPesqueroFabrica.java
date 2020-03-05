package ude.proyecto3.Servidor.Persistencia;

import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.PesqueroFabrica;

public interface IDAOPesqueroFabrica {

	public void borrar(IConexion icon, int i) throws SQLException;

	public PesqueroFabrica encontrar(IConexion icon, int n) throws SQLException;

	public boolean esVacio(IConexion icon) throws SQLException;
	

}
