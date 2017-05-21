package models;

import java.util.List;

public class Niveau {
	
	private String nom;
	private Personnage2D perso;
	private Goal2D goal2D;

    private Thread chronoThread;
	
	private boolean enCoursDeFonctionnement;
	
	private int index;
	
	private List<Mur2D> listeDesMurs;
	
	private String infos;

	public String getNom() {
		
		if(nom != null && nom.toLowerCase().startsWith("practice")){
			return String.format("Practice %02d", Integer.parseInt(nom.toLowerCase().split("practice")[1].trim()));
		}
		else if(nom != null && nom.toLowerCase().startsWith("niveau")){
			return String.format("Niveau %02d", Integer.parseInt(nom.toLowerCase().split("niveau")[1].trim()));
		}		
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Personnage2D getPerso() {
		return perso;
	}
	public void setPerso(Personnage2D perso) {
		this.perso = perso;
	}
	public List<Mur2D> getListeDesMurs() {
		return listeDesMurs;
	}
	
    public void setListeDesMurs(List<Mur2D> listeDesMurs) {
		this.listeDesMurs = listeDesMurs;
	}

	public Goal2D getGoal2D() {
		return goal2D;
	}
	public void setGoal2D(Goal2D goal2d) {
		goal2D = goal2d;
	}
	public Thread getChronoThread() {
		return chronoThread;
	}
	public void setChronoThread(Thread chronoThread) {
		this.chronoThread = chronoThread;
	}
	public boolean isEnCoursDeFonctionnement() {
		return enCoursDeFonctionnement;
	}
	public void setEnCoursDeFonctionnement(boolean enCoursDeFonctionnement) {
		this.enCoursDeFonctionnement = enCoursDeFonctionnement;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	
	
}
