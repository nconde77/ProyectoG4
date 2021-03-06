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
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;

public class DAOPartida implements IDAOPartida {
	private Consultas consul;
	
	public void guardarPartida(IConexion icon, Partida p) throws FileNotFoundException, IOException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con;
		String query;
		PreparedStatement pstmt;
		Jugador j;
		
        try {
        	con = conSQLite.getConexion(); 
            query = consul.guardarPartida();
            pstmt = con.prepareStatement(query);
 //           pstmt.setInt(1, p.getJPatId()); //guardo Id patrulla
 //           pstmt.setInt(2, p.getJPesId()); //guardo Id pesquero
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
	}	// guardarPartida

	public boolean miembro(IConexion icon, int j1, int j2) throws SQLException {
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
        
    	query = consul.darPartidaPorJugadores();
    	pstmt = con.prepareStatement(query);
    	pstmt.setInt(1, j1);
    	pstmt.setInt(2, j2);
    	rs = pstmt.executeQuery();
    	esMiembro = rs.isBeforeFirst();
    	rs.close();
    	pstmt.close();
    	con.close();
        
        return esMiembro;
	}	// miembro
	
	// encontrar por nombre o correo-e.
	@Override
	public Partida encontrar(IConexion icon, int n) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        Partida p = null;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }
        
    	query = consul.encontrarPorId();
    	pstmt = con.prepareStatement(query);
        pstmt.setString(1, "Partidas");
        pstmt.setInt(2, n);
    	rs = pstmt.executeQuery();
  	
  	// Si el jugador existe se crea el objeto y se lo devuelve.
  	//super (id,angulo,rotacion,posx,posy,energia);	
  	if (rs.next()) {
  		/*p = new PesqueroFabrica(rs.getInt("id"), 
  				rs.getFloat("angulo"),
  				rs.getFloat("rotacion"),
  				rs.getFloat("posx"),
  				rs.getFloat("posy"),
  				rs.getInt("energia")); */
  	}
  	rs.close();
  	pstmt.close();
  	con.close();
      
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
          query = consul.eliminarPesquero();
          pstmt = con.prepareStatement(query);
          pstmt.setString(1, "PesqueroFabrica");
          pstmt.setInt(2, i);
          pstmt.executeUpdate();
          pstmt.close();
      }
      catch (SQLException e) {
          System.out.println("Error al insertar un pesquero fabrica .\n" + e.getMessage());
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
      
    query = consul.listarPesquero();
  	pstmt = con.prepareStatement(query);
  	pstmt.setString(1, "PesqueroFabrica");
  	rs = pstmt.executeQuery();
  	aux = !(rs.isBeforeFirst());
  	rs.close();
  	pstmt.close();
      
      return aux;
	}	// esVacio

}	/* DAOPartida */