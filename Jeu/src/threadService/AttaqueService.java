package threadService;

import controle.ControleEntite;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
/**
 * @date 25/05/2020
 * @author Corentin BRILLANT
 */



public class AttaqueService extends Service<Object>{
	
	private ControleEntite ctlEntite;

	public AttaqueService(ControleEntite ctlEntite) {
		this.ctlEntite = ctlEntite;
	}
	@Override
	protected Task<Object> createTask() {
		// TODO Auto-generated method stub
		return new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(100);
				ctlEntite.setAttaqueEnCours(false);
				return null;
			}
			
		};
	}
	
}
