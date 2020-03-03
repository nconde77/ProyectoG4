package ude.proyecto3.Servidor.Logica;

public class Vehiculo {

	private float angulo, rotacion, posX, posY;
	private int id;//,energia;
	
	public Vehiculo(int i,float ang, float rot, float posx, float posy) {
		
		id=i;
		angulo = ang;
		rotacion=rot;
		posX=posx;
		posY=posy;
		//energia=ener;
	}
	
	public void setId(int i) {
		if (i != 0) {
			id = i;
		}	// if
	}	// set
	
	public void setAngulo(float ang) {
		if (ang != 0) {
			angulo = ang;
		}	// if
	}	// set
	
	public void setRotacion(float rot) {
		if (rot != 0) {
			rotacion = rot;
		}	// if
	}	// set
	
	public void setPosX(float posx) {
		if (posx != 0) {
			posX = posx;
		}	// if
	}	// set
	
	public void setPosY(float posy) {
		if (posy != 0) {
			posY = posy;
		}	// if
	}	// set
	
	/*public void setEnergia(int ener) {
		if (ener != 0) {
			energia = ener;
		}	// if
	}*/// set
	
	public int getId() {
		return id;
	}	
	
	public float getAngulo() {
		return angulo;
	}	
	
	public float getRotacion() {
		return rotacion ;
	}
	
	public float getPosY() {
		return posY;
	}
	
	public float getPosX() {
		return posX ;
	}
	
	/*public int getEnergia() {
		return energia;
	}*/	
	
}