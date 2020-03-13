package ude.proyecto3.Pruebas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;


public class CrearBDSQLite {
	public static void main(String args[]) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		String url = "base.db3";
		
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
					" Puntaje INT       NOT NULL);";
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
					"(Id   VARCHAR PRIMARY KEY NOT NULL," +
					" Tipo VARCHAR NOT NULL,"  +
					" Angulo   REAL NOT NULL," +
					" Rotacion REAL NOT NULL," +
					" PosX     REAL NOT NULL," +
					" PosY     REAL NOT NULL," +
					" Energia  REAL NOT NULL);";
			System.out.println("Update 3: " + sql);
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