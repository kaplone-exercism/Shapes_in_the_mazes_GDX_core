package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import app.ExitScreen;
import app.LaunchScreen;
import app.Main_SITM_GDX;

public class AnimationActor extends Actor{

    Texture logoImage;
    Animation<AtlasRegion> animUp;
    Texture nb;
    
    Label label;
    
    public AnimationActor(final TextureAtlas atlas, Stage stage, int x, int y, final Main_SITM_GDX game) {
		
    	setBounds(x ,y ,atlas.getRegions().get(0).getRegionWidth(), atlas.getRegions().get(0).getRegionHeight());
    	
    	addListener(new InputListener(){
        	
        	@Override
        	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
        		
        		animUp = new Animation<AtlasRegion>(0.15f, atlas.getRegions());
        		animUp.setPlayMode(Animation.PlayMode.LOOP);
        	}
        	
        	@Override
        	public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
        		
        		animUp = null;
        	}
        	
        	@Override
        	 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
 
        		switch (atlas.getRegions().size){
        		
        		// maze
        		case 3 : System.out.println("click : " + button + " Maze");
        		break;
        		
        		// settings
        		case 5 : System.out.println("click : " + button + " Settings");
        		break;
        		
        		// launch
        		case 6 : game.setScreen(new LaunchScreen(game));
        		break;
        		
        		// exit
        		case 23 : game.setScreen(new ExitScreen(game));
        		break;
        		
        		}
                 return true;
        	 }
        	
        	@Override
       	    public boolean keyTyped(InputEvent event, char character){
        		System.out.println("key : ");
        		System.out.println(event);
       		    System.out.println(character);
                return true;
       	 }
        });
	}
    
    @Override
    public void draw(Batch batch, float alpha){
    }

	public Animation<AtlasRegion> getAnimUp() {
		return animUp;
	}

	public void setAnimUp(Animation<AtlasRegion> animUp) {
		this.animUp = animUp;
	}
	
}
