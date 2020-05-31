
package Jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
/**
 * 
 * @author Corentin BRILLANT
 *
 */
public class Case implements Observer,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Integer VIDE = new Integer(0);
	public static Integer OBSTACLE = new Integer(1);
	public static Integer PORTE = new Integer(2);
	public static Integer OUVERTURE_PORTE= new Integer(30);
	
	private Object contenu;
	private Stack<Item> items;
	private Stack<Ressource> ressources;


	public Case(Stack<Item> items,Stack<Ressource> ressources) {
		this.contenu=VIDE;
		this.items = items;
		this.ressources = ressources;
	}
	
	
	public Case(Object contenu,Stack<Item> items,Stack<Ressource> ressources) {
		this.contenu=contenu;
		this.items = items;
		this.ressources = ressources;
	}
	
	
	public Case(Object contenu) {
		this.contenu=contenu;
		this.items = new Stack<Item>();
		this.ressources = new Stack<Ressource>();
	}
	
	public Case() {
		this.contenu = VIDE;
		this.items = new Stack<Item>();
		this.ressources = new Stack<Ressource>();
	}
	
	public void addItem(Item item) {
		this.items.push(item);
	}
	public void addRessource(Ressource ressource) {
		this.ressources.push(ressource);
	}
	public Item getItem() {
		if (!this.items.isEmpty()) {
			return this.items.pop();
		}
		return null;
	}
	public Ressource getRessource() {
		if(!this.ressources.isEmpty()) {
			return this.ressources.pop();
		}
		return null;
	}
	public ArrayList<Ressource> getAllRessources() {
		ArrayList<Ressource> allRessources = new ArrayList<Ressource>();
		while (!this.ressources.isEmpty()) {
			allRessources.add(this.getRessource());
		}
		return allRessources;
	}
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> allItems = new ArrayList<Item>();
		while (!this.items.isEmpty()) {
			allItems.add(this.getItem());
		}
		return allItems;
	}
	public void addSomeRessources(Stack<Ressource> ressources) {
		while(!ressources.isEmpty()) {
			this.addRessource(ressources.pop());
		}
	}
	public void addSomeItems(Stack<Item> items) {
		while(!items.isEmpty()) {
			this.addItem(items.pop());
		}
	}

	public Object getContenu() {
		return contenu;
	}
	
	public boolean hasStaff() {
		return (!this.items.isEmpty())||(!this.ressources.isEmpty());
	}

	public void setContenu(Object contenu) {
		if (this.getContenu() instanceof Entité) {
			Entité entiteAncienne = (Entité) this.getContenu();
			entiteAncienne.deleteObserver(this);
		}
		if (contenu instanceof Entité) {
			Entité entiteNouvelle = (Entité) contenu;
			entiteNouvelle.addObserver(this);
		}
		this.contenu = contenu;
	}
	
	public void addSomeObjets(List<Objet> liste) {
		for (int i =0;i<liste.size();i++) {
			if (liste.get(i) instanceof Ressource) {
				this.addRessource((Ressource) liste.get(i));
			}
			else if(liste.get(i) instanceof Item) {
				this.addItem((Item) liste.get(i));
			}
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg.equals(Entité.EST_MORT)) {
			if (o instanceof Entité && this.getContenu() instanceof Entité && ((Entité) this.getContenu()).equals((Entité) o)) {
				Entité entiteMorte = (Entité) o;
				List<Objet> liste = ((Ennemi)entiteMorte).Jeter();
				this.addSomeObjets(liste);
				this.setContenu(Case.VIDE);
				o.deleteObserver(this);
			}else {
				System.out.println("Il y a un problème avec le dépot de l'inventaire de ce défunt");
			}
		}
		if (arg.equals(OUVERTURE_PORTE)) {
			this.setContenu(VIDE);
		}
	}
}
