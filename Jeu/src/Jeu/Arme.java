package Jeu;

import java.io.Serializable;
/**
 * 
 * @author Lilian Naretto
 *
 */

public class Arme extends Item implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Durabilité;
	private TypeArme Type;
	private boolean DegatsUp;
	private int Degats;
	private int Portée;
	
	public Arme(TypeArme _Type, int _Niveau ){
		super(true,_Niveau,false);
		this.setDegatsUp(false);
		switch (_Type) {
		case EpéeLongue:
			this.setDegats(_Niveau * 5);
			this.setDurabilité(20);
			this.setType(_Type);
			this.setPortée(2);
			break;
			
		case EpéeCourte:
			this.setDegats(_Niveau * 3);
			this.setDurabilité(10);
			this.setType(_Type);
			this.setPortée(1);
			break;
		
		case Arc:
			this.setDegats(_Niveau * 2);
			this.setDurabilité(15);
			this.setType(_Type);
			this.setPortée(10);
			break;
		
		case Main:
			this.setRamassé(true);
			this.setDegats(_Niveau*1);
			this.setDurabilité(9999999);
			this.setType(_Type);
			this.setPortée(1);
			break;
			
		default:
			break;
		}
		
	}
	
	public void Ameliorer(Joueur _Joueur) {
		((Arme)_Joueur.getEnMain()).setDegats(_Joueur.getNiveau()*3);
		switch (((Arme)_Joueur.getEnMain()).getType()) {
		case EpéeLongue:
			((Arme)_Joueur.getEnMain()).setDegats(_Joueur.getNiveau()*5);
			((Arme)_Joueur.getEnMain()).setNiveau(_Joueur.getNiveau());
			break;
			
		case EpéeCourte:
			((Arme)_Joueur.getEnMain()).setDegats(_Joueur.getNiveau()*3);
			((Arme)_Joueur.getEnMain()).setNiveau(_Joueur.getNiveau());
			break;
		
		case Arc:
			((Arme)_Joueur.getEnMain()).setDegats(_Joueur.getNiveau()*2);
			((Arme)_Joueur.getEnMain()).setNiveau(_Joueur.getNiveau());
			break;
			
		default:
			break;
		}
	}
	
	public void Reparer(Entité _Entité) {
		switch (((Arme)_Entité.getEnMain()).getType()) {
		case EpéeLongue:
			((Arme)_Entité.getEnMain()).setDurabilité(20);
			break;
			
		case EpéeCourte:
			((Arme)_Entité.getEnMain()).setDurabilité(10);
			break;
		
		case Arc:
			((Arme)_Entité.getEnMain()).setDurabilité(15);
			break;
			
		default:
			break;
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
	
	public int getDegats() {
		return Degats;
	}
	
	public void setDegats(int degats) {
		Degats = degats;
	}
	
	public String toString() {
		return "Type:"+this.getType()+" Niveau:"+this.getNiveau() +" Durabilité:"+this.getDurabilité();
	}

	public boolean isDegatsUp() {
		return DegatsUp;
	}

	public void setDegatsUp(boolean degatsUp) {
		DegatsUp = degatsUp;
	}
	

}
