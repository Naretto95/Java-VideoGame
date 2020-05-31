package Jeu;

import java.io.Serializable;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Item extends Objet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean Etat;
	private int Niveau;
	
	public Item(boolean _Etat, int _Niveau, boolean _Ramassé) {
		this.setEtat(_Etat);
		this.setNiveau(_Niveau);
		this.setRamassé(_Ramassé);
	}
	
	public boolean isEtat() {
		return Etat;
	}

	public void setEtat(boolean etat) {
		Etat = etat;
	}

	public int getNiveau() {
		return Niveau;
	}

	public void setNiveau(int niveau) {
		Niveau = niveau;
	}
	
}
