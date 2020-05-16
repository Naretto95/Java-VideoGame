package Jeu;

public class Arme extends Item {
	
	private int Durabilit�;
	private TypeArme Type;
	private int Degats;
	private int Port�e;
	private int Am�lior�;
	
	public Arme(TypeArme _Type, int _Niveau ){
		super(true,_Niveau,false);
		switch (_Type) {
		case Ep�eLongue:
			this.setDegats(_Niveau * 5);
			this.setDurabilit�(20);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(2);
			break;
			
		case Ep�eCourte:
			this.setDegats(_Niveau * 3);
			this.setDurabilit�(10);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(1);
			break;
		
		case Arc:
			this.setDegats(_Niveau * 2);
			this.setDurabilit�(15);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(10);
			break;
		
		case Main:
			this.setRamass�(true);
			this.setDegats(_Niveau * 1);
			this.setDurabilit�(9999999);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(1);
			break;
			
		default:
			break;
		}
		
	}
	
	public void Ameliorer() {
		if (this.getAm�lior�()<5) {
			this.setAm�lior�(this.getAm�lior�()+1);
			this.setDegats(this.getDegats()+10);
		}
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
	
	public int getAm�lior�() {
		return Am�lior�;
	}
	
	public void setAm�lior�(int am�lior�) {
		Am�lior� = am�lior�;
	}
	
	public int getDegats() {
		return Degats;
	}
	
	public void setDegats(int degats) {
		Degats = degats;
	}
	

}
