package controle;

import java.util.Observable;

import java.util.Observer;

import Jeu.Carte;
import Jeu.Entité;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * @date 20/05/2020
 * @author Corentin BRILLANT
 */


public class ControleBarreDeVie  extends Group implements Observer{
	
	private ControleEntite ctlEntite;
	private int largeurBarre=100;
	private int hauteurBarre=10;
	private boolean isDynamic=false;
	private double vieMax;
	private Rectangle barBorder;
	private Rectangle unlife;
	private Rectangle life;
	
	public ControleBarreDeVie(ControleEntite ctlEntite,int posX, int posY,int zoom) {
		this.ctlEntite = ctlEntite;
		this.vieMax=ctlEntite.getEntite().getVie();
		hauteurBarre *= zoom;
		largeurBarre  *= zoom;
		barBorder = new Rectangle(posX,posY,largeurBarre,hauteurBarre);
		barBorder.setFill(Color.BLACK);
		unlife = new Rectangle(posX+5,posY+3,largeurBarre-10,hauteurBarre-6);
		unlife.setFill(Color.RED);
		life = new Rectangle(posX+5,posY+3,largeurBarre-10,hauteurBarre-6);
		life.setFill(Color.GREEN);
		ctlEntite.addObserver(this);
		ctlEntite.getEntite().addObserver(this);
		this.getChildren().add(barBorder);
		this.getChildren().add(unlife);
		this.getChildren().add(life);
	}
	
	public ControleBarreDeVie(ControleEntite ctlEntite, int zoom) {
		this.isDynamic=true;
		this.ctlEntite = ctlEntite;
		hauteurBarre *= zoom;
		largeurBarre  *= zoom;
		this.ctlEntite.getCarte().addObserver(this);
		this.vieMax=ctlEntite.getEntite().getVie();
		barBorder = new Rectangle(this.ctlEntite.getPositionXPixel()+this.ctlEntite.getLargeurPixelEntite()/2-largeurBarre/2,this.ctlEntite.getPositionYPixel()-hauteurBarre,largeurBarre,hauteurBarre);
		barBorder.setFill(Color.BLACK);
		unlife = new Rectangle(this.ctlEntite.getPositionXPixel()+this.ctlEntite.getLargeurPixelEntite()/2-largeurBarre/2+5,this.ctlEntite.getPositionYPixel()-hauteurBarre+3,largeurBarre-10,hauteurBarre-6);
		unlife.setFill(Color.RED);
		life = new Rectangle(this.ctlEntite.getPositionXPixel()+this.ctlEntite.getLargeurPixelEntite()/2-largeurBarre/2+5,this.ctlEntite.getPositionYPixel()-hauteurBarre+3,largeurBarre-10,hauteurBarre-6);
		life.setFill(Color.GREEN);
		ctlEntite.addObserver(this);
		ctlEntite.getEntite().addObserver(this);
		this.getChildren().add(barBorder);
		this.getChildren().add(unlife);
		this.getChildren().add(life);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg.equals(Entité.VIE_MODIFIEE)) {
			life.setWidth(largeurBarre*((double)this.ctlEntite.getEntite().getVie()/this.vieMax)-10);
		}
		if ((arg.equals(ControleEntite.A_BOUGE)||arg.equals(Carte.CARTE_QUI_BOUGE)) && (this.isDynamic)) {
			if (this.ctlEntite.detecteCollision(ctlEntite.getPositionXPixel(), ctlEntite.getPositionYPixel(), ctlEntite.getLargeurPixelEntite(), ctlEntite.getHauteurPixelEntite(), ctlEntite.getCarte().getFenetreEcran().getPosXPixelEcran(), ctlEntite.getCarte().getFenetreEcran().getPosYPixelEcran(), ctlEntite.getCarte().getFenetreEcran().getLargeurPixelEcran(), ctlEntite.getCarte().getFenetreEcran().getHauteurPixelEcran())) {this.setVisible(true);}
			else {this.setVisible(false);}
			barBorder.setX(this.ctlEntite.getPositionXPixel()+this.ctlEntite.getLargeurPixelEntite()/2-largeurBarre/2);
			barBorder.setY(this.ctlEntite.getPositionYPixel()-hauteurBarre);
			unlife.setX(this.ctlEntite.getPositionXPixel()+this.ctlEntite.getLargeurPixelEntite()/2-largeurBarre/2+5);
			unlife.setY(this.ctlEntite.getPositionYPixel()-hauteurBarre+3);
			life.setX(this.ctlEntite.getPositionXPixel()+this.ctlEntite.getLargeurPixelEntite()/2-largeurBarre/2+5);
			life.setY(this.ctlEntite.getPositionYPixel()-hauteurBarre+3);
		}
		if (arg.equals(Entité.EST_MORT)) {
			
			this.ctlEntite.getEntite().deleteObservers();
			this.ctlEntite.deleteObservers();
			this.ctlEntite.getCarte().deleteObserver(this);
			this.getChildren().removeAll(this.life,this.barBorder,this.unlife);
		}
	}

}
