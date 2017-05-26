package models;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import enums.Orientation;

public class Mur2D extends Rectangle{
	
	private final Orientation orientation;
	private final int epaisseur;
	private final int position;
	private final int debut;
	private final int fin;
	private final String nom;
	private Label infos;
	private Color couleur;
	
	private double decalageH;
	private double decalageV;
	
	private Map<String, Boolean> contacts;

	
	public Mur2D(Orientation orientation, int epaisseur, int position, int debut, int fin, String nom) {
		
		super(orientation == Orientation.HORIZONTAL ? debut : position,
		      orientation == Orientation.HORIZONTAL ? position : debut, 
		      orientation == Orientation.HORIZONTAL ? fin - debut : epaisseur,
		      orientation == Orientation.HORIZONTAL ? epaisseur : fin - debut);
		
		this.orientation = orientation;
		this.epaisseur = epaisseur;
		this.position = position;
		this.debut = debut;
		this.fin = fin;
		this.nom = nom;
		this.couleur = Color.BLACK;
		
		
		// reperage inverse (pour correspondre a ceux de DetectionContact)
		contacts = new HashMap<>();		
		contacts.put("haut", false);
		contacts.put("bas", false);
		contacts.put("droite", false);
		contacts.put("gauche", false);
	}

	@Override
	public String toString(){
		//return String.format("%s :\nposition = %d\ndébut = %d\nfin = %d", this.nom, this.position, this.debut, this.fin);
		return "Mur2D";
	}
	
    


	public boolean estEnContact(){		
		return false;	
	}

    public Rectangle getRectangle(){
    	return this;
    }

	public Orientation getOrientation() {
		return orientation;
	}

	public int getEpaisseur() {
		return epaisseur;
	}

	public int getPosition() {
		return position;
	}

	public int getDebut() {
		return debut;
	}

	public int getFin() {
		return fin;
	}

	public boolean horizontal(){
		return this.orientation == Orientation.HORIZONTAL;
	}
	
	public boolean vertical(){
		return this.orientation == Orientation.VERTICAL;
	}


	public void setInfos(Label infos) {
		this.infos = infos;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public Map<String, Boolean> getContacts() {
		return contacts;
	}

	public void setContacts(Map<String, Boolean> contacts) {
		this.contacts = contacts;
	}	
}
