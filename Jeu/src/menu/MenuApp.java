package menu;

import game.GameSession;
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
    String title="Beat the Zombies"; // so it can be changed everywhere at once

    private GameSession gameSession;
    private MediaPlayer mediaPlayer;

    private Parent createContent(Stage primaryStage) {
    	        
        this.music();
        this.gameSession = new GameSession();

        Pane root = new Pane();
        
        ImageView imageView = new ImageView(new Image("file:imagesmenu/Fallout4_bg.jpg"));

        //ImageView imgIcone = new ImageView(new Image(getClass().getResource("icon.png").toExternalForm())); // no longer used

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

//--------------------------------- New Game Option ---------------------------------------------------------------


        MenuItem itemNew = new MenuItem("New Game", 250);
        itemNew.setOnAction(() -> {

            FadeTransition ft = new FadeTransition(Duration.seconds(3), masker); // with a loading screen lasting 3s

            ft.setToValue(1);

            ft.setOnFinished(a -> {
            	this.mediaPlayer.stop();
				root.getChildren().setAll(this.gameSession.startNewGame());
				primaryStage.addEventFilter(KeyEvent.ANY,this.gameSession.getPlayerController());
                
            });

            ft.play();
            
            
            
        });
        menuBox.addItem(itemNew);

//--------------------------------- Load Option -----------------------------------------------------------------------



        MenuItem load = new MenuItem("Load", 250);
        load.setOnAction(() -> {
        	StackPane main = this.gameSession.loadGame();
        	if (main!=null) {
            	root.getChildren().setAll(main);
            	this.mediaPlayer.stop();
                primaryStage.addEventFilter(KeyEvent.ANY,this.gameSession.getPlayerController());
        	}
        });
        menuBox.addItem(load);

//--------------------------------- Tips Option -----------------------------------------------------------------------

        MenuItem astuce = new MenuItem("Tips", 250);
        astuce.setOnAction(() -> {
            menuBox2.addItems(
                    new MenuItem("Pick up and drop items", 400),
                    new MenuItem("Switch weapon", 510));
        });
        menuBox.addItem(astuce);

//--------------------------------- Settings Option -----------------------------------------------------------------------


        MenuItem settings = new MenuItem("Settings", 250);
        settings.setOnAction(() -> {

            menuBox2.addItems(
                    new MenuItem("Graphics", 400),
                    new MenuItem("Sound", 510),
                    new MenuItem("Difficulty", 510));
        });


        menuBox.addItem(settings);

        //menuBox.addItem(new MenuItem("Credits", 250));

//--------------------------------- Credits Option -----------------------------------------------------------------------

        File file = new File("../Rapport.pdf");
        HostServices hostServices = getHostServices();
        MenuItem Credits = new MenuItem("Credits", 250);
        Credits.setOnAction(() -> hostServices.showDocument(file.getAbsolutePath()));
        menuBox.addItem(Credits);



//--------------------------------- Quit Option -----------------------------------------------------------------------

        MenuItem itemExit = new MenuItem("Quit", 250);
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
