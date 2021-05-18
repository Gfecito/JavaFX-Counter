package mvc;

/*
 *	Notez que cette classe est completement indépendante de la representation.
 *	On pourraît facilement definir un interface complètement different.
 */
public class Modele {

	private int valeur;

	public Modele() {
		this.valeur = 0;
	}

	public Modele(int v) {
		this.valeur = v;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public void ajouter(int montant) {
		this.valeur = this.valeur + montant;
	}

	public void supprimer(int montant) {
		this.valeur = this.valeur - montant;
	}

	public void multiplier(int fois) {
		this.valeur = this.valeur * fois;
	}

	public void diviser(int fois) {
		this.valeur = this.valeur / fois;
	}

}
