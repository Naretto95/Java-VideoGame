package Jeu;

import java.util.Map.Entry;

public class Joueur extends Entit� {
	
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
			this.setNiveau(this.getNiveau()+1);
			this.setExperience(this.getExperience()-this.getNiveau()*100);
			this.getInventaireArme().get(0).setDegats(this.getNiveau());
			this.getInventaireArme().get(0).setNiveau(this.getNiveau());
		}
	}
	
	public void Ameliorer() {
		if (this.getEnMain() instanceof Arme) {
			AmeliorerArme((Arme)this.getEnMain());
		}
	}
	
	public void AmeliorerArme(Arme _Arme) {
		for(Entry<Ressource, Integer> entry : this.getInventaireRessource().entrySet()) {
		    Ressource cle = entry.getKey();
		    Integer valeur = entry.getValue();
		    switch (_Arme.getType()) {
			case Ep�eLongue:
				if (cle.getType()==TypeRessource.Fer) {
					if (valeur>=5) {
						_Arme.Ameliorer(this);
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-5);
					}			
				}
				break;
				
			case Ep�eCourte:
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
	}
	
	public void Jeter() {
		if (this.getEnMain() instanceof Arme) {
			for (int i = 0; i < this.getInventaireArme().size(); i++) {
				if ((Arme)this.getEnMain()==this.getInventaireArme().get(i)) {
					for (int j = 0; j < this.getInventaireArme().size(); j++) {
						if (this.getInventaireArme().get(i).getType()==this.getInventaireArme().get(j).getType()) {
							this.setEnMain(this.getInventaireArme().get(j));
							break;
						}
						this.setEnMain(this.getInventaireArme().get(0));
					}
					this.getInventaireArme().remove(i);
				}
			}
		}
		if (this.getEnMain() instanceof Potion) {
			for (int i = 0; i < this.getInventairePotion().size(); i++) {
				if ((Potion)this.getEnMain()==this.getInventairePotion().get(i)) {
					for (int j = 0; j < this.getInventairePotion().size(); j++) {
						if (this.getInventairePotion().get(i).getEffet()==this.getInventairePotion().get(j).getEffet()) {
							this.setEnMain(this.getInventairePotion().get(j));
							break;
						}
						this.setEnMain(this.getInventaireArme().get(0));
					}
					this.getInventairePotion().remove(i);
				}
			}
		}
	}
	
	public void Ramasser(Item _Item) {
		int accum=0;
		if (_Item.getNiveau()<=this.getNiveau()) {
			if (_Item instanceof Arme) {
				for (int i = 0; i < this.getInventaireArme().size(); i++) {
					if (this.getInventaireArme().get(i).getType()==((Arme)_Item).getType()) {
						accum++;
					}
				}
				if (accum<3) {
					this.getInventaireArme().add((Arme)_Item);
				}
			}
			if (_Item instanceof Potion) {
				for (int i = 0; i < this.getInventairePotion().size(); i++) {
					if (this.getInventairePotion().get(i).getEffet()==((Potion)_Item).getEffet()) {
						accum++;
					}
				}
				if (accum<3) {
					this.getInventairePotion().add((Potion)_Item);
				}
			}
		}
	}
	
	public void Bouger(int guauche, int droite, int bas, int haut){
		if (this.getEtat()==EtatEntit�.Vivant) {
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
