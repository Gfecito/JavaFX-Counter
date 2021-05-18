package mvc;

import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Controleur {

	private Modele modele1;
	private Modele modele2;
	private Text vue1;
	private Text vue2;
	private List<String> historique= new ArrayList<>();
	private int index;



	// Constructeur
	public Controleur(Modele m1,Modele m2, Text v1,Text v2) {
		this.modele1 = m1;
		this.modele2 = m2;
		this.vue1 = v1;
		this.vue2 = v2;
		index=0;
		historique.add("DEBUT, ,0,0");

	}

	// On attribue la valeur precedent aux compteurs
	public void undo(){
		// Est qu'il y a une precedent?
		if(index>0) {
			index+=-1;
			// On separe les donnes
			String[] parts = historique.get(index).split(",");

			// On modifie le compteur gauche
			int valeur1 = Integer.parseInt(parts[2]);
			modele1.setValeur(valeur1);
			updateText(modele1,vue1);

			// On modifie le compteur droite
			int valeur2 = Integer.parseInt(parts[3]);
			modele2.setValeur(valeur2);
			updateText(modele2,vue2);
		}
	}

	// On atribue la valeur suivante aux compteurs
	public void redo(){
		// Est qu'il y a un element suivant?
		if(index<historique.size()-1){
			index+=1;
			// On separe les donnes
			String[] parts = historique.get(index).split(",");

			// On modifie le compteur gauche
			int valeur1 = Integer.parseInt(parts[2]);
			modele1.setValeur(valeur1);
			updateText(modele1,vue1);

			// On modifie le compteur droite
			int valeur2 = Integer.parseInt(parts[3]);
			modele2.setValeur(valeur2);
			updateText(modele2,vue2);
		}
	}

	// On ecrit un fichier contenant l'historique
	public void historique() throws IOException {
		FileWriter file= new FileWriter("historique.txt");
		// On cree le texte
		String texte=historiqueTxt();
		file.write(texte);
		file.close();

	}


	public void inc(Boolean isSelected) {
		String compteur="";
		// Est ce que le bouton gauche est selectionné?
		if(isSelected) {
			this.modele1.ajouter(1);
			this.updateText(modele1,vue1);
			compteur="GAUCHE";

		}
		else{
			this.modele2.ajouter(1);
			this.updateText(modele2,vue2);
			compteur="DROITE";
		}
		// On met a jour la liste
		mostRecent();
		// Est ce que le bouton gauche est selectionné?
		historique.add("ADDITION,"+compteur+","+modele1.getValeur()+","+modele2.getValeur());
		index++;
	}

	public void dec(Boolean isSelected) {
		String compteur="";
		// Est ce que le bouton gauche est selectionné?
		if(isSelected) {
			this.modele1.supprimer(1);
			this.updateText(modele1,vue1);
			compteur="GAUCHE";
		}
		else{
			this.modele2.supprimer(1);
			this.updateText(modele2,vue2);
			compteur="DROITE";
		}
		// On met a jour la liste
		mostRecent();
		// Est ce que le bouton gauche est selectionné?
		historique.add("SOUSTRACTION,"+compteur+","+modele1.getValeur()+","+modele2.getValeur());
		index++;
	}

	public void dub(Boolean isSelected) {
		String compteur="";
		// Est ce que le bouton gauche est selectionné?
		if(isSelected) {
			this.modele1.multiplier(2);
			this.updateText(modele1,vue1);
			compteur="GAUCHE";
		}
		else{
			this.modele2.multiplier(2);
			this.updateText(modele2,vue2);
			compteur="DROITE";
		}
		// On met a jour la liste
		mostRecent();
		// On ajoute l'operation, le compteur et les valeur a ce moment
		historique.add("MULTIPLICATION,"+compteur+","+modele1.getValeur()+","+modele2.getValeur());
		index++;
	}

	public void div(Boolean isSelected) {
		String compteur="";
		// Est ce que le bouton gauche est selectionné?
		if(isSelected) {
			this.modele1.diviser(2);
			this.updateText(modele1,vue1);
			compteur="GAUCHE";
		}
		else{
			this.modele2.diviser(2);
			this.updateText(modele2,vue2);
			compteur="DROITE";
		}
		// On met a jour la liste
		mostRecent();
		// On ajoute l'operation, le compteur et les valeur a ce moment
		historique.add("DIVISION,"+compteur+","+modele1.getValeur()+","+modele2.getValeur());
		index++;
	}

	private void updateText(Modele modele,Text vue) {
		vue.setText(String.valueOf(modele.getValeur()));
	}

	// On coupe la liste si les elements suivants sont ecrases par une operation
	public void mostRecent(){
		// Est ce l'index se trouve a la fin
		if(historique.size()-index>1){
			historique=historique.subList(0,index+1);
		}
	}

	// On cree le string qu'on va mettre dans le fichier
	public String historiqueTxt(){
		String[] parts ;
		String separateur="----------\n";
		String texte=separateur+separateur;
		// Debut
		texte+="DEBUT\n"+"RESULTAT COMPTEUR GAUCHE 0\n"+"RESULTAT COMPTEUR DROITE 0\n"+separateur;
		// Le reste des instructions
		for(int i=1;i<index+1;i++){
			parts = historique.get(i).split(",");
			texte+=parts[0]+" COMPTEUR "+parts[1]+"\n";
			texte+="RESULTAT COMPTEUR GAUCHE "+parts[2]+"\n";
			texte+="RESULTAT COMPTEUR DROITE "+parts[3]+"\n";
			texte+=separateur;
		}
		// Fin
		parts=historique.get(index).split(",");
		texte+="FIN\n"+"RESULTAT COMPTEUR GAUCHE "+parts[2]+"\nRESULTAT COMPTEUR DROITE "+parts[3]+"\n";
		texte+=separateur+separateur;

		return texte;
	}
}