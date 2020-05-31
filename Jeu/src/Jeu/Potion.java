package Jeu;
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
	private Effet Effet;
	
	public Potion(Effet _Effet, int _Niveau) {
		super(true,_Niveau,false);
		this.setEffet(_Effet);
	}
	
	public Effet getEffet() {
		return Effet;
	}

	public void setEffet(Effet effet) {
		Effet = effet;
	}
	
	public String toString() {
		return "Effet:"+this.getEffet()+" Niveau:"+this.getNiveau();
	}
	
}
