package utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import app.GameScreen;
import models.Mur2D;
import models.Personnage2D;

public class DetectionContact implements ContactListener{
	
	private GameScreen gs;
	
	private Fixture personnage;
	private Body personnageBody;
	private Personnage2D personnageObjet;
	private Fixture mur;
	private Body murBody;
	private Mur2D murObjet;
	
	private float witdhtSave; 
	private float heightSave;	
	private boolean sauvegarde = true;
	private boolean undo_possible = true;
	private Map<String, Boolean> contacts;
	
	private Vector2 vitesse;
	
	public DetectionContact(GameScreen gs) {
		this.gs = gs;
		contacts = new HashMap<>();		
		contacts.put("haut", false);
		contacts.put("bas", false);
		contacts.put("droite", false);
		contacts.put("gauche", false);
	}
	
	protected int contactsCount(){
		int x = 0;
		for (String s : contacts.keySet()){
			if (contacts.get(s)) {
				System.out.print(s + ", ");
				x++;
			}		
		}
		System.out.println();
		return x;
	}

	@Override
	public void beginContact(Contact contact) {
		
		vitesse = gs.getBody().getLinearVelocity();
		
		//System.out.println(contact.);
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
			
		if (fa.getUserData() != null && fa.getUserData().toString().equals("Personnage2D")){		
			personnage = fa;
			mur = fb;			
		}
		
		else if (fb.getUserData() != null && fb.getUserData().toString().equals("Personnage2D")){
			
			personnage = fb;
			mur = fa;
		}
		
		personnageBody = personnage.getBody();
		personnageObjet = (Personnage2D) personnage.getUserData();
		
		murBody = mur.getBody();
		murObjet = (Mur2D) mur.getUserData();
		
		if (sauvegarde){
			//System.out.println("sauvegarde de " + ((Personnage2D) personnage.getUserData()).getHeight());
			witdhtSave = ((Personnage2D) personnage.getUserData()).getWidth();
		    heightSave = ((Personnage2D) personnage.getUserData()).getHeight();
		    sauvegarde = false;
		}
		
		System.out.print(mur.getUserData() + " -> ");
		System.out.println(mur);
		System.out.println("gauche : "  +((personnageBody.getPosition().x - personnageObjet.getWidth() /2) - (murBody.getPosition().x + murObjet.getWidth() /2)));
		System.out.println("droite : "  +((murBody.getPosition().x - murObjet.getWidth() /2) - (personnageBody.getPosition().x + personnageObjet.getWidth() /2)));
		System.out.println("haut : "  +((murBody.getPosition().y - murObjet.getHeight() /2) - (personnageBody.getPosition().y + personnageObjet.getHeight() /2)));
		System.out.println("bas : "  +((personnageBody.getPosition().y - personnageObjet.getHeight() /2) - (murBody.getPosition().y + murObjet.getHeight() /2)));

		if ((murBody.getPosition().y - murObjet.getHeight() /2) - (personnageBody.getPosition().y + personnageObjet.getHeight() /2) > -3){
			System.out.println(mur + " --> contact par le haut");
			
			contacts.put("haut", true);
			
			System.out.println("contactsCount() : " + contactsCount() + " cotés");
			System.out.println("gs.getWorld().getContactCount() : " + gs.getWorld().getContactCount() + " zones de contact");
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			if (contactsCount() < 3){
				personnageObjet.setHeight(Math.max(personnageObjet.getHeight() * 0.95f, 20f));
				personnageObjet.setWidth(personnageObjet.getSurface() / personnageObjet.getHeight());
				personnage.setUserData(personnageObjet);
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
			}
		}
		if ((personnageBody.getPosition().y - personnageObjet.getHeight() /2)  - (murBody.getPosition().y + murObjet.getHeight() /2) > -3){
			System.out.println(mur + " --> contact par le bas ");
			
			contacts.put("bas", true);
			
			System.out.println("contactsCount() : " + contactsCount() + " cotés");
			System.out.println("gs.getWorld().getContactCount() : " + gs.getWorld().getContactCount() + " zones de contact");
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			if (contactsCount() < 3){
				personnageObjet.setHeight(Math.max(personnageObjet.getHeight() * 0.95f, 20f));
				personnageObjet.setWidth(personnageObjet.getSurface() / personnageObjet.getHeight());
				personnage.setUserData(personnageObjet);
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
			}
		}
		if ((murBody.getPosition().x - murObjet.getWidth() /2) - (personnageBody.getPosition().x + personnageObjet.getWidth() /2)  > -3){
			System.out.println(mur + " --> contact par le coté droit");
			
			contacts.put("droite", true);
			
			System.out.println("contactsCount() : " + contactsCount() + " cotés");
			System.out.println("gs.getWorld().getContactCount() : " + gs.getWorld().getContactCount() + " zones de contact");
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			if (contactsCount() < 3){
				personnageObjet.setWidth(Math.max(personnageObjet.getWidth() * 0.95f, 20f));
				personnageObjet.setHeight(personnageObjet.getSurface() / personnageObjet.getWidth());
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
			}
		}
		if ((personnageBody.getPosition().x - personnageObjet.getWidth() /2) - (murBody.getPosition().x + murObjet.getWidth() /2)  > -3){
			System.out.println(mur + " --> contact par le coté gauche");
			
			contacts.put("gauche", true);
			
			System.out.println("contactsCount() : " + contactsCount() + " cotés");
			System.out.println("gs.getWorld().getContactCount() : " + gs.getWorld().getContactCount() + " zones de contact");
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			if (contactsCount() < 3){
				personnageObjet.setWidth(Math.max(personnageObjet.getWidth() * 0.95f, 20f));
				personnageObjet.setHeight(personnageObjet.getSurface() / personnageObjet.getWidth());
				personnage.setUserData(personnageObjet);
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
			}
		}
		
		if (contactsCount() >= 3){
			System.out.println("... undo ...");
			Personnage2D p_temp = (Personnage2D) personnage.getUserData();
			p_temp.setWidth(witdhtSave);
			p_temp.setHeight(heightSave);
			personnage.setUserData(p_temp);
			PolygonShape poly_temp = (PolygonShape) personnage.getShape();
			poly_temp.setAsBox(witdhtSave / 2f, heightSave / 2f);	
			vitesse = new Vector2(0f, 0f);
		}
		//System.out.println("relance : " + vitesse);
		//gs.getBody().setLinearVelocity(vitesse);
	}

