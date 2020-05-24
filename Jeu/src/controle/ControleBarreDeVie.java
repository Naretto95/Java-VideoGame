package controle;

import java.util.Observable;
import java.util.Observer;

import Jeu.Entité;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ControleBarreDeVie  extends Group implements Observer{
	
	private ControleEntite ctlEntite;
	private boolean isDynamic=false;
	private int vie;
	private int vieMax;
	private int zoom; 
	private Rectangle barBorder;
	private Rectangle unlife;
	private Rectangle life;
	
	public ControleBarreDeVie(ControleEntite ctlEntite,int posX, int posY, int zoom) {
		this.ctlEntite = ctlEntite;
		this.vie=ctlEntite.getEntite().getVie();
		this.vieMax=this.vie;
		this.zoom=zoom;
		barBorder = new Rectangle(posX,posY,zoom*vieMax+10,(zoom*vieMax+10)/10+6);
		barBorder.setFill(Color.BLACK);
		unlife = new Rectangle(posX+5,posY+3,zoom*(vieMax),(zoom*vieMax+10)/10);
		unlife.setFill(Color.RED);
		life = new Rectangle(posX+5,posY+3,zoom*vie,(zoom*vieMax+10)/10);
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
		this.vie=ctlEntite.getEntite().getVie();
		this.vieMax=this.vie;
		this.zoom=zoom;
		barBorder = new Rectangle(this.ctlEntite.getPositionXPixel(),this.ctlEntite.getPositionYPixel()-20,zoom*vieMax+10,(zoom*vieMax+10)/10+6);
		barBorder.setFill(Color.BLACK);
		unlife = new Rectangle(this.ctlEntite.getPositionXPixel()+5,this.ctlEntite.getPositionYPixel()+3-20,zoom*(vieMax),(zoom*vieMax+10)/10);
		unlife.setFill(Color.RED);
		life = new Rectangle(this.ctlEntite.getPositionXPixel()+5,this.ctlEntite.getPositionYPixel()+3-20,zoom*vie,(zoom*vieMax+10)/10);
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
			life.setWidth(zoom*this.ctlEntite.getEntite().getVie());
		}
		if (arg.equals(ControleEntite.A_BOUGE) && (this.isDynamic)) {
			barBorder.setX(this.ctlEntite.getPositionXPixel());
			barBorder.setY(this.ctlEntite.getPositionYPixel()-20);
			unlife.setX(this.ctlEntite.getPositionXPixel()+5);
			unlife.setY(this.ctlEntite.getPositionYPixel()+3-20);
			life.setX(this.ctlEntite.getPositionXPixel()+5);
			life.setY(this.ctlEntite.getPositionYPixel()+3-20);
		}
		if (arg.equals(Entité.EST_MORT)) {
			/*
			this.ctlEntite.getEntite().deleteObservers();
			this.ctlEntite.deleteObservers();
			this.setVisible(false);
			*/
		}
	}

}
