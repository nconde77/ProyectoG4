package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.Vehiculo;

public class Pesquero extends Vehiculo {
	private int energia;
	
	public Pesquero(int id,long angulo, long rotacion, long posx, long posy, int ener)
	{
		super (id,angulo,rotacion,posx,posy);
		
		energia = 0;
	}	
		public void setEnergia(int ener) {
			if (ener != 0) {
				energia=ener;
			}	// if
		}	// setJugadorPesquero
		public int getEnergia() {
		return energia;
	}
	
}
