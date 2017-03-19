package app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ExitScreen implements Screen {
	
	final Main_SITM_GDX game;
	
	TextButton quitter;
	TextButton revenir;
	
	Label label;

	Stage stage;
	
	public ExitScreen(final Main_SITM_GDX game) {
		super();
		this.game = game;
		
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		Gdx.graphics.setWindowedMode(600, 150);
		     
        stage = new Stage();
        
        quitter = new TextButton("Quitter", game.skin);
        quitter.setX(340);
        quitter.setY(15);
        quitter.setWidth(250);
        quitter.setHeight(40);
        quitter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                Gdx.app.exit();
            }
        });
        stage.addActor(quitter);
        
        revenir = new TextButton("Reprendre", game.skin);
        revenir.setX(10);
        revenir.setY(15);
        revenir.setWidth(250);
        revenir.setHeight(40);
        revenir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
            	game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(revenir);
        
        label = new Label("Vous etes sur le point de quitter le jeu.\nVoulez vous vraiment abandonner ?", game.skin);
        label.setAlignment(0);
        label.setX(190);
        label.setY(90);
        label.setColor(Color.BLACK);
        label.setFontScale(1.5F, 1.5F);
        stage.addActor(label);
        
        Gdx.input.setInputProcessor(stage);	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.8F, 0.7F, 0.6f, 0.1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.draw();
        stage.act();
		
	}

	@Override
	public void resize(int width, int height) {
		
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
		// TODO Auto-generated method stub
		
	}

}
