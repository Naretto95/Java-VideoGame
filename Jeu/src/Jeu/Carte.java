package Jeu;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import controle.ControleEntite;
import javafx.scene.image.Image;
/**
 * 
 * @date 21/05/20
 * @author Corentin BRILLANT
 *
 */
public class Carte extends Observable implements Observer,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Integer CARTE_QUI_BOUGE = new Integer(40);
	
	private transient ArrayList<ArrayList<Image>> imagesCasesCarte;
	private transient Map<String,Image> listeImagesElementsJeu;
	private ArrayList<ArrayList<Case>> casesCarte;
	private FenetreEcran fenetreEcran;
	private int largeurCasePixel=50;
	private int hauteurCasePixel=50;
	private int hauteurFenetreJeu=720;
	private int largeurFenetreJeu=1280;
	
	public Carte(String repertoireImages, String[][] saisieCarte,double largeurFenetreJeu,double hauteurFenetreJeu) {
		this.largeurFenetreJeu=(int) largeurFenetreJeu;
		this.hauteurFenetreJeu=(int) hauteurFenetreJeu;
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
					casesCarte.get(i).add(new Case(Case.OBSTACLE));
					break;
				case "plancher.jpg":
					casesCarte.get(i).add(new Case(Case.VIDE));
					break;
				case "tiles.png":
					casesCarte.get(i).add(new Case(Case.OBSTACLE));
					break;
				case "door.png":
					Porte porte = new Porte(1);
					casesCarte.get(i).add(new Case(porte));
					porte.addObserver(this.getCase(i, casesCarte.get(i).size()-1));
					porte.addObserver(this);
					break;
				case "enclume_ameliore.png":
					Enclume enclume = new Enclume(TypeEnclume.EnclumeAmeliorer);
					casesCarte.get(i).add(new Case(enclume));
					break;
				case "enclume_repare.png":
					Enclume enclume2 = new Enclume(TypeEnclume.EnclumeReparer);
					casesCarte.get(i).add(new Case(enclume2));
					break;
				default:
					casesCarte.get(i).add(new Case());
					break;				
				}
			}
		}
		
	}
	
	/** {@literal met l'entité entite en case i,j si cela est possible}
	 *  */	
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
				while(item!=null && joueur.Ramasser(item)) {
					item = _case.getItem(); 
				}	
				if (item!=null) {_case.addItem(item);}
				Ressource ressource = _case.getRessource();
				while(ressource!=null && joueur.Ramasser(ressource)) {
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
	
	/**{@literal déplacer la fenetre de l'ecran sur la carte d'un mouvement (deltaX, deltaY) en pixels}
	*/
	
	public boolean deplaceFenetre(int deltaX, int deltaY) {
		boolean result = this.fenetreEcran.deplaceFenetreEcran(deltaX, deltaY);
		this.setChanged();
		this.notifyObservers(CARTE_QUI_BOUGE);
		return result;
	}
	
	/**{@literal met à jour les images de la carte en fonction du contenu des cases}*/
	public void majImagesCasesCarte() {
		for (int i = 0;i <this.imagesCasesCarte.size();i++) {
			for (int j = 0;j <this.imagesCasesCarte.get(i).size();j++) {
				if (this.casesCarte.get(i).get(j).getContenu() instanceof Porte && ((Porte)this.casesCarte.get(i).get(j).getContenu()).isOpen) {
					this.imagesCasesCarte.get(i).set(j,this.listeImagesElementsJeu.get("plancher.jpg"));
				}
				
			}
		}
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
	/**{@literal la classe statique des portes}*/
	
	public final class Porte extends Observable implements Serializable {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int nbCle;
		private boolean isOpen=false;
		
		public Porte(int nbCle) {
			
			this.nbCle=nbCle;
			
		}
		
		/**{@literal permet à l'entite controlée par ctlEntite d'ouvrir la porte uniquement si elle a assez de clés}*/
		public boolean ouvre(ControleEntite ctlEntite) {
			int nbClees=0;
			Entité entite = ctlEntite.getEntite();
			for (Entry<Ressource, Integer> entry : entite.getInventaireRessource().entrySet()) {
				if (entry.getKey().getType()==TypeRessource.Cle) {
					nbClees=entry.getValue();
					if (nbClees>=this.nbCle) {
						Map<Ressource, Integer> ressources = entite.getInventaireRessource();
						ressources.put(entry.getKey(), nbClees-this.nbCle);
						entite.setInventaireRessource(ressources);
						this.setOpen(true);
						this.deleteObservers();
						return true;
					}
				}
			}
			return false;
		}
		
		public int getNbCle() {
			return nbCle;
		}
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
			majImagesCasesCarte();
			this.setChanged();
			this.notifyObservers(Case.OUVERTURE_PORTE);
		}
		public boolean isOpen() {
			return isOpen;
		}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<ArrayList<Case>> getCasesCarte() {
		return casesCarte;
	}
}
