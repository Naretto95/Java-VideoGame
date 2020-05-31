
package controle;

import java.util.Observable;
import java.util.Observer;

import Jeu.Enclume;
import javafx.scene.control.Button;
/**
 * @date 27/05/2020
 * @author Corentin BRILLANT
 */


public class ControleEnclume extends Button implements Observer,Runnable{
	
	private ControleEntite ctlEntite;
	
	public ControleEnclume(ControleEntite ctlEntite) {
		super("Utiliser l'enclume");
		this.setVisible(false);
		this.ctlEntite=ctlEntite;
		ctlEntite.addObserver(this);
		this.getStyleClass().add("custom-button");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg1 instanceof Enclume) {
			(new Thread(this)).start();
			this.setOnMouseClicked(e->{((Enclume)arg1).Utiliser(((ControleJoueur) this.ctlEntite).getJoueur());});
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVisible(false);
	}

}