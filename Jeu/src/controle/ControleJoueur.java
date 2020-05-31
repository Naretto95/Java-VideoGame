package controle;

import java.util.Observable;

import Jeu.Carte;
import Jeu.Entité;
import Jeu.Joueur;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * @date 16/05/2020
 * @author Corentin BRILLANT
 */

public class ControleJoueur extends ControleEntite implements EventHandler<KeyEvent>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Joueur joueur;
	private int distMinBordEcranX=150;
	private int distMinBordEcranY=200;

	public ControleJoueur(String feuilleDeSpriteEntite,Carte carte, Joueur joueur, GraphicsContext gc,int hauteurPixelEntite,int largeurPixelEntite){
		super(feuilleDeSpriteEntite,carte,gc,(Entité) joueur,hauteurPixelEntite,largeurPixelEntite);
		this.setJoueur(joueur);
	}
	
	
	//gestion des input clavier pour le joueur
	/**{@literal le gestionnaire des input clavier du joueur}*/
	public void handle(KeyEvent event){
		//si on appuye sur une touche
		if (event.getEventType()==KeyEvent.KEY_PRESSED) {
			switch(event.getCode()) {
			case SPACE:
				if (!this.isAttaqueEnCours()) {
					this.setBouge(0);
					setAvanceB(false);
					setAvanceD(false);
					setAvanceG(false);
					setAvanceH(false);
					this.setDistance(this.attaque());
				}
				break;
			case Z:
				this.setBouge(1);
				setAvanceB(false);
				setAvanceH(true);
				this.setLastDirection(KeyCode.UP);
				break;
			case S:
				this.setBouge(1);
				setAvanceH(false);
				setAvanceB(true);
				this.setLastDirection(KeyCode.DOWN);
				break;
			case Q:
				this.setBouge(1);
				setAvanceD(false);
				setAvanceG(true);
				this.setLastDirection(KeyCode.LEFT);
				break;
			case D:
				this.setBouge(1);
				setAvanceG(false);
				setAvanceD(true);
				this.setLastDirection(KeyCode.RIGHT);
				break;
			case SHIFT:
				this.setVitesse(this.getSpeed());
				break;
			default:
				break;
			
			}
		}
		//si on relache la touche
		if (event.getEventType()==KeyEvent.KEY_RELEASED) {
			switch (event.getCode()) {
				case SPACE:
					this.setBouge(1);
					break;
				case Z:
					setAvanceH(false);
					break;
				case S:
					setAvanceB(false);
					break;
				case Q:
					setAvanceG(false);
					break;
				case D:
					setAvanceD(false);
					break;
				case SHIFT:
					this.setVitesse(5);
				default:
					break;
		
			}
			if (!(this.isAvanceG()||this.isAvanceD()||this.isAvanceH()||this.isAvanceB())) {
				this.setBouge(0);
			}
		}
	}
	
	/**{@literal la méthode pour déplacer le joueur}*/
	public void deplacerJoueur(KeyCode kc) {
		deplacer(kc,this.distMinBordEcranX,this.distMinBordEcranY,true);
	}
	
	
	/**{@literal la méthode pour afficher le joueur}*/
	public void afficheJoueur(KeyCode keycode,int bouge) {
		//l'affichage des entites est variable pour chaque feuille de Sprite qui lui correspond
		setIndiceSprite((getIndiceSprite()+1));//on change l'image affichée par celle qui suit dans la feuille
		switch(keycode) {
		//pour le joueur qui se déplace dans plusieurs directions il faut adapter l'image au sens du mouvement
		case RIGHT:
			getGc().drawImage(this.getFeuilleDeSpriteEntite(),(((getIndiceSprite()%10)*bouge+4)%10)*this.getLargeurPixelEntite(),925,this.getLargeurPixelEntite(),this.getHauteurPixelEntite(),this.getPositionXPixel(),this.getPositionYPixel()+this.getCarte().getHauteurCasePixel()-this.getHauteurPixelEntite()/this.getFacteurTaille(),this.getLargeurPixelEntite()/this.getFacteurTaille(),this.getHauteurPixelEntite()/this.getFacteurTaille());
			break;
		case LEFT:
			getGc().drawImage(this.getFeuilleDeSpriteEntite(),(getIndiceSprite()%10)*bouge*this.getLargeurPixelEntite(),660,this.getLargeurPixelEntite(),this.getHauteurPixelEntite(),this.getPositionXPixel(),this.getPositionYPixel()+this.getCarte().getHauteurCasePixel()-this.getHauteurPixelEntite()/this.getFacteurTaille(),this.getLargeurPixelEntite()/this.getFacteurTaille(),this.getHauteurPixelEntite()/this.getFacteurTaille());
			break;
		case UP:
			getGc().drawImage(this.getFeuilleDeSpriteEntite(),(getIndiceSprite()%10)*bouge*this.getLargeurPixelEntite(),785,this.getLargeurPixelEntite(),this.getHauteurPixelEntite(),this.getPositionXPixel(),this.getPositionYPixel()+this.getCarte().getHauteurCasePixel()-this.getHauteurPixelEntite()/this.getFacteurTaille(),this.getLargeurPixelEntite()/this.getFacteurTaille(),this.getHauteurPixelEntite()/this.getFacteurTaille());
			break;
		case DOWN:
			getGc().drawImage(this.getFeuilleDeSpriteEntite(),(getIndiceSprite()%10)*bouge*this.getLargeurPixelEntite(),530,this.getLargeurPixelEntite(),this.getHauteurPixelEntite(),this.getPositionXPixel(),this.getPositionYPixel()+this.getCarte().getHauteurCasePixel()-this.getHauteurPixelEntite()/this.getFacteurTaille(),this.getLargeurPixelEntite()/this.getFacteurTaille(),this.getHauteurPixelEntite()/this.getFacteurTaille());
			break;
		default:
			break;
		}

	}
	


	public Joueur getJoueur() {
		return joueur;
	}


	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
