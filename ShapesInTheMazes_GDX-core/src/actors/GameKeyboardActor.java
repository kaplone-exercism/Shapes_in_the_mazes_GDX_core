package actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameKeyboardActor extends Actor {
	
    public GameKeyboardActor() {
    	
    	System.out.println("constructeur GameKeyboardActor()");
    	
    	this.setBounds(0, 0, 1040, 740);
    	
    	addListener(new InputListener(){
        	
        	@Override
       	    public boolean keyTyped(InputEvent event, char character){
        		System.out.println(event);
        		System.out.println(character);
                return true;
       	    }
        	
        	@Override
       	    public boolean keyDown(InputEvent event, int character){
        		System.out.println(event);
        		System.out.println(character);
                return true;
       	    }
        	
        	@Override
       	    public boolean keyUp(InputEvent event, int character){
        		System.out.println(event);
        		System.out.println(character);
                return true;
       	    }
        	
        	@Override
       	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
       		 System.out.println("touch !");
                return true;
       	 }
    		
    	});
    	
    	addListener(new ClickListener(){
    		@Override
       	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
       		 System.out.println("click touch !");
                return true;
       	    }
    		
    		@Override
       	    public void clicked (InputEvent event, float x, float y) {
       		 System.out.println("click !");
       	    }
    	});
    }
    
    @Override
    public void draw(Batch batch, float alpha){
    	
    }

}
