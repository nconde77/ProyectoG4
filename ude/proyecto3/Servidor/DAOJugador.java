package ude.proyecto3.Servidor;

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

import ude.proyecto3.Servidor.Jugador;
import ude.proyecto3.Servidor.Consultas;


public class DAOJugador {
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
			System.out.println(e.getMessage());
		}
		finally {
			return conn;
		}	// try-catch-finally
	}	// conectar

	
	public void insertarJugador(String n, String c, int i) {
		Connection con = null;
		String query;
		PreparedStatement pstmt;
		
        try {
        	con = conectar();
            query = consul.insertarJugador();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, n);
            pstmt.setString(2, c);
            pstmt.setInt(3, i);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            throw new PersistenciaException("Error al intentar agregar una revision",e);
        }
	}	// insertarJugador
}	/* DAOJugador */
