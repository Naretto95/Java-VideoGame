package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Jeu.Arme;
import Jeu.Carte;
import Jeu.Categorie;
import Jeu.Ennemi;
import Jeu.Entité;
import Jeu.Joueur;
import Jeu.Race;
import Jeu.Ressource;
import Jeu.TypeArme;
import Jeu.TypeRessource;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	
	
	public static void sauvegarder(Carte carte, Joueur joueur, ArrayList<Ennemi> ennemis) {
		new File("sauvegarde").mkdir();
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("sauvegarde/playerSave");
		    ObjectOutputStream out = new ObjectOutputStream(fileOut);
		    out.writeObject(joueur);
		    out.close();
		    fileOut.close();
		    System.out.println("\nSerialisation du joueur terminée avec succès...\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileOut = new FileOutputStream("sauvegarde/mapSave");
		    ObjectOutputStream out = new ObjectOutputStream(fileOut);
		    out.writeObject(carte);
		    out.close();
		    fileOut.close();
		    System.out.println("\nSerialisation de la carte terminée avec succès...\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileOut = new FileOutputStream("sauvegarde/enemiesSave");
		    ObjectOutputStream out = new ObjectOutputStream(fileOut);
		    out.writeObject(joueur);
		    out.close();
		    fileOut.close();
		    System.out.println("\nSerialisation des ennemis terminée avec succès...\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		StackPane fenetreJeu = new StackPane();
		Group fenetreJeuBis = new Group();
		
		
		Canvas canvas = new Canvas(1000,1000);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		fenetreJeuBis.getChildren().add(canvas);
		fenetreJeu.getChildren().add(fenetreJeuBis);
		
		String[][] saisieMain = 
			{{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png"},
			{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","tiles.png","tiles.png","tiles.png","door.png","tiles.png","tiles.png","tiles.png","tiles.png","tiles.png"},
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
		
		Carte carte = new Carte("images",saisieMain,canvas.getWidth(),canvas.getHeight(),fenetreJeu);
		ControleCarte ctlCarte = new ControleCarte(carte,gc);
		carte.getCase(6, 6).addRessource(new Ressource(TypeRessource.Cle));
		Joueur joueur = new Joueur("Bob",50,6,3);
		Ennemi ennemi = new Ennemi(50,16,5,Categorie.Boss,Race.Humain);
		ControleEnnemi ctlEnnemi = new ControleEnnemi("imagesmonstres/zombie.png",carte,gc,ennemi,32,32);
		
		ControleJoueur ctlJoueur = new ControleJoueur("link2.png",carte,joueur,gc,120,120);
		ControleOuverturePorte ctlOP = new ControleOuverturePorte((ControleEntite)ctlJoueur);
		ControleInventaireItemsEntite ctlInventaireJoueur = new ControleInventaireItemsEntite((Entité)joueur);
		ControleBarreDeVie ctlbdv2 = new ControleBarreDeVie(ctlJoueur,50,950,100);
		ControleBarreDeVie ctlbdvEnnemi = new ControleBarreDeVie(ctlEnnemi,ctlEnnemi.getPositionXPixel()-20,ctlEnnemi.getPositionYPixel()-30,10);
		fenetreJeuBis.getChildren().add(ctlbdvEnnemi);
		ControleStatsRessourcesEntite ctlSRE = new ControleStatsRessourcesEntite(joueur,50,50,100);
		joueur.Ramasser(new Arme(TypeArme.EpéeCourte,1));
		fenetreJeuBis.getChildren().add(ctlbdv2);
		fenetreJeu.getChildren().add(ctlSRE);
		FlowPane fp = new FlowPane();
		fp.getChildren().addAll(ctlOP,ctlInventaireJoueur);
		fp.setAlignment(Pos.BOTTOM_RIGHT);
		fenetreJeu.getChildren().add(fp);

        new Thread(ctlJoueur).start();
        new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ctlCarte.afficheCarte();
					ctlJoueur.afficheJoueur(ctlJoueur.getLastDirection(), ctlJoueur.getBouge());
					ctlEnnemi.afficheEnnemi();
				}
			}}).start();
        Scene scene = new Scene(fenetreJeu);
        scene.getStylesheets().add("file:css/styles.css");
		arg0.addEventFilter(KeyEvent.ANY,ctlJoueur);
        arg0.setScene(scene);
        arg0.show();
	}
}
