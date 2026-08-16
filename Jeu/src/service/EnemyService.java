package service;

import game.EntityState;
import controller.EnemyController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
/**
 * @date 28/05/2020
 * @author Corentin BRILLANT
 */


public class EnemyService extends Service<Object>{
	
	private EnemyController enemyController;

	public EnemyService(EnemyController enemyController) {
		this.enemyController = enemyController;
	}
	@Override
	protected Task<Object> createTask() {
		return new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				while (enemyController.getEntity().getState()!=EntityState.DEAD) {
					Thread.sleep(500);
					enemyController.autoAttackSurroundings();
					
				}
				return null;
			}
			
		};
	}
	
}