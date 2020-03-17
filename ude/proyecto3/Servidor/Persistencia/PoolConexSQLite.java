/*
 * PoolConexSQLite.
 * 
 * Esta clase implementa la interfaz IPoolConexiones. Se encarga de administrar
 * un pool de conexiones a una base de datos SQLite como mecanismo de persistencia
 * del juego.
 */

package ude.proyecto3.Servidor.Persistencia;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ude.proyecto3.Servidor.Persistencia.IConexion;
import ude.proyecto3.Servidor.Persistencia.IPoolConexiones;


public class PoolConexSQLite implements IPoolConexiones {
	private String dbURL;
	private String user;
	private String dbDriver;
	private String password;
	private int tope;
	private int tamanio;
	private int creadas;
	private int nivelTransaccionalidad;
	private IConexion[] conexiones;
	private static PoolConexSQLite poolConexiones;
	
	static Logger logger = Logger.getLogger(PoolConexSQLite.class.getName());
	
	/* 
	 * Constructor de la clase. Realiza la carga del driver, solicita memoria para el arreglo con tope e inicializa
	 * los distintos atributos.
	 * Es privado para que la clase sea singleton.
	 */
	private PoolConexSQLite(String url, String usr, String pswd, int tam, String dvr) {
		dbURL = url;
		///dbURL = "/usr/share/tomcat/webapps/servidor/base.db3";
		user = usr;
		password = pswd;
		///dbDriver = "";
		dbDriver = dvr;
		this.tope = -1;
		this.tamanio = tam;
		this.creadas = 0;
		this.nivelTransaccionalidad = Connection.TRANSACTION_NONE;

		conexiones = new ConexionSQLite [tamanio];
	}	// PoolConexiones
	
	/*
	 * Obtener la unica instancia del pool de conexiones.
	 */
	public static PoolConexSQLite getPoolConexiones(String url, String usr, String pswd, int tam, String drvr) {
		if (poolConexiones == null) {
			poolConexiones = new PoolConexSQLite(url, usr, pswd, tam, drvr);
		}	// if
		return poolConexiones;
	}	// getPoolConexiones
	
	@Override
	public IConexion obtenerConexion(boolean modifica) {
		IConexion con = null;
		
		if ((tope + 1) < tamanio) {
			try {
				// Cargar el manejador de bases SQLite.
				Class.forName("org.sqlite.JDBC");
				creadas++;
				nivelTransaccionalidad = (modifica ? Connection.TRANSACTION_SERIALIZABLE : Connection.TRANSACTION_NONE);
				Connection connection = DriverManager.getConnection(dbDriver + "://" + dbURL); //, user, password);
				//Connection connection = DriverManager.getConnection("jdbc:sqlite://home/nconde/eclipse-workspace/servidor/base.db3"); //, user, password);
				con = new ConexionSQLite(connection);
				conexiones[++tope] = con;
			}
			catch (SQLException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}	// try-catch
		}	// if
		
		return con;
	}	// obtenerConexion

	@Override
	public void liberarConexion(IConexion icon, boolean ok) {
		try {
			int i=0, j;
			
			// Buscar la conexion a cerrar y cerrarla.
			while (conexiones[i] != icon)
				i++;
			
			ConexionSQLite conSQLite = (ConexionSQLite)conexiones[i];
	        Connection con = conSQLite.getConexion();
	        con.close();
			// Se bajan las conexiones en el array desde la siguiente hasta el tope
			// para que no haya fragmentación (huecos).
			for (j = i + 1; j < tope; j++)
				conexiones[j] = conexiones[j + 1]; 
			tope--;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	// try-catch
	}	// liberarConexion

}	/* PoolConexSQLite */