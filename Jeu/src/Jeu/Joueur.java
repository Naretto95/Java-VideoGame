package Jeu;

import java.util.Map.Entry;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Joueur extends Entité {
	
	/**
	 * 
	 */
	public static Integer GAIN_EXP = new Integer(737);
	private static final long serialVersionUID = 1L;
	private int Experience;
	private String Nom;
	
	public Joueur(String _Nom, int _Niveau, int _PositionX, int PositionY) {
		super(_Niveau, _PositionX,PositionY);
		this.setEnMain(new Arme(TypeArme.Main,_Niveau));
		this.setNom(_Nom);
		this.setExperience(0);
	}
	
	public void levelup() {
		if (this.getExperience()>this.getNiveau()*100) {
			this.setExperience(this.getExperience()-this.getNiveau()*100);
			this.setNiveau(this.getNiveau()+1);
			this.getInventaireArme().get(0).setDegats(this.getNiveau());
			this.getInventaireArme().get(0).setNiveau(this.getNiveau());
		}
		this.setChanged();
		this.notifyObservers(GAIN_EXP);
	}
	
	public void Ameliorer() {
		if (this.getEnMain() instanceof Arme) {
			AmeliorerArme((Arme)this.getEnMain());
		}
		this.ActualiserInventaire();
	}
	
	public void AmeliorerArme(Arme _Arme) {
		for(Entry<Ressource, Integer> entry : this.getInventaireRessource().entrySet()) {
		    Ressource cle = entry.getKey();
		    Integer valeur = entry.getValue();
		    switch (_Arme.getType()) {
			case EpéeLongue:
				if (cle.getType()==TypeRessource.Fer) {
					if (valeur>=5) {
						_Arme.Ameliorer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-5);
					}			
				}
				break;
				
			case EpéeCourte:
				if (cle.getType()==TypeRessource.Fer) {
					if (valeur>=3) {
						_Arme.Ameliorer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-3);
					}			
				}
				break;
				
			case Arc:
				if (cle.getType()==TypeRessource.Bois) {
					if (valeur>=3) {
						_Arme.Ameliorer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-3);
					}			
				}
				break;

			default:
				break;
			}
		}
		this.setChanged();
		this.notifyObservers(Entité.RESSOURCES_MODIFIEES);
	}
	
	public void Reparer() {
		if (this.getEnMain() instanceof Arme) {
			ReparerArme((Arme)this.getEnMain());
		}
		this.ActualiserInventaire();
	}
	
	public void ReparerArme(Arme _Arme) {
		for(Entry<Ressource, Integer> entry : this.getInventaireRessource().entrySet()) {
		    Ressource cle = entry.getKey();
		    Integer valeur = entry.getValue();
		    switch (_Arme.getType()) {
			case EpéeLongue:
				if (cle.getType()==TypeRessource.Fer) {
					if (valeur>=2) {
						_Arme.Reparer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-2);
					}			
				}
				break;
				
			case EpéeCourte:
				if (cle.getType()==TypeRessource.Fer) {
					if (valeur>=1) {
						_Arme.Reparer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-1);
					}			
				}
				break;
				
			case Arc:
				if (cle.getType()==TypeRessource.Bois) {
					if (valeur>=1) {
						_Arme.Reparer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-1);
					}			
				}
				break;

			default:
				break;
			}
		}
	}
	
	public Item Jeter() {
		if (this.getEnMain() instanceof Arme) {
			if (((Arme)this.getEnMain()).getType()!=TypeArme.Main) {
				for (int i = 0; i < this.getInventaireArme().size(); i++) {
					if (((Arme)this.getEnMain()).getType()==this.getInventaireArme().get(i).getType()) {
							for (int j = i+1; j < this.getInventaireArme().size(); j++) {
								if (this.getInventaireArme().get(j).getType()==this.getInventaireArme().get(i).getType()) {
										this.setEnMain(this.getInventaireArme().get(j));
										break;
								}
							}
							if ((Arme)this.getEnMain()==this.getInventaireArme().get(i)) {
								this.setEnMain(this.getInventaireArme().get(0));
							}
							this.getInventaireArme().get(i).setRamassé(false);
							return this.getInventaireArme().remove(i);
				}
			}
			}
		}
		if (this.getEnMain() instanceof Potion) {
			for (int i = 0; i < this.getInventairePotion().size(); i++) {
					if (((Potion)this.getEnMain()).getEffet()==this.getInventairePotion().get(i).getEffet()) {
							for (int j = i+1; j < this.getInventairePotion().size(); j++) {
								if (this.getInventairePotion().get(j).getEffet()==this.getInventairePotion().get(i).getEffet()) {
										this.setEnMain(this.getInventairePotion().get(j));
										break;
								}
							}
							if ((Potion)this.getEnMain()==this.getInventairePotion().get(i)) {
								this.setEnMain(this.getInventaireArme().get(0));
							}
							this.getInventairePotion().get(i).setRamassé(false);
							return this.getInventairePotion().remove(i);
				}
			}
		}
		return null;
	}
	
	public boolean Ramasser(Objet _Objet) {
		if (_Objet instanceof Item) {
			int accum=0;
			if (((Item)_Objet).getNiveau()<=this.getNiveau()) {
				if (((Item)_Objet) instanceof Arme) {
					for (int i = 0; i < this.getInventaireArme().size(); i++) {
						if (this.getInventaireArme().get(i).getType()==((Arme)((Item)_Objet)).getType()) {
							accum++;
						}
					}
					if (accum<3) {
						((Arme)((Item)_Objet)).setRamassé(true);
						this.getInventaireArme().add((Arme)((Item)_Objet));
						this.setChanged();
						this.notifyObservers(Entité.CHANGEMENT_ITEM);
						return true;
					}
				}
				if (((Item)_Objet) instanceof Potion) {
					for (int i = 0; i < this.getInventairePotion().size(); i++) {
						if (this.getInventairePotion().get(i).getEffet()==((Potion)((Item)_Objet)).getEffet()) {
							accum++;
						}
					}
					if (accum<3) {
						((Potion)((Item)_Objet)).setRamassé(true);
						this.getInventairePotion().add((Potion)((Item)_Objet));
						this.setChanged();
						this.notifyObservers(Entité.CHANGEMENT_ITEM);
						return true;
					}
				}
			}
		}
		if (_Objet instanceof Ressource) {
			for(Entry<Ressource, Integer> entry : this.getInventaireRessource().entrySet()) {
				Ressource cle = entry.getKey();
			    if (((Ressource)_Objet).getType()==cle.getType()) {
			    	cle.setRamassé(true);
			    	this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)+1);
			    	this.setChanged();
			    	this.notifyObservers(Entité.RESSOURCES_MODIFIEES);
			    	return true;
				}
			}
		}
		return false;
	}
	
	public void Bouger(int guauche, int droite, int bas, int haut){
		if (this.getEtat()==EtatEntité.Vivant) {
			this.setPositionX(this.getPositionX()+droite);
			this.setPositionX(this.getPositionX()-guauche);
			this.setPositionY(this.getPositionY()+haut);
			this.setPositionY(this.getPositionY()-bas);
		}	
	}
	
	public int getExperience() {
		return Experience;
	}
	
	public void setExperience(int experience) {
		Experience = experience;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}
	
	
	
}
