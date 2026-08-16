package game;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Resource extends GameObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResourceType Type;
	
	public Resource(ResourceType type) {
		this.setPickedUp(true);
		this.setType(type);
	}
	
	public ResourceType getType() {
		return Type;
	}

	public void setType(ResourceType type) {
		Type = type;
	}
	
	public String toString() {
		return ""+this.getType();
	}
}
