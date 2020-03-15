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

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Persistencia.Consultas;
import ude.proyecto3.Servidor.Logica.Partida;
//import ude.proyecto3.Servidor;
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;

public class DAOPartidaSQLite implements IDAOPartida {
	private Consultas consul;
	
	public DAOPartidaSQLite() {
		consul = new Consultas();
	}	// DAOPartidaSQLite
	
	public void guardar(IConexion icon, Partida p) throws FileNotFoundException, IOException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con;
		String query;
		PreparedStatement pstmt;
		Jugador j;
		
        try {
        	con = conSQLite.getConexion(); 
            query = consul.guardarPartida();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, p.getId());
            pstmt.setLong(4, p.getPtosJPat());
            pstmt.setLong(5, p.getPtosJPes());
            pstmt.setInt(6, p.getCombusJPat());
            pstmt.setInt(7, p.getCombusJPes());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al guardar una partida.\n" + e.getMessage());
        }	// try-catch
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
        
        return esMiembro;
	}	// miembro
	
	// encontrar por nombre o correo-e.
	@Override
	public Partida encontrar(IConexion icon, String id) throws SQLException {
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
        pstmt.setString(2, id);
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
  	
    return p;
}	// encontrar
	
	/*
	 * Eliminar un jugador por nombre.
	 * @see ude.proyecto3.Servidor.Persistencia.IDAOJugador#borrar(ude.proyecto3.Servidor.Persistencia.IConexion, java.lang.String)
	 */
	@Override
	public void borrar(IConexion icon, String id) throws SQLException {
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();

		String query;
		PreparedStatement pstmt;
		
      try {
          query = consul.eliminarPorId();
          pstmt = con.prepareStatement(query);
          pstmt.setString(1, "PesqueroFabrica");
          pstmt.setString(2, id);
          pstmt.executeUpdate();
          pstmt.close();
      }
      catch (SQLException e) {
          System.out.println("Error al insertar un pesquero fabrica .\n" + e.getMessage());
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
      
    query = consul.listarPorId();
  	pstmt = con.prepareStatement(query);
  	pstmt.setString(1, "PesqueroFabrica");
  	rs = pstmt.executeQuery();
  	aux = !(rs.isBeforeFirst());
  	rs.close();
  	pstmt.close();
      
      return aux;
	}	// esVacio
	
	
	/**
	 * lista las partidas creadas que esperan por otro jugador para arrancar.
	 */
	public List<Partida> partidasCreadas(IConexion icon) {
		List<Partida> lista = null;
		
		return lista;
		
	}// listarPartidas
	
	public List<Partida> partidaPorId(IConexion icon, String id) throws SQLException{
		List<Partida> lista = null;
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
		        
		    	query = consul.partidasPorId();
		    	pstmt = con.prepareStatement(query);
		        pstmt.setString(1,id);
		        rs = pstmt.executeQuery();
		  	
		  	// Si el jugador existe se crea el objeto y se lo devuelve.
		  	if (rs.next()) {
		  				p = new Partida(rs.getString("id"), 
		  					rs.getString("nomUsu"),
			  				rs.getString("bando"),
			  				rs.getInt("ptosJPat"),
			  				rs.getInt("ptosJPes"),
			  				obtenerEstadoId(rs.getString("estadoId")),
			  				rs.getInt("combusJPes"),
			  				rs.getInt("combusJPat"),
			  				rs.getInt("tiempo"));
		  				lista.add(p);
		  	}
		  	rs.close();
		  	pstmt.close();
		return lista;		
	}

	private EstadoPartida obtenerEstadoId(String estado) {
		switch (estado) {
			case "CREADA":
				return EstadoPartida.CREADA;
			case "INICIADA":
	        	return EstadoPartida.INICIADA;
	        case "PAUSADA":
	            return EstadoPartida.PAUSADA;
	        case "TERMINADA":
	            return EstadoPartida.TERMINADA;
	        default: 
	        	return null;	       
		}
	}

}	/* DAOPartida */
