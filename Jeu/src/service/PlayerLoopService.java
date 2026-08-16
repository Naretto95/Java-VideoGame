package service;

import game.GameSession;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
/**
 * @date 19/05/2020
 * @author Corentin BRILLANT
 */


public class PlayerLoopService extends Service<Integer>{

	private GameSession gameSession;
	public static Integer PAUSED = new Integer(400);
	public static Integer GAME_OVER = new Integer(401);
	
	public PlayerLoopService(GameSession gameSession) {
		this.gameSession=gameSession;
	}
	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				while(!gameSession.isPaused() && !gameSession.isGameOver()) {
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(gameSession.getPlayerController().isMovingRight()) {
						gameSession.getPlayerController().movePlayer(KeyCode.RIGHT);
					}
					if(gameSession.getPlayerController().isMovingUp()) {
						gameSession.getPlayerController().movePlayer(KeyCode.UP);
					}
					if(gameSession.getPlayerController().isMovingDown()) {
						gameSession.getPlayerController().movePlayer(KeyCode.DOWN);
					}
					if(gameSession.getPlayerController().isMovingLeft()) {
						gameSession.getPlayerController().movePlayer(KeyCode.LEFT);
					}
				}
				if (gameSession.isPaused()) {return PAUSED;}
				return GAME_OVER;
			}
			
		};
	}
	
}
