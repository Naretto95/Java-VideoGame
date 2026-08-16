
package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
/**
 * 
 * @author Corentin BRILLANT
 *
 */
public class Tile implements Observer,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Integer EMPTY = new Integer(0);
	public static Integer OBSTACLE = new Integer(1);
	public static Integer DOOR = new Integer(2);
	public static Integer DOOR_OPENED= new Integer(30);
	
	private Object content;
	private Stack<Item> items;
	private Stack<Resource> resources;


	public Tile(Stack<Item> items,Stack<Resource> resources) {
		this.content=EMPTY;
		this.items = items;
		this.resources = resources;
	}
	
	
	public Tile(Object content,Stack<Item> items,Stack<Resource> resources) {
		this.content=content;
		this.items = items;
		this.resources = resources;
	}
	
	
	public Tile(Object content) {
		this.content=content;
		this.items = new Stack<Item>();
		this.resources = new Stack<Resource>();
	}
	
	public Tile() {
		this.content = EMPTY;
		this.items = new Stack<Item>();
		this.resources = new Stack<Resource>();
	}
	
	public void addItem(Item item) {
		this.items.push(item);
	}
	public void addResource(Resource resource) {
		this.resources.push(resource);
	}
	public Item getItem() {
		if (!this.items.isEmpty()) {
			return this.items.pop();
		}
		return null;
	}
	public Resource getResource() {
		if(!this.resources.isEmpty()) {
			return this.resources.pop();
		}
		return null;
	}
	public ArrayList<Resource> getAllResources() {
		ArrayList<Resource> allResources = new ArrayList<Resource>();
		while (!this.resources.isEmpty()) {
			allResources.add(this.getResource());
		}
		return allResources;
	}
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> allItems = new ArrayList<Item>();
		while (!this.items.isEmpty()) {
			allItems.add(this.getItem());
		}
		return allItems;
	}
	public void addResources(Stack<Resource> resources) {
		while(!resources.isEmpty()) {
			this.addResource(resources.pop());
		}
	}
	public void addItems(Stack<Item> items) {
		while(!items.isEmpty()) {
			this.addItem(items.pop());
		}
	}

	public Object getContent() {
		return content;
	}
	
	public boolean hasLoot() {
		return (!this.items.isEmpty())||(!this.resources.isEmpty());
	}

	public void setContent(Object content) {
		if (this.getContent() instanceof Entity) {
			Entity previousEntity = (Entity) this.getContent();
			previousEntity.deleteObserver(this);
		}
		if (content instanceof Entity) {
			Entity newEntity = (Entity) content;
			newEntity.addObserver(this);
		}
		this.content = content;
	}
	
	public void addObjects(List<GameObject> objects) {
		for (int i =0;i<objects.size();i++) {
			if (objects.get(i) instanceof Resource) {
				this.addResource((Resource) objects.get(i));
			}
			else if(objects.get(i) instanceof Item) {
				this.addItem((Item) objects.get(i));
			}
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(Entity.DIED)) {
			if (o instanceof Entity && this.getContent() instanceof Entity && ((Entity) this.getContent()).equals((Entity) o)) {
				Entity deadEntity = (Entity) o;
				List<GameObject> droppedLoot = ((Enemy)deadEntity).dropLoot();
				this.addObjects(droppedLoot);
				this.setContent(Tile.EMPTY);
				o.deleteObserver(this);
			}else {
				System.out.println("There was a problem depositing this dead entity's inventory");
			}
		}
		if (arg.equals(DOOR_OPENED)) {
			this.setContent(EMPTY);
		}
	}
}
