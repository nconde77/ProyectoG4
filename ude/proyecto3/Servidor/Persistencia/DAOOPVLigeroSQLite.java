package ude.proyecto3.Servidor.Persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ude.proyecto3.Servidor.Logica.OPVLigero;
//import ude.proyecto3.Servidor.Logica.PesqueroLigero;

public class DAOOPVLigeroSQLite implements IDAOOPVLigero {
	private Consultas consul;
	
	public DAOOPVLigeroSQLite() {
		consul = new Consultas();
		
	}	// DAOOPVLigeroSQLite
	
	@Override
	public void guardar(IConexion icon, OPVLigero p) throws FileNotFoundException, IOException, SQLException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		String query;
		PreparedStatement pstmt;
		
        try {
            query = consul.guardarOPVLigero();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, p.getId()); 
            pstmt.setFloat(2, p.getAngulo()); 
            pstmt.setFloat(3, p.getRotacion());
            pstmt.setFloat(4, p.getPosY());
            pstmt.setFloat(5, p.getPosX());
            pstmt.setInt(6, p.getEnergia());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al insertar un opv ligero .\n" + e.getMessage());
        }	// try-catch
	}	// guardar
	
	//Agregado
	public boolean miembro(IConexion icon, int i) throws SQLException {
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
        
    	query = consul.encontrarPorId();
    	pstmt = con.prepareStatement(query);
    	pstmt.setString(1, "OPVLigero");
    	pstmt.setInt(2, i);
    	rs = pstmt.executeQuery();
    	esMiembro = rs.isBeforeFirst();
    	rs.close();
    	pstmt.close();
        
        return esMiembro;
	}	// miembro
	
	// encontrar por nombre o correo-e.
	@Override
	public OPVLigero encontrar(IConexion icon, int n) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        //Jugador j = null;
		OPVLigero p = null;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }
        
    	query = consul.encontrarPorId();
    	pstmt = con.prepareStatement(query);
        pstmt.setString(1, "OPVLigero");
        pstmt.setInt(2, n);
    	rs = pstmt.executeQuery();
    	
    	// Si el jugador existe se crea el objeto y se lo devuelve.
    	//super (id,angulo,rotacion,posx,posy,energia);	
    	if (rs.next()) {
    		p = new OPVLigero(rs.getString("id"), 
    				rs.getFloat("angulo"),
    				rs.getFloat("rotacion"),
    				rs.getFloat("posx"),
    				rs.getFloat("posy"),
    				rs.getInt("energia"));
    		//j.sumarPuntos(rs.getLong("Puntaje"));
    	}
    	rs.close();
    	pstmt.close();
        
        return p;
	}	// encontrar
	
	/*
	 * Eliminar un jugador por nombre.
	 * @see ude.proyecto3.Servidor.Persistencia.IDAOJugador#borrar(ude.proyecto3.Servidor.Persistencia.IConexion, java.lang.String)
	 */
	@Override
	public void borrar(IConexion icon, int i) throws SQLException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();

		String query;
		PreparedStatement pstmt;
		
        try {
            query = consul.eliminarPorId();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "OPVLigero");
            pstmt.setInt(2, i);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al insertar un opv ligero .\n" + e.getMessage());
        }	// try-catch
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
        
        query = consul.listarOPVs();
    	pstmt = con.prepareStatement(query);
    	pstmt.setString(1, "Patrulleros");
    	rs = pstmt.executeQuery();
    	aux = !(rs.isBeforeFirst());
    	rs.close();
    	pstmt.close();
        
        return aux;
	}	// esVacio
	
}	/* DAOOPVLigeroSQLite */
