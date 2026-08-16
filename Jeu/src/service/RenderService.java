package service;

import java.util.ArrayList;

import game.GameSession;
import controller.MapController;
import controller.EnemyController;
import controller.PlayerController;
import javafx.animation.AnimationTimer;
/**
 * @date 24/05/2020
 * @author Corentin BRILLANT
 */


public class RenderService extends AnimationTimer{
	
	private GameSession gameSession;
	private MapController mapController;
	private PlayerController playerController;
	private ArrayList<EnemyController> enemyControllers;
	
	public RenderService(GameSession gameSession) {
		this.gameSession = gameSession;
		this.mapController=gameSession.getMapController();
		this.playerController = gameSession.getPlayerController();
		this.enemyControllers=gameSession.getEnemyControllers();
	}
	@Override
	public void handle(long arg0) {
		if(!gameSession.isPaused() && !gameSession.isGameOver()) {
		mapController.drawMap();
		for (int i = 0 ;i< enemyControllers.size();i++) {
			enemyControllers.get(i).drawEnemy();
		}
		playerController.drawPlayer(playerController.getLastDirection(), playerController.getMovementFlag());
		for (int i = 0 ;i< enemyControllers.size();i++) {
			enemyControllers.get(i).drawAttack();
		}
		playerController.drawAttack();
		}
	}
}
