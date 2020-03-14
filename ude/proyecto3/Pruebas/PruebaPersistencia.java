package ude.proyecto3.Pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

import ude.proyecto3.Servidor.Logica.FachadaSQLite;
import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Logica.OPVLigero;
import ude.proyecto3.Servidor.Logica.OPVPesado;
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;
import ude.proyecto3.Servidor.Logica.PesqueroLigero;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.ConexionSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOJugadorSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOPesqueroFabricaSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOPesqueroLigeroSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOOPVLigeroSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOOPVPesadoSQLite;
import ude.proyecto3.Servidor.Persistencia.IFabrica;
import ude.proyecto3.Servidor.Persistencia.FabricaSQLite;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.ConexionSQLite;


public class PruebaPersistencia {
	public static void main(String args[]) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
		IConexion icon;
		FachadaSQLite facade = new FachadaSQLite();
		DAOJugadorSQLite daoJugador = new DAOJugadorSQLite();
		
		String driver = "jdbc:sqlite";
		String db_factory = "ude.proyecto3.Servidor.Persistencia.FabricaSQLite";
		String db_url = "base.db3";

		Class.forName("org.sqlite.JDBC");
		//IFabrica fabJuego = (IFabrica) Class.forName(db_factory).newInstance();
		Connection con = DriverManager.getConnection(driver + ":" + db_url);
		icon = new ConexionSQLite(con);
		
		/* Jugadores */
		
		String j1 = facade.crearJugador("nconde77", "ncondeg@gmail.com", "passwdnc");
		String j2 = facade.crearJugador("mvidal", "mvidal@gmail.com", "passwdmv");
		String j3 = facade.crearJugador("diego_rulo", "drulo@gmail.com", "passwddg");
		String j4 = facade.crearJugador("bgnini", "bgnini@gmail.com", "passwdbg");
		
		System.out.println("Insertando jugadores...");
		daoJugador.guardar(icon, j1);
		daoJugador.guardar(icon, j4);
		daoJugador.guardar(icon, j2);
		daoJugador.guardar(icon, j3);
		System.out.println("Fin.\nInsertando pesqueros...");
		
//		/* Pesqueros */
//		pf1 = new PesqueroFabrica(UUID.randomUUID().toString(), 0, 0,  50,  50, 100);
//		pf2 = new PesqueroFabrica(UUID.randomUUID().toString(), 0, 0, 150, 150, 100);
//		daoPF.guardar(icon, pf1);
//		daoPF.guardar(icon, pf2);
//		pl1 = new PesqueroLigero(UUID.randomUUID().toString(), 0, 0,  50,  50, 100);
//		pl2 = new PesqueroLigero(UUID.randomUUID().toString(), 0, 0, 150, 150, 100);
//		daoPL.guardar(icon, pl1);
//		daoPL.guardar(icon, pl2);
//		System.out.println("Fin.\nInsertando OPVs...");
//		
//		/* OPV */
//		op1 = new OPVPesado(UUID.randomUUID().toString(), 0, 590,  50,  50, 100);
//		op2 = new OPVPesado(UUID.randomUUID().toString(), 0, 590, 150, 150, 100);
//		daoOP.guardar(icon, op1);
//		daoOP.guardar(icon, op2);
//		ol1 = new OPVLigero(UUID.randomUUID().toString(), 0, 0,  50,  50, 100);
//		ol2 = new OPVLigero(UUID.randomUUID().toString(), 0, 0, 150, 150, 100);
//		daoOL.guardar(icon, ol1);
//		daoOL.guardar(icon, ol2);
//		System.out.println("Fin.");

		con.close();
	}	// main
}	/* PruPersistJugador */