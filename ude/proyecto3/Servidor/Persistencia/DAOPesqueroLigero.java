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
import ude.proyecto3.Servidor.Logica.Pesquero;
//import ude.proyecto3.Servidor;

public class DAOPesqueroLigero implements IDAOPesqueroLigero {
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

	
	public void guardarPesqueroLigero(Pesquero p) throws FileNotFoundException, IOException {
		Connection con = null;
		String query;
		PreparedStatement pstmt;
		Jugador j;
        try {
        	con = conectar(); 
            query = consul.guardarPesqueroLigero();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getId()); 
            pstmt.setFloat(2, p.getAngulo()); 
            pstmt.setFloat(3, p.getRotacion());
            pstmt.setFloat(4, p.getPosY());
            pstmt.setFloat(5, p.getPosX());
            pstmt.setInt(6, p.getEnergia());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al insertar un jugador.\n" + e.getMessage());
        }
	}
	//Agregado
	public boolean miembro(IConexion icon, String n) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        boolean esMiembro = false;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }
        
    	query = consul.darJugadorPorNombre();
    	pstmt = con.prepareStatement(query);
    	pstmt.setString(1, n);
    	rs = pstmt.executeQuery();
    	esMiembro = rs.isBeforeFirst();
    	rs.close();
    	pstmt.close();
    	con.close();
        
        return esMiembro;
	}	// miembro
	
	// encontrar por nombre o correo-e.
	@Override
	public Pesquero encontrar(IConexion icon, String n) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        Jugador j = null;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }
        
    	query = consul.darJugadorPorNombre();
    	pstmt = con.prepareStatement(query);
    	pstmt.setString(1, n);
    	rs = pstmt.executeQuery();
    	
    	// Si el jugador existe se crea el objeto y se lo devuelve.
    	if (rs.next()) {
    		j = new Jugador(rs.getString("Nombre"), 
    				rs.getString("Correo"),
    				rs.getInt("Id"));
    		j.sumarPuntos(rs.getLong("Puntaje"));
    	}
    	rs.close();
    	pstmt.close();
        
        return j;
	}	// encontrar
	
	/*
	 * Eliminar un jugador por nombre.
	 * @see ude.proyecto3.Servidor.Persistencia.IDAOJugador#borrar(ude.proyecto3.Servidor.Persistencia.IConexion, java.lang.String)
	 */
	@Override
	public void borrar(IConexion icon, String s) throws SQLException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();

		String query;
		PreparedStatement pstmt;
		
        try {
            query = consul.eliminarJugador();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, s);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al insertar un jugador.\n" + e.getMessage());
        }
        finally {
            con.close();
        }
	}	// borrar
	
	/*
	 * Retorna true si no hay jugadores guardados en la tabla.
	 * @see ude.proyecto3.Servidor.Persistencia.IDAOJugador#esVacio(ude.proyecto3.Servidor.Persistencia.IConexion)
	 */
	@Override
	public boolean esVacio(IConexion icon) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        boolean aux = true;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }	// if
        
        query = consul.listarJugadores();
    	pstmt = con.prepareStatement(query);
    	rs = pstmt.executeQuery();
    	aux = !(rs.isBeforeFirst());
    	rs.close();
    	pstmt.close();
        
        return aux;
	}	// esVacio


}
