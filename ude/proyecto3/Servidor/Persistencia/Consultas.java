package ude.proyecto3.Servidor.Persistencia;


public class Consultas {
	/*
	 * Insertar un jugador.
	 */
	public String insertarJugador() {
		return "INSER INTO Jugadores VALUES (?, ?, ?, ?)";
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
	public String darPartidaPorJugadoress() {
		return "SELECT * FROM Partidas WHERE Juagdor1 = ? AND Jugador2 = ?";
	}	// darPartidaPorUsuarios
	
	/*
	 * Insertar id, nombre, jugador1 y jugador2 de una partida.
	 */
	public String guardarPartida() {
		return "INSERT INTO Partidas VALUES (?, ?, ?)";
	}	// guardarPartida
	
}	//	Consultas
