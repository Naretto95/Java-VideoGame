package game;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Potion extends Item {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Effect Effect;
	
	public Potion(Effect effect, int level) {
		super(true,level,false);
		this.setEffect(effect);
	}
	
	public Effect getEffect() {
		return Effect;
	}

	public void setEffect(Effect effect) {
		Effect = effect;
	}
	
	public String toString() {
		return "Effect:"+this.getEffect()+" level:"+this.getLevel();
	}
	
}
