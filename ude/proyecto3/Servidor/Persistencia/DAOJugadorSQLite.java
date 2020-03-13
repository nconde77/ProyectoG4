package ude.proyecto3.Servidor.Persistencia;

//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;

import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Persistencia.Consultas;
import ude.proyecto3.Servidor.Persistencia.IDAOJugador;


public class DAOJugadorSQLite implements IDAOJugador {
	private Consultas consul;
	
	public DAOJugadorSQLite() {
		consul = new Consultas();
	}	// DAOJugadorSQLite
	
	@Override
	public void guardar(IConexion icon, Jugador j) throws FileNotFoundException, IOException, SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		long id = -1;
		String query;
		PreparedStatement pstmt;
		
        try {
            // Insertar el jugador en la base.
        	query = consul.guardarJugador();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, j.getId());
            pstmt.setString(2, j.getNombre());
            pstmt.setString(3, j.getCorreo());
            pstmt.setLong(4, j.getPuntaje());
            System.out.println(j.getId() + "/" + j.getNombre() + "/" + j.getCorreo() + "/" + j.getPuntaje());
            pstmt.executeUpdate();
            pstmt.close();
            // Obtener el id.
        }
        catch (SQLException e) {
            System.out.println("Error al insertar un jugador.\n" + e.getMessage());
        }
	}	// insertar
	
	@Override
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
        
        return esMiembro;
	}	// miembro
	
	// encontrar por nombre o correo-e.
	@Override
	public Jugador encontrar(IConexion icon, String n) throws SQLException {
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
    		j = new Jugador(rs.getString("Id"),
    				rs.getString("Nombre"), 
    				rs.getString("Correo"));
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

}	/* DAOJugador */
