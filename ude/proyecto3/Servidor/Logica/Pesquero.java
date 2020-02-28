package ude.proyecto3.Servidor.Logica;

/*
 * Pesquero.
 * 
 * Clase abstracta para derivar de ella los buques pesqueros.
 * 
 */
public abstract class Pesquero {
	private int carga, posX, posY;
	private float ang, vel;
	
	public Pesquero(int c=0; int x=0, int y=0, float a=0, float v=0) {
		ang   = a;
		vel   = v;
		carga = c;
		posX  = x;
		posY  = y;
	}	// Pesquero
	
	public int getCarga {
		return carga;
	}	// getPosX
	
	public int getPosX {
		return posX;
	}	// getPosX
	
	public int getPosY {
		return posY;
	}	// getPosY
	
	public float getAngulo {
		return ang;
	}	// getAngulo
	
	public getVelocidad {
		return vel;
	}	// getVelocidad
	
	public void setCarga(int c) {
		carga = c;
	}	// setcarga
	
	public void setPosX(int x) {
		posX = x;
	}	// setPosX
	
	public void setPosY(int y) {
		posY = y;
	}	// setPosY
	
	public void setAngulo(float a) {
		ang = a;
	}	// setAngulo
	
	public void setVelocidad(float v) {
		vel = v;
	}	// setVelocidad
	
}	/* Pesquero */
