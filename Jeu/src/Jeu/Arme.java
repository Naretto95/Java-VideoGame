package Jeu;

public class Arme extends Item {
	
	private int Durabilit�;
	private TypeArme Type;
	private int Degats;
	private int Port�e;
	private int Am�lior�;
	
	public Arme(TypeArme _Type, int _Niveau ){
		switch (_Type) {
		case Ep�eLongue:
			this.setDegats(_Niveau * 5);
			this.setDurabilit�(20);
			this.setEtat(true);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(2);
			this.setNiveau(_Niveau);
			break;
			
		case Ep�eCourte:
			this.setDegats(_Niveau * 3);
			this.setDurabilit�(10);
			this.setEtat(true);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(1);
			this.setNiveau(_Niveau);
			break;
		
		case Arc:
			this.setDegats(_Niveau * 2);
			this.setDurabilit�(15);
			this.setEtat(true);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(10);
			this.setNiveau(_Niveau);
			break;
		
		case Main:
			this.setDegats(_Niveau * 1);
			this.setDurabilit�(9999999);
			this.setEtat(true);
			this.setType(_Type);
			this.setAm�lior�(0);
			this.setPort�e(1);
			this.setNiveau(_Niveau);
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
