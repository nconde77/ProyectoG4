package ude.proyecto3.Pruebas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Logica.Partida;


public class CrearBDSQLite {
	public static void main(String args[]) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		String url = "/home/nconde/eclipse-workspace/servidor/WebContent/base.db3";
		
		try {	// Conectar con la BD e inicializarla.
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			String sql = new String();
			stmt = conn.createStatement();
			
			System.out.println("Conexión a la base " + url + " establecida.");
			
			/* Tabla de jugadores */
			sql = "CREATE TABLE Jugadores " +
					"(Id      VARCHAR PRIMARY KEY NOT NULL," +
					" Nombre  CHARACTER(64)  NOT NULL," +
					" Correo  CHARACTER(128) NOT NULL," +
					" Contrasenia VARCHAR NOT NULL," +
					" Sal     CHAR(32) NOT NULL," +
					" Puntaje INT      NOT NULL);";
			System.out.println("Update 1: " + sql);
			stmt.executeUpdate(sql);
			
			/* Tabla de pesqueros. */
			sql = "CREATE TABLE Pesqueros " +
					"(Id   VARCHAR PRIMARY KEY NOT NULL," +
					" Tipo VARCHAR NOT NULL,"  +
					" Angulo   REAL NOT NULL," +
					" Rotacion REAL NOT NULL," +
					" PosX     REAL NOT NULL," +
					" PosY     REAL NOT NULL," +
					" Energia  REAL NOT NULL);";
			System.out.println("Update 2: " + sql);
			stmt.executeUpdate(sql);
			
			/* Tabla de OPV. */
			sql = "CREATE TABLE Patrulleros " +
					"(Id    VARCHAR PRIMARY KEY NOT NULL," +
					" IdLan VARCHAR,"  +
					" IdHel VARCHAR,"  +
					" Tipo VARCHAR NOT NULL,"  +
					" Angulo   REAL NOT NULL," +
					" Rotacion REAL NOT NULL," +
					" PosX     REAL NOT NULL," +
					" PosY     REAL NOT NULL," +
					" Energia  REAL NOT NULL);";
			System.out.println("Update 3: " + sql);
			stmt.executeUpdate(sql);
			
			/* Tabla de Partidas. */
			sql = "CREATE TABLE Partidas " +
					"(Id    VARCHAR PRIMARY KEY NOT NULL," +
					" Nombre VARCHAR NOT NULL,"  +
					" Estado VARCHAR NOT NULL,"  +
					" IdJPat VARCHAR,"  +
					" IdJPes VARCHAR,"  +
					" IdPes1 VARCHAR,"  +
					" IdPes2 VARCHAR,"  +
					" IdPes3 VARCHAR,"  +
					" IdPes4 VARCHAR,"  +
					" IdPat1 VARCHAR,"  +
					" IdPat2 VARCHAR,"  +
					" IdLan  VARCHAR,"  +
					" IdHeli VARCHAR,"  +
					" ComJPes INTEGER NOT NULL," +
					" ComJPat INTEGER NOT NULL," +
					" CantPeces INTEGER NOT NULL," +
					" Tiempo  INTEGER NOT NULL," +
					" FOREIGN KEY (IdJPat) REFERENCES Jugadores(Id)," + 
					" FOREIGN KEY (IdJPes) REFERENCES Jugadores(Id)," + 
					" FOREIGN KEY (IdPes1) REFERENCES Pesqueros(Id)," + 
					" FOREIGN KEY (IdPes2) REFERENCES Pesqueros(Id)," + 
					" FOREIGN KEY (IdPes3) REFERENCES Pesqueros(Id)," + 
					" FOREIGN KEY (IdPes4) REFERENCES Pesqueros(Id)," + 
					" FOREIGN KEY (IdPat1) REFERENCES Patrulleros(Id)," + 
					" FOREIGN KEY (IdPat2) REFERENCES Patrulleros(Id))";
			System.out.println("Update 4: " + sql);
			stmt.executeUpdate(sql);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {	// Cerrar la conexión.
			try {
				if (conn != null) {
					System.out.println("Cerrando conexión a la bd.");
					conn.close();
				}
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}	// try-catch
		}	// try-catch-finally
	}	// main

}	/* CrearBDSQLite */