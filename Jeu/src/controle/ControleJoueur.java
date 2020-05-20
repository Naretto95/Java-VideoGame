package controle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Jeu.Carte;
import Jeu.Case;
import Jeu.Joueur;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ControleJoueur implements Observer,EventHandler<KeyEvent>,Runnable{
	
	
	private Carte carte;
	private Joueur joueur;
	private GraphicsContext gc;
	

	private int positionXPixel;
	private int positionYPixel;
	private double indice=0;
	private int distMinBordEcranX=20;
	private int distMinBordEcranY=20;
	private int vitesse = 20;
	private boolean avanceD = false;
	private boolean avanceG = false;
	private boolean avanceH = false;
	private boolean avanceB = false;
	
	
	private int hauteurPixelEntite=120;
	private int largeurPixelEntite=120;
	private int deltaXHitBox;
	private int deltaYHitBox;

	private Image feuilleDeSpriteEntite = new Image("file:link.png");
	
	

	public ControleJoueur(Carte carte, Joueur joueur, GraphicsContext gc){
		super();
		this.carte = carte;
		this.deltaXHitBox=0;
		this.deltaYHitBox=0;
		this.joueur = joueur;
		this.gc=gc;
		this.positionXPixel=((this.joueur.getPositionX()+1)*this.carte.getLargeurCasePixel())-this.carte.getLargeurCasePixel()/2 - this.carte.getFenetreEcran().getPosXPixelEcran()-this.largeurPixelEntite/2;
		this.positionYPixel=((this.joueur.getPositionY()+1)*this.carte.getHauteurCasePixel())-this.carte.getHauteurCasePixel()/2 - this.carte.getFenetreEcran().getPosYPixelEcran()-(this.hauteurPixelEntite/2);
		this.carte.mettreEntite(joueur, this.joueur.getPositionY(), this.joueur.getPositionX());
		afficheCarte();
		afficheJoueur(KeyCode.RIGHT);
	}
	
	public void handle(KeyEvent event){
		//si on appuye sur une touche
		if (event.getEventType()==KeyEvent.KEY_PRESSED) {
			System.out.println("vous avez cliqué");
			switch(event.getCode()) {
			case UP:
				avanceB=false;
				avanceH=true;
				break;
			case DOWN:
				avanceH=false;
				avanceB=true;
				break;
			case LEFT:
				avanceD=false;
				avanceG=true;
				break;
			case RIGHT:
				avanceG=false;
				avanceD=true;
				break;
			default:
				break;
			
			}
		}
		//si on relache la touche
		if (event.getEventType()==KeyEvent.KEY_RELEASED) {
			System.out.println("vous avez relache");
			switch (event.getCode()) {
				case UP:
					avanceH=false;
					break;
				case DOWN:
					avanceB=false;
					break;
				case LEFT:
					avanceG=false;
					break;
				case RIGHT:
					avanceD=false;
					break;
				default:
					break;
		
			}
		}
	}
	
	
	public void update(Observable o, Object arg) {
	}
	
	public void run() {
		//le thread qui execute les mouvements en fonction de l'état des variables : avanceH,avanceB,avanceG,avanceD
		while(true) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(this.avanceD) {
				deplacer(KeyCode.RIGHT);
				afficheCarte();
				afficheJoueur(KeyCode.RIGHT);
			}
			if(this.avanceH) {
				deplacer(KeyCode.UP);
				afficheCarte();
				afficheJoueur(KeyCode.UP);
			}
			if(this.avanceB) {
				deplacer(KeyCode.DOWN);
				afficheCarte();
				afficheJoueur(KeyCode.DOWN);
			}
			if(this.avanceG) {
				deplacer(KeyCode.LEFT);
				afficheCarte();
				afficheJoueur(KeyCode.LEFT);
			}
		}
	}
	
	
	public void deplacer(KeyCode kc) {

		System.out.println("le joueur a bouge");
		
		switch (kc) {
		case UP:
			//si le perso n'a pas atteint le bord de l'écran
			if (this.getPositionYPixel()-vitesse-this.distMinBordEcranY>0) {
				//si le perso s'approche d'une nouvelle case(il ne faut pas superposer les persos dont le sprite dépasse la taille standard d'une case)
				if(this.carte.getFenetreEcran().getPosYPixelEcran()+this.getPositionYPixel()+this.hauteurPixelEntite/2 -this.deltaYHitBox/2-vitesse<(this.joueur.getPositionY())*this.carte.getHauteurCasePixel()) {

					System.out.println("je change vers le haut");
					//si cette case est libre
					if(this.carte.getCase(this.joueur.getPositionY()-1, this.joueur.getPositionX()).getContenu()==Case.VIDE) {
						this.changerPositionPixel(0, -vitesse);
						//si le perso s'est non seulement approché masi est aussi rentré dans la case
						if (this.carte.getFenetreEcran().getPosYPixelEcran()+this.getPositionYPixel()+this.hauteurPixelEntite/2<this.joueur.getPositionY()*this.carte.getHauteurCasePixel()) {
							this.carte.mettreEntite(this.joueur, this.joueur.getPositionY()-1, this.joueur.getPositionX());
						}
					}
				// si le perso ne s'approche pas d'une nouvelle case
				}else {
					this.changerPositionPixel(0, -vitesse);
				}
			}
			break;
		case DOWN:
			if (this.getPositionYPixel()+this.hauteurPixelEntite+vitesse+this.distMinBordEcranY<this.carte.getFenetreEcran().getHauteurPixelEcran()) {
					if(this.carte.getFenetreEcran().getPosYPixelEcran()+this.getPositionYPixel()+this.hauteurPixelEntite/2 +this.deltaYHitBox/2+vitesse>(this.joueur.getPositionY()+1)*this.carte.getHauteurCasePixel()) {
						if(this.carte.getCase(this.joueur.getPositionY()+1, this.joueur.getPositionX()).getContenu()==Case.VIDE) {
							this.changerPositionPixel(0, vitesse);
							if(this.carte.getFenetreEcran().getPosYPixelEcran()+this.getPositionYPixel()+this.hauteurPixelEntite/2>(this.joueur.getPositionY()+1)*this.carte.getHauteurCasePixel()){
								this.carte.mettreEntite(this.joueur, this.joueur.getPositionY()+1, this.joueur.getPositionX());
							}
						}
					}else {
						this.changerPositionPixel(0, vitesse);
					}
				}
			break;
		case LEFT:
			if (this.getPositionXPixel()-vitesse-this.distMinBordEcranX>0) {
					if(this.carte.getFenetreEcran().getPosXPixelEcran()+this.getPositionXPixel()+this.largeurPixelEntite/2 -this.deltaXHitBox/2-vitesse<(this.joueur.getPositionX())*this.carte.getLargeurCasePixel()) {
						if(this.carte.getCase(this.joueur.getPositionY(), this.joueur.getPositionX()-1).getContenu()==Case.VIDE) {
							this.changerPositionPixel(-vitesse, 0);
							if(this.carte.getFenetreEcran().getPosXPixelEcran()+this.getPositionXPixel()+this.largeurPixelEntite/2<(this.joueur.getPositionX())*this.carte.getLargeurCasePixel()){
								this.carte.mettreEntite(this.joueur, this.joueur.getPositionY(), this.joueur.getPositionX()-1);
							}
							
						}
					}else {
						this.changerPositionPixel(-vitesse, 0);
					}
				}
			break;
		case RIGHT:
			if (this.getPositionXPixel()+this.largeurPixelEntite+vitesse+this.distMinBordEcranX<this.carte.getFenetreEcran().getLargeurPixelEcran()) {
					if(this.carte.getFenetreEcran().getPosXPixelEcran()+this.getPositionXPixel()+this.largeurPixelEntite/2 +this.deltaXHitBox/2+vitesse>(this.joueur.getPositionX()+1)*this.carte.getLargeurCasePixel()) {
						if(this.carte.getCase(this.joueur.getPositionY(), this.joueur.getPositionX()+1).getContenu()==Case.VIDE) {
							this.changerPositionPixel(vitesse, 0);
							if(this.carte.getFenetreEcran().getPosXPixelEcran()+this.getPositionXPixel()+this.largeurPixelEntite/2>(this.joueur.getPositionX()+1)*this.carte.getLargeurCasePixel()) {
								this.carte.mettreEntite(this.joueur, this.joueur.getPositionY(), this.joueur.getPositionX()+1);
							}
						}
					}else {
						this.changerPositionPixel(vitesse, 0);
					}
				}
			break;
		default:
			break;
		}
		System.out.println("je suis en case :"+this.joueur.getPositionY()+" ,"+this.joueur.getPositionX());
	}
	
	
	public void effaceJoueur() {
		gc.fillRect(this.getPositionXPixel()-20, this.getPositionYPixel()-20,200,200);
	}
	
	public void afficheJoueur(KeyCode keycode) {
		indice=(indice+1)%10;
		switch(keycode) {
		case RIGHT:
			gc.drawImage(this.feuilleDeSpriteEntite,indice*this.largeurPixelEntite,925,this.largeurPixelEntite,this.hauteurPixelEntite,this.getPositionXPixel(),this.getPositionYPixel(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
			break;
		case LEFT:
			gc.drawImage(this.feuilleDeSpriteEntite,indice*this.largeurPixelEntite,660,this.largeurPixelEntite,this.hauteurPixelEntite,this.getPositionXPixel(),this.getPositionYPixel(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
			break;
		case UP:
			gc.drawImage(this.feuilleDeSpriteEntite,indice*this.largeurPixelEntite,785,this.largeurPixelEntite,this.hauteurPixelEntite,this.getPositionXPixel(),this.getPositionYPixel(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
			break;
		case DOWN:
			gc.drawImage(this.feuilleDeSpriteEntite,indice*this.largeurPixelEntite,530,this.largeurPixelEntite,this.hauteurPixelEntite,this.getPositionXPixel(),this.getPositionYPixel(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
			break;
		default:
			break;
		}

	}
	
	public void changerPositionPixel(int deltaXPixel, int deltaYPixel) {
		this.setPositionXPixel(this.getPositionXPixel()+deltaXPixel);
		this.setPositionYPixel(this.getPositionYPixel()+deltaYPixel);
		System.out.println("Ma nouvelle position est "+this.getPositionXPixel()+","+this.getPositionYPixel());
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
	
	public void afficheCarte() {
		ArrayList<ArrayList<Image>> imagesCasesCarte = carte.getImagesCasesCarte();
		for (int i = 0 ; i < imagesCasesCarte.size();i++) {
			for (int j = 0 ; j < imagesCasesCarte.get(i).size();j++) {
				gc.drawImage(imagesCasesCarte.get(i).get(j), this.carte.getHauteurCasePixel()*j, this.carte.getLargeurCasePixel()*i);
			}
		}
	}
}
