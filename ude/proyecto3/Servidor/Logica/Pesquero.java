package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.Vehiculo;

public abstract class Pesquero extends Vehiculo {
	private int energia;
	
	public Pesquero(String i, float angulo, float rotacion, float posx, float posy, int ener)
	{
		super (i, angulo, rotacion, posx, posy);
		energia = 0;
	}	// Pesquero
	
	public void setEnergia(int ener) {
		if (ener != 0) {
			energia=ener;
		}	// if
	}	// setJugadorPesquero
		
	public int getEnergia() {
		return energia;
	}	// getEnergia

}	/* Pesquero */