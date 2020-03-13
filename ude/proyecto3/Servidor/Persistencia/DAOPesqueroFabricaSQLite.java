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
import ude.proyecto3.Servidor.Persistencia.IDAOPesqueroFabrica;
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;


public class DAOPesqueroFabricaSQLite implements IDAOPesqueroFabrica {
	private Consultas consul;
	
	public DAOPesqueroFabricaSQLite() {
		consul = new Consultas();
	}	// DAOPesqueroFabricaSQLite
	
	@Override
	public void guardar(IConexion icon, PesqueroFabrica p) throws FileNotFoundException, IOException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		String query;
		PreparedStatement pstmt;
		
		try {
			Connection con = conSQLite.getConexion();
			query = consul.guardarPesqueroFabrica();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, p.getId()); 
			pstmt.setFloat(2, p.getAngulo()); 
			pstmt.setFloat(3, p.getRotacion());
			pstmt.setFloat(4, p.getPosX());
			pstmt.setFloat(5, p.getPosY());
			pstmt.setInt(6, p.getEnergia());
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("Error al insertar un pesquero fabrica .\n" + e.getMessage());
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
    	pstmt.setString(1, "PesqueroLigero");
    	pstmt.setInt(2, i);
    	rs = pstmt.executeQuery();
    	esMiembro = rs.isBeforeFirst();
    	rs.close();
    	pstmt.close();
    	con.close();
        
        return esMiembro;
	}	// miembro
	
	// encontrar por nombre o correo-e.
	@Override
	public PesqueroFabrica encontrar(IConexion icon, int n) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        //Jugador j = null;
        PesqueroFabrica p = null;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }
        
    	query = consul.encontrarPesquero();
    	pstmt = con.prepareStatement(query);
        pstmt.setString(1, "PesqueroFabrica");
        pstmt.setInt(2, n);
    	rs = pstmt.executeQuery();
  	
  	// Si el jugador existe se crea el objeto y se lo devuelve.
  	//super (id,angulo,rotacion,posx,posy,energia);	
  	if (rs.next()) {
  		p = new PesqueroFabrica(rs.getString("id"), 
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

}	/* DAOPesqueroFabrica  */
