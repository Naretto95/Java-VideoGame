package Jeu;

public class Arme extends Item {
	
	private int Durabilité;
	private TypeArme Type;
	private int Degats;
	private int Portée;
	private int Amélioré;
	
	public Arme(TypeArme _Type, int _Niveau ){
		super(true,_Niveau,false);
		switch (_Type) {
		case EpéeLongue:
			this.setDegats(_Niveau * 5);
			this.setDurabilité(20);
			this.setType(_Type);
			this.setAmélioré(0);
			this.setPortée(2);
			break;
			
		case EpéeCourte:
			this.setDegats(_Niveau * 3);
			this.setDurabilité(10);
			this.setType(_Type);
			this.setAmélioré(0);
			this.setPortée(1);
			break;
		
		case Arc:
			this.setDegats(_Niveau * 2);
			this.setDurabilité(15);
			this.setType(_Type);
			this.setAmélioré(0);
			this.setPortée(10);
			break;
		
		case Main:
			this.setRamassé(true);
			this.setDegats(_Niveau * 1);
			this.setDurabilité(9999999);
			this.setType(_Type);
			this.setAmélioré(0);
			this.setPortée(1);
			break;
			
		default:
			break;
		}
		
	}
	
	public void Ameliorer() {
		if (this.getAmélioré()<5) {
			this.setAmélioré(this.getAmélioré()+1);
			this.setDegats(this.getDegats()+10);
		}
	}
	
	public int getDurabilité() {
		return Durabilité;
	}
	
	public void setDurabilité(int durabilité) {
		Durabilité = durabilité;
	}
	
	public TypeArme getType() {
		return Type;
	}
	
	public void setType(TypeArme type) {
		Type = type;
	}
	
	public int getPortée() {
		return Portée;
	}
	
	public void setPortée(int portée) {
		Portée = portée;
	}
	
	public int getAmélioré() {
		return Amélioré;
	}
	
	public void setAmélioré(int amélioré) {
		Amélioré = amélioré;
	}
	
	public int getDegats() {
		return Degats;
	}
	
	public void setDegats(int degats) {
		Degats = degats;
	}
	

}
