package Jeu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Entité {
	
	private int Vie;
	private int PositionX;
	private int PositionY;
	private int Niveau;
	private Item enMain;
	private EtatEntité Etat;
	private Map<Item, Integer> InventaireItem = new HashMap<>();
	private Map<Ressource, Integer> InventaireRessource = new HashMap<>();
	
	public Entité(int _Niveau, int _PositionX, int PositionY) {
		Map<Item, Integer> _InventaireItem = new HashMap<>();
		Map<Ressource, Integer> _InventaireRessource= new HashMap<>();
		this.setEtat(EtatEntité.Vivant);
		this.setNiveau(_Niveau);
		this.setInventaireItem(_InventaireItem);
		this.setInventaireRessource(_InventaireRessource);
		this.setPositionX(_PositionX);
		this.setPositionY(PositionY);
		this.setVie(_Niveau*100);
	}
	
	public void DegatsReçues(Arme _Arme){
		this.setVie(this.getVie()-_Arme.getDegats());
		if (this.getVie()<=0) {
			this.setEtat(EtatEntité.Mort);
		}
	}
	
	public void Attaque(Entité _Entité, Arme _Arme){
		boolean etourdis = new Random().nextInt(5)==0;
		boolean rater = new Random().nextInt(5)==0;
		if (this.getEtat()==EtatEntité.Vivant) {
			if (etourdis==true && rater==false) {
				if (Math.abs(_Entité.getPositionX()-this.getPositionX())<= _Arme.getPortée() || Math.abs(_Entité.getPositionY()-this.getPositionY())<= _Arme.getPortée() ) {
					_Entité.DegatsReçues(_Arme);
					_Entité.setEtat(EtatEntité.Etourdis);
					_Arme.setDurabilité(_Arme.getDurabilité()-1);
					if (_Arme.getDurabilité()<=0) {
						_Arme.setEtat(false);
					}
				}
			}else {
				if (rater==false) {
					if (Math.abs(_Entité.getPositionX()-this.getPositionX())<= _Arme.getPortée()) {
						_Entité.DegatsReçues(_Arme);
						_Arme.setDurabilité(_Arme.getDurabilité()-1);
						if (_Arme.getDurabilité()<=0) {
							_Arme.setEtat(false);
						}
					}
				}
			}
			if (this instanceof Joueur && _Entité instanceof Ennemi) {
				if (_Entité.getEtat()==EtatEntité.Mort) {
					((Joueur)this).setExperience(((Joueur)this).getExperience()+((Ennemi)_Entité).getExperienceMonstre());
					((Joueur)this).levelup();
				}
			}
		}
	}
	
	public void Utiliser(Entité _Entité) {
		if (this.getEtat()==EtatEntité.Vivant) {
			if (this.enMain instanceof Arme) {
				Attaque(_Entité,(Arme)this.enMain);
			}else {
				if (this.enMain instanceof Potion) {
					switch (((Potion)this.enMain).getEffet()) {
					case Etourdissement:
						if (_Entité.getPositionX() == this.getPositionX()) {
							this.Empoisonner(_Entité);
						}else {
							if (_Entité.getPositionY() == this.getPositionY()) {
								this.Empoisonner(_Entité);
							}
						}
						break;
						
					case Poison:
						if (_Entité.getPositionX() == this.getPositionX()) {
							this.Empoisonner(_Entité);
						}else {
							if (_Entité.getPositionY() == this.getPositionY()) {
								this.Empoisonner(_Entité);
							}
						}
						break;
						
					case GainDeVie :
						if (_Entité instanceof Joueur) {
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
					_Entité.Empoisonnement((Potion)this.enMain);
					((Potion)this.enMain).setEtat(false);
				}
			}
		}
	}
	
	public void Empoisonner(Entité _Entité) {
		_Entité.Empoisonnement((Potion)this.enMain);
		((Potion)this.enMain).setEtat(false);
	}
	
	
	public void Empoisonnement(Potion _Potion){
		switch (_Potion.getEffet()) {
		case Etourdissement:
			this.setEtat(EtatEntité.Etourdis);
			break;
		case Poison:
			this.setVie(this.getVie()-_Potion.getNiveau()*10);
			if (this.getVie()<=0) {
				this.setEtat(EtatEntité.Mort);
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

	public EtatEntité getEtat() {
		return Etat;
	}

	public void setEtat(EtatEntité etat) {
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
