package Jeu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class Carte {
	
	private ArrayList<ArrayList<Image>> imagesCasesCarte;
	private Map<String,Image> listeImagesElementsJeu;
	private ArrayList<ArrayList<Case>> casesCarte;
	private FenetreEcran fenetreEcran;
	private int largeurCasePixel=50;
	private int hauteurCasePixel=50;
	private int hauteurFenetreJeu=1000;
	private int largeurFenetreJeu=1000;
	
	public Carte(String repertoireImages, String[][] saisieCarte) {
		int nbLigne = saisieCarte.length;
		int nbColonne = saisieCarte[0].length;
		this.fenetreEcran=new FenetreEcran(0,0,this.largeurFenetreJeu,this.hauteurFenetreJeu,nbColonne*this.largeurCasePixel-this.largeurFenetreJeu,nbLigne*this.hauteurCasePixel-this.hauteurFenetreJeu);
		this.listeImagesElementsJeu= new HashMap<String,Image>(); 
		File repImages = new File(repertoireImages);
		for (String s : repImages.list()) {
			this.listeImagesElementsJeu.put(s,new Image("file:" + repertoireImages + "/" + s,largeurCasePixel,hauteurCasePixel,false,true));
		}
		this.imagesCasesCarte = new ArrayList<ArrayList<Image>>();
		this.casesCarte = new ArrayList<ArrayList<Case>>();
		for (int i =0;i<nbLigne;i++) {
			imagesCasesCarte.add(new ArrayList<Image>());
			casesCarte.add(new ArrayList<Case>());
			for(int j = 0 ; j<nbColonne;j++) {
				imagesCasesCarte.get(i).add(this.listeImagesElementsJeu.get(saisieCarte[i][j]));
				switch(saisieCarte[i][j]) {
				case "brique.png":
					casesCarte.get(i).add(new Case(Case.VIDE));
					break;
				case "water.png":
					casesCarte.get(i).add(new Case(Case.VIDE));
					break;	
				case "porte":
					casesCarte.get(i).add(new Case(Case.PORTE));
					break;
				default:
					casesCarte.get(i).add(new Case());
					break;				
				}
			}
		}
		
	}
	
	public boolean mettreEntite(Entité entite,int i , int j) {
		Case _case = this.getCase(i, j);
		if (_case.getContenu()==Case.VIDE) {
			Case _caseDepart = this.getCase(entite.getPositionY(), entite.getPositionX());
			_caseDepart.setContenu(Case.VIDE);
			_case.setContenu(entite);
			entite.setPositionX(j);
			entite.setPositionY(i);
			if (entite instanceof Joueur) {
				Joueur joueur = (Joueur) entite;
				Item item = _case.getItem();
				while(item!=null/* && joueur.Ramasser(item)*/ ) {
					item = _case.getItem(); 
				}	
				if (item!=null) {_case.addItem(item);}
				Ressource ressource = _case.getRessource();
				while(ressource!=null/* && joueur.Ramasser(ressource)*/ ) {
					ressource = _case.getRessource(); 
				}	
				if (ressource!=null) {_case.addRessource(ressource);}
			}
			return true;
		}
		return false;
	}
	
	public Case getCase(int i, int j) {
		if (i<this.casesCarte.size() && j<this.casesCarte.get(i).size()) {
			return this.casesCarte.get(i).get(j);
		}
		return null;
	}

	public FenetreEcran getFenetreEcran() {
		return fenetreEcran;
	}

	public ArrayList<ArrayList<Image>> getImagesCasesCarte() {
		return imagesCasesCarte;
	}

	public int getLargeurCasePixel() {
		return largeurCasePixel;
	}

	public int getHauteurCasePixel() {
		return hauteurCasePixel;
	}
	
}
