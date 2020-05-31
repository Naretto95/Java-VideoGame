package controle;

import Jeu.Partie;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Partie partie1 = new Partie();
		StackPane root = partie1.nouvellePartie();
		Scene scene = new Scene(root);
		arg0.addEventFilter(KeyEvent.ANY,partie1.getCtlJoueur());
        arg0.setScene(scene);
        arg0.show();
	}
}
