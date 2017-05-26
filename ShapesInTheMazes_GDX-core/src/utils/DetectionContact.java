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
	private String maleable = "";
	private Map<String, Integer> contacts;
	
	private Vector2 vitesse;

	public DetectionContact(GameScreen gs) {
		this.gs = gs;
		contacts = new HashMap<>();		
		contacts.put("haut", 0);
		contacts.put("bas", 0);
		contacts.put("droite", 0);
		contacts.put("gauche", 0);
	}
	
	protected int contactsCount(){
		int x = 0;
		for (String s : contacts.keySet()){
			if (contacts.get(s) > 0) {
				x++;
			}		
		}
		return x;
	}

	@Override
	public void beginContact(Contact contact) {
		
		System.out.println("begin");
		
		vitesse = gs.getBody().getLinearVelocity();
		
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

		checkAjout(maleable);
	}

	@Override
	public void endContact(Contact contact) {
		
		System.out.println("end");
		
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

		for (String s : murObjet.getContacts().keySet()){
			if (murObjet.getContacts().get(s)){
				System.out.println(mur + " --> perte de contact par la " + s);	
				contacts.put(s, Math.max(contacts.get("gauche") - 1, 0));
				murObjet.getContacts().put(s, false);
				murObjet.setCouleur(Color.CHARTREUSE);
			}
		}
		

		maleable = "";
	
		sauvegarde = true;	
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		System.out.println("presolve");

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
		
		if (vitesse.y > 0 && contacts.get("haut") > 0 && (maleable.equals("H") || maleable.equals(""))){
			
		
			if (! (contacts.get("droite") > 0 && contacts.get("gauche") > 0)){
				System.out.println(mur + " --> déformation par le haut");

				personnageObjet.setHeight(Math.max(personnageObjet.getHeight() * 0.99f, 20f));
				personnageObjet.setWidth(personnageObjet.getSurface() / personnageObjet.getHeight());
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
				
				checkAjout(maleable);
				maleable = "H";
			}
		}
		if (vitesse.y < 0 && contacts.get("bas") > 0 && (maleable.equals("B") || maleable.equals(""))){
			

			if (! (contacts.get("droite") > 0 && contacts.get("gauche") > 0)){
				System.out.println(mur + " --> déformation par le bas ");

				personnageObjet.setHeight(Math.max(personnageObjet.getHeight() * 0.99f, 20f));
				personnageObjet.setWidth(personnageObjet.getSurface() / personnageObjet.getHeight());
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
				
				checkAjout(maleable);
				maleable = "B";
			}
		}
		if (vitesse.x > 0 && contacts.get("droite") > 0 && (maleable.equals("D") || maleable.equals(""))){
			
			
			if (! (contacts.get("haut") > 0 && contacts.get("bas") > 0)){
				System.out.println(mur + " --> déformation par le coté droit");
				
				personnageObjet.setWidth(Math.max(personnageObjet.getWidth() * 0.99f, 20f));
				personnageObjet.setHeight(personnageObjet.getSurface() / personnageObjet.getWidth());
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
				
				checkAjout(maleable);
				maleable = "D";
			}
			
		}
		if (vitesse.x < 0 && contacts.get("gauche") > 0 && (maleable.equals("G") || maleable.equals(""))){
			
			
			if (! (contacts.get("haut") > 0 && contacts.get("bas") > 0)){
				System.out.println(mur + " --> déformation par le coté gauche");				
				
				personnageObjet.setWidth(Math.max(personnageObjet.getWidth() * 0.99f, 20f));
				personnageObjet.setHeight(personnageObjet.getSurface() / personnageObjet.getWidth());
				PolygonShape poly_temp = (PolygonShape) personnage.getShape();
				poly_temp.setAsBox(personnageObjet.getWidth() / 2f, personnageObjet.getHeight() / 2f);
				
				checkAjout(maleable);
				maleable = "G";
			}
			
		}
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		System.out.println("postsolve");
		
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
		
		if (contactsCount() >= 3){
			System.out.println("... undo ...");

			maleable = "";
			
			Personnage2D p_temp = (Personnage2D) personnage.getUserData();
			p_temp.setWidth(witdhtSave);
			p_temp.setHeight(heightSave);
			personnage.setUserData(p_temp);
			PolygonShape poly_temp = (PolygonShape) personnage.getShape();
			poly_temp.setAsBox(witdhtSave / 2f, heightSave / 2f);	
			
		}
		vitesse = new Vector2(0f, 0f);
	}
	
	private void checkAjout(String sauf){
		
		if (! sauf.equals("H") && murBody.getPosition().y - murObjet.getHeight() /2 - personnageBody.getPosition().y - personnageObjet.getHeight() /2 > 0){
			System.out.println(mur + " --> contact par le haut");
			
			contacts.put("haut", contacts.get("haut") + 1);
			murObjet.getContacts().put("haut", true);
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			maleable = "H";
		}
		if (! sauf.equals("B") && personnageBody.getPosition().y - personnageObjet.getHeight() /2  - murBody.getPosition().y - murObjet.getHeight() /2 > 0){
			System.out.println(mur + " --> contact par le bas ");
			
			contacts.put("bas", contacts.get("bas") + 1);
			murObjet.getContacts().put("bas", true);
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			maleable = "B";

		}
		if (! sauf.equals("D") && murBody.getPosition().x - murObjet.getWidth() /2 - personnageBody.getPosition().x - personnageObjet.getWidth() /2  > 0){
			System.out.println(mur + " --> contact par le coté droit");
			
			contacts.put("droite", contacts.get("droite") + 1);
			murObjet.getContacts().put("droite", true);
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			maleable = "D";
		}
		if (! sauf.equals("G") && personnageBody.getPosition().x - personnageObjet.getWidth() /2 - murBody.getPosition().x - murObjet.getWidth() /2 > 0){
			System.out.println(mur + " --> contact par le coté gauche");
			
			contacts.put("gauche", contacts.get("gauche") + 1);
			murObjet.getContacts().put("gauche", true);
			
			murObjet.setCouleur(Color.FIREBRICK);
			
			maleable = "G";
		}
	}

}
