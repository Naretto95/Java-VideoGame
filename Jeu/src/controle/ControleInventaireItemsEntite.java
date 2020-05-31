package controle;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Jeu.Arme;
import Jeu.Effet;
import Jeu.Entité;
import Jeu.Item;
import Jeu.Potion;
import Jeu.TypeArme;
import affichage.AffichageBarreInventaire;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
/**
 * @date 23/05/2020
 * @author Corentin BRILLANT
 */


public class ControleInventaireItemsEntite extends FlowPane implements Observer{

	
	AffichageBarreInventaire barre;
	Entité entite;
	
	public ControleInventaireItemsEntite(Entité entite) {
		this.entite = entite;
		this.entite.addObserver(this);
		this.barre = new AffichageBarreInventaire();
		this.majBarre();
		this.barre.getCaseArc().setOnMouseClicked((e)->{this.entite.ChangerItem(new Arme(TypeArme.Arc,1));});
		this.barre.getCaseEpeeCourte().setOnMouseClicked((e)->{this.entite.ChangerItem(new Arme(TypeArme.EpéeCourte,1));});
		this.barre.getCaseEpeeLongue().setOnMouseClicked((e)->{this.entite.ChangerItem(new Arme(TypeArme.EpéeLongue,1));});
		this.barre.getCasePotionArme().setOnMouseClicked((e)->{this.entite.ChangerItem(new Potion(Effet.GainDegats,1));});
		this.barre.getCasePotionEtourdi().setOnMouseClicked((e)->{this.entite.ChangerItem(new Potion(Effet.Etourdissement,1));});
		this.barre.getCasePotionPoison().setOnMouseClicked((e)->{this.entite.ChangerItem(new Potion(Effet.Poison,1));});
		this.barre.getCasePotionVie().setOnMouseClicked((e)->{this.entite.ChangerItem(new Potion(Effet.GainDeVie,1));});
		this.barre.getCaseArmeEnMain().setOnMouseClicked((e)->{this.entite.ChangerItem(new Arme(TypeArme.Main,1));});
		this.getChildren().add(barre);
		this.setAlignment(Pos.BOTTOM_RIGHT);
		FlowPane.setMargin(barre, new Insets(0, 10, 10, 0));
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Integer && ((Integer)arg1).intValue()==(Entité.CHANGEMENT_ITEM).intValue()) {
			majBarre();
		}
	}
	
	/**{@literal met à jour l'affichage de l'inventaire en fonction de l'inventaire du joueur}*/
	public void majBarre() {
		this.barre.videCases();
		Item enMain = this.entite.getEnMain();
		if (enMain instanceof Arme) {
			switch(((Arme) enMain).getType()) {
			case EpéeLongue:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.epeeLongue);
				break;
			case EpéeCourte:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.epeeCourte);
				break;
			case Arc:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.arc);
				break;
			default:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.main);
					break;
			}
		}
		else if (enMain instanceof Potion) {
			switch(((Potion)enMain).getEffet()) {
			case Etourdissement:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.potionEtourdi);
				break;
			case Poison:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.potionPoison);
				break;
			case GainDeVie:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.potionVie);
				break;
			case GainDegats:
				this.barre.setCaseArmeEnMain(AffichageBarreInventaire.potionArme);
				break;
				default:
					this.barre.setCaseArmeEnMain(AffichageBarreInventaire.main);
					break;
			}
		}
		else {
			this.barre.setCaseArmeEnMain(AffichageBarreInventaire.main);
		}
		List<Arme> armes = this.entite.getInventaireArme();
		List<Potion> potions = this.entite.getInventairePotion();
		for (int i = 0;i<armes.size();i++) {
			switch(armes.get(i).getType()) {
			case EpéeLongue:
				this.barre.setCaseEpeeLongue();
				break;
			case EpéeCourte:
				this.barre.setCaseEpeeCourte();
				break;
			case Arc:
				this.barre.setCaseArc();
				break;
			default:
					break;
			}
		}
		for (int j = 0;j<potions.size();j++) {
			switch(potions.get(j).getEffet()) {
			case Etourdissement:
				this.barre.setCasePotionEtourdi();
				break;
			case Poison:
				this.barre.setCasePotionPoison();
				break;
			case GainDeVie:
				this.barre.setCasePotionVie();
				break;
			case GainDegats:
				this.barre.setCasePotionArme();
				break;
				default:
					break;
			}
		}
		
	}
}
