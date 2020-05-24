package controle;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import Jeu.*;
import javafx.scene.canvas.GraphicsContext;


public class ControleEnnemi extends ControleEntite implements Observer {

     public ControleEnnemi(String feuilleDeSpriteEntite, Carte carte, GraphicsContext gc, Entité entite,
                          int hauteurPixelEntite, int largeurPixelEntite) {

        super(feuilleDeSpriteEntite, carte, gc, entite, hauteurPixelEntite, largeurPixelEntite);
       // ControleCarte.afficheCarte();

    }


    public void afficheEnnemi() {
    	if (this.entite.getEtat()==EtatEntité.Vivant) {
    		gc.drawImage(this.feuilleDeSpriteEntite,indiceSprite*this.largeurPixelEntite,0,
                    this.largeurPixelEntite,this.hauteurPixelEntite,this.getPositionXPixel()-20,
                    this.getPositionYPixel()+this.carte.getHauteurCasePixel()-this.hauteurPixelEntite-40,
                    this.largeurPixelEntite+40,this.hauteurPixelEntite+40);
    	}
        


    }


    @Override
    public void update(Observable o, Object arg) {

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

    // pour supprimer un ennemi, on pourra faire ennemi.hide() ou bien setvisibility(false)

}

