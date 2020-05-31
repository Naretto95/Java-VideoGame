package Jeu;
/**
 * 
 * @author Lilian Naretto
 *
 */
public class Ressource extends Objet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TypeRessource Type;
	
	public Ressource(TypeRessource _Type) {
		this.setRamassé(true);
		this.setType(_Type);
	}
	
	public TypeRessource getType() {
		return Type;
	}

	public void setType(TypeRessource type) {
		Type = type;
	}
	
	public String toString() {
		return ""+this.getType();
	}
}
