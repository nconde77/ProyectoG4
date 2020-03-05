package ude.proyecto3.Servidor.Persistencia;


public class Consultas {
	/*
	 * Insertar un jugador.
	 */
	public String insertarJugador() {
		return "INSERT INTO Jugadores VALUES (?, ?, ?, ?)";
	}	// darUsuarioPorNombre
	
	/*
	 * Eliminar un jugador.
	 */
	public String eliminarJugador() {
		return "DELETE FROM Jugadores WHERE Nombre = UPPER(TRIM(?))";
	}	// darUsuarioPorNombre
	
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
	
	/*
	 * Dados los dos usuarios que jugaban, retorna su partida.
	 */
	public String darPartidaPorJugadores() {
		return "SELECT * FROM Partidas WHERE Jugador1 = ? AND Jugador2 = ?";
	}	// darPartidaPorUsuarios
	
	/*
	 * Insertar id, nombre, jugador1 y jugador2 de una partida.
	 */
	public String guardarPartida() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}	// guardarPartida
	
	public String guardarPesqueroFabrica() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?, ?)"; 
	}
	public String guardarPesqueroLigero() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?, ?)"; 
	}
	public String guardarOPVPesado() {
		return "INSERT INTO Partidas VALUES (?, ?, ?, ?, ?)"; 
	}
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

	public String darPorId() {
		return "SELECT * FROM ? WHERE id = ?";
	}
	
}	//	Consultas
