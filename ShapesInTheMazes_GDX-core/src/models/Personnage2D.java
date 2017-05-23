package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;

import enums.Sens;


public class Personnage2D extends Actor{

	private Niveau niveau;
	private final float surface;


	private float x;
	private float y;
	private float width;
	private float height;
	
	
	public Personnage2D(double x, double y, double width, double height, com.badlogic.gdx.graphics.Color color, Niveau niveau) {
        
		this.x = (float) x;
		this.y = (float) y;
		this.width = (float) width;
		this.height = (float) height;
		this.surface = (float) (width * height);
		this.niveau = niveau;
	}

	public float getX() {
		return x;
	}

	public void setX(double x) {
		this.x = (float) x;
	}

	public float getY() {
		return y;
	}

	public void setY(double y) {
		this.y = (float) y;
	}
    
	@Override
	public float getWidth() {
		return width;
	}

//	public void setWidth(double width) {
//		this.width = (float) width;
//	}
	
	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public float getHeight() {
		return height;
	}
    
//	public void setHeight(double height) {
//		this.height = (float) height;
//	}
	
	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	public float getSurface() {
		return surface;
	}	
}
