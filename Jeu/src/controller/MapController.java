package controller;

import java.util.ArrayList;

import game.GameMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * @date 20/05/2020
 * @author Corentin BRILLANT
 */


public class MapController {
	
	public static Image lootIcon;
	private GameMap map;
	private GraphicsContext gc;

	public MapController(GameMap map, GraphicsContext gc) {
		this.map = map;
		this.gc = gc;
		MapController.lootIcon = new Image("file:imagesitems/staff.png",this.map.getTileWidthPixels(),this.map.getTileHeightPixels(),false,true);
	}

	/**{@literal draws every visible tile of the map, plus a loot marker on tiles that hold items or resources}*/
	public void drawMap() {
		ArrayList<ArrayList<Image>> tileImages = map.getTileImages();
		int firstVisibleRow = this.map.getViewport().getY()/this.map.getTileHeightPixels();
		int firstVisibleColumn = this.map.getViewport().getX()/this.map.getTileWidthPixels();
		int screenX = (firstVisibleColumn)*this.map.getTileWidthPixels()-this.map.getViewport().getX();
		int screenY = (firstVisibleRow)*this.map.getTileHeightPixels()-this.map.getViewport().getY();
		int currentRow = firstVisibleRow;
		int currentColumn = firstVisibleColumn;
		while(screenY<this.map.getViewport().getHeight()) {
			while(screenX<this.map.getViewport().getWidth()) {
				gc.drawImage(tileImages.get(currentRow).get(currentColumn), screenX, screenY);
				if (this.map.getTile(currentRow, currentColumn).hasLoot()) {gc.drawImage(MapController.lootIcon, screenX, screenY);}
				screenX+=this.map.getTileWidthPixels();
				currentColumn++;
			}
			screenX=(firstVisibleColumn)*this.map.getTileWidthPixels()-this.map.getViewport().getX();
			screenY+=this.map.getTileHeightPixels();
			currentColumn=firstVisibleColumn;
			currentRow++;
		}
	}

}
