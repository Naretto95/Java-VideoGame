package threadService;

import Jeu.EtatEntité;
import controle.ControleEnnemi;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
/**
 * @date 28/05/2020
 * @author Corentin BRILLANT
 */


public class EnnemiService extends Service<Object>{
	
	private ControleEnnemi ctlEnnemi;

	public EnnemiService(ControleEnnemi ctlEnnemi) {
		this.ctlEnnemi = ctlEnnemi;
	}
	@Override
	protected Task<Object> createTask() {
		// TODO Auto-generated method stub
		return new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				while (ctlEnnemi.getEntite().getEtat()!=EtatEntité.Mort) {
					Thread.sleep(500);
					ctlEnnemi.autoDefenseCollision();
					
				}
				return null;
			}
			
		};
	}
	
}