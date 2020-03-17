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
import java.util.logging.Level;
import java.util.logging.Logger;

import ude.proyecto3.Servidor.Logica.EstadoPartida;
import ude.proyecto3.Servidor.Logica.FachadaSQLite;
import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Logica.Partida;
import ude.proyecto3.Servidor.Logica.PartidaCreada;
import ude.proyecto3.Servidor.Logica.PesqueroFabrica;
import ude.proyecto3.Servidor.Persistencia.Consultas;


public class DAOPartidaSQLite implements IDAOPartida {
	Logger logger = Logger.getLogger(DAOPartidaSQLite.class.getName());
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
        	logger.log(Level.INFO, "DAOPartida: guardar: " + p.getNombre());
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
	
	
	/**
	 *  Encontrar por nombre o correo-e.
	 */
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
	
	
	/**
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
	
	
	/**
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
	@Override
	public List<PartidaCreada> partidasCreadas(IConexion icon) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		PreparedStatement pstmt;
		ResultSet rs = null;
		String query, bando, idUsu;
		List<PartidaCreada> lista = new ArrayList<PartidaCreada>();
		PartidaCreada part = new PartidaCreada();
		
		if (con == null) {
			throw new SQLException("No hay conexiones disponibles.");
        }	// if
        
    	query = consul.partidasPorEstado();
    	pstmt = con.prepareStatement(query);
        pstmt.setString(1, "CREADA");
        rs = pstmt.executeQuery();
	  	
        // Cargar la lista desde el result set.
        while (rs.next()) {
        	idUsu = rs.getString("IdJPat");
        	
        	if (idUsu == null) {
        		bando = "Pesquero";
        		idUsu = rs.getString("IdJPes");
        	}
        	else {
        		bando = "Patrullero";
        		idUsu = rs.getString("IdJPat");
        	}	// if
        	
        	part.setId(rs.getString("Id"));
        	part.setNombre(rs.getString("Nombre"));
        	part.setJugador(idUsu);
        	part.setBando(bando);
        	logger.log(Level.INFO, part.enJSON());
        	lista.add(part);
        	logger.log(Level.INFO, lista.toString());
        }	// while
        
	  	rs.close();
	  	pstmt.close();
	  	logger.log(Level.INFO, "dao partidas: Fin.");
	  	
		return lista;
	}	// partidasCreadas
		
		
	/**
	 * Lista las partidas.
	 * @throws SQLException 
	 */
	public List<Partida> partidas(IConexion icon) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		PreparedStatement pstmt;
		ResultSet rs = null;
		String query, bando, idUsu;
		EstadoPartida est;
		List<Partida> lista = new ArrayList<Partida>();
		Partida part = new Partida();
		
		logger.log(Level.INFO, "dao partidas: partidasCreadas: calculando...");
		if (con == null) {
			throw new SQLException("No hay conexiones disponibles.");
        }	// if
        
    	query = consul.partidasPorEstado();
    	pstmt = con.prepareStatement(query);
        pstmt.setString(1, "CREADA");
        rs = pstmt.executeQuery();
	  	
        // Cargar la lista desde el result set.
        while (rs.next()) {
        	if (rs.getString("idJPat").equals(null)) {
        		bando = "Pesquero";
        		idUsu = rs.getString("idJPes");
        	}
        	else {
        		bando = "Patrullero";
        		idUsu = rs.getString("idJPat");
        	}	// if
        	
        	switch (rs.getString("Estado")) {
    			case "CREADA":
    				est = EstadoPartida.CREADA;
    				break;
        		case "INICIADA":
        			est = EstadoPartida.INICIADA;
        			break;
        		case "PAUSADA":
        			est = EstadoPartida.PAUSADA;
        			break;
        		case "TERMINADA":
    				est = EstadoPartida.TERMINADA;
    				break;
        		default:
        			est = null;
        			break;
        	}	// switch
        	
        	part.inicializar(rs.getString("Id") , rs.getString("Nombre"),  idUsu, bando);
        	part.setPat1(rs.getString("idPat1"));
        	part.setPat2(rs.getString("idPat2"));
        	part.setLancha(rs.getString("idLan"));
        	part.setHeli(rs.getString("idHeli"));
        	part.setPes1(rs.getString("idPes1"));
        	part.setPes2(rs.getString("idPes2"));
        	part.setPes3(rs.getString("idPes3"));
        	part.setPes4(rs.getString("idPes4"));
        	part.setEstado(est);
        	part.setCantidadPeces(rs.getInt("CantPeces"));
        	part.setCombusJPat(rs.getInt("ComJPat"));
        	part.setCombusJPes(rs.getInt("ComJPes"));
        	part.setTiempo(rs.getInt("CantTiempo"));
        	lista.add(part);
        }	// while
        
	  	rs.close();
	  	pstmt.close();
	  	logger.log(Level.INFO, "dao partidas: Fin.");
	  	
		return lista;
	}	// partidas
	
	
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
		}	// switch
	}	// obtenerEstadoId

}	/* DAOPartida */
