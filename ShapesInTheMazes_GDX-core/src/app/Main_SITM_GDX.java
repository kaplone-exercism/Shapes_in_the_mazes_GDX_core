package app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Main_SITM_GDX extends Game{

	public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;

	@Override
	public void create() {
		batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("skins/flat/flat-earth-ui.json"));
        this.setScreen(new MainMenuScreen(this));		
	}
	
	public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
