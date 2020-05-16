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
						_Arme.Ameliorer();
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-5);
					}			
				}
				break;
				
			case Ep�eCourte:
				if (cle.getType()==TypeRessource.Fer) {
					if (valeur>=3) {
						_Arme.Ameliorer();
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-3);
					}			
				}
				break;
				
			case Arc:
				if (cle.getType()==TypeRessource.Bois) {
					if (valeur>=3) {
						_Arme.Ameliorer();
						this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-3);
					}			
				}
				break;

			default:
				break;
			}
		}
	}
	
	public void Ramasser(Item _Item) {
		for(Entry<Item, Integer> entry : this.getInventaireItem().entrySet()) {
			Item cle = entry.getKey();
		    Integer valeur = entry.getValue();
		    if (cle==_Item){
		    	if (valeur<3) {
					this.getInventaireItem().put(cle,this.getInventaireItem().get(cle)+1);
					cle.setRamass�(true);
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
