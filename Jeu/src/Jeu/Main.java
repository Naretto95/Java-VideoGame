package Jeu;

public class Main {
	
	
	
	
public static void main(String[] args) {
	Joueur heros = new Joueur("Lilian",1,0,0);
	Ennemi monstre = new Ennemi(1,1,0,Categorie.Normal,Race.Humain);
	monstre.setExperienceMonstre(110);
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	heros.Utiliser(monstre);
	System.out.println(monstre.getVie());
	System.out.println(heros.getNiveau());
	System.out.println(heros.getExperience());
}
}
