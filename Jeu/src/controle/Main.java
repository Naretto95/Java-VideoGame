package controle;

import Jeu.Carte;
import Jeu.Joueur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Canvas canvas = new Canvas(1000,1000);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		/*
		HashMap<Coordonnee,Case> carte = new HashMap<Coordonnee,Case>();
		
		
		Joueur joueur = new Joueur("Bob",50,250,-250);
		ControleJoueur ctlJoueur = new ControleJoueur(carte,joueur,gc);
		new Thread(ctlJoueur).start();
	*/
		
		String[][] saisieMain = 
			{{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","water.png","water.png","water.png","brique.png","brique.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png"}};
		Carte carte = new Carte("images",saisieMain);
		Joueur joueur = new Joueur("Bob",50,6,3);
		ControleJoueur ctlJoueur = new ControleJoueur(carte,joueur,gc);
		new Thread(ctlJoueur).start();
		System.out.println(carte.getCase(13, 6).getContenu().toString());
		BorderPane borderpane = new BorderPane();
		borderpane.setCenter(canvas);
        Scene scene = new Scene(borderpane);
		arg0.addEventFilter(KeyEvent.ANY,ctlJoueur);
        arg0.setScene(scene);
        arg0.show();
	}
}
