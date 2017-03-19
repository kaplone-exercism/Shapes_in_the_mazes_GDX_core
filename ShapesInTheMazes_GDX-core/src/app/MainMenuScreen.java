package app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import actors.AnimationActor;

public class MainMenuScreen implements Screen {
	
	final Main_SITM_GDX game;

	Texture logoImage;
    Texture settingsImage;
    Texture launchImage;
    Texture exitImage;	
    
    AnimationActor logoActor;
    AnimationActor settingsActor;
    AnimationActor launchActor;
    AnimationActor exitActor;
    
    Stage stage;
    
    float animTime;

	public MainMenuScreen(final Main_SITM_GDX game) {
		super();
		this.game = game;
		
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		Gdx.graphics.setWindowedMode(620, 395);

        logoImage = new Texture(Gdx.files.internal("logo/maze_v2_nb_0.png"));
        settingsImage = new Texture(Gdx.files.internal("settings/settings_sprite_nb.png"));
        launchImage = new Texture(Gdx.files.internal("launch/launch_nb.png"));
        exitImage = new Texture(Gdx.files.internal("exit/exit_nb.png"));
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);	
         
        logoActor = new AnimationActor(new TextureAtlas(Gdx.files.internal("logo/logo_sprites_couleur.atlas")), stage, 0, 0, game);
        logoActor.setTouchable(Touchable.enabled);    
        stage.addActor(logoActor);   
        
        settingsActor = new AnimationActor(new TextureAtlas(Gdx.files.internal("settings/settings_sprites_couleur.atlas")), stage, 430, 250, game);
        settingsActor.setTouchable(Touchable.enabled);
        stage.addActor(settingsActor); 
        
        launchActor = new AnimationActor(new TextureAtlas(Gdx.files.internal("launch/launch_sprites_couleur.atlas")), stage, 450, 120, game);
        launchActor.setTouchable(Touchable.enabled);
        stage.addActor(launchActor);
        
        exitActor = new AnimationActor(new TextureAtlas(Gdx.files.internal("exit/exit_sprites_couleur.atlas")), stage, 450, 5, game);
        exitActor.setTouchable(Touchable.enabled);
        stage.addActor(exitActor);
        
        logoActor.setAnimUp(null);
        settingsActor.setAnimUp(null);
        
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8F, 0.7F, 0.6f, 0.1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //camera.update();
        //game.batch.setProjectionMatrix(camera.combined);
        
        stage.draw();
        stage.act();
        
        animTime+=Gdx.graphics.getDeltaTime();
        
        stage.getBatch().begin();
        if (logoActor.getAnimUp() != null){
        	stage.getBatch().draw( logoActor.getAnimUp().getKeyFrame(animTime), 0,0);
        }
        else if (settingsActor.getAnimUp() != null){
        	stage.getBatch().draw( settingsActor.getAnimUp().getKeyFrame(animTime), 430,250);
        }
        else if (launchActor.getAnimUp() != null){
        	stage.getBatch().draw( launchActor.getAnimUp().getKeyFrame(animTime), 450, 120);
        }
        else if (exitActor.getAnimUp() != null){
        	stage.getBatch().draw( exitActor.getAnimUp().getKeyFrame(animTime), 450, 5);
        }
        
        stage.getBatch().end();
        
        game.batch.begin();
        
        if (logoActor.getAnimUp() == null){
        	game.batch.draw(logoImage, 0, 0);
        }
        if (settingsActor.getAnimUp() == null){
        	game.batch.draw(settingsImage, 430, 250);
        }
        if (launchActor.getAnimUp() == null){
        	game.batch.draw(launchImage, 450, 120);
        }
        if (exitActor.getAnimUp() == null){
        	game.batch.draw(exitImage, 450, 5);
        }
        
        game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		//Gdx.graphics.setWindowedMode(620, 395);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		logoImage.dispose();
		settingsImage.dispose();
	    launchImage.dispose();
	    exitImage.dispose();	

	    stage.dispose();

	}

}
