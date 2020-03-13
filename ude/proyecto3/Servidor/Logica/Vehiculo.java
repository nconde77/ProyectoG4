package ude.proyecto3.Servidor.Logica;

public abstract class Vehiculo {
	private float angulo, rotacion, posX, posY;
	private String id;
	
	public Vehiculo(String i, float ang, float rot, float posx, float posy) {
		id = i;
		angulo = ang;
		rotacion = rot;
		posX = posx;
		posY = posy;
	}	// Vehiculo
	
	
	public void setAngulo(float ang) {
			angulo = ang;
	}	// setAngulo
	
	public void setRotacion(float rot) {
			rotacion = rot;
	}	// setRotacion
	
	public void setPosX(float posx) {
			posX = posx;
	}	// setPosX
	
	public void setPosY(float posy) {
			posY = posy;
	}	// setPosY
	
	public String getId() {
		return id;
	}	// getId	
	
	public float getAngulo() {
		return angulo;
	}	// getAngulo
	
	public float getRotacion() {
		return rotacion;
	}	// getRotacion
	
	public float getPosY() {
		return posY;
	}	// getPosY
	
	public float getPosX() {
		return posX;
	}	// getPosX

}	/* Vehiculo */