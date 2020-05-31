package Jeu;

/**
 * 
 * @author Lilian Naretto
 *
 */

public class Enclume{
	public static Integer UTILISATION_ENCLUME = new Integer(800);
	private TypeEnclume Type;
	
	public Enclume(TypeEnclume _Type) {
		this.setType(_Type);
	}
	
	public void Utiliser(Joueur joueur) {
		switch (this.getType()) {
		case EnclumeReparer:
			joueur.Reparer();
			break;
			
		case EnclumeAmeliorer:
			joueur.Ameliorer();
			break;			
			
		default:
			break;
		}
	}
	
	public TypeEnclume getType() {
		return Type;
	}

	public void setType(TypeEnclume type) {
		Type = type;
	}	
}
