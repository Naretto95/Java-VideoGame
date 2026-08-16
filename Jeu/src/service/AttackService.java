package service;

import controller.EntityController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
/**
 * @date 25/05/2020
 * @author Corentin BRILLANT
 */



public class AttackService extends Service<Object>{
	
	private EntityController entityController;

	public AttackService(EntityController entityController) {
		this.entityController = entityController;
	}
	@Override
	protected Task<Object> createTask() {
		return new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				Thread.sleep(100);
				entityController.setAttackInProgress(false);
				return null;
			}
			
		};
	}
	
}
