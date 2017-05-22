package utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import app.GameScreen;
import models.Mur2D;
import models.Personnage2D;

public class DetectionContact implements ContactListener{
	
	private GameScreen gs;
	
	public DetectionContact(GameScreen gs) {
		this.gs = gs;
	}

	@Override
	public void beginContact(Contact contact) {
		System.out.println("Contact");
		
		System.out.println(contact.toString());
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		System.out.println(fa.getUserData());
		System.out.println(fb.getUserData());
		
		if (fa.getUserData() != null && fa.getUserData().toString().equals("Personnage2D")){
			
//			System.out.println(((Personnage2D)fa.getUserData()).getWidth());
//			
			System.out.println("fa droite = " + (fa.getBody().getPosition().x + ((Personnage2D)fa.getUserData()).getWidth() /2));
			System.out.println("fa gauche = " + (fa.getBody().getPosition().x - ((Personnage2D)fa.getUserData()).getWidth() /2));
			System.out.println("fa haut = " + (fa.getBody().getPosition().y + ((Personnage2D)fa.getUserData()).getHeight() /2));
			System.out.println("fa bas = " + (fa.getBody().getPosition().y - ((Personnage2D)fa.getUserData()).getHeight() /2));
			
			if (fb.getUserData() != null && fb.getUserData().toString().equals("Mur2D")){
				System.out.println("fb droite = " + (fb.getBody().getPosition().x + ((Mur2D)fb.getUserData()).getWidth() /2));
				System.out.println("fb gauche = " + (fb.getBody().getPosition().x - ((Mur2D)fb.getUserData()).getWidth() /2));
				System.out.println("fb haut = " + (fb.getBody().getPosition().y + ((Mur2D)fb.getUserData()).getHeight() /2));
				System.out.println("fb bas = " + (fb.getBody().getPosition().y - ((Mur2D)fb.getUserData()).getHeight() /2));
				
				if (Math.abs((fb.getBody().getPosition().y - ((Mur2D)fb.getUserData()).getHeight() /2) - (fa.getBody().getPosition().y + ((Personnage2D)fa.getUserData()).getHeight() /2)) < 0.55){
					System.out.println(" --> contact par le haut");
				}
				if (Math.abs((fb.getBody().getPosition().y + ((Mur2D)fb.getUserData()).getHeight() /2) - (fa.getBody().getPosition().y - ((Personnage2D)fa.getUserData()).getHeight() /2)) < 0.55){
					System.out.println(" --> contact par le bas");
				}
				if (Math.abs((fb.getBody().getPosition().x - ((Mur2D)fb.getUserData()).getWidth() /2) - (fa.getBody().getPosition().x + ((Personnage2D)fa.getUserData()).getWidth() /2)) < 0.55){
					System.out.println(" --> contact par le coté droit");
					
					Personnage2D p_temp = (Personnage2D)fa.getUserData();
					//p_temp.
					p_temp.setWidth(Math.max(p_temp.getWidth() - 1d, 20d));
					p_temp.setHeight(p_temp.getSurface() / p_temp.getWidth());
					fa.setUserData(p_temp);
					System.out.println(((Personnage2D)fa.getUserData()).getWidth());
					PolygonShape poly_temp = (PolygonShape) fa.getShape();
					poly_temp.setAsBox(p_temp.getWidth() / 2 - 0.5f, p_temp.getHeight() / 2 - 0.5f);
				}
				if (Math.abs((fb.getBody().getPosition().x + ((Mur2D)fb.getUserData()).getWidth() /2) - (fa.getBody().getPosition().x - ((Personnage2D)fa.getUserData()).getWidth() /2)) < 0.55){
					System.out.println(" --> contact par le coté gauche");
				}
			}
			
		}
		
		else if (fb.getUserData() != null && fb.getUserData().toString().equals("Personnage2D")){
			
//			System.out.println(((Personnage2D)fb.getUserData()).getWidth());
			
			System.out.println("fb droite = " + (fb.getBody().getPosition().x + ((Personnage2D)fb.getUserData()).getWidth() /2));
			System.out.println("fb gauche = " + (fb.getBody().getPosition().x - ((Personnage2D)fb.getUserData()).getWidth() /2));
			System.out.println("fb haut = " +(fb.getBody().getPosition().y + ((Personnage2D)fb.getUserData()).getHeight() /2));
			System.out.println("fb bas = " + (fb.getBody().getPosition().y - ((Personnage2D)fb.getUserData()).getHeight() /2));
			
			if (fa.getUserData() != null && fa.getUserData().toString().equals("Mur2D")){
				System.out.println("fa droite = " + (fa.getBody().getPosition().x + ((Mur2D)fa.getUserData()).getWidth() /2));
				System.out.println("fa gauche = " + (fa.getBody().getPosition().x - ((Mur2D)fa.getUserData()).getWidth() /2));
				System.out.println("fa haut = " + (fa.getBody().getPosition().y + ((Mur2D)fa.getUserData()).getHeight() /2));
				System.out.println("fa bas = " + (fa.getBody().getPosition().y - ((Mur2D)fa.getUserData()).getHeight() /2));
				
				if (Math.abs((fa.getBody().getPosition().y - ((Mur2D)fa.getUserData()).getHeight() /2) - (fb.getBody().getPosition().y + ((Personnage2D)fb.getUserData()).getHeight() /2)) < 0.55){
					System.out.println(" --> contact par le haut");
				}
				if (Math.abs((fa.getBody().getPosition().y + ((Mur2D)fa.getUserData()).getHeight() /2) - (fb.getBody().getPosition().y - ((Personnage2D)fb.getUserData()).getHeight() /2)) < 0.55){
					System.out.println(" --> contact par le bas");
				}
				if (Math.abs((fa.getBody().getPosition().x - ((Mur2D)fa.getUserData()).getWidth() /2) - (fb.getBody().getPosition().x + ((Personnage2D)fb.getUserData()).getWidth() /2)) < 0.55){
					System.out.println(" --> contact par le coté droit");
					
					Personnage2D p_temp = (Personnage2D)fb.getUserData();
					p_temp.setWidth(Math.max(p_temp.getWidth() - 1d, 20d));
					p_temp.setHeight(p_temp.getSurface() / p_temp.getWidth());
					fb.setUserData(p_temp);
					System.out.println(((Personnage2D)fb.getUserData()).getWidth());
					PolygonShape poly_temp = (PolygonShape) fb.getShape();
					poly_temp.setAsBox(p_temp.getWidth() / 2 - 0.5f, p_temp.getHeight() / 2 - 0.5f);
					
				}
				if (Math.abs((fa.getBody().getPosition().x + ((Mur2D)fa.getUserData()).getWidth() /2) - (fb.getBody().getPosition().x - ((Personnage2D)fb.getUserData()).getWidth() /2)) < 0.55){
					System.out.println(" --> contact par le coté gauche");
				}
			}
		}		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		System.out.println(fa.getBody().getType()+" fin de hit "+ fb.getBody().getType());
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
