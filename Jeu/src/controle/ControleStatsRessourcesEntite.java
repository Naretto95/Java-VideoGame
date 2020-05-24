package controle;

import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import Jeu.Entité;
import Jeu.Ressource;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ControleStatsRessourcesEntite extends GridPane implements Observer{

		private Entité entite;	
		private ImageView cle = new ImageView(new Image("file:imagesitems/cle.png",50,50,false,true));
		private ImageView or = new ImageView(new Image("file:imagesitems/or.png",50,50,false,true));
		private ImageView fer = new ImageView(new Image("file:imagesitems/fer.png",50,50,false,true));
		private ImageView bois = new ImageView(new Image("file:imagesitems/bois.png",50,50,false,true));
		private Text nbCle=new Text("0");
		private Text nbOr=new Text("0");
		private Text nbFer=new Text("0");
		private Text nbBois=new Text("0");
		
		
		public ControleStatsRessourcesEntite(Entité entite, int posX, int posY, int zoom) {
			this.setAlignment(Pos.BOTTOM_RIGHT);
			this.entite= entite;
			this.nbBois.setFont(new Font(40));
			this.nbCle.setFont(new Font(40));
			this.nbFer.setFont(new Font(40));
			this.nbOr.setFont(new Font(40));
			this.add(this.cle, 1, 1);
			ControleStatsRessourcesEntite.setMargin(cle, new Insets(50,0,0,0));
			this.add(or, 1, 2);
			this.add(fer, 1, 3);
			this.add(bois, 1, 4);
			this.add(nbCle, 2, 1);
			ControleStatsRessourcesEntite.setMargin(nbCle, new Insets(50,20,0,0));
			this.add(nbOr, 2, 2);
			this.add(nbFer, 2, 3);
			this.add(nbBois, 2, 4);
			for(Entry<Ressource, Integer> entry : this.entite.getInventaireRessource().entrySet()) {
				Ressource cle = entry.getKey();
				switch(cle.getType()) {
				case Or:
					this.nbOr.setText(""+entry.getValue());
					break;
				case Fer:
					this.nbFer.setText(""+entry.getValue());
					break;
				case Bois:
					this.nbBois.setText(""+entry.getValue());
					break;
				case Cle:
					this.nbCle.setText(""+entry.getValue());
					break;
				default:
					break;
				}
			}
			this.entite.addObserver(this);
		}


		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			if (arg1 instanceof Integer && ((Integer) arg1).equals(Entité.RESSOURCES_MODIFIEES)) {
				for(Entry<Ressource, Integer> entry : this.entite.getInventaireRessource().entrySet()) {
					Ressource cle = entry.getKey();
					switch(cle.getType()) {
					case Or:
						this.nbOr.setText(""+entry.getValue());
						break;
					case Fer:
						this.nbFer.setText(""+entry.getValue());
						break;
					case Bois:
						this.nbBois.setText(""+entry.getValue());
						break;
					case Cle:
						this.nbCle.setText(""+entry.getValue());
						break;
					default:
						break;
					}
				}
			}
		}
	
}
