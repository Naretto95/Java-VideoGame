package Jeu;

public class Main {
	
	
	
	
public static void main(String[] args) {
	Joueur heros = new Joueur("Lilian",1,0,0);
	Ennemi monstre = new Ennemi(1,1,0,Categorie.Normal,Race.Humain);
	monstre.getInventaireArme().add(new Arme(TypeArme.Arc,1));
	monstre.getInventaireArme().add(new Arme(TypeArme.Arc,1));
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
	monstre.Utiliser(heros);
	System.out.println(monstre.getEnMain());
	System.out.println(monstre.getInventaireArme());
}
}
