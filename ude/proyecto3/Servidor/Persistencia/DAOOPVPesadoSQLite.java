package ude.proyecto3.Servidor.Persistencia;

import ude.proyecto3.Servidor.Persistencia.IDAOOPVPesado;

public class DAOOPVPesadoSQLite implements IDAOOPVPesado {
	private Consultas consul;
	
	public DAOOPVPesadoSQLite() {
		consul = new Consultas();
	}	// DAOOPVPesadoSQLite

}	/* DAOOPVPesado */