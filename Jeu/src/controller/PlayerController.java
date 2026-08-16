package controller;

import java.util.Observable;

import game.GameMap;
import game.Entity;
import game.Player;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * @date 16/05/2020
 * @author Corentin BRILLANT
 */

public class PlayerController extends EntityController implements EventHandler<KeyEvent>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Player player;
	private int minScreenMarginX=150;
	private int minScreenMarginY=200;

	public PlayerController(String spriteSheet,GameMap map, Player player, GraphicsContext gc,int entityHeightPixels,int entityWidthPixels){
		super(spriteSheet,map,gc,(Entity) player,entityHeightPixels,entityWidthPixels);
		this.setPlayer(player);
	}
	
	
	//handles keyboard input for the player
	/**{@literal the player's keyboard input handler}*/
	public void handle(KeyEvent event){
		//if a key is pressed down
		if (event.getEventType()==KeyEvent.KEY_PRESSED) {
			switch(event.getCode()) {
			case SPACE:
				if (!this.isAttackInProgress()) {
					this.setMovementFlag(0);
					setMovingDown(false);
					setMovingRight(false);
					setMovingLeft(false);
					setMovingUp(false);
					this.setDistance(this.attack());
				}
				break;
			case Z:
				this.setMovementFlag(1);
				setMovingDown(false);
				setMovingUp(true);
				this.setLastDirection(KeyCode.UP);
				break;
			case S:
				this.setMovementFlag(1);
				setMovingUp(false);
				setMovingDown(true);
				this.setLastDirection(KeyCode.DOWN);
				break;
			case Q:
				this.setMovementFlag(1);
				setMovingRight(false);
				setMovingLeft(true);
				this.setLastDirection(KeyCode.LEFT);
				break;
			case D:
				this.setMovementFlag(1);
				setMovingLeft(false);
				setMovingRight(true);
				this.setLastDirection(KeyCode.RIGHT);
				break;
			case SHIFT:
				this.setSpeed(this.getSprintSpeed());
				break;
			default:
				break;
			
			}
		}
		//if a key is released
		if (event.getEventType()==KeyEvent.KEY_RELEASED) {
			switch (event.getCode()) {
				case SPACE:
					this.setMovementFlag(1);
					break;
				case Z:
					setMovingUp(false);
					break;
				case S:
					setMovingDown(false);
					break;
				case Q:
					setMovingLeft(false);
					break;
				case D:
					setMovingRight(false);
					break;
				case SHIFT:
					this.setSpeed(5);
				default:
					break;
		
			}
			if (!(this.isMovingLeft()||this.isMovingRight()||this.isMovingUp()||this.isMovingDown())) {
				this.setMovementFlag(0);
			}
		}
	}
	
	/**{@literal moves the player in the given direction}*/
	public void movePlayer(KeyCode kc) {
		move(kc,this.minScreenMarginX,this.minScreenMarginY,true);
	}


	/**{@literal draws the player on screen}*/
	public void drawPlayer(KeyCode keycode,int movementFlag) {
		//the display differs for each sprite sheet, since each entity has its own
		setSpriteIndex((getSpriteIndex()+1));//advance to the next frame in the sprite sheet
		switch(keycode) {
		//since the player can move in several directions, the sprite must match the movement direction
		case RIGHT:
			getGc().drawImage(this.getSpriteSheet(),(((getSpriteIndex()%10)*movementFlag+4)%10)*this.getEntityWidthPixels(),925,this.getEntityWidthPixels(),this.getEntityHeightPixels(),this.getPixelX(),this.getPixelY()+this.getMap().getTileHeightPixels()-this.getEntityHeightPixels()/this.getSizeFactor(),this.getEntityWidthPixels()/this.getSizeFactor(),this.getEntityHeightPixels()/this.getSizeFactor());
			break;
		case LEFT:
			getGc().drawImage(this.getSpriteSheet(),(getSpriteIndex()%10)*movementFlag*this.getEntityWidthPixels(),660,this.getEntityWidthPixels(),this.getEntityHeightPixels(),this.getPixelX(),this.getPixelY()+this.getMap().getTileHeightPixels()-this.getEntityHeightPixels()/this.getSizeFactor(),this.getEntityWidthPixels()/this.getSizeFactor(),this.getEntityHeightPixels()/this.getSizeFactor());
			break;
		case UP:
			getGc().drawImage(this.getSpriteSheet(),(getSpriteIndex()%10)*movementFlag*this.getEntityWidthPixels(),785,this.getEntityWidthPixels(),this.getEntityHeightPixels(),this.getPixelX(),this.getPixelY()+this.getMap().getTileHeightPixels()-this.getEntityHeightPixels()/this.getSizeFactor(),this.getEntityWidthPixels()/this.getSizeFactor(),this.getEntityHeightPixels()/this.getSizeFactor());
			break;
		case DOWN:
			getGc().drawImage(this.getSpriteSheet(),(getSpriteIndex()%10)*movementFlag*this.getEntityWidthPixels(),530,this.getEntityWidthPixels(),this.getEntityHeightPixels(),this.getPixelX(),this.getPixelY()+this.getMap().getTileHeightPixels()-this.getEntityHeightPixels()/this.getSizeFactor(),this.getEntityWidthPixels()/this.getSizeFactor(),this.getEntityHeightPixels()/this.getSizeFactor());
			break;
		default:
			break;
		}

	}
	


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	@Override
	public void update(Observable o, Object arg) {
	}
}
