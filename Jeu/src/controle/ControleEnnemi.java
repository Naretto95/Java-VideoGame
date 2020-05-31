package controle;

import java.util.Observable;
import java.util.Random;

import Jeu.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import threadService.EnnemiService;


public class ControleEnnemi extends ControleEntite{

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnnemiService ennemiService;
	public ControleEnnemi(String feuilleDeSpriteEntite, Carte carte, GraphicsContext gc, Entité entite,
                          int hauteurPixelEntite, int largeurPixelEntite) {

        super(feuilleDeSpriteEntite, carte, gc, entite, hauteurPixelEntite, largeurPixelEntite);
        this.ennemiService = new EnnemiService(this);
        this.ennemiService.start();
    }
	
	/**{@literal l'ennemi cherche a attaquer dans toutes les directions et frappe si il y a un joueur}*/
	public void autoDefenseCollision() {
		int i = this.getEntite().getPositionY();
		int j = this.getEntite().getPositionX();
		autoDefenseCollisionCase(i-1,j);
		autoDefenseCollisionCase(i+1,j);
		autoDefenseCollisionCase(i,j+1);
		autoDefenseCollisionCase(i,j-1);
	}
	
	/**{@literal l'ennemi frappe si il y a un joueur dans la case i,j}*/
	public void autoDefenseCollisionCase(int i,int j) {
		if(i>=0 && j>=0 && i<this.getCarte().getImagesCasesCarte().size() && j<this.getCarte().getImagesCasesCarte().get(i).size()) {
			Object objet = this.getCarte().getCase(i, j).getContenu();
			if (this.getEntite() instanceof Ennemi && objet instanceof Joueur) {
				Ennemi ennemi = (Ennemi) this.getEntite();
				if (ennemi.getPositionY()<i) {this.setLastDirection(KeyCode.DOWN);}
				else if (ennemi.getPositionY()>i) {this.setLastDirection(KeyCode.UP);}
				else if (ennemi.getPositionX()<j) {this.setLastDirection(KeyCode.RIGHT);}
				else if (ennemi.getPositionX()>j) {this.setLastDirection(KeyCode.LEFT);}
				this.attaque();
				}
		}
	}


    public void afficheEnnemi() {
		this.setPositionXPixel(((this.getEntite().getPositionX())*this.getCarte().getLargeurCasePixel()) - this.getCarte().getFenetreEcran().getPosXPixelEcran());
		this.setPositionYPixel(((this.getEntite().getPositionY())*this.getCarte().getHauteurCasePixel()) - this.getCarte().getFenetreEcran().getPosYPixelEcran());
    	if (this.getEntite().getEtat()!=EtatEntité.Mort) {
    		getGc().drawImage(this.getFeuilleDeSpriteEntite(),getIndiceSprite()*this.getLargeurPixelEntite(),0,
                    this.getLargeurPixelEntite(),this.getHauteurPixelEntite(),this.getPositionXPixel()-5,
                    this.getPositionYPixel()+this.getCarte().getHauteurCasePixel()-this.getHauteurPixelEntite()-15,
                    this.getLargeurPixelEntite()+10,this.getHauteurPixelEntite()+15);
    	}
    }

    ////////////// quelques methodes qui nous servirons par la suite, mais appartiennent pas à cette classe ////////////////////


    // creation d'un ennemi d'une façon aleatoire dans le tableau
/*
    public void CreerEnnemi(int nbEnnemis){
                int err=0;
            for (int i = 0; i < nbEnnemis ; i++) {
                   int _PositionX=Math.random()*(X_max-X_min); // a changer une fois d'acc sur la taille du tableau intervalle
                   int _PositionY=Math.random()*(Y_max-Y_min); //intervalle  entre Xmin et Xmax

                if (tab[_PositionX][_PositionY]==null) { // verifier si la case est vide sinon on peut faire !instanceof (Joueur, Ennemi, Obstacle) car pas sùr que null est le bon truc

                Ennemi ennemi = new Ennemi((int) Math.random()*(10-1),(int) _PositionX,(int) _PositionY,getRandomCategorie(),getRandomRace() );

                 }
                 else {
                    err++; // si par exemple on veut crreer 10 ennemis, et il tombe sur 2 cases occupés il va creerque 8
                            // donc je rajoute le truc au nb d'ennemis pour qu'il fait bien 10
                }
                // root.getChildren().add(ennemi); rajouter ça dans la classe de type Parent, avec root : Pane root = new Pane();
             }
        }
*/
        // pour recuperer les trucs en random dans une boucle et eviter de le faire manuelement plusieurs fois
    public static Categorie getRandomCategorie() {
        Random random = new Random();
        return Categorie.values()[random.nextInt(Categorie.values().length)];
    }
    public static Race getRandomRace() {
        Random random = new Random();
        return Race.values()[random.nextInt(Race.values().length)];
    }
    public static TypeArme getRandomArme() {
        Random random = new Random();
        return TypeArme.values()[random.nextInt(TypeArme.values().length)];
    }
    public static TypeRessource getRandomRessource() {
        Random random = new Random();
        return TypeRessource.values()[random.nextInt(TypeRessource.values().length)];
    }


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

    // pour supprimer un ennemi, on pourra faire ennemi.hide() ou bien setvisibility(false)

}

