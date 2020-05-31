package threadService;

import Jeu.Partie;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
/**
 * @date 19/05/2020
 * @author Corentin BRILLANT
 */


public class RunJoueurService extends Service<Integer>{

	private Partie partie;
	public static Integer EN_PAUSE = new Integer(400);
	public static Integer GAME_OVER = new Integer(401);
	
	public RunJoueurService(Partie partie) {
		this.partie=partie;
	}
	@Override
	protected Task<Integer> createTask() {
		// TODO Auto-generated method stub
		return new Task<Integer>() {
			
			@Override
			protected Integer call() throws Exception {
				// TODO Auto-generated method stub
				while(!partie.isPaused() && !partie.gameOver()) {
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(partie.getCtlJoueur().isAvanceD()) {
						partie.getCtlJoueur().deplacerJoueur(KeyCode.RIGHT);
					}
					if(partie.getCtlJoueur().isAvanceH()) {
						partie.getCtlJoueur().deplacerJoueur(KeyCode.UP);
					}
					if(partie.getCtlJoueur().isAvanceB()) {
						partie.getCtlJoueur().deplacerJoueur(KeyCode.DOWN);
					}
					if(partie.getCtlJoueur().isAvanceG()) {
						partie.getCtlJoueur().deplacerJoueur(KeyCode.LEFT);
					}
				}
				if (partie.isPaused()) {return EN_PAUSE;}
				return GAME_OVER;
			}
			
		};
	}
	
}
