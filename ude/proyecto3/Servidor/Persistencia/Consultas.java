package ude.proyecto3.Servidor.Persistencia;


public class Consultas {
	/*
	 * Insertar un jugador.
	 */
	public String guardarJugador() {
		return "INSERT INTO Jugadores (Id, Nombre, Correo, Puntaje) VALUES (?, ?, ?, ?)";
	}	// darUsuarioPorNombre
	
	/*
	 * Eliminar un jugador.
	 */
	public String eliminarJugador() {
		return "DELETE FROM Jugadores WHERE Nombre = UPPER(TRIM(?))";
	}	// darUsuarioPorNombre
	
	public String listarPartidasElegibles() {
		return "SELECT (CASE WHEN JugPat IS NULL THEN JugPes ELSE JugPat END), " +
				"(CASE WHEN JugPat IS NULL THEN 'Pesquero' ELSE 'Patrullero' END), " +
				"NomPart FROM Partidas";
	}
	
	/*
	 * Buscar un usuario por su nombre.
	 */
	public String darJugadorPorNombre() {
		return "SELECT * FROM Usuarios WHERE Nombre = UPPER(TRIM(?))";
	}	// darUsuarioPorNombre
	
	/*
	 * Buscar un usuario por su nombre.
	 */
	public String listarJugadores() {
		return "SELECT Id,Nombre FROM Jugadores ORDER BY Id";
	}	// darUsuarioPorNombre
	
	/**
	 * Dados los dos usuarios que jugaban, retorna su partida.
	 */
	public String darPartidaPorJugadores() {
		return "SELECT * FROM Partidas WHERE Jugador1 = ? AND Jugador2 = ?";
	}	// darPartidaPorUsuarios
	
	/**
	 * Insertar id, nombre, jugador1 y jugador2 de una partida.
	 * @return Devuelve el identificador de la partida guardada.
	 */
	public String guardarPartida() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}	// guardarPartida
	
	/**
	 * Devuelve la consulta SQLite para guardar un pesquero de clase "fábrica".
	 */
	public String guardarPesqueroFabrica() {
		return "INSERT INTO Pesqueros VALUES (\"Fabrica\", ?, ?, ?, ?, ?)"; 
	}
	
	/**
	 * Devuelve la consulta SQLite para guardar un pesquero de clase "ligero".
	 */
	public String guardarPesqueroLigero() {
		return "INSERT INTO Pesqueros VALUES (\"Ligero\", ?, ?, ?, ?, ?)"; 
	}
	
	/**
	 * Insertar 
	 * @return Devuelve el identificador de la partida guardada.
	 */
	public String guardarOPVPesado() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)";
	}
	
	/**
	 * Insertar 
	 * @return Devuelve el identificador de la partida guardada.
	 */
	public String guardarOPVLigero() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)"; 
	}
	
	public String encontrarPesquero() {
		return "SELECT * FROM ? WHERE id = ?"; 
	}
//	public String eliminarPesqueroLigero() {
//		return "DELETE FROM PesqueroLigero WHERE id = TRIM(?)";
//	}
	public String listarPesquero() {
		return "SELECT Id FROM ? ORDER BY Id";
	}
//	public String encontrarPesqueroFabrica() {
//		return "SELECT * FROM PesqueroFabrica WHERE id = ?"; 
//	}
	public String eliminarPesquero() {
		return "DELETE FROM ? WHERE id = TRIM(?)";
	}
//	public String listarPesqueroFabrica() {
//		return "SELECT Id FROM PesqueroFabrica ORDER BY Id";
//	}

	public String encontrarPorId() {
		return "SELECT * FROM ? WHERE id = ?";
	}
	
}	/* Consultas */
