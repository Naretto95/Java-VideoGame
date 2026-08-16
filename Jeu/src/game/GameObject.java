package game;

import java.io.Serializable;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean pickedUp;

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	

}
