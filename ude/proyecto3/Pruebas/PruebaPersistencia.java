package ude.proyecto3.Pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

import ude.proyecto3.Servidor.Logica.*;
import ude.proyecto3.Servidor.Persistencia.*;


public class PruebaPersistencia {
	public static void main(String args[]) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		IFachada facade = new FachadaSQLite();
	
//		/* Jugadores */
//		
//		String j1 = facade.crearJugador("nconde77", "ncondeg@gmail.com", "passwdnc");
//		String j2 = facade.crearJugador("mvidal", "mvidal@gmail.com", "passwdmv");
//		String j3 = facade.crearJugador("diego_rulo", "drulo@gmail.com", "passwddg");
//		String j4 = facade.crearJugador("bgnini", "bgnini@gmail.com", "passwdbg");
//		
//		/* Pesqueros */
//		
//		String pf1 = facade.crearPesqueroFabrica(0, 0,  50,  50, 100);
//		String pf2 = facade.crearPesqueroFabrica(0, 0, 150, 150, 100);
//		
//		String pl1 = facade.crearPesqueroLigero(0, 0,  50,  50, 100);
//		String pl2 = facade.crearPesqueroLigero(0, 0, 150, 150, 100);
//
//		System.out.println("Fin.\nInsertando OPVs...");
//		
//		/* OPV */
//		String op1 = facade.crearPesqueroLigero(0, 590,  50,  50, 100);
//		String op2 = facade.crearPesqueroLigero(0, 590, 150, 150, 100);
//		
//		String ol1 = facade.crearPesqueroLigero(0, 0,  50,  50, 100);
//		String ol2 = facade.crearPesqueroLigero(0, 0, 150, 150, 100);
		
		System.out.println(facade.partidasCreadas());
		System.out.println("Fin.");

	}	// main
}	/* PruPersistJugador */