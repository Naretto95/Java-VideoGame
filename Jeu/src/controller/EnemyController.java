package controller;

import java.util.Observable;

import game.Entity;
import game.EntityState;
import game.Enemy;
import game.GameMap;
import game.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import service.EnemyService;


public class EnemyController extends EntityController{

	private static final long serialVersionUID = 1L;
	private EnemyService enemyService;
	public EnemyController(String spriteSheet, GameMap map, GraphicsContext gc, Entity entity,
                          int entityHeightPixels, int entityWidthPixels) {

        super(spriteSheet, map, gc, entity, entityHeightPixels, entityWidthPixels);
        this.enemyService = new EnemyService(this);
        this.enemyService.start();
    }

	/**{@literal the enemy tries to attack in every direction and strikes if a player is adjacent}*/
	public void autoAttackSurroundings() {
		int i = this.getEntity().getPositionY();
		int j = this.getEntity().getPositionX();
		autoAttackTile(i-1,j);
		autoAttackTile(i+1,j);
		autoAttackTile(i,j+1);
		autoAttackTile(i,j-1);
	}

	/**{@literal the enemy strikes if a player is standing on tile (i,j)}*/
	public void autoAttackTile(int i,int j) {
		if(i>=0 && j>=0 && i<this.getMap().getTileImages().size() && j<this.getMap().getTileImages().get(i).size()) {
			Object tileContent = this.getMap().getTile(i, j).getContent();
			if (this.getEntity() instanceof Enemy && tileContent instanceof Player) {
				Enemy enemy = (Enemy) this.getEntity();
				if (enemy.getPositionY()<i) {this.setLastDirection(KeyCode.DOWN);}
				else if (enemy.getPositionY()>i) {this.setLastDirection(KeyCode.UP);}
				else if (enemy.getPositionX()<j) {this.setLastDirection(KeyCode.RIGHT);}
				else if (enemy.getPositionX()>j) {this.setLastDirection(KeyCode.LEFT);}
				this.attack();
				}
		}
	}

    public void drawEnemy() {
		this.setPixelX(((this.getEntity().getPositionX())*this.getMap().getTileWidthPixels()) - this.getMap().getViewport().getX());
		this.setPixelY(((this.getEntity().getPositionY())*this.getMap().getTileHeightPixels()) - this.getMap().getViewport().getY());
    	if (this.getEntity().getState()!=EntityState.DEAD) {
    		getGc().drawImage(this.getSpriteSheet(),getSpriteIndex()*this.getEntityWidthPixels(),0,
                    this.getEntityWidthPixels(),this.getEntityHeightPixels(),this.getPixelX()-5,
                    this.getPixelY()+this.getMap().getTileHeightPixels()-this.getEntityHeightPixels()-15,
                    this.getEntityWidthPixels()+10,this.getEntityHeightPixels()+15);
    	}
    }

	@Override
	public void update(Observable o, Object arg) {
	}

}
