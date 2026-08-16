package game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.swing.JFileChooser;

import controller.HealthBarController;
import controller.MapController;
import controller.AnvilController;
import controller.EnemyController;
import controller.EntityController;
import controller.ExperienceBarController;
import controller.InventoryBarController;
import controller.PlayerController;
import controller.DoorController;
import controller.ResourceStatsController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import persistence.SaveManager;
import service.RenderService;
import service.PlayerLoopService;
/**
 * @author Ilham Laatarsi
 */
public class GameSession implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private Media media;
	private MediaPlayer mediaPlayer;
	//graphical components
	private StackPane mainPane;//the main graphical component
	private Group canvasAndHealthBarsGroup;//the component holding the canvas and the characters' health bars
	private Canvas canvas; // the canvas
	private GraphicsContext gc;//its GraphicsContext
	private FlowPane controlBar;//the FlowPane holding the door button and the player's inventory display
	private Button saveButton;
	private Button exitButton;
	private Button pauseButton;
	private Button playButton;

	//the game session's core state
	private GameMap map;//this game session's map
	private Player player;//this game session's player
	private ArrayList<Enemy> enemies;//this game session's list of enemies

	//the game session's controllers
	private MapController mapController;
	private PlayerController playerController;
	private ExperienceBarController experienceBarController;
	private HealthBarController playerHealthBarController;//the controller for the player's health bar display
	private InventoryBarController playerInventoryController;
	private ResourceStatsController resourceStatsController;//the controller for the player's resources
	private DoorController doorController;
	private AnvilController anvilController;
	private ArrayList<EnemyController> enemyControllers;
	private ArrayList<HealthBarController> enemyHealthBarControllers;


	//the game session's threads
	private PlayerLoopService playerLoopService;
	private RenderService renderService;

	private Timer gameTimer;
	private Outcome outcome;
	public boolean paused;
	public boolean won;
	
	
	//........................................................................
	
	public GameSession() {

		//create the components
		this.mainPane = new StackPane();
		this.canvasAndHealthBarsGroup = new Group();
		this.canvas = new Canvas(1280,720);
		this.gc = canvas.getGraphicsContext2D();
		this.controlBar = new FlowPane();
		controlBar.setAlignment(Pos.BOTTOM_CENTER);
		this.saveButton = new Button("Save");
		this.saveButton.setAlignment(Pos.TOP_RIGHT);
		this.saveButton.setOnMouseClicked((e)->{this.save();});
		this.exitButton = new Button("Exit");
		this.exitButton.setAlignment(Pos.TOP_RIGHT);
		this.exitButton.setOnMouseClicked((e)->{this.quitGame();});
		this.pauseButton = new Button("Pause");
		this.pauseButton.setAlignment(Pos.TOP_RIGHT);
		this.pauseButton.setOnMouseClicked((e)->{this.pause();});
		this.playButton = new Button("Play");
		this.playButton.setAlignment(Pos.TOP_RIGHT);
		this.playButton.setOnMouseClicked((e)->{this.resumeGame();});
		try {
			File file = new File("musiques/song2.mp3");
			this.media = new Media(file.toURI().toString());
			this.mediaPlayer = new MediaPlayer(this.media);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		this.gameTimer = new Timer();
    	this.paused = false;
	}
		
	//..........................................................................
	
	/**
	 * @date 26/05/20
	 * @author Corentin BRILLANT
	 */
	public StackPane startNewGame(){

		//import a map built from a CSV file
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("cartes/carte9.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
			        String[] values = line.split(",");
			        records.add(Arrays.asList(values));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		String[][] records2= new String[84][35];
		for (int i =0;i<records.size();i++) {
		   for (int j =0;j<records.get(i).size();j++) {
		    	switch (records.get(i).get(j)){
		    		case "0":
		    			records2[i][j]="plancher.jpg";
		    			break;
		    		case "1":
		    			records2[i][j]="tiles.png";
		    			break;
		    		case "2":
		    			records2[i][j]="door.png";
		    			break;
		    		case "3":
		    			records2[i][j]="enclume_ameliore.png";
		    		default:
		    			break;
		    	}
		    }
		}

		//create the game's core state (map, player, enemies)
		this.map = new GameMap("images",records2,canvas.getWidth(),canvas.getHeight());
		for (int i = 0;i<20;i++) {
			map.getTile(6, 6).addResource(new Resource(ResourceType.KEY));
		}for (int i = 0;i<20;i++) {
			map.getTile(6, 6).addResource(new Resource(ResourceType.WOOD));
		}
		this.player = new Player("Bob",21,7,7);
		player.pickUp(new Weapon(WeaponType.SHORTSWORD,20));
		this.enemies = new ArrayList<Enemy>();
		this.enemies.add(new Enemy(10,16,5,Category.NORMAL,Race.DWARF));
		this.enemies.add(new Enemy(10,18,6,Category.NORMAL,Race.DWARF));
		this.enemies.add(new Enemy(10,16,7,Category.NORMAL,Race.DWARF));

		this.setupGame();

		return this.mainPane;
	}
	/**
	 * @date 26/05/20
	 * @author Corentin BRILLANT
	 */
	public StackPane loadGame() {
		
		JFileChooser dialogue = new JFileChooser(new File("sauvegardes"));
		File fichier;
		
		if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		    fichier = dialogue.getSelectedFile();
			if (fichier!=null) {
				try {
					GameSession loadedSession = (GameSession) SaveManager.load(fichier.getCanonicalPath().toString());
					
					this.map = loadedSession.getMap();
					this.player = loadedSession.getPlayer();
					this.enemies = loadedSession.getEnemies();
							
					this.setupGame();
					return this.mainPane;
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("There was an error while selecting the file");
		return null;
	}
	/**
	 * @date 26/05/20
	 * @author Corentin BRILLANT
	 */
	public void setupGame() {

		/* Observables aren't serializable, so they need to be recreated every time a game session is loaded, and so do the bindings between the graphical components */

		//create the game controllers, which automatically subscribe to the observables they depend on
		this.mapController = new MapController(map,gc);
		this.playerController = new PlayerController("imagesmonstres/link2.png",map,player,gc,120,120);
		this.doorController = new DoorController((EntityController)playerController);
		this.anvilController = new AnvilController((EntityController)playerController);
		this.playerInventoryController = new InventoryBarController((Entity)player);
		this.resourceStatsController = new ResourceStatsController(player,50,50,100);
		this.playerHealthBarController = new HealthBarController(playerController,50,670,3);
		this.experienceBarController = new ExperienceBarController(player,1050,10,2);
		this.enemyControllers = new ArrayList<EnemyController>();
		this.enemyHealthBarControllers = new ArrayList<HealthBarController>();
		for (int i =0;i<this.enemies.size();i++) {
			this.enemyControllers.add(new EnemyController("imagesmonstres/zombie.png",map,gc,this.enemies.get(i),32,32));
			this.enemyHealthBarControllers.add(new HealthBarController(this.enemyControllers.get(this.enemyControllers.size()-1) ,1));
		}
				
		
		//wire the game controllers to the game's graphical components
		canvasAndHealthBarsGroup.getChildren().add(canvas);
		this.controlBar.getChildren().addAll(anvilController,doorController,playerInventoryController,saveButton,exitButton,pauseButton,playButton);
		for (int i =0 ; i<this.enemyHealthBarControllers.size();i++) {
			this.canvasAndHealthBarsGroup.getChildren().add(this.enemyHealthBarControllers.get(i));
		}
		this.canvasAndHealthBarsGroup.getChildren().add(this.playerHealthBarController);
		this.canvasAndHealthBarsGroup.getChildren().add(this.experienceBarController);
		this.mainPane.getChildren().add(canvasAndHealthBarsGroup);
		this.mainPane.getChildren().add(resourceStatsController);
		this.mainPane.getChildren().add(controlBar);
		

		//create the game's threads
		this.playerLoopService = new PlayerLoopService(this);
		this.renderService = new RenderService(this);

		//start the game's threads to launch the game session
		this.playerLoopService.start();
		this.renderService.start();
		if (mediaPlayer!=null) {this.mediaPlayer.play();}
		
	}
	
	public boolean isGameOver() {
		return (this.player.getState()==EntityState.DEAD);
	}
	
	public void pause() {
		this.paused=true;
		this.renderService.stop();
		if (this.mediaPlayer!=null) {this.mediaPlayer.stop();}
		this.gameTimer.cancel();
	}
		
	public void save() {
		
		DateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		try {
			SaveManager.save(this.getMap().getTiles(), "sauvegardes/save_"+format.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public StackPane restartGame() {
		return this.startNewGame();
	}
	
	/**
	 * 
	 * @author Corentin BRILLANT
	 *
	 */
	
	public void resumeGame() {
		/* resumes the game session's threads after a pause */
		this.paused = false;
		this.gameTimer = new Timer();
		if (this.mediaPlayer!=null) {this.mediaPlayer.seek(Duration.ZERO);this.mediaPlayer.play();}
		this.playerLoopService.restart();
		this.renderService.start();
		
		
	}
	
	public void quitGame() {
		this.paused=true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public GraphicsContext getGc() {
		return gc;
	}

	//..................................................................................
	public Timer getGameTimer() {
		return gameTimer;
	}
	
	public void setGameTimer(Timer gameTimer) {
		this.gameTimer = gameTimer;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public GameMap getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public MapController getMapController() {
		return mapController;
	}

	public ArrayList<EnemyController> getEnemyControllers() {
		return enemyControllers;
	}

	public boolean isPaused() {
		return paused;
	}

	public HealthBarController getPlayerHealthBarController() {
		return playerHealthBarController;
	}


}