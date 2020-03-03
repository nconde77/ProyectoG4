package ude.proyecto3.Servidor.Persistencia;

//import ude.proyecto3.Servidor.Persistencia.IDAOPartida;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Persistencia.Consultas;
import ude.proyecto3.Servidor.Logica.Partida;
//import ude.proyecto3.Servidor;

public class DAOPartida implements IDAOPartida {
	private Consultas consul;
	
	public static Connection conectar() throws FileNotFoundException, IOException {
		String dbDriver, dbURL;
		Connection conn = null;
		Properties configuracion = new Properties();
		
		configuracion.load (new FileInputStream ("./servidor.config"));
		dbDriver = configuracion.getProperty("db_driver");
		dbURL = configuracion.getProperty("db_url");
		
		try {
			conn = DriverManager.getConnection(dbURL);
			System.out.println("Conexión a la base " + dbURL + " establecida.");
		}
		catch (SQLException e) {
			System.out.println("Error al conectar a la base" + dbURL + ".\n" + e.getMessage());
		}
		finally {
			return conn;
		}	// try-catch-finally
	}	// conectar

	
	public void guardarPartida(Partida p) throws FileNotFoundException, IOException {
		Connection con = null;
		String query;
		PreparedStatement pstmt;
		Jugador j;
        try {
        	con = conectar(); 
            query = consul.guardarPartida();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getJPatId()); //guardo Id patrulla
            pstmt.setInt(2, p.getJPesId()); //guardo Id pesquero
            pstmt.setLong(3, p.getId());
            pstmt.setLong(4, p.getPtosJPat());
            pstmt.setLong(5, p.getPtosJPes());
            pstmt.setInt(6, p.getCombusJPat());
            pstmt.setInt(7, p.getCombusJPes());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al insertar un jugador.\n" + e.getMessage());
        }
	}



}