package Jeu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

import controle.ControleBarreDeVie;
import controle.ControleCarte;
import controle.ControleEnclume;
import controle.ControleEnnemi;
import controle.ControleEntite;
import controle.ControleExpBarre;
import controle.ControleInventaireItemsEntite;
import controle.ControleJoueur;
import controle.ControleOuverturePorte;
import controle.ControleStatsRessourcesEntite;
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
import saveAndLoad.RessourceManager;
import threadService.AffichageService;
import threadService.RunJoueurService;
/**
 * @author Ilham Laatarsi
 */
public class Partie implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private Media media;
	private MediaPlayer mediaPlayer;
	//les composants graphiques
	private StackPane MainPane;//le composant graphique principal
	private Group GroupCanvasEtLifeBar;//le composant qui contient le canvas et les barres de vie des persos
	private Canvas canvas; // le canvas
	private GraphicsContext gc;//son GraphicsContext
	private FlowPane fp;//le FlowPane qui contient le bouton des portes et la barre d'inventaire du joueur
	private Button btnSave;
	private Button exitButton;
	private Button pauseButton;
	private Button playButton;
	
	//le dur de la partie
	private Carte carte;//la carte de cette partie
	private Joueur joueur;//le joueur de la partie 
	private ArrayList<Ennemi> listeEnnemi;//la liste des ennemis de la partie
	
	//les controleurs de la partie
	private ControleCarte ctlCarte;
	private ControleJoueur ctlJoueur;
	private ControleExpBarre ctlExpJoueur;
	private ControleBarreDeVie ctlBdvJoueur;//le controle de la barre de vie du joueur
	private ControleInventaireItemsEntite ctlInventaireJoueur;
	private ControleStatsRessourcesEntite ctlSRE;//le controle des ressources du joueur
	private ControleOuverturePorte ctlOP;
	private ControleEnclume ctlEnclume;
	private ArrayList<ControleEnnemi> listeControleEnnemi;
	private ArrayList<ControleBarreDeVie> listeCtlBdvEnnemi;
	
	
	//les threads de la partie
	private RunJoueurService threadJoueur;
	private AffichageService threadAffichageJeu;

	private Timer Duree;
	private TypeIssus Issus;
	public boolean paused;
	public boolean win;
	
	//la carte par défaut rentrée à la main
	String[][] saisieMain = 
		{{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","tiles.png","tiles.png","tiles.png","door.png","tiles.png","tiles.png","tiles.png","tiles.png","tiles.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","water.png","water.png","water.png","brique.png","brique.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","plancher.jpg","plancher.jpg","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","tiles.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","tiles.png","tiles.png","tiles.png","tiles.png","door.png","tiles.png","tiles.png","tiles.png","tiles.png","tiles.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","water.png","water.png","water.png","brique.png","brique.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","water.png","water.png","water.png","water.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"},
		{"brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","brique.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","tiles.png","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","plancher.jpg","brique.png","brique.png","brique.png","brique.png","brique.png"}};
	
	//........................................................................
	
	public Partie() {
		
		//on crée les composants 
		this.MainPane = new StackPane();
		this.GroupCanvasEtLifeBar = new Group();
		this.canvas = new Canvas(1280,720);
		this.gc = canvas.getGraphicsContext2D();
		this.fp = new FlowPane();
		fp.setAlignment(Pos.BOTTOM_CENTER);
		this.btnSave = new Button("Sauvegarder");
		this.btnSave.setAlignment(Pos.TOP_RIGHT);
		this.btnSave.setOnMouseClicked((e)->{this.save();});
		this.exitButton = new Button("Quitter");
		this.exitButton.setAlignment(Pos.TOP_RIGHT);
		this.exitButton.setOnMouseClicked((e)->{this.quitter();});
		this.pauseButton = new Button("Pause");
		this.pauseButton.setAlignment(Pos.TOP_RIGHT);
		this.pauseButton.setOnMouseClicked((e)->{this.pause();});
		this.playButton = new Button("Jouer");
		this.playButton.setAlignment(Pos.TOP_RIGHT);
		this.playButton.setOnMouseClicked((e)->{this.jouer();});
		try {
			File file = new File("musiques/song2.mp3");
			this.media = new Media(file.toURI().toString());
			this.mediaPlayer = new MediaPlayer(this.media);
		}catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		this.Duree = new Timer();
    	this.paused = false;
	}
		
	//..........................................................................
	
	@SuppressWarnings("resource")
	/**
	 * @date 26/05/20
	 * @author Corentin BRILLANT
	 */
	public StackPane nouvellePartie(){
		
		//import d'une autre carte faite avec un csv
		List<List<String>> records = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("cartes/carte9.csv"));
			String line;
			while ((line = br.readLine()) != null) {
			        String[] values = line.split(",");
			        records.add(Arrays.asList(values));
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
			
			//on crée le dur du jeu (carte , joueur , ennemis)
			this.carte = new Carte("images",records2,canvas.getWidth(),canvas.getHeight());
			for (int i = 0;i<20;i++) {
				carte.getCase(6, 6).addRessource(new Ressource(TypeRessource.Cle));
			}for (int i = 0;i<20;i++) {
				carte.getCase(6, 6).addRessource(new Ressource(TypeRessource.Bois));
			}
			this.joueur = new Joueur("Bob",21,7,7);
			joueur.Ramasser(new Arme(TypeArme.EpéeCourte,20));
			this.listeEnnemi = new ArrayList<Ennemi>();
			this.listeEnnemi.add(new Ennemi(10,16,5,Categorie.Normal,Race.Nain));
			this.listeEnnemi.add(new Ennemi(10,18,6,Categorie.Normal,Race.Nain));
			this.listeEnnemi.add(new Ennemi(10,16,7,Categorie.Normal,Race.Nain));
			//Joueur heros = new Joueur("Lilian",1,0,0);
	    	//Ennemi monstre = new Ennemi(1,1,0,Categorie.Normal,Race.Humain);
			
			
			this.preparerPartie();
			
			return this.MainPane;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * @date 26/05/20
	 * @author Corentin BRILLANT
	 */
	public StackPane choisirPartie() {
		
		JFileChooser dialogue = new JFileChooser(new File("sauvegardes"));
		File fichier;
		
		if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		    fichier = dialogue.getSelectedFile();
			if (fichier!=null) {
				try {
					Partie partieChoisie = (Partie) RessourceManager.load(fichier.getCanonicalPath().toString());
					
					this.carte = partieChoisie.getCarte();
					this.joueur = partieChoisie.getJoueur();
					this.listeEnnemi = partieChoisie.getListeEnnemi();
							
					this.preparerPartie();
					return this.MainPane;
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Il y a eu une erreur lors de la sélection du fichier");
		return null;
	}
	/**
	 * @date 26/05/20
	 * @author Corentin BRILLANT
	 */
	public void preparerPartie() {
		
		/* Les observables ne sont pas serialisables, ils faut donc les recharger à chaque chargement de partie, de même que pour les liaisons entre les composants graphiques */
		
		//on crée les controles du jeu qui se souscrivent automatiquement auprès des observables qui les concernent
		this.ctlCarte = new ControleCarte(carte,gc);
		this.ctlJoueur = new ControleJoueur("imagesmonstres/link2.png",carte,joueur,gc,120,120);
		this.ctlOP = new ControleOuverturePorte((ControleEntite)ctlJoueur);
		this.ctlEnclume = new ControleEnclume((ControleEntite)ctlJoueur);
		this.ctlInventaireJoueur = new ControleInventaireItemsEntite((Entité)joueur);
		this.ctlSRE = new ControleStatsRessourcesEntite(joueur,50,50,100);
		this.ctlBdvJoueur = new ControleBarreDeVie(ctlJoueur,50,670,3);
		this.ctlExpJoueur = new ControleExpBarre(joueur,1050,10,2);
		this.listeControleEnnemi = new ArrayList<ControleEnnemi>();
		this.listeCtlBdvEnnemi = new ArrayList<ControleBarreDeVie>();
		for (int i =0;i<this.listeEnnemi.size();i++) {
			this.listeControleEnnemi.add(new ControleEnnemi("imagesmonstres/zombie.png",carte,gc,this.listeEnnemi.get(i),32,32));
			this.listeCtlBdvEnnemi.add(new ControleBarreDeVie(this.listeControleEnnemi.get(this.listeControleEnnemi.size()-1) ,1));
		}
				
		
		//on relie les controles de jeu aux composants graphiques du jeu
		GroupCanvasEtLifeBar.getChildren().add(canvas);
		this.fp.getChildren().addAll(ctlEnclume,ctlOP,ctlInventaireJoueur,btnSave,exitButton,pauseButton,playButton);
		for (int i =0 ; i<this.listeCtlBdvEnnemi.size();i++) {
			this.GroupCanvasEtLifeBar.getChildren().add(this.listeCtlBdvEnnemi.get(i));
		}
		this.GroupCanvasEtLifeBar.getChildren().add(this.ctlBdvJoueur);
		this.GroupCanvasEtLifeBar.getChildren().add(this.ctlExpJoueur);
		this.MainPane.getChildren().add(GroupCanvasEtLifeBar);
		this.MainPane.getChildren().add(ctlSRE);
		this.MainPane.getChildren().add(fp);
		

		//on crée les threads du jeu
		this.threadJoueur = new RunJoueurService(this);
		this.threadAffichageJeu = new AffichageService(this);
		
		//on lance les threads du jeu pour lancer la partie
		this.threadJoueur.start();
		this.threadAffichageJeu.start();
		if (mediaPlayer!=null) {this.mediaPlayer.play();}
		
	}
	
	public boolean gameOver() {
		return (this.joueur.getEtat()==EtatEntité.Mort);
	}
	
	public void pause() {
		this.paused=true;
		this.threadAffichageJeu.stop();
		if (this.mediaPlayer!=null) {this.mediaPlayer.stop();}
		this.Duree.cancel();
	}
		
	public void save() {
		
		DateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		try {
			RessourceManager.save(this.getCarte().getCasesCarte(), "sauvegardes/save_"+format.format(date));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void exit() {
		System.exit(0);
	}
	
	public StackPane recommencer() {
		return this.nouvellePartie();
	}
	
	/**
	 * 
	 * @author Corentin BRILLANT
	 *
	 */
	
	public void jouer() {
		/*la fonction permet de relancer les threads de la partie après une pause*/
		this.paused = false;
		this.Duree = new Timer();
		if (this.mediaPlayer!=null) {this.mediaPlayer.seek(Duration.ZERO);this.mediaPlayer.play();}
		this.threadJoueur.restart();
		this.threadAffichageJeu.start();
		
		
	}
	
	public void quitter() {
		this.paused=true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public GraphicsContext getGc() {
		return gc;
	}

	//..................................................................................
	public Timer getDuree() {
		return Duree;
	}
	
	public void setDuree(Timer duree) {
		Duree = duree;
	}
	
	public TypeIssus getIssus() {
		return Issus;
	}
	
	public void setIssus(TypeIssus issus) {
		Issus = issus;
	}

	public Carte getCarte() {
		return carte;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public ControleJoueur getCtlJoueur() {
		return ctlJoueur;
	}

	public ArrayList<Ennemi> getListeEnnemi() {
		return listeEnnemi;
	}

	public ControleCarte getCtlCarte() {
		return ctlCarte;
	}

	public ArrayList<ControleEnnemi> getListeControleEnnemi() {
		return listeControleEnnemi;
	}

	public boolean isPaused() {
		return paused;
	}

	public ControleBarreDeVie getCtlBdvJoueur() {
		return ctlBdvJoueur;
	}


}