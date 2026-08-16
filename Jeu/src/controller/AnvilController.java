
package controller;

import java.util.Observable;
import java.util.Observer;

import game.Anvil;
import javafx.scene.control.Button;
/**
 * @date 27/05/2020
 * @author Corentin BRILLANT
 */


public class AnvilController extends Button implements Observer,Runnable{
	
	private EntityController entityController;
	
	public AnvilController(EntityController entityController) {
		super("Use anvil");
		this.setVisible(false);
		this.entityController=entityController;
		entityController.addObserver(this);
		this.getStyleClass().add("custom-button");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof Anvil) {
			(new Thread(this)).start();
			this.setOnMouseClicked(e->{((Anvil)arg1).use(((PlayerController) this.entityController).getPlayer());});
		}

	}

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