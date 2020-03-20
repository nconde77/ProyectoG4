package ude.proyecto3.Servidor.Persistencia;


public class Consultas {
	
		/** Guardar elementos **/
	
	/**
	* Insertar id, nombre, jugador1 y jugador2 de una partida.
	* @return Devuelve el identificador de la partida guardada.
	*/
	public String guardarPartida() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}	// guardarPartida
	
	/**
	* Devuelve la consulta SQLite para guardar un pesquero de clase "fábrica".
	*/
	public String guardarPesqueroFabrica() {
		return "INSERT INTO Pesqueros VALUES (?, \"Fabrica\", ?, ?, ?, ?, ?)"; 
	}	// guardarPesqueroFabrica
	
	/**
	* Devuelve la consulta SQLite para guardar un pesquero de clase "ligero".
	*/
	public String guardarPesqueroLigero() {
		return "INSERT INTO Pesqueros VALUES (?, \"Ligero\", ?, ?, ?, ?, ?)"; 
	}	// guardarPesqueroLigero
	
	public String guardarHelicoptero() {
		return "INSERT INTO Pesqueros VALUES (?, \"Helicoptero\", ?, ?, ?, ?, ?)"; 
	}	// guardarHelicoptero
	
	public String guardarLancha() {
		return "INSERT INTO Pesqueros VALUES (?, \"Lancha\", ?, ?, ?, ?, ?)"; 
	}	// guardarLancha
	
	public String guardarOPVPesado() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)";
	}	// guardarOPVPesado
	
	public String guardarOPVLigero() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)"; 
	}	// guardarOPVLigero
	
	public String guardarJugador() {
		return "INSERT INTO Jugadores VALUES (?, ?, ?, ?, ?, ?)";
	}	// darUsuarioPorNombre
	
	
			/** Listas de elementos **/
	/**
	 * Listar los jugadores.
	 */
	public String listarJugadores() {
		return "SELECT Id, Nombre FROM Jugadores ORDER BY Id";
	}	// darUsuarioPorNombre
	
	/**
	 * Listar los top ? jugadores.
	 */
	public String topNJugadores() {
		return "SELECT * FROM Jugadores ORDER BY Puntaje DESC LIMIT ?";
	}	// darUsuarioPorNombre
	
	/**
	 * Listar por Id en la tabla que se indique.
	 */
	public String listarPorId() {
		return "SELECT * FROM ? ORDER BY Id";
	}	// listarPorId
	
	/**
	 * Listar las partidas creadas y no iniciadas.
	 */
	public String listarPartidasCreadas() {
		return "SELECT (CASE WHEN JugPat IS NULL THEN JugPes ELSE JugPat END), " +
				"(CASE WHEN JugPat IS NULL THEN 'Pesquero' ELSE 'Patrullero' END), " +
				"NomPart FROM Partidas";
	}	// listarPartidasElegibles
	
	/**
	 * Listar pesqueros por tipo.
	 */
	public String listarPesqueros() {
		return "SELECT * FROM Pesqueros WHERE Tipo = ?";
	}	// listarTodos
	
	/**
	 * Listar OPVs por tipo.
	 */
	public String listarOPVs() {
		return "SELECT * FROM Patrulleros WHERE Tipo = ?";
	}	// listarTodos
	
	
			/** Buscar elementos  */
	
	/**
	 * Buscar un usuario por su nombre.
	 */
	public String darJugadorPorNombre() {
		return "SELECT * FROM Jugadores WHERE Nombre = ?";
	}	// darUsuarioPorNombre

	public String darJugadorPorId() {
		return "SELECT * FROM Jugadores WHERE Id = ?";
	}	// darUsuarioPorNombre
	
	/**
	 * Dados los dos usuarios que jugaban, retorna su partida.
	 */
	public String darPartidaPorNombre() {
		return "SELECT * FROM Partidas WHERE Nombre = ?";
	}	// darPartidaPorUsuarios
	
	public String encontrarPorId() {
//		No se puede pasar el nombre de la table como parámetro a un Prep. Statement.
		// return "SELECT * FROM ? WHERE Id = ?";
		return null;
	}	// encontrarPorId
	
	public String eliminarPorId() {
//		No se puede pasar el nombre de la table como parámetro a un Prep. Statement.
		// return "DELETE FROM ? WHERE Id = ?";
		return null;
	}	// encontrarPorId
	
	public String partidasPorEstado() {
		return "SELECT * FROM Partidas WHERE Estado = ?";
	}	// partidasPorEstado

	
			/** Eliminar elementos **/
	
	/**
	 * Eliminar un jugador.
	 */
	public String eliminarJugador() {
		return "DELETE FROM Jugadores WHERE Nombre = ?";
	}	// darUsuarioPorNombre

}	/* Consultas */