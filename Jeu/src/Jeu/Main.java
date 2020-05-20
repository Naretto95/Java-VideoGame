package Jeu;

public class Main {
	
	
	
	
public static void main(String[] args) {
	Joueur heros = new Joueur("Lilian",1,0,0);
	Ennemi monstre = new Ennemi(1,1,0,Categorie.Normal,Race.Humain);
	System.out.println(heros.getInventaireArme());
	heros.Ramasser(new Arme(TypeArme.Arc,1));
	heros.getInventairePotion().add(new Potion(Effet.Poison,1));
	heros.getInventairePotion().add(new Potion(Effet.Poison,2));
	heros.getInventairePotion().add(new Potion(Effet.Poison,3));
	System.out.println(heros.getInventairePotion());
	heros.ChangerItem(new Potion(Effet.Poison,3));
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	System.out.println(heros.getInventairePotion());
	heros.ChangerItem(new Potion(Effet.Poison,3));
	System.out.println(heros.getInventairePotion());
	heros.ChangerItem(new Arme(TypeArme.Main,1));
	System.out.println(heros.getEnMain());
}
}
