package Jeu;

public class Arme extends Item {
	
	private int Durabilit�;
	private TypeArme Type;
	private int Degats;
	private int Port�e;
	
	public Arme(TypeArme _Type, int _Niveau ){
		super(true,_Niveau,false);
		switch (_Type) {
		case Ep�eLongue:
			this.setDegats(_Niveau * 5);
			this.setDurabilit�(3);
			this.setType(_Type);
			this.setPort�e(2);
			break;
			
		case Ep�eCourte:
			this.setDegats(_Niveau * 3);
			this.setDurabilit�(3);
			this.setType(_Type);
			this.setPort�e(1);
			break;
		
		case Arc:
			this.setDegats(_Niveau * 2);
			this.setDurabilit�(15);
			this.setType(_Type);
			this.setPort�e(10);
			break;
		
		case Main:
			this.setRamass�(true);
			this.setDegats(_Niveau*1);
			this.setDurabilit�(9999999);
			this.setType(_Type);
			this.setPort�e(1);
			break;
			
		default:
			break;
		}
		
	}
	
	public void Ameliorer(Joueur _Joueur) {
		((Arme)_Joueur.getEnMain()).setDegats(((Arme)_Joueur.getEnMain()).getDegats()+10);
	}
	
	public int getDurabilit�() {
		return Durabilit�;
	}
	
	public void setDurabilit�(int durabilit�) {
		Durabilit� = durabilit�;
	}
	
	public TypeArme getType() {
		return Type;
	}
	
	public void setType(TypeArme type) {
		Type = type;
	}
	
	public int getPort�e() {
		return Port�e;
	}
	
	public void setPort�e(int port�e) {
		Port�e = port�e;
	}
	
	public int getDegats() {
		return Degats;
	}
	
	public void setDegats(int degats) {
		Degats = degats;
	}
	
	public String toString() {
		return ""+this.getType()+" "+this.getNiveau() +" "+this.getDurabilit�();
	}
	

}
