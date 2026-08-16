package game;

/**
 * 
 * @author Lilian Naretto
 *
 */

public class Anvil{
	public static Integer ANVIL_USED = new Integer(800);
	private AnvilType Type;
	
	public Anvil(AnvilType type) {
		this.setType(type);
	}
	
	public void use(Player player) {
		switch (this.getType()) {
		case REPAIR:
			player.repair();
			break;
			
		case UPGRADE:
			player.upgrade();
			break;			
			
		default:
			break;
		}
	}
	
	public AnvilType getType() {
		return Type;
	}

	public void setType(AnvilType type) {
		Type = type;
	}	
}
