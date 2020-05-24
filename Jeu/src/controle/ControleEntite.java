package controle;

import java.util.Observable;
import java.util.Observer;

import Jeu.Carte;
import Jeu.Carte.Porte;
import Jeu.Case;
import Jeu.Entité;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ControleEntite extends Observable implements Observer{
	
	public static Integer A_BOUGE = new Integer(10);
	
	protected Entité entite;
	protected Carte carte;
	protected GraphicsContext gc;
	protected Image feuilleDeSpriteEntite;
	
	protected boolean avanceD = false;
	protected boolean avanceG = false;
	protected boolean avanceH = false;
	protected boolean avanceB = false;
	protected int indiceSprite=0;

	protected int positionXPixel;
	protected int positionYPixel;
	protected int vitesse=5;
	protected int hauteurPixelEntite;
	protected int largeurPixelEntite;
	protected int deltaXHitBox=0;
	protected int deltaYHitBox=0;
	protected int paddingX;
	protected int paddingY;
	protected int facteurTaille=2;

	
	public ControleEntite(String feuilleDeSpriteEntite,Carte carte, GraphicsContext gc,Entité entite,int hauteurPixelEntite,int largeurPixelEntite) {
		this.feuilleDeSpriteEntite=new Image("file:"+feuilleDeSpriteEntite);
		this.hauteurPixelEntite=hauteurPixelEntite;
		this.largeurPixelEntite=largeurPixelEntite;
		this.entite=entite;
		this.carte = carte;
		this.gc = gc;
		this.carte.mettreEntite(entite, this.entite.getPositionY(), this.entite.getPositionX());
		this.positionXPixel=((this.entite.getPositionX())*this.carte.getLargeurCasePixel()) - this.carte.getFenetreEcran().getPosXPixelEcran();
		this.positionYPixel=((this.entite.getPositionY())*this.carte.getHauteurCasePixel()) - this.carte.getFenetreEcran().getPosYPixelEcran();
		this.paddingX=5;
		this.paddingY=20;
	}
	
	public boolean detecteCollision(int rect1x,int rect1y, int rect1w, int rect1h,int rect2x,int rect2y, int rect2w, int rect2h) {
		return ((rect1x +paddingX< rect2x + rect2w) && (rect1x-paddingX + rect1w > rect2x) && ( rect1y+paddingY < rect2y + rect2h) && ( rect1h + rect1y > rect2y));
	}
	
	public boolean detecteCollisionCaseMouvement(int i, int j,int deltaX, int deltaY) {
		if(i>=0 && j>=0 && i<this.carte.getImagesCasesCarte().size() && j<this.carte.getImagesCasesCarte().get(i).size()) {
			Case caseR = this.carte.getCase(i, j);
			if (caseR.getContenu() instanceof Porte) {this.setChanged();this.notifyObservers(caseR.getContenu());}
			return detecteCollision(this.getPositionXPixel()+deltaX,this.getPositionYPixel()+deltaY,this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel(),j*this.carte.getLargeurCasePixel()-this.carte.getFenetreEcran().getPosXPixelEcran(),i*this.carte.getHauteurCasePixel()-this.carte.getFenetreEcran().getPosYPixelEcran(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel()) && (caseR.getContenu()!=Case.VIDE);
		}
		return true;
	}
	
	public boolean detecteCollisionsMouvement(int deltaX,int deltaY) {
		//soit deltax est nul soit deltay est nul
		if (deltaX>0) {
			return (detecteCollisionCaseMouvement(this.entite.getPositionY()-1,this.entite.getPositionX()+1,deltaX,deltaY) ||detecteCollisionCaseMouvement(this.entite.getPositionY(),this.entite.getPositionX()+1,deltaX,deltaY)||detecteCollisionCaseMouvement(this.entite.getPositionY()+1,this.entite.getPositionX()+1,deltaX,deltaY));
		}
		if (deltaX<0) {
			return (detecteCollisionCaseMouvement(this.entite.getPositionY()-1,this.entite.getPositionX()-1,deltaX,deltaY) ||detecteCollisionCaseMouvement(this.entite.getPositionY(),this.entite.getPositionX()-1,deltaX,deltaY)||detecteCollisionCaseMouvement(this.entite.getPositionY()+1,this.entite.getPositionX()-1,deltaX,deltaY));
		}
		if (deltaY>0) {
			return (detecteCollisionCaseMouvement(this.entite.getPositionY()+1,this.entite.getPositionX()-1,deltaX,deltaY) ||detecteCollisionCaseMouvement(this.entite.getPositionY()+1,this.entite.getPositionX(),deltaX,deltaY)||detecteCollisionCaseMouvement(this.entite.getPositionY()+1,this.entite.getPositionX()+1,deltaX,deltaY));
		}
		if (deltaY<0) {
			return (detecteCollisionCaseMouvement(this.entite.getPositionY()-1,this.entite.getPositionX()-1,deltaX,deltaY) ||detecteCollisionCaseMouvement(this.entite.getPositionY()-1,this.entite.getPositionX(),deltaX,deltaY)||detecteCollisionCaseMouvement(this.entite.getPositionY()-1,this.entite.getPositionX()+1,deltaX,deltaY));
		}
		return true;
	}
	
	public void changerPositionPixel(int deltaXPixel, int deltaYPixel) {
		this.setPositionXPixel(this.getPositionXPixel()+deltaXPixel);
		this.setPositionYPixel(this.getPositionYPixel()+deltaYPixel);
		this.setChanged();
		this.notifyObservers(ControleEntite.A_BOUGE);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		
		
	}

	public int getPositionXPixel() {
		return positionXPixel;
	}

	public void setPositionXPixel(int positionXPixel) {
		this.positionXPixel = positionXPixel;
	}

	public int getPositionYPixel() {
		return positionYPixel;
	}

	public void setPositionYPixel(int positionYPixel) {
		this.positionYPixel = positionYPixel;
	}

	public Entité getEntite() {
		return entite;
	}
}
