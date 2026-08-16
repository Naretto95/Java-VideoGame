package controller;

import java.util.Observable;
import java.util.Observer;

import game.Player;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
/**
 * @date 28/05/2020
 * @author Corentin BRILLANT
 */


public class ExperienceBarController  extends Group implements Observer{
	
	private Player player;
	private int barWidth=100;
	private int barHeight=10;
	private Rectangle barBorder;
	private Rectangle emptyBar;
	private Rectangle healthBar;
	private Label levelLabel;
	
	public ExperienceBarController(Player player,int posX, int posY,int zoom) {
		this.player =player;
		this.levelLabel = new Label(player.getLevel()+"");
		this.levelLabel.setFont(new Font(40));
		this.levelLabel.setLayoutX(1000);
		barHeight *= zoom;
		barWidth  *= zoom;
		barBorder = new Rectangle(posX,posY,barWidth,barHeight);
		barBorder.setFill(Color.BLACK);
		emptyBar = new Rectangle(posX+5,posY+3,barWidth-10,barHeight-6);
		emptyBar.setFill(Color.BLACK);
		healthBar = new Rectangle(posX+5,posY+3,((double)player.getExperience()/(double)(player.getLevel()*100))*barWidth-10,barHeight-6);
		healthBar.setFill(Color.BLUE);
		player.addObserver(this);
		this.getChildren().add(this.levelLabel);
		this.getChildren().add(barBorder);
		this.getChildren().add(emptyBar);
		this.getChildren().add(healthBar);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Integer && arg1.equals(Player.EXPERIENCE_GAINED)) {
			this.healthBar.setWidth((double)((double)player.getExperience()/((double)player.getLevel()*100))*barWidth-10);
			this.levelLabel.setText(this.player.getLevel()+"");
			System.out.println("Experience gained: "+player.getExperience()+" out of "+player.getLevel()*100);
		}
	}
}
