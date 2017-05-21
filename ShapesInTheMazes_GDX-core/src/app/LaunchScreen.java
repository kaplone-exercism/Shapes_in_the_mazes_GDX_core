package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import enums.Orientation;
import models.Goal2D;
import models.Mur2D;
import models.Niveau;
import models.Personnage2D;
import utils.FontUtils;
import utils.NetResources;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LaunchScreen implements Screen {
	
	final Main_SITM_GDX game;
	
	ShapeRenderer shapeRenderer;
	
	TextButton quitter;
	TextButton revenir;
	TextButton lancer;
	
	ScrollPane scrollPane;
	VerticalGroup vg;

	List<Niveau> niveaux;
	List<Mur2D> murs;
	Personnage2D perso;
	Goal2D goal;
	Map<String, Niveau> niveaux_map;
	
	Label label;
	Label labelInfos;
	Niveau niveau;
	Stage stage;
	
	TextButton tb_actif;
	
	public LaunchScreen(final Main_SITM_GDX game) {
		super();
		this.game = game;
		
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		Gdx.graphics.setWindowedMode(1000, 600);
        
        stage = new Stage();
 
        final Table scrollTable = new Table();
        scrollTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        scrollPane = new ScrollPane(scrollTable, game.skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollBarPositions(false, true);
        scrollPane.setForceScroll(false, true);
        //scrollPane.setX(400);
        //scrollPane.setY(200);
        
        final Table table = new Table();
        table.setFillParent(true);
        table.add(scrollPane).width(250f).height(300f);
        table.setX(150);
        //table.setY(100);
        
        stage.addActor(table);
        
        label = new Label("", FontUtils.generator(60));
        label.setColor(Color.BLACK);
        label.setX(300);
        label.setY(550);
        stage.addActor(label);
        
        labelInfos = new Label("", FontUtils.generator(20));
        labelInfos.setColor(Color.BLACK);
        labelInfos.setX(50);
        labelInfos.setY(500);
        stage.addActor( labelInfos);
        
        shapeRenderer = new ShapeRenderer();
        
        niveaux = new ArrayList<Niveau>();
        
        vg = new VerticalGroup();
        
        String levels = NetResources.getLevels();
        
        Integer index = 0;
        
        for (String s : levels.split(System.lineSeparator())){
        	
        	System.out.println(s);
        	
        	if (s.trim() == "" || s.startsWith("#")){}
        	
        	else if (s.toLowerCase().trim().startsWith("[")){
        		
        		

        		if (niveaux_map != null){
        			
        			niveau.setListeDesMurs(murs);
                    niveaux_map.put(niveau.getNom(),niveau);
        			
        			final TextButton tb = new TextButton(niveau.getNom(), game.skin);
        			
        			tb.addListener(new ClickListener() {
        		        @Override
        		        public void clicked(InputEvent event, float x, float y) {  
        		        	Niveau n = niveaux_map.get(tb.getLabel().getText().toString());
        		            murs = n.getListeDesMurs();
        		            label.setText(n.getNom().toUpperCase());
        		            labelInfos.setText(n.getInfos().length() > 100 ? n.getInfos().substring(0, 100) + " ..." : n.getInfos());
        		            
        		            lancer.setTouchable(Touchable.enabled);
        		            lancer.setColor(lancer.getColor().r, lancer.getColor().g, lancer.getColor().b, 1F);
        		            
        		            tb_actif = tb;
        		        };
        			});
        			scrollTable.add(tb).align(Align.left);
                    scrollTable.row();
        		}
        		else {
        			niveaux_map = new HashMap<String, Niveau>();
        		}
        		
        		niveau = new Niveau();
        		murs = new ArrayList<Mur2D>();
        		niveau.setIndex(index ++);
        		niveau.setNom(s.trim().substring(1, s.trim().length() -1));	    			

        	}
        	else if (s.toLowerCase().startsWith("mur")){
        		Mur2D m = new Mur2D(Orientation.valueOf(s.split("=")[1].split(",")[0].trim().toUpperCase()),
        				              Integer.parseInt(s.split("=")[1].split(",")[1].trim()),
        				              Integer.parseInt(s.split("=")[1].split(",")[2].trim()),
        				              Integer.parseInt(s.split("=")[1].split(",")[3].trim()),
        				              Integer.parseInt(s.split("=")[1].split(",")[4].trim()),
        				              s.split("=")[1].split(",").length > 5 ? s.split("=")[1].split(",")[5].trim() : "");
        		
        		murs.add(m);
        		
        	}
        	else if (s.toLowerCase().startsWith("goal")){
        		Goal2D g = new Goal2D(Integer.parseInt(s.split("=")[1].split(",")[0].trim()),
        				              Integer.parseInt(s.split("=")[1].split(",")[1].trim()));
        		
        		niveau.setGoal2D(g);
        	}
        	else if (s.toLowerCase().startsWith("perso")){
        		
        		Personnage2D p = new Personnage2D(Double.parseDouble(s.split("=")[1].split(",")[1].trim()),
        				                          Double.parseDouble(s.split("=")[1].split(",")[2].trim()),
        				                          Double.parseDouble(s.split("=")[1].split(",")[3].trim()),
        				                          Double.parseDouble(s.split("=")[1].split(",")[4].trim()),
			                                      Color.CORAL,//(s.split("=")[1].split(",")[0].trim()),
			                                      niveau);
        		niveau.setPerso(p);
        	}
        	
        	else if (s.toLowerCase().startsWith("info")){
        		niveau.setInfos(s.split("=")[1].split("\"")[1].trim());
        	}
        }
		
        List<Mur2D> murs_dernier = new ArrayList<Mur2D>();
        murs_dernier.addAll(murs);
		niveau.setListeDesMurs(murs_dernier);
		
		niveaux_map.put(niveau.getNom(),niveau);
		
		final TextButton tb = new TextButton(niveau.getNom(), game.skin);
		
		tb.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	Niveau n = niveaux_map.get(tb.getLabel().getText().toString());
	            murs = n.getListeDesMurs();
	            label.setText(n.getNom().toUpperCase());
	            labelInfos.setText(n.getInfos().length() > 100 ? n.getInfos().substring(0, 100) + " ..." : n.getInfos());
	            
	            lancer.setTouchable(Touchable.enabled);
	            lancer.setColor(lancer.getColor().r, lancer.getColor().g, lancer.getColor().b, 1F);	  
	            
	            tb_actif = tb;
	        };
		});
		scrollTable.add(tb).align(Align.left);
        scrollTable.row();

        murs.clear();

        label.setText("Choisissez un niveau");

        quitter = new TextButton("Quitter", game.skin);
        quitter.setX(340);
        quitter.setY(15);
        quitter.setWidth(250);
        quitter.setHeight(40);
        quitter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
            	game.setScreen(new ExitScreen(game));
            }
        });
        stage.addActor(quitter);
        
        revenir = new TextButton("Menu principal", game.skin);
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
        
        lancer = new TextButton("Lancer", game.skin);
        lancer.setX(680);
        lancer.setY(15);
        lancer.setWidth(250);
        lancer.setHeight(40);
        lancer.setTouchable(Touchable.disabled);
        lancer.setColor(lancer.getColor().r, lancer.getColor().g, lancer.getColor().b, 0.5F);
        lancer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
            	game.setScreen(new GameScreen(game, niveaux_map.get(tb_actif.getLabel().getText().toString())));
            }
        });
        stage.addActor(lancer);
  
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
        
        shapeRenderer.begin(ShapeType.Filled);
        
        if (! murs.isEmpty()){
        	shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(20, 100, 500, 300);
        }
        
        
        shapeRenderer.setColor(0, 0, 0, 1);
        
        for (Mur2D m : murs){        	
        	shapeRenderer.rect(m.getX() / 2 + 20 ,
        			           (100 + 300) - m.getY() / 2 - (m.getHeight() / 2),
        			           m.getWidth() / 2,
        			           m.getHeight() / 2);
	    }
        
        shapeRenderer.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
