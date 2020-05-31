package Jeu;

import java.io.Serializable;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Objet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean Ramassé;

	public boolean isRamassé() {
		return Ramassé;
	}

	public void setRamassé(boolean ramassé) {
		Ramassé = ramassé;
	}
	

}
