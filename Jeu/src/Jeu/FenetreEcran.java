package Jeu;

import java.io.Serializable;
/**
 * 
 * @author Corentin BRILLANT
 *
 */
public class FenetreEcran implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int posXPixelEcran;
	private int posYPixelEcran;
	private int limXPixelEcran;
	private int limYPixelEcran;
	private int largeurPixelEcran;
	private int hauteurPixelEcran;
	
	public FenetreEcran(int x,int y, int width, int height,int limX, int limY) {
		this.posXPixelEcran=x;
		this.posYPixelEcran=y;
		this.limXPixelEcran=limX;
		this.limYPixelEcran=limY;
		this.largeurPixelEcran=width;
		this.hauteurPixelEcran=height;
	}
	
	public boolean deplaceFenetreEcran(int deltaX,int deltaY){
		if (deltaX>0) {
		if (this.getPosXPixelEcran()+deltaX<this.limXPixelEcran) {
			this.setPosXPixelEcran(this.getPosXPixelEcran()+deltaX);
			this.setPosYPixelEcran(this.getPosYPixelEcran()+deltaY);
			return true;
		}
		}
		if (deltaX<0) {
			if (this.getPosXPixelEcran()+deltaX>0) {
				this.setPosXPixelEcran(this.getPosXPixelEcran()+deltaX);
				this.setPosYPixelEcran(this.getPosYPixelEcran()+deltaY);
				return true;
			}
			}
		if (deltaY>0) {
			if (this.getPosYPixelEcran()+deltaY<this.limYPixelEcran) {
				this.setPosXPixelEcran(this.getPosXPixelEcran()+deltaX);
				this.setPosYPixelEcran(this.getPosYPixelEcran()+deltaY);
				return true;
			}
			}
		if (deltaY<0) {
			if (this.getPosYPixelEcran()+deltaY>0 ) {
				this.setPosXPixelEcran(this.getPosXPixelEcran()+deltaX);
				this.setPosYPixelEcran(this.getPosYPixelEcran()+deltaY);
				return true;
			}
			}
		return false;
	}

	public int getPosXPixelEcran() {
		return posXPixelEcran;
	}

	public void setPosXPixelEcran(int posXPixelEcran) {
		this.posXPixelEcran = posXPixelEcran;
	}

	public int getPosYPixelEcran() {
		return posYPixelEcran;
	}

	public void setPosYPixelEcran(int posYPixelEcran) {
		this.posYPixelEcran = posYPixelEcran;
	}

	public int getLimXPixelEcran() {
		return limXPixelEcran;
	}

	public int getLimYPixelEcran() {
		return limYPixelEcran;
	}

	public int getLargeurPixelEcran() {
		return largeurPixelEcran;
	}

	public int getHauteurPixelEcran() {
		return hauteurPixelEcran;
	}

}
