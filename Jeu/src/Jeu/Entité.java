package Jeu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

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
		this.getInventaireItem().put(new Arme(TypeArme.Ep�eLongue,this.getNiveau()),0);
		this.getInventaireItem().put(new Arme(TypeArme.Ep�eCourte,this.getNiveau()),0);
		this.getInventaireItem().put(new Arme(TypeArme.Arc,this.getNiveau()),0);
		this.getInventaireItem().put(new Arme(TypeArme.Main,this.getNiveau()),1);
		this.getInventaireItem().put(new Potion(Effet.Etourdissement,this.getNiveau()),0);
		this.getInventaireItem().put(new Potion(Effet.Poison,this.getNiveau()),0);
		this.getInventaireItem().put(new Potion(Effet.GainDeVie,this.getNiveau()),0);
		this.getInventaireItem().put(new Potion(Effet.GainDegats,this.getNiveau()),0);
		this.getInventaireRessource().put(new Ressource(TypeRessource.Cle),0);
		this.getInventaireRessource().put(new Ressource(TypeRessource.Bois),0);
		this.getInventaireRessource().put(new Ressource(TypeRessource.Fer),0);
		this.getInventaireRessource().put(new Ressource(TypeRessource.Or),0);
	}
	
	public void DegatsRe�ues(Arme _Arme){
		this.setVie(this.getVie()-_Arme.getDegats());
		if (this.getVie()<=0) {
			this.setEtat(EtatEntit�.Mort);
		}
	}
	
	public void Attaque(Entit� _Entit�, Arme _Arme){
		if (_Arme.getDurabilit�()>0) {
			boolean etourdis = new Random().nextInt(5)==0;
			boolean rater = new Random().nextInt(5)==0;
				if (etourdis==true && rater==false) {
					if (Math.abs(_Entit�.getPositionX()-this.getPositionX())<= _Arme.getPort�e() && _Entit�.getPositionY()==this.getPositionY() || Math.abs(_Entit�.getPositionY()-this.getPositionY())<= _Arme.getPort�e() && _Entit�.getPositionX()==this.getPositionX() ) {
						_Entit�.DegatsRe�ues(_Arme);
						_Entit�.setEtat(EtatEntit�.Etourdis);
						_Arme.setDurabilit�(_Arme.getDurabilit�()-1);
						if (_Arme.getDurabilit�()<=0) {
							_Arme.setEtat(false);
							this.ActualiserInventaire();
						}
					}
				}else {
					if (rater==false) {
						if (Math.abs(_Entit�.getPositionX()-this.getPositionX())<= _Arme.getPort�e() && _Entit�.getPositionY()==this.getPositionY() || Math.abs(_Entit�.getPositionY()-this.getPositionY())<= _Arme.getPort�e() && _Entit�.getPositionX()==this.getPositionX() ) {
							_Entit�.DegatsRe�ues(_Arme);
							_Arme.setDurabilit�(_Arme.getDurabilit�()-1);
							if (_Arme.getDurabilit�()<=0) {
								_Arme.setEtat(false);
								this.ActualiserInventaire();
							}
						}
					}
				}
				if (this instanceof Joueur && _Entit� instanceof Ennemi) {
					if (_Entit�.getEtat()==EtatEntit�.Mort) {
						((Joueur)this).setExperience(((Joueur)this).getExperience()+((Ennemi)_Entit�).getExperienceMonstre());
						((Joueur)this).levelup();
						//IL FAUT DELETE LENNEMI
					}
				}
		}
	}
	
	public void ChangerItem(Item _Item) {
		if (this.getEtat()==EtatEntit�.Vivant) {
			for(Entry<Item, Integer> entry : this.getInventaireItem().entrySet()) {
				Item cle = entry.getKey();
			    Integer valeur = entry.getValue();
			    if (cle instanceof Arme && _Item instanceof Arme && valeur>0) {
					if (((Arme) cle).getType()==((Arme)_Item).getType()) {
						this.getInventaireItem().put(this.enMain,this.getInventaireItem().get(new Arme(((Arme)this.enMain).getType(),((Arme)this.enMain).getNiveau())));
						this.getInventaireItem().remove(new Arme(((Arme)this.enMain).getType(),((Arme)this.enMain).getNiveau()));
						this.setEnMain(cle);
					}
				}
			    if (cle instanceof Potion && _Item instanceof Potion && valeur>0) {
					if (((Potion) cle).getEffet()==((Potion)_Item).getEffet()) {
						this.getInventaireItem().put(this.enMain,this.getInventaireItem().get(new Potion(((Potion)this.enMain).getEffet(),((Potion)this.enMain).getNiveau())));
						this.getInventaireItem().remove(new Potion(((Potion)this.enMain).getEffet(),((Potion)this.enMain).getNiveau()));
						this.setEnMain(cle);
					}
				}
			}
		}
	}
	
	public void ActualiserInventaire(){
		if ((this.enMain).isEtat()==false) {
			for(Entry<Item, Integer> entry : this.getInventaireItem().entrySet()) {
				Item cle = entry.getKey();
			    Integer valeur = entry.getValue();
			    if (cle instanceof Arme && this.enMain instanceof Arme && valeur>0) {
			    	if (((Arme) cle).getType()==((Arme)this.enMain).getType()) {
				    	if (valeur>1) {
				    		this.getInventaireItem().put(cle,this.getInventaireItem().get(cle)-1);
				    		this.setEnMain(new Arme(((Arme)this.enMain).getType(),((Arme)this.enMain).getNiveau()));
						}else {
							this.getInventaireItem().put(cle,this.getInventaireItem().get(cle)-1);
							this.setEnMain(new Arme(TypeArme.Main, this.getNiveau()));
						}
				}
			}
			    if (cle instanceof Potion && this.enMain instanceof Potion && valeur>0) {
			    	if (((Potion) cle).getEffet()==((Potion)this.enMain).getEffet()) {
				    	if (valeur>1) {
				    		this.getInventaireItem().put(cle,this.getInventaireItem().get(cle)-1);
				    		this.setEnMain(new Potion(((Potion)this.enMain).getEffet(),((Potion)this.enMain).getNiveau()));
						}else {
							this.getInventaireItem().put(cle,this.getInventaireItem().get(cle)-1);
							this.setEnMain(new Arme(TypeArme.Main, this.getNiveau()));
						}
				}
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
							if (this.getVie()<this.getNiveau()*100) {
								if (((Potion)this.enMain).getNiveau()+this.getVie() <= this.getNiveau()*100) {
									this.setVie(((Potion)this.enMain).getNiveau()+this.getVie());
									((Potion)this.enMain).setEtat(false);
									this.ActualiserInventaire();								
								}else {
									this.setVie(this.getNiveau()*100);
									((Potion)this.enMain).setEtat(false);
									this.ActualiserInventaire();
								}
						}
						break;
						
					case GainDegats :
						for(Entry<Item, Integer> entry : this.getInventaireItem().entrySet()) {
							Item cle = entry.getKey();
						    Integer valeur = entry.getValue();
						    if (cle instanceof Arme) {
								if (((Arme) cle).getType() != TypeArme.Main) {
									if (valeur>0) {
										this.setEnMain(new Arme(((Arme) cle).getType(),((Arme) cle).getNiveau()));
										((Arme)this.getEnMain()).setDegats(((Arme)this.getEnMain()).getDegats()+((Potion)this.enMain).getNiveau()*2);
										((Potion)this.enMain).setEtat(false);
										this.ActualiserInventaire();
									}
								}
							}
						    
						}
						break;
						
					default:
						break;
					}
				}
			}
		}
	}
	
	public void Empoisonner(Entit� _Entit�) {
		_Entit�.Empoisonnement((Potion)this.enMain);
		((Potion)this.enMain).setEtat(false);
		this.ActualiserInventaire();
	}
	
	
	public void Empoisonnement(Potion _Potion){
		switch (_Potion.getEffet()) {
		case Etourdissement:
			this.setEtat(EtatEntit�.Etourdis);
			break;
		case Poison:
			this.setVie(this.getVie()-_Potion.getNiveau()*10);//iciiiii
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
