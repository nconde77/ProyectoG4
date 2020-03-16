package ude.proyecto3.Pruebas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Logica.Partida;


public class CargarDatosPrueba {
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
			
			//* Cargar Jugadores */
			
			
			//* Cargar Vehiculos */
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

}	/* CargarDatosPrueba */
