package display;

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


public class InventoryBarDisplay extends GridPane{
	
		public static double zoom=0;
		public static Image longswordImage = new Image("file:imagesitems/epeelongue.png",50+50*zoom,50+50*zoom,false,true);
		public static Image shortswordImage = new Image("file:imagesitems/epeecourte.png",50+50*zoom,50+50*zoom,false,true);
		public static Image emptyImage = new Image("file:imagesitems/casevide.png",50+50*zoom,50+50*zoom,false,true);
		public static Image arc = new Image("file:imagesitems/arc.png",50+50*zoom,50+50*zoom,false,true);
		public static Image unarmedImage = new Image("file:imagesitems/main.png",50+50*zoom,50+50*zoom,false,true);
		public static Image healPotionImage = new Image("file:imagesitems/potionvie.png",50+50*zoom,50+50*zoom,false,true);
		public static Image poisonPotionImage = new Image("file:imagesitems/potionpoison.png",50+50*zoom,50+50*zoom,false,true);
		public static Image damagePotionImage = new Image("file:imagesitems/potiongaindegats.png",50+50*zoom,50+50*zoom,false,true);
		public static Image stunPotionImage = new Image("file:imagesitems/potionetourdi.png",50+50*zoom,50+50*zoom,false,true);
		

		private ImageView shortswordSlot = new ImageView(InventoryBarDisplay.emptyImage);
		private ImageView longswordSlot = new ImageView(InventoryBarDisplay.emptyImage);
		private ImageView bowSlot = new ImageView(InventoryBarDisplay.emptyImage);
		private ImageView equippedSlot = new ImageView(InventoryBarDisplay.unarmedImage);
		private ImageView healPotionSlot = new ImageView(InventoryBarDisplay.emptyImage);
		private ImageView poisonPotionSlot = new ImageView(InventoryBarDisplay.emptyImage);
		private ImageView damagePotionSlot = new ImageView(InventoryBarDisplay.emptyImage);
		private ImageView stunPotionSlot = new ImageView(InventoryBarDisplay.emptyImage);
		
		public InventoryBarDisplay(){
			this.add(equippedSlot, 1, 1);
			GridPane.setMargin(this.getChild(1,1), new Insets(0,20,0,0));
			this.add(this.bowSlot, 2, 1);
			this.add(this.shortswordSlot, 3, 1);
			this.add(this.longswordSlot, 4, 1);
			this.add(this.damagePotionSlot, 5, 1);
			this.add(this.stunPotionSlot, 6, 1);
			this.add(this.poisonPotionSlot, 7, 1);
			this.add(this.healPotionSlot, 8, 1);
		}
		
		public void clearSlots() {
			shortswordSlot.setImage(InventoryBarDisplay.emptyImage);
			longswordSlot.setImage(InventoryBarDisplay.emptyImage);
			bowSlot.setImage(InventoryBarDisplay.emptyImage);
			equippedSlot.setImage(InventoryBarDisplay.unarmedImage);
			healPotionSlot.setImage(InventoryBarDisplay.emptyImage);
			poisonPotionSlot.setImage(InventoryBarDisplay.emptyImage);
			damagePotionSlot.setImage(InventoryBarDisplay.emptyImage);
			stunPotionSlot.setImage(InventoryBarDisplay.emptyImage);
		}

		public ImageView getShortswordSlot() {
			return shortswordSlot;
		}

		public ImageView getLongswordSlot() {
			return longswordSlot;
		}

		public ImageView getBowSlot() {
			return bowSlot;
		}

		public ImageView getEquippedSlot() {
			return equippedSlot;
		}

		public ImageView getHealPotionSlot() {
			return healPotionSlot;
		}

		public ImageView getPoisonPotionSlot() {
			return poisonPotionSlot;
		}

		public ImageView getDamagePotionSlot() {
			return damagePotionSlot;
		}

		public ImageView getStunPotionSlot() {
			return stunPotionSlot;
		}

		public void setShortswordSlot() {
			this.shortswordSlot.setImage(InventoryBarDisplay.shortswordImage);
		}

		public void setLongswordSlot() {
			this.longswordSlot.setImage(InventoryBarDisplay.longswordImage);
		}

		public void setBowSlot() {
			this.bowSlot.setImage(InventoryBarDisplay.arc);
		}

		public void setEquippedSlot(Image image) {
			this.equippedSlot.setImage(image);
		}

		public void setHealPotionSlot() {
			this.healPotionSlot.setImage(InventoryBarDisplay.healPotionImage);
		}

		public void setPoisonPotionSlot() {
			this.poisonPotionSlot.setImage(InventoryBarDisplay.poisonPotionImage);
		}

		public void setDamagePotionSlot() {
			this.damagePotionSlot.setImage(InventoryBarDisplay.damagePotionImage);
		}

		public void setStunPotionSlot() {
			this.stunPotionSlot.setImage(InventoryBarDisplay.stunPotionImage);
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
