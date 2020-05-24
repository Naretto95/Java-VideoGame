package controle;

import java.util.ArrayList;

import Jeu.Carte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ControleCarte {
	 
	private Carte carte;
	private GraphicsContext gc;
	
	public ControleCarte(Carte carte, GraphicsContext gc) {
		this.carte = carte;
		this.gc = gc;
	}
	

	
	
	public void afficheCarte() {
		ArrayList<ArrayList<Image>> imagesCasesCarte = carte.getImagesCasesCarte();
		for (int i = 0 ; i < imagesCasesCarte.size();i++) {
			for (int j = 0 ; j < imagesCasesCarte.get(i).size();j++) {
				gc.drawImage(imagesCasesCarte.get(i).get(j), this.carte.getHauteurCasePixel()*j, this.carte.getLargeurCasePixel()*i);
			}
		}
	}
}
