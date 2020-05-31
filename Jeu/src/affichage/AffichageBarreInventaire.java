package affichage;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
/**
 * @date 23/05/2020
 * @author Corentin BRILLANT
 */


public class AffichageBarreInventaire extends GridPane{
	
		public static double zoom=0;
		public static Image epeeLongue = new Image("file:imagesitems/epeelongue.png",50+50*zoom,50+50*zoom,false,true);
		public static Image epeeCourte = new Image("file:imagesitems/epeecourte.png",50+50*zoom,50+50*zoom,false,true);
		public static Image vide = new Image("file:imagesitems/casevide.png",50+50*zoom,50+50*zoom,false,true);
		public static Image arc = new Image("file:imagesitems/arc.png",50+50*zoom,50+50*zoom,false,true);
		public static Image main = new Image("file:imagesitems/main.png",50+50*zoom,50+50*zoom,false,true);
		public static Image potionVie = new Image("file:imagesitems/potionvie.png",50+50*zoom,50+50*zoom,false,true);
		public static Image potionPoison = new Image("file:imagesitems/potionpoison.png",50+50*zoom,50+50*zoom,false,true);
		public static Image potionArme = new Image("file:imagesitems/potiongaindegats.png",50+50*zoom,50+50*zoom,false,true);
		public static Image potionEtourdi = new Image("file:imagesitems/potionetourdi.png",50+50*zoom,50+50*zoom,false,true);
		

		private ImageView caseEpeeCourte = new ImageView(AffichageBarreInventaire.vide);
		private ImageView caseEpeeLongue = new ImageView(AffichageBarreInventaire.vide);
		private ImageView caseArc = new ImageView(AffichageBarreInventaire.vide);
		private ImageView caseArmeEnMain = new ImageView(AffichageBarreInventaire.main);
		private ImageView casePotionVie = new ImageView(AffichageBarreInventaire.vide);
		private ImageView casePotionPoison = new ImageView(AffichageBarreInventaire.vide);
		private ImageView casePotionArme = new ImageView(AffichageBarreInventaire.vide);
		private ImageView casePotionEtourdi = new ImageView(AffichageBarreInventaire.vide);
		
		public AffichageBarreInventaire(){
			this.add(caseArmeEnMain, 1, 1);
			GridPane.setMargin(this.getChild(1,1), new Insets(0,20,0,0));
			this.add(this.caseArc, 2, 1);
			this.add(this.caseEpeeCourte, 3, 1);
			this.add(this.caseEpeeLongue, 4, 1);
			this.add(this.casePotionArme, 5, 1);
			this.add(this.casePotionEtourdi, 6, 1);
			this.add(this.casePotionPoison, 7, 1);
			this.add(this.casePotionVie, 8, 1);
		}
		
		public void videCases() {
			caseEpeeCourte.setImage(AffichageBarreInventaire.vide);
			caseEpeeLongue.setImage(AffichageBarreInventaire.vide);
			caseArc.setImage(AffichageBarreInventaire.vide);
			caseArmeEnMain.setImage(AffichageBarreInventaire.main);
			casePotionVie.setImage(AffichageBarreInventaire.vide);
			casePotionPoison.setImage(AffichageBarreInventaire.vide);
			casePotionArme.setImage(AffichageBarreInventaire.vide);
			casePotionEtourdi.setImage(AffichageBarreInventaire.vide);
		}

		public ImageView getCaseEpeeCourte() {
			return caseEpeeCourte;
		}

		public ImageView getCaseEpeeLongue() {
			return caseEpeeLongue;
		}

		public ImageView getCaseArc() {
			return caseArc;
		}

		public ImageView getCaseArmeEnMain() {
			return caseArmeEnMain;
		}

		public ImageView getCasePotionVie() {
			return casePotionVie;
		}

		public ImageView getCasePotionPoison() {
			return casePotionPoison;
		}

		public ImageView getCasePotionArme() {
			return casePotionArme;
		}

		public ImageView getCasePotionEtourdi() {
			return casePotionEtourdi;
		}

		public void setCaseEpeeCourte() {
			this.caseEpeeCourte.setImage(AffichageBarreInventaire.epeeCourte);
		}

		public void setCaseEpeeLongue() {
			this.caseEpeeLongue.setImage(AffichageBarreInventaire.epeeLongue);
		}

		public void setCaseArc() {
			this.caseArc.setImage(AffichageBarreInventaire.arc);
		}

		public void setCaseArmeEnMain(Image image) {
			this.caseArmeEnMain.setImage(image);
		}

		public void setCasePotionVie() {
			this.casePotionVie.setImage(AffichageBarreInventaire.potionVie);
		}

		public void setCasePotionPoison() {
			this.casePotionPoison.setImage(AffichageBarreInventaire.potionPoison);
		}

		public void setCasePotionArme() {
			this.casePotionArme.setImage(AffichageBarreInventaire.potionArme);
		}

		public void setCasePotionEtourdi() {
			this.casePotionEtourdi.setImage(AffichageBarreInventaire.potionEtourdi);
		}
		public Node getChild(final int row, final int column) {
		    Node result = null;
		    ObservableList<Node> childrens = this.getChildren();

		    for (Node node : childrens) {
		        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
		            result = node;
		            break;
		        }
		    }

		    return result;
		}
}
