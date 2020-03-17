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
	
	/**
	* Insertar 
	* @return Devuelve el identificador de la partida guardada.
	*/
	public String guardarOPVPesado() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)";
	}	// guardarOPVPesado
	
	/**
	* Insertar 
	* @return Devuelve el identificador de la partida guardada.
	*/
	public String guardarOPVLigero() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)"; 
	}	// guardarOPVLigero
	

	/**
	 * Insertar un jugador.
	 */
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
	public String topJugadores() {
		return "SELECT Nombre, Puntaje FROM Jugadores ORDER BY Puntaje LIMIT ?";
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
	
	/**
	 * Dados los dos usuarios que jugaban, retorna su partida.
	 */
	public String darPartidaPorNombre() {
		return "SELECT * FROM Partidas WHERE Nombre = ?";
	}	// darPartidaPorUsuarios

	
			/** Eliminar elementos **/
	
	/**
	 * Eliminar un jugador.
	 */
	public String eliminarJugador() {
		return "DELETE FROM Jugadores WHERE Nombre = ?";
	}	// darUsuarioPorNombre
	
	public String eliminarPorId() {
		return "DELETE FROM ? WHERE Id = TRIM(?)";
	}	// eliminarPorId
	
	public String encontrarPorId() {
		return "SELECT * FROM ? WHERE Id = ?";
	}	// encontrarPorId
	
	public String partidasPorEstado() {
		return "SELECT * FROM Partidas WHERE Estado = ?";
	}	// partidasPorEstado

}	/* Consultas */