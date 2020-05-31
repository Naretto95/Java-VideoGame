package controle;

import java.util.Observable;
import java.util.Observer;

import Jeu.Carte.Porte;
import javafx.scene.control.Button;
/**
 * @date 23/05/2020
 * @author Corentin BRILLANT
 */


public class ControleOuverturePorte extends Button implements Observer,Runnable{
	
	private ControleEntite ctlEntite;
	
	public ControleOuverturePorte(ControleEntite ctlEntite) {
		super("Ouvrir la porte");
		this.setVisible(false);
		this.ctlEntite=ctlEntite;
		ctlEntite.addObserver(this);
		this.getStyleClass().add("custom-button");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Porte) {
			(new Thread(this)).start();
			this.setOnMouseClicked(e->{((Porte)arg1).ouvre(this.ctlEntite);});
		}
	}
	
	/** {@literal la méthode lancer le décompte pour afficher le bouton d'ouverture d'une porte}*/

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
