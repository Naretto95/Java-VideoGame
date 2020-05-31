package Jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Ennemi extends Entité implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Categorie Categorie;
	private Race Race;
	private int ExperienceMonstre;
	
	public Ennemi( int _Niveau, int _PositionX, int PositionY,Categorie _Categorie, Race _Race) {
		super(_Niveau, _PositionX,PositionY);
		this.setExperienceMonstre(_Niveau*10);
		this.setCategorie(_Categorie);
		this.setRace(_Race);
		if (this.getCategorie()==Jeu.Categorie.Boss) {
			this.setVie(this.getVie()*2);
			this.getInventaireRessource().put(new Ressource(TypeRessource.Cle),1);
		}
		switch (this.getRace()) {
		case Orc:
			this.getInventaireArme().add(new Arme(TypeArme.EpéeLongue,this.getNiveau()));
			this.ChangerItem(new Arme(TypeArme.EpéeLongue,this.getNiveau()));
			this.getInventairePotion().add(new Potion(Effet.Poison,this.getNiveau()));
			this.getInventaireRessource().put(new Ressource(TypeRessource.Fer),1);
			this.getInventaireRessource().put(new Ressource(TypeRessource.Or),1);
			break;
			
		case Humain:
			this.getInventaireArme().add(new Arme(TypeArme.EpéeCourte,this.getNiveau()));
			this.ChangerItem(new Arme(TypeArme.EpéeCourte,this.getNiveau()));
			this.getInventaireRessource().put(new Ressource(TypeRessource.Bois),2);
			this.getInventairePotion().add(new Potion(Effet.GainDeVie,this.getNiveau()));
			break;
			
		case Dragon:
			this.getInventaireArme().add(new Arme(TypeArme.EpéeLongue,this.getNiveau()));
			this.ChangerItem(new Arme(TypeArme.EpéeLongue,this.getNiveau()));
			this.getInventairePotion().add(new Potion(Effet.GainDegats,this.getNiveau()));
			this.getInventaireRessource().put(new Ressource(TypeRessource.Or),2);
			
			break;
			
		case Nain:
			this.getInventaireArme().add(new Arme(TypeArme.Arc,this.getNiveau()));
			this.ChangerItem(new Arme(TypeArme.Arc,this.getNiveau()));
			this.getInventairePotion().add(new Potion(Effet.Etourdissement,this.getNiveau()));
			this.getInventaireRessource().put(new Ressource(TypeRessource.Fer),2);
			
			break;

		default:
			break;
		}
	}
	
	public List<Objet> Jeter() {
		List<Objet> Liste = new ArrayList<>();
		for (int i = 0; i < this.getInventaireArme().size(); i++) {
			if (this.getInventaireArme().get(i).getType()!=TypeArme.Main) {
				this.getInventaireArme().get(i).setRamassé(false);
				this.getInventaireArme().get(i).Reparer(this);
				Liste.add(this.getInventaireArme().get(i));
				this.getInventaireArme().remove(i);
			}
		}
		for (int i = 0; i < this.getInventairePotion().size(); i++) {
			this.getInventairePotion().get(i).setRamassé(false);
			Liste.add(this.getInventairePotion().get(i));
			this.getInventairePotion().remove(i);
		}
		for(Entry<Ressource, Integer> entry : this.getInventaireRessource().entrySet()) {
			Ressource cle = entry.getKey();
			Integer valeur = entry.getValue();
			if (valeur >0) {
				cle.setRamassé(false);
				while (valeur>0) {
					Liste.add(cle);
					this.getInventaireRessource().put(cle,this.getInventaireRessource().get(cle)-1);
					valeur=valeur-1;
				}
			}
			
		}
		return Liste;
	}
	
	public Categorie getCategorie() {
		return Categorie;
	}
	
	public void setCategorie(Categorie categorie) {
		Categorie = categorie;
	}
	
	public Race getRace() {
		return Race;
	}
	
	public void setRace(Race race) {
		Race = race;
	}

	public int getExperienceMonstre() {
		return ExperienceMonstre;
	}

	public void setExperienceMonstre(int experienceMonstre) {
		ExperienceMonstre = experienceMonstre;
	}
	
}
