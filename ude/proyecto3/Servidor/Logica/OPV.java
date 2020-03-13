package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.Vehiculo;

public abstract class OPV extends Vehiculo {
	private int energia;
	
	public OPV(String id, float angulo, float rotacion, float posx, float posy, int ene)
	{
		super (id, angulo, rotacion, posx, posy);
		energia = ene;
	}	// OPV
	
	public int getEnergia() {
		return energia;
	}	// getEnergia
	
	public void setEnergia(int ene) {
		energia = ene;
	}	// setEnergia

}	/* OPV */