package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Sens;


public class Personnage2D {

	private Niveau niveau;
	private final double surface;
	
	private double undoRectangleX;
	private double undoRectangleY;
	private double undoRectangleWidth;
	private double undoRectangleHeight;
	
	private double nouvelleDimension;

	
	
	public Personnage2D(double x, double y, double width, double height, com.badlogic.gdx.graphics.Color color, Niveau niveau) {

		this.surface = width * height;
		this.niveau = niveau;
	}

	public void deplacement(double x, double y){
		
		deplacement((int) x, (int) y);
	}
	
	public void deplacement(int x, int y){
			
	}
	
	public boolean sansContact(){
		
		for (Mur2D m2D : niveau.getListeDesMurs()){			
			
//			if (m2D.getBoundsInParent().intersects(this.getX() +1, this.getY() +1, this.getWidth() -2, this.getHeight() -2)){
//				return false;
//			}
		}
		return true;		
	}
	
    public boolean enContact(){
		return ! sansContact();	
	}
	
    public void deformationGauche(double ratio){
    	
    }
    
    public boolean deformationGaucheUnitaire(int deformation){
    	
    	return true;
    }
    
    public void deformationDroite(double ratio){
    	
    }
    public boolean deformationDroiteUnitaire(int deformation){
    	
        return true;
    }
    
    public void deformationHaut(double ratio){
    	

	}
    
    public boolean deformationHautUnitaire(int deformation){
    	
        return true;
    }
    
    public void deformationBas(double ratio){
        

	}
    
    public boolean deformationBasUnitaire(int deformation){
    	
    	return true;
    }
	
}
