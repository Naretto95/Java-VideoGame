package controle;

import java.util.Observable;
import java.util.Observer;

import Jeu.Joueur;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
/**
 * @date 28/05/2020
 * @author Corentin BRILLANT
 */


public class ControleExpBarre  extends Group implements Observer{
	
	private Joueur joueur;
	private int largeurBarre=100;
	private int hauteurBarre=10;
	private Rectangle barBorder;
	private Rectangle unlife;
	private Rectangle life;
	private Label niveau;
	
	public ControleExpBarre(Joueur joueur,int posX, int posY,int zoom) {
		this.joueur =joueur;
		this.niveau = new Label(joueur.getNiveau()+"");
		this.niveau.setFont(new Font(40));
		this.niveau.setLayoutX(1000);
		hauteurBarre *= zoom;
		largeurBarre  *= zoom;
		barBorder = new Rectangle(posX,posY,largeurBarre,hauteurBarre);
		barBorder.setFill(Color.BLACK);
		unlife = new Rectangle(posX+5,posY+3,largeurBarre-10,hauteurBarre-6);
		unlife.setFill(Color.BLACK);
		life = new Rectangle(posX+5,posY+3,((double)joueur.getExperience()/(double)(joueur.getNiveau()*100))*largeurBarre-10,hauteurBarre-6);
		life.setFill(Color.BLUE);
		joueur.addObserver(this);
		this.getChildren().add(this.niveau);
		this.getChildren().add(barBorder);
		this.getChildren().add(unlife);
		this.getChildren().add(life);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Integer && arg1.equals(Joueur.GAIN_EXP)) {
			this.life.setWidth((double)((double)joueur.getExperience()/((double)joueur.getNiveau()*100))*largeurBarre-10);
			this.niveau.setText(this.joueur.getNiveau()+"");
			System.out.println("j'ai gagné en expériece"+joueur.getExperience()+"sur"+joueur.getNiveau()*100);
		}
	}
}
