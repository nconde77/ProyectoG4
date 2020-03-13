package ude.proyecto3.Pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;
import ude.proyecto3.Servidor.Logica.PesqueroLigero;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.ConexionSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOJugadorSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOPesqueroFabricaSQLite;
import ude.proyecto3.Servidor.Persistencia.DAOPesqueroLigeroSQLite;
import ude.proyecto3.Servidor.Persistencia.IFabrica;
import ude.proyecto3.Servidor.Persistencia.FabricaSQLite;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.ConexionSQLite;


public class PruebaPersistencia {
	public static void main(String args[]) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
		IConexion icon;
		
		DAOJugadorSQLite daoJugador = new DAOJugadorSQLite();
		Jugador j1 = new Jugador(UUID.randomUUID().toString(), "nconde77", "ncondeg@gmail.com");
		Jugador j2 = new Jugador(UUID.randomUUID().toString(), "mvidal", "mvidal@gmail.com");
		Jugador j3 = new Jugador(UUID.randomUUID().toString(), "diego_rulo", "drulo@gmail.com");
		Jugador j4 = new Jugador(UUID.randomUUID().toString(), "bgnini", "bgnini@gmail.com");
		
		DAOPesqueroFabricaSQLite daoPF = new DAOPesqueroFabricaSQLite();
		PesqueroFabrica pf1, pf2;
		DAOPesqueroLigeroSQLite  daoPL = new DAOPesqueroLigeroSQLite();
		PesqueroLigero pl1, pl2;
		
		String driver = "jdbc:sqlite";
		String db_factory = "ude.proyecto3.Servidor.Persistencia.FabricaSQLite";
		String db_url = "base.db3";
		
		Class.forName("org.sqlite.JDBC");
		
		//IFabrica fabJuego = (IFabrica) Class.forName(db_factory).newInstance();
		Connection con = DriverManager.getConnection(driver + ":" + db_url);
		icon = new ConexionSQLite(con);
		
		/* Jugadores */
		System.out.println("Insertando jugadores...");
		daoJugador.guardar(icon, j1);
		daoJugador.guardar(icon, j4);
		daoJugador.guardar(icon, j2);
		daoJugador.guardar(icon, j3);
		System.out.println("Fin.\nInsertando pesqueros...");
		
		/* Pesqueros */
		pf1 = new PesqueroFabrica(UUID.fromString("f1").toString(), 0, 0,  50,  50, 100);
		pf2 = new PesqueroFabrica(UUID.fromString("f2").toString(), 0, 0, 150, 150, 100);
		daoPF.guardar(icon, pf1);
		daoPF.guardar(icon, pf2);
		pl1 = new PesqueroLigero(UUID.fromString("l1").toString(), 0, 0,  50,  50, 100);
		pl2 = new PesqueroLigero(UUID.fromString("l2").toString(), 0, 0, 150, 150, 100);
		daoPL.guardar(icon, pl1);
		daoPL.guardar(icon, pl2);
		System.out.println("Fin.");
	}	// main
}	/* PruPersistJugador */