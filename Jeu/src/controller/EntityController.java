package controller;

import java.io.File;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import game.Weapon;
import game.GameMap;
import game.GameMap.Door;
import game.Tile;
import game.Anvil;
import game.Entity;
import game.Item;
import game.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import service.AttackService;
/**
 * @date 15/05/2020
 * @author Corentin BRILLANT
 */



public abstract class EntityController extends Observable implements Observer,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Integer MOVED = new Integer(10);
	public static Image attackImage = new Image ("file:imagesitems/attack.png");
	
	private Media attackSound;
	private MediaPlayer mediaPlayer;
	private Entity entity;
	private GameMap map;
	private GraphicsContext gc;
	private Image spriteSheet;
	
	private boolean movingRight = false;
	private boolean movingLeft = false;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean attackInProgress = false;
	private int spriteIndex=0;
	private KeyCode lastDirection=KeyCode.DOWN;
	private int sprintSpeed=10;
	private int distance = 0;
	private int movementFlag=0;

	private int pixelX;
	private int pixelY;
	private int speed=5;
	private int entityHeightPixels;
	private int entityWidthPixels;
	private int paddingX;
	private int paddingY;
	private int sizeFactor=2;

	
	public EntityController(String spriteSheet,GameMap map, GraphicsContext gc,Entity entity,int entityHeightPixels,int entityWidthPixels) {
		this.spriteSheet=new Image("file:"+spriteSheet);
		this.entityHeightPixels=entityHeightPixels;
		this.entityWidthPixels=entityWidthPixels;
		this.entity=entity;
		this.map = map;
		this.gc = gc;
		this.map.placeEntity(entity, this.entity.getPositionY(), this.entity.getPositionX());
		this.pixelX=((this.entity.getPositionX())*this.map.getTileWidthPixels()) - this.map.getViewport().getX();
		this.pixelY=((this.entity.getPositionY())*this.map.getTileHeightPixels()) - this.map.getViewport().getY();
		this.paddingX=5;
		this.paddingY=20;
		this.entity.addObserver(this);
		try {
			File file = new File("musiques/song3.mp3");
			this.attackSound = new Media(file.toURI().toString());
	        this.mediaPlayer = new MediaPlayer(attackSound);
		}catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	/**{@literal detects collision between two rectangles, returns true if they overlap}*/
	public boolean checkCollision(int rect1x,int rect1y, int rect1w, int rect1h,int rect2x,int rect2y, int rect2w, int rect2h) {
		return ((rect1x +paddingX< rect2x + rect2w) && (rect1x-paddingX + rect1w > rect2x) && ( rect1y+paddingY < rect2y + rect2h) && ( rect1h + rect1y > rect2y));
	}

	/**{@literal detects collision between the player's current tile moving by (deltaX,deltaY) pixels and tile (i,j); returns true if the move is impossible, false otherwise}*/
	public boolean checkTileCollisionForMove(int i, int j,int deltaX, int deltaY) {
		if(i>=0 && j>=0 && i<this.map.getTileImages().size() && j<this.map.getTileImages().get(i).size()) {
			Object tileContent = this.map.getTile(i, j).getContent();
			if (this.getEntity() instanceof Player && tileContent instanceof Door) {this.setChanged();this.notifyObservers(tileContent);}
			if (this.getEntity() instanceof Player && tileContent instanceof Anvil) {this.setChanged();this.notifyObservers(tileContent);}
			return checkCollision(this.getPixelX()+deltaX,this.getPixelY()+deltaY,this.map.getTileWidthPixels(),this.map.getTileHeightPixels(),j*this.map.getTileWidthPixels()-this.map.getViewport().getX(),i*this.map.getTileHeightPixels()-this.map.getViewport().getY(),this.map.getTileWidthPixels(),this.map.getTileHeightPixels()) && (tileContent!=Tile.EMPTY);
		}
		return true;
	}
	
	/**{@literal returns true if the move by (deltaX,deltaY) pixels is impossible, false otherwise}*/
	public boolean checkMovementCollision(int deltaX,int deltaY) {
		//either deltaX or deltaY is zero
		if (deltaX>0) {
			return (checkTileCollisionForMove(this.entity.getPositionY()-1,this.entity.getPositionX()+1,deltaX,deltaY) ||checkTileCollisionForMove(this.entity.getPositionY(),this.entity.getPositionX()+1,deltaX,deltaY)||checkTileCollisionForMove(this.entity.getPositionY()+1,this.entity.getPositionX()+1,deltaX,deltaY));
		}
		if (deltaX<0) {
			return (checkTileCollisionForMove(this.entity.getPositionY()-1,this.entity.getPositionX()-1,deltaX,deltaY) ||checkTileCollisionForMove(this.entity.getPositionY(),this.entity.getPositionX()-1,deltaX,deltaY)||checkTileCollisionForMove(this.entity.getPositionY()+1,this.entity.getPositionX()-1,deltaX,deltaY));
		}
		if (deltaY>0) {
			return (checkTileCollisionForMove(this.entity.getPositionY()+1,this.entity.getPositionX()-1,deltaX,deltaY) ||checkTileCollisionForMove(this.entity.getPositionY()+1,this.entity.getPositionX(),deltaX,deltaY)||checkTileCollisionForMove(this.entity.getPositionY()+1,this.entity.getPositionX()+1,deltaX,deltaY));
		}
		if (deltaY<0) {
			return (checkTileCollisionForMove(this.entity.getPositionY()-1,this.entity.getPositionX()-1,deltaX,deltaY) ||checkTileCollisionForMove(this.entity.getPositionY()-1,this.entity.getPositionX(),deltaX,deltaY)||checkTileCollisionForMove(this.entity.getPositionY()-1,this.entity.getPositionX()+1,deltaX,deltaY));
		}
		return true;
	}
	
	/**{@literal launches an attack in the entity's last facing direction; returns the distance to the enemy if the attack landed, or -1 if the attack is impossible}*/
	public int attack() {
		if (!this.isAttackInProgress()) {
		setAttackInProgress(true);
		(new AttackService(this)).start();
		if (this.mediaPlayer!=null) {this.mediaPlayer.seek(Duration.ZERO);this.mediaPlayer.play();}
		Item heldItem = this.getEntity().getItemInHand();
			int range=0;
			if (heldItem instanceof Weapon) {range= ((Weapon) heldItem).getRange();}
			int distance = 0;
			while (distance <= range) {
				switch(this.lastDirection) {
				case UP:
					if (this.getEntity().getPositionY()-1-distance>-1) {
					Object contentUp = this.map.getTile(this.getEntity().getPositionY()-1-distance, this.getEntity().getPositionX()).getContent();
					if (contentUp instanceof Entity) {
						this.getEntity().use((Entity) contentUp);
						return distance;
					}
					else {distance++;}
					}
					else {return -1;}
					break;
				case DOWN:
					if (this.getEntity().getPositionY()+1+distance<this.map.getTileImages().size()) {
					Object contentDown = this.map.getTile(this.getEntity().getPositionY()+1+distance, this.getEntity().getPositionX()).getContent();
					if (contentDown instanceof Entity) {
						this.getEntity().use((Entity) contentDown);
						return distance;
					}
					else {distance++;}
					}
					else {return -1;}
					break;
				case RIGHT:
					if (this.getEntity().getPositionX()+1+distance<this.map.getTileImages().get(this.getEntity().getPositionY()).size()) {
					Object contentRight = this.map.getTile(this.getEntity().getPositionY(), this.getEntity().getPositionX()+1+distance).getContent();
					if (contentRight instanceof Entity) {
						this.getEntity().use((Entity) contentRight);
						return distance; 
					}
					else {distance++;}
					}
					else {return -1;}
					break;
				case LEFT:
					if (this.getEntity().getPositionX()-1-distance>-1) {
					Object contentLeft = this.map.getTile(this.getEntity().getPositionY(), this.getEntity().getPositionX()-1-distance).getContent();
					if (contentLeft instanceof Entity) {
						this.getEntity().use((Entity) contentLeft);
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
	

	
	/**{@literal moves the entity in direction kc, at its current speed, if the move is possible; minScreenMarginX/Y are the entity's minimum distance from the screen edge, and canMoveScreen indicates whether the entity is allowed to scroll the viewport. Designed for the player; will need adjustments to support moving enemies}*/
	public void move(KeyCode kc, int minScreenMarginX, int minScreenMarginY, boolean canMoveScreen) {

		switch (kc) {
		case UP:
			if (!checkMovementCollision(0,-this.getSpeed()) && this.getPixelY()-minScreenMarginY-this.getSpeed()>0) {this.movePixelPosition(0, -this.getSpeed());}
			else if (!checkMovementCollision(0,-this.getSpeed()) && canMoveScreen) {this.map.moveViewport(0, -this.getSpeed());}
			if(this.map.getViewport().getY()+this.getPixelY()+this.map.getTileHeightPixels()/2<(this.getEntity().getPositionY())*this.map.getTileHeightPixels()){
				this.map.placeEntity(this.getEntity(), this.getEntity().getPositionY()-1, this.getEntity().getPositionX());
			}
			break;
		case DOWN:
			if (!checkMovementCollision(0,this.getSpeed()) && this.getPixelY()+this.map.getTileHeightPixels()+minScreenMarginY+this.getSpeed()<this.map.getViewport().getHeight()) {this.movePixelPosition(0, this.getSpeed());}
			else if (!checkMovementCollision(0,this.getSpeed()) && canMoveScreen) {this.map.moveViewport(0, this.getSpeed());}
			if(this.map.getViewport().getY()+this.getPixelY()+this.map.getTileHeightPixels()/2>(this.getEntity().getPositionY()+1)*this.map.getTileHeightPixels()){
				this.map.placeEntity(this.getEntity(), this.getEntity().getPositionY()+1, this.getEntity().getPositionX());
			}
			break;
		case LEFT:
			if (!checkMovementCollision(-this.getSpeed(),0) && this.getPixelX()-minScreenMarginX-this.getSpeed()>0) {this.movePixelPosition(-this.getSpeed(), 0);}
			else if (!checkMovementCollision(-this.getSpeed(),0) && canMoveScreen) {this.map.moveViewport(-this.getSpeed(), 0);}
			if(this.map.getViewport().getX()+this.getPixelX()+this.map.getTileWidthPixels()/2<(this.getEntity().getPositionX())*this.map.getTileWidthPixels()){
				this.map.placeEntity(this.getEntity(), this.getEntity().getPositionY(), this.getEntity().getPositionX()-1);
			}
			break;
		case RIGHT:
			if (!checkMovementCollision(this.getSpeed(),0) && this.getPixelX()+this.map.getTileWidthPixels()+minScreenMarginX+this.getSpeed()<this.map.getViewport().getWidth()) {this.movePixelPosition(this.getSpeed(), 0);}
			else if (!checkMovementCollision(this.getSpeed(),0) && canMoveScreen) {this.map.moveViewport(this.getSpeed(), 0);}
			if(this.map.getViewport().getX()+this.getPixelX()+this.map.getTileWidthPixels()/2>(this.getEntity().getPositionX()+1)*this.map.getTileWidthPixels()){
				this.map.placeEntity(this.getEntity(), this.getEntity().getPositionY(), this.getEntity().getPositionX()+1);
			}
			break;
		default:
			break;
		}
	}
	

	public GameMap getMap() {
		return map;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public Image getSpriteSheet() {
		return spriteSheet;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}


	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	/**{@literal updates the entity's pixel position}*/
	public void movePixelPosition(int deltaXPixel, int deltaYPixel) {
		this.setPixelX(this.getPixelX()+deltaXPixel);
		this.setPixelY(this.getPixelY()+deltaYPixel);
		this.setChanged();
		this.notifyObservers(EntityController.MOVED);
	}
	

	// Attack sprite is a single frame cropped from attackImage's sprite sheet at this fixed atlas position.
	private static final int ATTACK_SPRITE_X = 91;
	private static final int ATTACK_SPRITE_Y = 49;
	private static final int ATTACK_SPRITE_WIDTH = 240;
	private static final int ATTACK_SPRITE_HEIGHT = 222;

	/**{@literal draws the attack sprite over the tile the entity is currently attacking}*/
	public void drawAttack() {
		if (this.getDistance()>-1 && this.attackInProgress) {
			switch (this.getLastDirection()) {
			case UP:
				gc.drawImage(EntityController.attackImage,ATTACK_SPRITE_X,ATTACK_SPRITE_Y,ATTACK_SPRITE_WIDTH,ATTACK_SPRITE_HEIGHT,(this.getEntity().getPositionX())*this.map.getTileWidthPixels()-this.map.getViewport().getX(),(this.getEntity().getPositionY()-1-this.getDistance())*this.map.getTileHeightPixels()-this.map.getViewport().getY(),this.map.getTileWidthPixels(),this.map.getTileHeightPixels());
				break;
			case DOWN:
				gc.drawImage(EntityController.attackImage,ATTACK_SPRITE_X,ATTACK_SPRITE_Y,ATTACK_SPRITE_WIDTH,ATTACK_SPRITE_HEIGHT,(this.getEntity().getPositionX())*this.map.getTileWidthPixels()-this.map.getViewport().getX(),(this.getEntity().getPositionY()+1+this.getDistance())*this.map.getTileHeightPixels()-this.map.getViewport().getY(),this.map.getTileWidthPixels(),this.map.getTileHeightPixels());
				break;
			case LEFT:
				gc.drawImage(EntityController.attackImage,ATTACK_SPRITE_X,ATTACK_SPRITE_Y,ATTACK_SPRITE_WIDTH,ATTACK_SPRITE_HEIGHT,(this.getEntity().getPositionX()-1-this.getDistance())*this.map.getTileWidthPixels()-this.map.getViewport().getX(),(this.getEntity().getPositionY())*this.map.getTileHeightPixels()-this.map.getViewport().getY(),this.map.getTileWidthPixels(),this.map.getTileHeightPixels());
				break;
			case RIGHT:
				gc.drawImage(EntityController.attackImage,ATTACK_SPRITE_X,ATTACK_SPRITE_Y,ATTACK_SPRITE_WIDTH,ATTACK_SPRITE_HEIGHT,(this.getEntity().getPositionX()+1+this.getDistance())*this.map.getTileWidthPixels()-this.map.getViewport().getX()+20,(this.getEntity().getPositionY())*this.map.getTileHeightPixels()-this.map.getViewport().getY(),this.map.getTileWidthPixels(),this.map.getTileHeightPixels());
				break;
			default:
				break;
			}
		}
	}
	
	
	
	public int getPixelX() {
		return pixelX;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setPixelX(int pixelX) {
		this.pixelX = pixelX;
	}

	public boolean isAttackInProgress() {
		return attackInProgress;
	}

	public void setAttackInProgress(boolean attackInProgress) {
		this.attackInProgress = attackInProgress;
	}

	public int getPixelY() {
		return pixelY;
	}

	public void setPixelY(int pixelY) {
		this.pixelY = pixelY;
	}

	public Entity getEntity() {
		return entity;
	}

	public KeyCode getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(KeyCode lastDirection) {
		this.lastDirection = lastDirection;
	}

	public int getSprintSpeed() {
		return sprintSpeed;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getEntityHeightPixels() {
		return entityHeightPixels;
	}

	public int getEntityWidthPixels() {
		return entityWidthPixels;
	}

	public int getSizeFactor() {
		return sizeFactor;
	}

	public int getMovementFlag() {
		return movementFlag;
	}

	public void setMovementFlag(int movementFlag) {
		this.movementFlag = movementFlag;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
}
