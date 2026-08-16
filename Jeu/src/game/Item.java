package game;

import java.io.Serializable;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Item extends GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean active;
	private int level;

	public Item(boolean active, int level, boolean pickedUp) {
		this.setActive(active);
		this.setLevel(level);
		this.setPickedUp(pickedUp);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
