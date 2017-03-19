package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class FontUtils {
	
	public static Label.LabelStyle generator(int size){
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/tahoma.ttf"));
	    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	    parameter.size = size;
	    BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
	    generator.dispose();
	 
	    Label.LabelStyle labelStyle = new Label.LabelStyle();
	    labelStyle.font = font24;
	    
	    return labelStyle;
	}

}
