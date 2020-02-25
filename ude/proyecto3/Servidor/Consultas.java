package ude.proyecto3.Servidor;


public class Consultas {
	/*
	 * Insertar un jugador.
	 */
	public String insertarJugador() {
		return "INSER INTO Jugadores VALUES (?, ?, ?, ?)";
	}	// darUsuarioPorNombre
	
	/*
	 * Buscar un usuario por su nombre.
	 */
	public String darUsuarioPorNombre() {
		return "SELECT * FROM Usuarios WHERE Nombre = ?";
	}	// darUsuarioPorNombre
	
	/*
	 * Dados los dos usuarios que jugaban, retorna su partida.
	 */
	public String darPartidaPorUsuarios() {
		return "SELECT * FROM Partidas WHERE Juagdor1 = ? AND Jugador2 = ?";
	}	// darPartidaPorUsuarios
	
	/*
	 * Insertar id, nombre, jugador1 y jugador2 de una partida.
	 */
	public String guardarPartida() {
		return "INSERT INTO Partidas VALUES (?, ?, ?)";
	}	// guardarPartida
	
}	//	Consultas
