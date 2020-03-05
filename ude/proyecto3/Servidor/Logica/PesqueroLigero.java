package ude.proyecto3.Servidor.Logica;

import ude.proyecto3.Servidor.Logica.Pesquero;

/*
 * PesqueroLiviano.
 * 
 * Clase concreta que implementa un buque pesquero liviano, pesca por arrastre.
 * 
 */
public class PesqueroLigero extends Pesquero {
	//public PesqueroLiviano(int c, int x, int y, float a, float v, int e) {
	public PesqueroLigero(int i, float a, float r, float x, float y, int e) {
		super(i, a, r, x, y, e);
	}
}	/* PesqueroLiviano */
