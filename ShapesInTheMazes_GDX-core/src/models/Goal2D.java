package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Goal2D extends Actor{
    
	private Texture texture;
	private double x;
	private double y;
	
	public Goal2D(double x, double y){
	    setTexture("images/goal.png");
		
		this.x = x;
		this.y = y;

	}

	public float getX() {
		return (float) x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public float getY() {
		return (float) y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = new Texture(Gdx.files.internal(texture));
		
		System.out.println(this.texture.getHeight());
		System.out.println(this.texture.getWidth());
	}
    
	

}