	@Override
	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa.getUserData() != null && fa.getUserData().toString().equals("Personnage2D")){		
			personnage = fa;
			mur = fb;			
		}
		
		else if (fb.getUserData() != null && fb.getUserData().toString().equals("Personnage2D")){
			
			personnage = fb;
			mur = fa;
		}
		
		personnageBody = personnage.getBody();
		personnageObjet = (Personnage2D) personnage.getUserData();
		
		murBody = mur.getBody();
		murObjet = (Mur2D) mur.getUserData();
		
		System.out.print(mur.getUserData() + " -> ");
		System.out.println(mur);
		System.out.println("gauche : "  +((personnageBody.getPosition().x - personnageObjet.getWidth() /2) - (murBody.getPosition().x + murObjet.getWidth() /2)));
		System.out.println("droite : "  +((murBody.getPosition().x - murObjet.getWidth() /2) - (personnageBody.getPosition().x + personnageObjet.getWidth() /2)));
		System.out.println("haut : "  +((murBody.getPosition().y - murObjet.getHeight() /2) - (personnageBody.getPosition().y + personnageObjet.getHeight() /2)));
		System.out.println("bas : "  +((personnageBody.getPosition().y - personnageObjet.getHeight() /2) - (murBody.getPosition().y + murObjet.getHeight() /2)));

		
		if ((personnageBody.getPosition().x - personnageObjet.getWidth() /2) - (murBody.getPosition().x + murObjet.getWidth() /2) > -3){
            System.out.println(mur + " --> perte de contact par la gauche ");		
			contacts.put("gauche", false);
			murObjet.setCouleur(Color.CHARTREUSE);			
		}
		if ((murBody.getPosition().x - murObjet.getWidth() /2) - (personnageBody.getPosition().x + personnageObjet.getWidth() /2) > -3){
            System.out.println(mur + " --> perte de contact par la droite");		
			contacts.put("droite", false);
			murObjet.setCouleur(Color.CHARTREUSE);
		}
		if ((personnageBody.getPosition().y - personnageObjet.getHeight() /2) - (murBody.getPosition().y + murObjet.getHeight() /2) > -3){
			System.out.println(mur + " --> perte de contact par le bas");			
			contacts.put("bas", false);
			murObjet.setCouleur(Color.CHARTREUSE);
		}
		if ((murBody.getPosition().y - murObjet.getHeight() /2) - (personnageBody.getPosition().y + personnageObjet.getHeight() /2) > -3){
			System.out.println(mur + " --> perte de contact par le haut");			
			contacts.put("haut", false);
			murObjet.setCouleur(Color.CHARTREUSE);
		}
		
		System.out.println("contactsCount() : " + contactsCount() + " cotés");
		System.out.println("gs.getWorld().getContactCount() : " + gs.getWorld().getContactCount() + " zones de contact");
		
		sauvegarde = true;	
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
