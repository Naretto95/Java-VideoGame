package game;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import controller.EntityController;
import javafx.scene.image.Image;
/**
 * 
 * @date 21/05/20
 * @author Corentin BRILLANT
 *
 */
public class GameMap extends Observable implements Observer,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Integer MAP_SCROLLED = new Integer(40);
	
	private transient ArrayList<ArrayList<Image>> tileImages;
	private transient Map<String,Image> gameElementImages;
	private ArrayList<ArrayList<Tile>> tiles;
	private Viewport viewport;
	private int tileWidthPixels=50;
	private int tileHeightPixels=50;
	private int gameWindowHeight=720;
	private int gameWindowWidth=1280;
	
	public GameMap(String imagesDirectory, String[][] mapLayout,double gameWindowWidth,double gameWindowHeight) {
		this.gameWindowWidth=(int) gameWindowWidth;
		this.gameWindowHeight=(int) gameWindowHeight;
		int rowCount = mapLayout.length;
		int columnCount = mapLayout[0].length;
		this.viewport=new Viewport(0,0,this.gameWindowWidth,this.gameWindowHeight,columnCount*this.tileWidthPixels-this.gameWindowWidth,rowCount*this.tileHeightPixels-this.gameWindowHeight);
		this.gameElementImages= new HashMap<String,Image>();
		File imagesDir = new File(imagesDirectory);
		for (String s : imagesDir.list()) {
			this.gameElementImages.put(s,new Image("file:" + imagesDirectory + "/" + s,tileWidthPixels,tileHeightPixels,false,true));
		}
		this.tileImages = new ArrayList<ArrayList<Image>>();
		this.tiles = new ArrayList<ArrayList<Tile>>();
		for (int i =0;i<rowCount;i++) {
			tileImages.add(new ArrayList<Image>());
			tiles.add(new ArrayList<Tile>());
			for(int j = 0 ; j<columnCount;j++) {
				tileImages.get(i).add(this.gameElementImages.get(mapLayout[i][j]));
				switch(mapLayout[i][j]) {
				case "brique.png":
					tiles.get(i).add(new Tile(Tile.EMPTY));
					break;
				case "water.png":
					tiles.get(i).add(new Tile(Tile.OBSTACLE));
					break;
				case "plancher.jpg":
					tiles.get(i).add(new Tile(Tile.EMPTY));
					break;
				case "tiles.png":
					tiles.get(i).add(new Tile(Tile.OBSTACLE));
					break;
				case "door.png":
					Door door = new Door(1);
					tiles.get(i).add(new Tile(door));
					door.addObserver(this.getTile(i, tiles.get(i).size()-1));
					door.addObserver(this);
					break;
				case "enclume_ameliore.png":
					Anvil anvil = new Anvil(AnvilType.UPGRADE);
					tiles.get(i).add(new Tile(anvil));
					break;
				case "enclume_repare.png":
					Anvil anvil2 = new Anvil(AnvilType.REPAIR);
					tiles.get(i).add(new Tile(anvil2));
					break;
				default:
					tiles.get(i).add(new Tile());
					break;				
				}
			}
		}
		
	}
	
	/** Places {@code entity} at cell (i, j) if that cell is free. */
	public boolean placeEntity(Entity entity,int i , int j) {
		Tile tile = this.getTile(i, j);
		if (tile.getContent()==Tile.EMPTY) {
			Tile startTile = this.getTile(entity.getPositionY(), entity.getPositionX());
			startTile.setContent(Tile.EMPTY);
			tile.setContent(entity);
			entity.setPositionX(j);
			entity.setPositionY(i);
			if (entity instanceof Player) {
				Player player = (Player) entity;
				Item item = tile.getItem();
				while(item!=null && player.pickUp(item)) {
					item = tile.getItem(); 
				}	
				if (item!=null) {tile.addItem(item);}
				Resource resource = tile.getResource();
				while(resource!=null && player.pickUp(resource)) {
					resource = tile.getResource(); 
				}	
				if (resource!=null) {tile.addResource(resource);}
			}
			return true;
		}
		return false;
	}
	
	public Tile getTile(int i, int j) {
		if (i<this.tiles.size() && j<this.tiles.get(i).size()) {
			return this.tiles.get(i).get(j);
		}
		return null;
	}

	public Viewport getViewport() {
		return viewport;
	}
	
	/** Moves the on-screen viewport across the map by (deltaX, deltaY) pixels. */
	public boolean moveViewport(int deltaX, int deltaY) {
		boolean result = this.viewport.move(deltaX, deltaY);
		this.setChanged();
		this.notifyObservers(MAP_SCROLLED);
		return result;
	}
	
	/** Updates the map's tile images based on each tile's content. */
	public void refreshTileImages() {
		for (int i = 0;i <this.tileImages.size();i++) {
			for (int j = 0;j <this.tileImages.get(i).size();j++) {
				if (this.tiles.get(i).get(j).getContent() instanceof Door && ((Door)this.tiles.get(i).get(j).getContent()).isOpen) {
					this.tileImages.get(i).set(j,this.gameElementImages.get("plancher.jpg"));
				}
				
			}
		}
	}

	public ArrayList<ArrayList<Image>> getTileImages() {
		return tileImages;
	}

	public int getTileWidthPixels() {
		return tileWidthPixels;
	}

	public int getTileHeightPixels() {
		return tileHeightPixels;
	}
	/** Inner class representing a door. */
	public final class Door extends Observable implements Serializable {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int keysRequired;
		private boolean isOpen=false;
		
		public Door(int keysRequired) {
			
			this.keysRequired=keysRequired;
			
		}
		
		/** Lets the entity controlled by {@code entityController} open this door, but only if it has enough keys. */
		public boolean open(EntityController entityController) {
			int keyCount=0;
			Entity entity = entityController.getEntity();
			for (Entry<Resource, Integer> entry : entity.getResourceInventory().entrySet()) {
				if (entry.getKey().getType()==ResourceType.KEY) {
					keyCount=entry.getValue();
					if (keyCount>=this.keysRequired) {
						Map<Resource, Integer> resources = entity.getResourceInventory();
						resources.put(entry.getKey(), keyCount-this.keysRequired);
						entity.setResourceInventory(resources);
						this.setOpen(true);
						this.deleteObservers();
						return true;
					}
				}
			}
			return false;
		}
		
		public int getKeysRequired() {
			return keysRequired;
		}
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
			refreshTileImages();
			this.setChanged();
			this.notifyObservers(Tile.DOOR_OPENED);
		}
		public boolean isOpen() {
			return isOpen;
		}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}

	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
}
