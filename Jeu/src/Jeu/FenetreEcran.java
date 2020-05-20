package Jeu;

public class FenetreEcran {
	
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
		if (this.getPosXPixelEcran()+deltaX<this.limXPixelEcran && this.getPosYPixelEcran()+deltaY<this.limYPixelEcran) {
			this.setPosXPixelEcran(this.getPosXPixelEcran()+deltaX);
			this.setPosYPixelEcran(this.getPosYPixelEcran()+deltaY);
			return true;
		}
		return false;
	}

	public int getPosXPixelEcran() {
		return posXPixelEcran;
	}

	private void setPosXPixelEcran(int posXPixelEcran) {
		this.posXPixelEcran = posXPixelEcran;
	}

	public int getPosYPixelEcran() {
		return posYPixelEcran;
	}

	private void setPosYPixelEcran(int posYPixelEcran) {
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
