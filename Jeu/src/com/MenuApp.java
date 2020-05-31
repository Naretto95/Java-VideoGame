package com;

import Jeu.Partie;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
/**
 * 
 * @author Hassan Yazane
 *
 */

public class MenuApp extends Application {
    String title="Beat the Zombies"; // pour pouvoir le changer partout d'un coup

    private Partie partie;
    private MediaPlayer mediaPlayer;

    private Parent createContent(Stage primaryStage) {
    	        
        this.music();
        this.partie = new Partie();

        Pane root = new Pane();
        
        ImageView imageView = new ImageView(new Image("file:imagesmenu/Fallout4_bg.jpg"));

        //ImageView imgIcone = new ImageView(new Image(getClass().getResource("icon.png").toExternalForm())); sert plus a rien

        imageView.setFitWidth(1280); //1280
        imageView.setFitHeight(720); //720

        SepiaTone tone = new SepiaTone(0.85);
        imageView.setEffect(tone);

        Rectangle masker = new Rectangle(1280, 720); // 1280, 720
        masker.setOpacity(0);
        masker.setMouseTransparent(true);

        MenuBox menuBox = new MenuBox(250, 350);
        menuBox.setTranslateX(250);
        menuBox.setTranslateY(230);

        MenuBox menuBox2 = new MenuBox(510, 350);
        menuBox2.setTranslateX(250 + 20 + 250);
        menuBox2.setTranslateY(230);

       /* MenuBox menuBox3 = new MenuBox(5, 5);
        menuBox3.setTranslateX(250 + 20 + 250 + 5);
        menuBox3.setTranslateY(230);

        */

        //menuBox2menuBox.addItem(new MenuItem("CONTINUE", 250));

//---------------------------------Option Nouvelle Partie---------------------------------------------------------------


        MenuItem itemNew = new MenuItem("Nouvelle Partie", 250);
        itemNew.setOnAction(() -> {
        	
            FadeTransition ft = new FadeTransition(Duration.seconds(3), masker); // avec page de loading qui durera 3s
            
            ft.setToValue(1);

            ft.setOnFinished(a -> {
            	this.mediaPlayer.stop();
				root.getChildren().setAll(this.partie.nouvellePartie());
				primaryStage.addEventFilter(KeyEvent.ANY,this.partie.getCtlJoueur());
                
            });

            ft.play();
            
            
            
        });
        menuBox.addItem(itemNew);

//---------------------------------Option Load-----------------------------------------------------------------------



        MenuItem load = new MenuItem("Charger", 250);
        load.setOnAction(() -> {
        	StackPane main = this.partie.choisirPartie();
        	if (main!=null) {
            	root.getChildren().setAll(main);
            	this.mediaPlayer.stop();
                primaryStage.addEventFilter(KeyEvent.ANY,this.partie.getCtlJoueur());
        	}
        });
        menuBox.addItem(load);

                ///////////////////////// Sauvegarder ////////////////////////////////////

         // à ajouter dans affichage/carte et faire apparaitre le boutton
        /*
        Button btnSave = new Button("Sauvegarder");
        btnSave.setOnAction(event -> {

            Joueur data = new Joueur( ); // mettre les parametres
            data.setExperience(exp);
            data.setVie(_);
            data.setPositionX(_);
            data.setPositionY(_);
            data.setNiveau(_);
            data.setInventaireRessource(_);
            data.setInventairePotion(_);
            data.setInventaireArme(_);
            data.setEnMain(_);
            data.setNom(_);
            data.setEtat(_);
            try {
                RessourceManager.save(data,"1.save"); // il faut :  implements java.io.Serializabl : Important
            }
            catch (Exception e) {
                System.out.println("Sauvegarde impossible : " + e.getMessage());
            }




                }







                );*/




//---------------------------------Option Astuces-----------------------------------------------------------------------

        MenuItem astuce = new MenuItem("Astuces", 250);
        astuce.setOnAction(() -> {
            menuBox2.addItems(
                    new MenuItem("Recuperer et jeter des Items", 400),
                    new MenuItem("Changer d'Arme", 510),
                    new MenuItem("blablabla", 510),
                    new MenuItem("blablabla", 510));
        });
        menuBox.addItem(astuce);

//---------------------------------Option parametres-----------------------------------------------------------------------


        MenuItem settings = new MenuItem("Parametres", 250);
        settings.setOnAction(() -> {

            menuBox2.addItems(
                    new MenuItem("Graphique", 400),
                    new MenuItem("Son", 510),
                    new MenuItem("Difficulté", 510),
                    new MenuItem("blablabla", 510));
        });


        menuBox.addItem(settings);

        //menuBox.addItem(new MenuItem("Credits", 250));

//---------------------------------Option Credits-----------------------------------------------------------------------

        File file = new File("H:\\Desktop\\cotegraph\\src\\com\\res\\test.pdf");
        HostServices hostServices = getHostServices();
        MenuItem Credits = new MenuItem("Credits", 250);
        Credits.setOnAction(() -> hostServices.showDocument(file.getAbsolutePath()));
        menuBox.addItem(Credits);



//---------------------------------Option Quitter-----------------------------------------------------------------------

        MenuItem itemExit = new MenuItem("Quitter", 250);
        itemExit.setOnAction(() -> System.exit(0));
        menuBox.addItem(itemExit);

        root.getChildren().addAll(imageView, menuBox, menuBox2, masker);
        return root;
    }

    public void music(){
		File file = new File("musiques/song.mp3");
		Media hit = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
        Scene scene = new Scene(createContent(primaryStage));
        primaryStage.setTitle(title);


        Image Imgicon = new Image("file:imagesmenu/icon.png");
        primaryStage.getIcons().add(Imgicon);


        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}