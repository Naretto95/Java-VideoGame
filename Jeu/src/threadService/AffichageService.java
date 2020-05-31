package threadService;

import java.util.ArrayList;

import Jeu.Partie;
import controle.ControleCarte;
import controle.ControleEnnemi;
import controle.ControleJoueur;
import javafx.animation.AnimationTimer;
/**
 * @date 24/05/2020
 * @author Corentin BRILLANT
 */


public class AffichageService extends AnimationTimer{
	
	private Partie partie;
	private ControleCarte ctlCarte;
	private ControleJoueur ctlJoueur;
	private ArrayList<ControleEnnemi> listeCtlEnnemi;
	
	public AffichageService(Partie partie) {
		this.partie = partie;
		this.ctlCarte=partie.getCtlCarte();
		this.ctlJoueur = partie.getCtlJoueur();
		this.listeCtlEnnemi=partie.getListeControleEnnemi();
	}
	@Override
	public void handle(long arg0) {
		// TODO Auto-generated method stub
		if(!partie.isPaused() && !partie.gameOver()) {
		ctlCarte.afficheCarte();
		for (int i = 0 ;i< listeCtlEnnemi.size();i++) {
			listeCtlEnnemi.get(i).afficheEnnemi();
		}
		ctlJoueur.afficheJoueur(ctlJoueur.getLastDirection(), ctlJoueur.getBouge());
		for (int i = 0 ;i< listeCtlEnnemi.size();i++) {
			listeCtlEnnemi.get(i).afficheAttaque();
		}
		ctlJoueur.afficheAttaque();
		}
	}
}
