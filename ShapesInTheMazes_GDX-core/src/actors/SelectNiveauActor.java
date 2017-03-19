package actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import app.ExitScreen;
import app.LaunchScreen;
import app.Main_SITM_GDX;
import models.Niveau;

public class SelectNiveauActor extends Actor{
    
    private Label label;
	private LaunchScreen launchScreen;
	List<Niveau> niveaux;
    
    public SelectNiveauActor(Stage stage, final Main_SITM_GDX game, final List<Niveau> niveaux, LaunchScreen launchScreen) {
    	
    	this.launchScreen = launchScreen;
    	this.niveaux = niveaux;
    	
    	addListener(new InputListener(){
        	
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){

        	}
        	
        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){

        	}
        	
        	@Override
        	 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		 System.out.println(niveaux.getSelection().toString());
                 return true;
        	 }
        	
        	@Override
       	    public boolean keyTyped(InputEvent event, char character){
                return false;
       	 }
        });
	}
    
	@Override
    public void draw(Batch batch, float alpha){
    	
    }
	
}
