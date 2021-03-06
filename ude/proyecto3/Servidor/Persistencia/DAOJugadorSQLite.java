package ude.proyecto3.Servidor.Persistencia;

//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ude.proyecto3.Servidor.Logica.FachadaSQLite;
import ude.proyecto3.Servidor.Logica.Jugador;
import ude.proyecto3.Servidor.Logica.PartidaCreada;
import ude.proyecto3.Servidor.Persistencia.Consultas;
import ude.proyecto3.Servidor.Persistencia.IDAOJugador;


public class DAOJugadorSQLite implements IDAOJugador {
	private Consultas consul;
	
	Logger logger = Logger.getLogger(DAOJugadorSQLite.class.getName());
	
	public DAOJugadorSQLite() {
		consul = new Consultas();
	}	// DAOJugadorSQLite
	
	@Override
	public void guardar(IConexion icon, Jugador j) throws FileNotFoundException, IOException, SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite) icon;
		Connection con = conSQLite.getConexion();
		String query;
		PreparedStatement pstmt;
		
        try {
            // Insertar el jugador en la base.
        	query = consul.guardarJugador();
        	pstmt = con.prepareStatement(query);
            pstmt.setString(1, j.getId());
            pstmt.setString(2, j.getNombre());
            pstmt.setString(3, j.getCorreo());
            pstmt.setString(4, j.getContrasenia());
            pstmt.setString(5, j.getSal());
            pstmt.setInt(6, j.getPuntaje());
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
	
	/**
	 * Encontrar por nombre.
	 */
	@Override
	public Jugador encontrarNombre(IConexion icon, String n) throws SQLException {
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
    				rs.getString("Correo"),
    				rs.getString("Contrasenia"),
    				rs.getString("Sal"),
    				rs.getInt("Puntaje"));
    	}	// if
    	rs.close();
    	pstmt.close();
        
        return j;
	}	// encontrar
	
	
	/**
	 * Encontrar por Id.
	 */
	@Override
	public Jugador encontrarId(IConexion icon, String i) throws SQLException {
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
        
    	query = consul.darJugadorPorId();
    	logger.log(Level.INFO, "encontrarId: " + query + ", " + i);
    	pstmt = con.prepareStatement(query);
    	pstmt.setString(1, i);
    	rs = pstmt.executeQuery();
    	
    	// Si el jugador existe se crea el objeto y se lo devuelve.
    	if (rs.next()) {
    		logger.log(Level.INFO, i + " encontrado.");
    		j = new Jugador(rs.getString("Id"),
    				rs.getString("Nombre"), 
    				rs.getString("Correo"),
    				rs.getString("Contrasenia"),
    				rs.getString("Sal"),
    				rs.getInt("Puntaje"));
    		j.sumarPuntos(rs.getInt("Puntaje"));
    		logger.log(Level.INFO, "daoJugador: encontrarId: " + j.enJSON());
    	}
    	rs.close();
    	pstmt.close();
    	
        return j;
	}	// encontrarId
	
	
	public ArrayList<Jugador> topNJugadores(IConexion icon, int cant) throws SQLException {
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		PreparedStatement pstmt;
		ResultSet rs = null;
		ArrayList<Jugador> lista = new ArrayList<Jugador>();
		String query;
		
		if (con == null) {
			throw new SQLException("No hay conexiones disponibles.");
        }	// if
        
    	query = consul.topNJugadores();
    	pstmt = con.prepareStatement(query);
        pstmt.setInt(1, cant);
        rs = pstmt.executeQuery();
	  	
        // Cargar la lista desde el result set.
        while (rs.next()) {
        	Jugador jug = new Jugador();
        	logger.log(Level.INFO, " Jugador " + rs.getString("Id"));
        	jug.setId(rs.getString("Id"));
        	jug.setNombre(rs.getString("Nombre"));
        	jug.setCorreo(rs.getString("Correo"));
        	jug.setContrasenia("");
        	jug.setSal("");
        	jug.setPuntaje(rs.getInt("Puntaje"));
        	lista.add(jug);
        }	// while
        
	  	rs.close();
	  	pstmt.close();
	  	
		return lista;
	}	// topNJugadores
	
	
	/**
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
        
        query = consul.listarJugadores();
    	pstmt = con.prepareStatement(query);
    	rs = pstmt.executeQuery();
    	aux = !(rs.isBeforeFirst());
    	rs.close();
    	pstmt.close();
        
        return aux;
	}	// esVacio
	
	/**
	 * @throws SQLException 
	 * 
	 */
	@Override
	public void actualizarPuntaje(IConexion icon, String uid, int p) throws SQLException {
        Jugador j = encontrarId(icon, uid);
		// Obtener una conexion concreta SQLite a la base.
		ConexionSQLite conSQLite = (ConexionSQLite)icon;
		Connection con = conSQLite.getConexion();
		
		PreparedStatement pstmt;
		ResultSet rs;
		String query;
        
        if (con == null) {
        	throw new SQLException("No hay conexiones disponibles.");
        }
        
    	query = consul.actPuntosJugador();
    	pstmt = con.prepareStatement(query);
    	pstmt.setInt(1, j.getPuntaje() + p);
    	pstmt.setString(2, uid);
    	rs = pstmt.executeQuery();
		logger.log(Level.INFO, "daoJugador: encontrarId: " + j.enJSON());
		pstmt.close();

	}	// actualizarPuntaje
}	/* DAOJugador */
