package controller;

import java.util.Observable;
import java.util.Observer;

import game.GameMap.Door;
import javafx.scene.control.Button;
/**
 * @date 23/05/2020
 * @author Corentin BRILLANT
 */


public class DoorController extends Button implements Observer,Runnable{
	
	private EntityController entityController;
	
	public DoorController(EntityController entityController) {
		super("Open door");
		this.setVisible(false);
		this.entityController=entityController;
		entityController.addObserver(this);
		this.getStyleClass().add("custom-button");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Door) {
			(new Thread(this)).start();
			this.setOnMouseClicked(e->{((Door)arg1).open(this.entityController);});
		}
	}
	
	/**{@literal starts the countdown before the door's "open" button is hidden again}*/

	@Override
	public void run() {
		this.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setVisible(false);
	}

}
