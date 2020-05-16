package Jeu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Entit� {
	
	private int Vie;
	private int PositionX;
	private int PositionY;
	private int Niveau;
	private Item enMain;
	private EtatEntit� Etat;
	private Map<Item, Integer> InventaireItem = new HashMap<>();
	private Map<Ressource, Integer> InventaireRessource = new HashMap<>();
	
	public Entit�(int _Niveau, int _PositionX, int PositionY) {
		Map<Item, Integer> _InventaireItem = new HashMap<>();
		Map<Ressource, Integer> _InventaireRessource= new HashMap<>();
		this.setEtat(EtatEntit�.Vivant);
		this.setNiveau(_Niveau);
		this.setInventaireItem(_InventaireItem);
		this.setInventaireRessource(_InventaireRessource);
		this.setPositionX(_PositionX);
		this.setPositionY(PositionY);
		this.setVie(_Niveau*100);
	}
	
	public void DegatsRe�ues(Arme _Arme){
		this.setVie(this.getVie()-_Arme.getDegats());
		if (this.getVie()<=0) {
			this.setEtat(EtatEntit�.Mort);
		}
	}
	
	public void Attaque(Entit� _Entit�, Arme _Arme){
		boolean etourdis = new Random().nextInt(5)==0;
		boolean rater = new Random().nextInt(5)==0;
		if (this.getEtat()==EtatEntit�.Vivant) {
			if (etourdis==true && rater==false) {
				if (Math.abs(_Entit�.getPositionX()-this.getPositionX())<= _Arme.getPort�e() || Math.abs(_Entit�.getPositionY()-this.getPositionY())<= _Arme.getPort�e() ) {
					_Entit�.DegatsRe�ues(_Arme);
					_Entit�.setEtat(EtatEntit�.Etourdis);
					_Arme.setDurabilit�(_Arme.getDurabilit�()-1);
					if (_Arme.getDurabilit�()<=0) {
						_Arme.setEtat(false);
					}
				}
			}else {
				if (rater==false) {
					if (Math.abs(_Entit�.getPositionX()-this.getPositionX())<= _Arme.getPort�e()) {
						_Entit�.DegatsRe�ues(_Arme);
						_Arme.setDurabilit�(_Arme.getDurabilit�()-1);
						if (_Arme.getDurabilit�()<=0) {
							_Arme.setEtat(false);
						}
					}
				}
			}
			if (this instanceof Joueur && _Entit� instanceof Ennemi) {
				if (_Entit�.getEtat()==EtatEntit�.Mort) {
					((Joueur)this).setExperience(((Joueur)this).getExperience()+((Ennemi)_Entit�).getExperienceMonstre());
					((Joueur)this).levelup();
				}
			}
		}
	}
	
	public void Utiliser(Entit� _Entit�) {
		if (this.getEtat()==EtatEntit�.Vivant) {
			if (this.enMain instanceof Arme) {
				Attaque(_Entit�,(Arme)this.enMain);
			}else {
				if (this.enMain instanceof Potion) {
					switch (((Potion)this.enMain).getEffet()) {
					case Etourdissement:
						if (_Entit�.getPositionX() == this.getPositionX()) {
							this.Empoisonner(_Entit�);
						}else {
							if (_Entit�.getPositionY() == this.getPositionY()) {
								this.Empoisonner(_Entit�);
							}
						}
						break;
						
					case Poison:
						if (_Entit�.getPositionX() == this.getPositionX()) {
							this.Empoisonner(_Entit�);
						}else {
							if (_Entit�.getPositionY() == this.getPositionY()) {
								this.Empoisonner(_Entit�);
							}
						}
						break;
						
					case GainDeVie :
						if (_Entit� instanceof Joueur) {
							if (this.getVie()<this.getNiveau()*100) {
								this.setVie(this.getNiveau()*100);
							}
						}
						break;
						
					case GainDegats :
						// A REMPLIR
						break;
						
					default:
						break;
					}
					_Entit�.Empoisonnement((Potion)this.enMain);
					((Potion)this.enMain).setEtat(false);
				}
			}
		}
	}
	
	public void Empoisonner(Entit� _Entit�) {
		_Entit�.Empoisonnement((Potion)this.enMain);
		((Potion)this.enMain).setEtat(false);
	}
	
	
	public void Empoisonnement(Potion _Potion){
		switch (_Potion.getEffet()) {
		case Etourdissement:
			this.setEtat(EtatEntit�.Etourdis);
			break;
		case Poison:
			this.setVie(this.getVie()-_Potion.getNiveau()*10);
			if (this.getVie()<=0) {
				this.setEtat(EtatEntit�.Mort);
			}
			break;

		default:
			break;
		}
	}
	
	public int getVie() {
		return Vie;
	}
	
	public void setVie(int vie) {
		Vie = vie;
	}

	public int getPositionX() {
		return PositionX;
	}

	public void setPositionX(int positionX) {
		PositionX = positionX;
	}

	public int getPositionY() {
		return PositionY;
	}

	public void setPositionY(int positionY) {
		PositionY = positionY;
	}

	public int getNiveau() {
		return Niveau;
	}

	public void setNiveau(int niveau) {
		Niveau = niveau;
	}

	public Item getEnMain() {
		return enMain;
	}

	public void setEnMain(Item enMain) {
		this.enMain = enMain;
	}

	public EtatEntit� getEtat() {
		return Etat;
	}

	public void setEtat(EtatEntit� etat) {
		Etat = etat;
	}

	public Map<Item, Integer> getInventaireItem() {
		return InventaireItem;
	}

	public void setInventaireItem(Map<Item, Integer> inventaireItem) {
		InventaireItem = inventaireItem;
	}

	public Map<Ressource, Integer> getInventaireRessource() {
		return InventaireRessource;
	}

	public void setInventaireRessource(Map<Ressource, Integer> inventaireRessource) {
		InventaireRessource = inventaireRessource;
	}
	
	
	
	
}
