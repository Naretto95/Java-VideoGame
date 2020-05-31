package controle;

import java.io.File;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import Jeu.Arme;
import Jeu.Carte;
import Jeu.Carte.Porte;
import Jeu.Case;
import Jeu.Enclume;
import Jeu.Entité;
import Jeu.Item;
import Jeu.Joueur;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import threadService.AttaqueService;
/**
 * @date 15/05/2020
 * @author Corentin BRILLANT
 */



public abstract class ControleEntite extends Observable implements Observer,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Integer A_BOUGE = new Integer(10);
	public static Image imageAttaque = new Image ("file:imagesitems/attaque.png");
	
	private Media sonEpee;
	private MediaPlayer mediaPlayer;
	private Entité entite;
	private Carte carte;
	private GraphicsContext gc;
	private Image feuilleDeSpriteEntite;
	
	private boolean avanceD = false;
	private boolean avanceG = false;
	private boolean avanceH = false;
	private boolean avanceB = false;
	private boolean attaqueEnCours = false;
	private int indiceSprite=0;
	private KeyCode lastDirection=KeyCode.DOWN;	
	private int speed=10;
	private int distance = 0;
	private int bouge=0;

	private int positionXPixel;
	private int positionYPixel;
	private int vitesse=5;
	private int hauteurPixelEntite;
	private int largeurPixelEntite;
	private int deltaXHitBox=0;
	private int deltaYHitBox=0;
	private int paddingX;
	private int paddingY;
	private int facteurTaille=2;

	
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
		this.entite.addObserver(this);
		try {
			File file = new File("musiques/song3.mp3");
			this.sonEpee = new Media(file.toURI().toString());
	        this.mediaPlayer = new MediaPlayer(sonEpee);
		}catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	/**{@literal cette méthode détecte la collision entre deux rectangles, elle retourne true si il y a collision}*/
	public boolean detecteCollision(int rect1x,int rect1y, int rect1w, int rect1h,int rect2x,int rect2y, int rect2w, int rect2h) {
		return ((rect1x +paddingX< rect2x + rect2w) && (rect1x-paddingX + rect1w > rect2x) && ( rect1y+paddingY < rect2y + rect2h) && ( rect1h + rect1y > rect2y));
	}
	
	/**{@literal cette méthode détecte la collision entre la case actuelle du joueur dont le déplacement est (deltaX,deltaY) en pixels et la case de coordonnées (i,j), elle retourne true si le mouvement est impossible et false sinon}*/
	public boolean detecteCollisionCaseMouvement(int i, int j,int deltaX, int deltaY) {
		if(i>=0 && j>=0 && i<this.carte.getImagesCasesCarte().size() && j<this.carte.getImagesCasesCarte().get(i).size()) {
			Object objet = this.carte.getCase(i, j).getContenu();
			if (this.getEntite() instanceof Joueur && objet instanceof Porte) {this.setChanged();this.notifyObservers(objet);}
			if (this.getEntite() instanceof Joueur && objet instanceof Enclume) {this.setChanged();this.notifyObservers(objet);}
			return detecteCollision(this.getPositionXPixel()+deltaX,this.getPositionYPixel()+deltaY,this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel(),j*this.carte.getLargeurCasePixel()-this.carte.getFenetreEcran().getPosXPixelEcran(),i*this.carte.getHauteurCasePixel()-this.carte.getFenetreEcran().getPosYPixelEcran(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel()) && (objet!=Case.VIDE);
		}
		return true;
	}
	
	/**{@literal cette méthode retourne true si le deplacement (int deltaX,int deltaY) en pixels est impossible et false sinon}*/
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
	
	/**{@literal cette méthode permet de lancer une attaque dans la derniere direction selon laquelle le personnage est orientée, elle retourne la distance de l'ennemi si l'attaque a été portée et -1 si l'attaque est impossible}*/
	public int attaque() {
		if (!this.isAttaqueEnCours()) {
		setAttaqueEnCours(true);
		(new AttaqueService(this)).start();
		if (this.mediaPlayer!=null) {this.mediaPlayer.seek(Duration.ZERO);this.mediaPlayer.play();}
		Item itemEnMain = this.getEntite().getEnMain();
			int portee=0;
			if (itemEnMain instanceof Arme) {portee= ((Arme) itemEnMain).getPortée();}
			int distance = 0;
			while (distance <= portee) {
				switch(this.lastDirection) {
				case UP:
					if (this.getEntite().getPositionY()-1-distance>-1) {
					Object objet1 = this.carte.getCase(this.getEntite().getPositionY()-1-distance, this.getEntite().getPositionX()).getContenu();
					if (objet1 instanceof Entité) {
						this.getEntite().Utiliser((Entité) objet1);
						return distance;
					}
					else {distance++;}
					}
					else {return -1;}
					break;
				case DOWN:
					if (this.getEntite().getPositionY()+1+distance<this.carte.getImagesCasesCarte().size()) {
					Object objet2 = this.carte.getCase(this.getEntite().getPositionY()+1+distance, this.getEntite().getPositionX()).getContenu();
					if (objet2 instanceof Entité) {
						this.getEntite().Utiliser((Entité) objet2);
						return distance;
					}
					else {distance++;}
					}
					else {return -1;}
					break;
				case RIGHT:
					if (this.getEntite().getPositionX()+1+distance<this.carte.getImagesCasesCarte().get(this.getEntite().getPositionY()).size()) {
					Object objet3 = this.carte.getCase(this.getEntite().getPositionY(), this.getEntite().getPositionX()+1+distance).getContenu();
					if (objet3 instanceof Entité) {
						this.getEntite().Utiliser((Entité) objet3);
						return distance; 
					}
					else {distance++;}
					}
					else {return -1;}
					break;
				case LEFT:
					if (this.getEntite().getPositionX()-1-distance>-1) {
					Object objet4 = this.carte.getCase(this.getEntite().getPositionY(), this.getEntite().getPositionX()-1-distance).getContenu();
					if (objet4 instanceof Entité) {
						this.getEntite().Utiliser((Entité) objet4);
						return distance; 
					}
					else {distance++;}
					}else  {return -1;}
					break;
				default:
					break;
				}
			}
		}
		return -1;
	}
	

	
	/**{@literal cette méthode déplace l'entite si c'est possible dans la direction KeyCode kc avec sa vitesse, elle prend en paramètres la direction et les distances minimales de l'entite avec le bord de l'écran, elle prend également le booléen indiquant si l'entité peut déplacer l'ecran sur la carte. Cette méthode a été conçu pour le joueur mais devra être modifiée pour déplacer les ennemis lorsque ceux -ci bougeront}*/
	public void deplacer(KeyCode kc, int distMinBordEcranX, int distMinBordEcranY, boolean canMoveScreen) {

		switch (kc) {
		case UP:
			if (!detecteCollisionsMouvement(0,-this.getVitesse()) && this.getPositionYPixel()-distMinBordEcranY-this.getVitesse()>0) {this.changerPositionPixel(0, -this.getVitesse());}
			else if (!detecteCollisionsMouvement(0,-this.getVitesse()) && canMoveScreen) {this.carte.deplaceFenetre(0, -this.getVitesse());}
			if(this.carte.getFenetreEcran().getPosYPixelEcran()+this.getPositionYPixel()+this.carte.getHauteurCasePixel()/2<(this.getEntite().getPositionY())*this.carte.getHauteurCasePixel()){
				this.carte.mettreEntite(this.getEntite(), this.getEntite().getPositionY()-1, this.getEntite().getPositionX());
			}
			break;
		case DOWN:
			if (!detecteCollisionsMouvement(0,this.getVitesse()) && this.getPositionYPixel()+this.carte.getHauteurCasePixel()+distMinBordEcranY+this.getVitesse()<this.carte.getFenetreEcran().getHauteurPixelEcran()) {this.changerPositionPixel(0, this.getVitesse());}
			else if (!detecteCollisionsMouvement(0,this.getVitesse()) && canMoveScreen) {this.carte.deplaceFenetre(0, this.getVitesse());}
			if(this.carte.getFenetreEcran().getPosYPixelEcran()+this.getPositionYPixel()+this.carte.getHauteurCasePixel()/2>(this.getEntite().getPositionY()+1)*this.carte.getHauteurCasePixel()){
				this.carte.mettreEntite(this.getEntite(), this.getEntite().getPositionY()+1, this.getEntite().getPositionX());
			}
			break;
		case LEFT:
			if (!detecteCollisionsMouvement(-this.getVitesse(),0) && this.getPositionXPixel()-distMinBordEcranX-this.getVitesse()>0) {this.changerPositionPixel(-this.getVitesse(), 0);}
			else if (!detecteCollisionsMouvement(-this.getVitesse(),0) && canMoveScreen) {this.carte.deplaceFenetre(-this.getVitesse(), 0);}
			if(this.carte.getFenetreEcran().getPosXPixelEcran()+this.getPositionXPixel()+this.carte.getLargeurCasePixel()/2<(this.getEntite().getPositionX())*this.carte.getLargeurCasePixel()){
				this.carte.mettreEntite(this.getEntite(), this.getEntite().getPositionY(), this.getEntite().getPositionX()-1);
			}
			break;
		case RIGHT:
			if (!detecteCollisionsMouvement(this.getVitesse(),0) && this.getPositionXPixel()+this.carte.getLargeurCasePixel()+distMinBordEcranX+this.getVitesse()<this.carte.getFenetreEcran().getLargeurPixelEcran()) {this.changerPositionPixel(this.getVitesse(), 0);}
			else if (!detecteCollisionsMouvement(this.getVitesse(),0) && canMoveScreen) {this.carte.deplaceFenetre(this.getVitesse(), 0);}
			if(this.carte.getFenetreEcran().getPosXPixelEcran()+this.getPositionXPixel()+this.carte.getLargeurCasePixel()/2>(this.getEntite().getPositionX()+1)*this.carte.getLargeurCasePixel()){
				this.carte.mettreEntite(this.getEntite(), this.getEntite().getPositionY(), this.getEntite().getPositionX()+1);
			}
			break;
		default:
			break;
		}
	}
	

	public Carte getCarte() {
		return carte;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public Image getFeuilleDeSpriteEntite() {
		return feuilleDeSpriteEntite;
	}

	public void setEntite(Entité entite) {
		this.entite = entite;
	}

	public int getIndiceSprite() {
		return indiceSprite;
	}

	public void setIndiceSprite(int indiceSprite) {
		this.indiceSprite = indiceSprite;
	}


	public void setAvanceD(boolean avanceD) {
		this.avanceD = avanceD;
	}

	public void setAvanceG(boolean avanceG) {
		this.avanceG = avanceG;
	}

	public void setAvanceH(boolean avanceH) {
		this.avanceH = avanceH;
	}

	public void setAvanceB(boolean avanceB) {
		this.avanceB = avanceB;
	}

	/**{@literal cette méthode change la position en pixels de l'entite}*/
	public void changerPositionPixel(int deltaXPixel, int deltaYPixel) {
		this.setPositionXPixel(this.getPositionXPixel()+deltaXPixel);
		this.setPositionYPixel(this.getPositionYPixel()+deltaYPixel);
		this.setChanged();
		this.notifyObservers(ControleEntite.A_BOUGE);
	}
	

	/**{@literal cette méthode affiche l'image d'attaque sur l'entite que l'entite est en train d'attaquer}*/
	public void afficheAttaque() {
		if (this.getDistance()>-1 && this.attaqueEnCours) {
			switch (this.getLastDirection()) {
			case UP:
				gc.drawImage(ControleEntite.imageAttaque,91,49,240,222,(this.getEntite().getPositionX())*this.carte.getLargeurCasePixel()-this.carte.getFenetreEcran().getPosXPixelEcran(),(this.getEntite().getPositionY()-1-this.getDistance())*this.carte.getHauteurCasePixel()-this.carte.getFenetreEcran().getPosYPixelEcran(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
				break;
			case DOWN:
				gc.drawImage(ControleEntite.imageAttaque,91,49,240,222,(this.getEntite().getPositionX())*this.carte.getLargeurCasePixel()-this.carte.getFenetreEcran().getPosXPixelEcran(),(this.getEntite().getPositionY()+1+this.getDistance())*this.carte.getHauteurCasePixel()-this.carte.getFenetreEcran().getPosYPixelEcran(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
				break;
			case LEFT:
				gc.drawImage(ControleEntite.imageAttaque,91,49,240,222,(this.getEntite().getPositionX()-1-this.getDistance())*this.carte.getLargeurCasePixel()-this.carte.getFenetreEcran().getPosXPixelEcran(),(this.getEntite().getPositionY())*this.carte.getHauteurCasePixel()-this.carte.getFenetreEcran().getPosYPixelEcran(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
				break;
			case RIGHT:
				gc.drawImage(ControleEntite.imageAttaque,91,49,240,222,(this.getEntite().getPositionX()+1+this.getDistance())*this.carte.getLargeurCasePixel()-this.carte.getFenetreEcran().getPosXPixelEcran()+20,(this.getEntite().getPositionY())*this.carte.getHauteurCasePixel()-this.carte.getFenetreEcran().getPosYPixelEcran(),this.carte.getLargeurCasePixel(),this.carte.getHauteurCasePixel());
				break;
			default:
				break;
			}
		}
	}
	
	
	
	public int getPositionXPixel() {
		return positionXPixel;
	}

	public boolean isAvanceD() {
		return avanceD;
	}

	public boolean isAvanceG() {
		return avanceG;
	}

	public boolean isAvanceH() {
		return avanceH;
	}

	public boolean isAvanceB() {
		return avanceB;
	}

	public void setPositionXPixel(int positionXPixel) {
		this.positionXPixel = positionXPixel;
	}

	public boolean isAttaqueEnCours() {
		return attaqueEnCours;
	}

	public void setAttaqueEnCours(boolean attaqueEnCours) {
		this.attaqueEnCours = attaqueEnCours;
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

	public KeyCode getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(KeyCode lastDirection) {
		this.lastDirection = lastDirection;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getHauteurPixelEntite() {
		return hauteurPixelEntite;
	}

	public int getLargeurPixelEntite() {
		return largeurPixelEntite;
	}

	public int getFacteurTaille() {
		return facteurTaille;
	}

	public int getBouge() {
		return bouge;
	}

	public void setBouge(int bouge) {
		this.bouge = bouge;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public int getDeltaXHitBox() {
		return deltaXHitBox;
	}

	public void setDeltaXHitBox(int deltaXHitBox) {
		this.deltaXHitBox = deltaXHitBox;
	}

	public int getDeltaYHitBox() {
		return deltaYHitBox;
	}

	public void setDeltaYHitBox(int deltaYHitBox) {
		this.deltaYHitBox = deltaYHitBox;
	}
}
