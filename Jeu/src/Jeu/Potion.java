package Jeu;

public class Potion extends Item {
	
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
	
}
