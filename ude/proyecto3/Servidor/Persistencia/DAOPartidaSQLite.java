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
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getEstado());
            pstmt.setString(4, p.getJPat());
            pstmt.setString(5, p.getJPes());
            pstmt.setString(6, p.getPes1());
            pstmt.setString(7, p.getPes2());
            pstmt.setString(8, p.getPes3());
            pstmt.setString(9, p.getPes4());
            pstmt.setString(10, p.getPat1());
            pstmt.setString(11, p.getPat2());
            pstmt.setString(12, p.getLancha());
            pstmt.setString(13, p.getHeli());
            pstmt.setInt(14, p.getCombusJPes());
            pstmt.setInt(15, p.getCombusJPat());
            pstmt.setInt(16, p.getCantPeces());
            pstmt.setInt(17, p.getTiempo());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println("Error al guardar una partida.\n" + e.getMessage());
        }	// try-catch
	}	// guardarPartida

	public boolean miembro(IConexion icon, String nom) throws SQLException {
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
        
    	query = consul.darPartidaPorNombre();
    	pstmt = con.prepareStatement(query);
    	pstmt.setString(1, nom);
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
	 * @throws SQLException 
	 */
	public List<Partida> partidasCreadas(IConexion icon) throws SQLException {
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
		        
		    	query = consul.partidasXEstado();
		    	pstmt = con.prepareStatement(query);
		        pstmt.setString(1,"CREADA");
		        rs = pstmt.executeQuery();
		  	
		  	// Si el jugador existe se crea el objeto y se lo devuelve.
		  	/*if (rs.next()) {
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
		  	}*/
		  	rs.close();
		  	pstmt.close();
		return lista;		
		
	}// listarPartidas
	
	public Partida partidaPorId(IConexion icon, String id) throws SQLException{
		Partida part = null;
		
		return part;
	}	// partidaPorId
	

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
