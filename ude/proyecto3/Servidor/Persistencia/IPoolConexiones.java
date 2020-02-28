package ude.proyecto3.Servidor.Persistencia;

public interface IPoolConexiones {
	
	/*
	* Solicita una conexión al pool. En caso de que todas están actualmente en
	* uso, bloqueará al usuario hasta que otro usuario libere alguna.
	*/
	public IConexion obtenerConexion(boolean modifica); 
	
	/*
	 * Devuelve una conexión al pool y avisa a posibles usuarios bloqueados.
	 *  Siok vale true, hará commit al devolverla, si no hará rollback. 
	 */
	public void liberarConexion(IConexion icon, boolean ok);
	
}	/* IPoolConexiones */
