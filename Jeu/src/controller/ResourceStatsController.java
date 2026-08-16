package controller;

import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import game.Entity;
import game.Resource;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * @date 23/05/2020
 * @author Corentin BRILLANT
 */


public class ResourceStatsController extends GridPane implements Observer{

		private Entity entity;
		private ImageView keyIcon = new ImageView(new Image("file:imagesitems/cle.png",50,50,false,true));
		private ImageView goldIcon = new ImageView(new Image("file:imagesitems/or.png",50,50,false,true));
		private ImageView ironIcon = new ImageView(new Image("file:imagesitems/fer.png",50,50,false,true));
		private ImageView woodIcon = new ImageView(new Image("file:imagesitems/bois.png",50,50,false,true));
		private Text keyCountText=new Text("0");
		private Text goldCountText=new Text("0");
		private Text ironCountText=new Text("0");
		private Text woodCountText=new Text("0");


		public ResourceStatsController(Entity entity, int posX, int posY, int zoom) {
			this.setAlignment(Pos.BOTTOM_RIGHT);
			this.entity= entity;
			this.woodCountText.setFont(new Font(40));
			this.keyCountText.setFont(new Font(40));
			this.ironCountText.setFont(new Font(40));
			this.goldCountText.setFont(new Font(40));
			this.add(this.keyIcon, 1, 1);
			ResourceStatsController.setMargin(keyIcon, new Insets(50,0,0,0));
			this.add(goldIcon, 1, 2);
			this.add(ironIcon, 1, 3);
			this.add(woodIcon, 1, 4);
			this.add(keyCountText, 2, 1);
			ResourceStatsController.setMargin(keyCountText, new Insets(50,20,0,0));
			this.add(goldCountText, 2, 2);
			this.add(ironCountText, 2, 3);
			this.add(woodCountText, 2, 4);
			for(Entry<Resource, Integer> entry : this.entity.getResourceInventory().entrySet()) {
				Resource resourceKey = entry.getKey();
				switch(resourceKey.getType()) {
				case GOLD:
					this.goldCountText.setText(""+entry.getValue());
					break;
				case IRON:
					this.ironCountText.setText(""+entry.getValue());
					break;
				case WOOD:
					this.woodCountText.setText(""+entry.getValue());
					break;
				case KEY:
					this.keyCountText.setText(""+entry.getValue());
					break;
				default:
					break;
				}
			}
			this.entity.addObserver(this);
		}


		@Override
		public void update(Observable arg0, Object arg1) {
			if (arg1 instanceof Integer && ((Integer) arg1).equals(Entity.RESOURCES_CHANGED)) {
				for(Entry<Resource, Integer> entry : this.entity.getResourceInventory().entrySet()) {
					Resource resourceKey = entry.getKey();
					switch(resourceKey.getType()) {
					case GOLD:
						this.goldCountText.setText(""+entry.getValue());
						break;
					case IRON:
						this.ironCountText.setText(""+entry.getValue());
						break;
					case WOOD:
						this.woodCountText.setText(""+entry.getValue());
						break;
					case KEY:
						this.keyCountText.setText(""+entry.getValue());
						break;
					default:
						break;
					}
				}
			}
		}

}
