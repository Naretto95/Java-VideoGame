package controller;

import java.util.Observable;

import java.util.Observer;

import game.GameMap;
import game.Entity;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * @date 20/05/2020
 * @author Corentin BRILLANT
 */


public class HealthBarController  extends Group implements Observer{
	
	private EntityController entityController;
	private int barWidth=100;
	private int barHeight=10;
	private boolean isDynamic=false;
	private double maxHealth;
	private Rectangle barBorder;
	private Rectangle emptyBar;
	private Rectangle healthBar;
	
	public HealthBarController(EntityController entityController,int posX, int posY,int zoom) {
		this.entityController = entityController;
		this.maxHealth=entityController.getEntity().getHealth();
		barHeight *= zoom;
		barWidth  *= zoom;
		barBorder = new Rectangle(posX,posY,barWidth,barHeight);
		barBorder.setFill(Color.BLACK);
		emptyBar = new Rectangle(posX+5,posY+3,barWidth-10,barHeight-6);
		emptyBar.setFill(Color.RED);
		healthBar = new Rectangle(posX+5,posY+3,barWidth-10,barHeight-6);
		healthBar.setFill(Color.GREEN);
		entityController.addObserver(this);
		entityController.getEntity().addObserver(this);
		this.getChildren().add(barBorder);
		this.getChildren().add(emptyBar);
		this.getChildren().add(healthBar);
	}
	
	public HealthBarController(EntityController entityController, int zoom) {
		this.isDynamic=true;
		this.entityController = entityController;
		barHeight *= zoom;
		barWidth  *= zoom;
		this.entityController.getMap().addObserver(this);
		this.maxHealth=entityController.getEntity().getHealth();
		barBorder = new Rectangle(this.entityController.getPixelX()+this.entityController.getEntityWidthPixels()/2-barWidth/2,this.entityController.getPixelY()-barHeight,barWidth,barHeight);
		barBorder.setFill(Color.BLACK);
		emptyBar = new Rectangle(this.entityController.getPixelX()+this.entityController.getEntityWidthPixels()/2-barWidth/2+5,this.entityController.getPixelY()-barHeight+3,barWidth-10,barHeight-6);
		emptyBar.setFill(Color.RED);
		healthBar = new Rectangle(this.entityController.getPixelX()+this.entityController.getEntityWidthPixels()/2-barWidth/2+5,this.entityController.getPixelY()-barHeight+3,barWidth-10,barHeight-6);
		healthBar.setFill(Color.GREEN);
		entityController.addObserver(this);
		entityController.getEntity().addObserver(this);
		this.getChildren().add(barBorder);
		this.getChildren().add(emptyBar);
		this.getChildren().add(healthBar);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(Entity.HEALTH_CHANGED)) {
			healthBar.setWidth(barWidth*((double)this.entityController.getEntity().getHealth()/this.maxHealth)-10);
		}
		if ((arg.equals(EntityController.MOVED)||arg.equals(GameMap.MAP_SCROLLED)) && (this.isDynamic)) {
			if (this.entityController.checkCollision(entityController.getPixelX(), entityController.getPixelY(), entityController.getEntityWidthPixels(), entityController.getEntityHeightPixels(), entityController.getMap().getViewport().getX(), entityController.getMap().getViewport().getY(), entityController.getMap().getViewport().getWidth(), entityController.getMap().getViewport().getHeight())) {this.setVisible(true);}
			else {this.setVisible(false);}
			barBorder.setX(this.entityController.getPixelX()+this.entityController.getEntityWidthPixels()/2-barWidth/2);
			barBorder.setY(this.entityController.getPixelY()-barHeight);
			emptyBar.setX(this.entityController.getPixelX()+this.entityController.getEntityWidthPixels()/2-barWidth/2+5);
			emptyBar.setY(this.entityController.getPixelY()-barHeight+3);
			healthBar.setX(this.entityController.getPixelX()+this.entityController.getEntityWidthPixels()/2-barWidth/2+5);
			healthBar.setY(this.entityController.getPixelY()-barHeight+3);
		}
		if (arg.equals(Entity.DIED)) {
			
			this.entityController.getEntity().deleteObservers();
			this.entityController.deleteObservers();
			this.entityController.getMap().deleteObserver(this);
			this.getChildren().removeAll(this.healthBar,this.barBorder,this.emptyBar);
		}
	}

}
