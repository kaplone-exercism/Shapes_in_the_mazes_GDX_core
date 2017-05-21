package app;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

//import ContactListener;

import actors.GameKeyboardActor;
import models.Goal2D;
import models.Mur2D;
import models.Niveau;
import models.Personnage2D;
import utils.DetectionContact;

public class GameScreen implements Screen ,InputProcessor {
	
	final short MURS_CATEGORY = 0x0002;  // 0000000000000001 in binary
	final short JOUEURS_CATEGORY = 0x0001; // 0000000000000010 in binary
	final short MURS_MASK = ~MURS_CATEGORY; 
	final short JOUEURS_MASK = ~JOUEURS_CATEGORY;
	final short MURS = -1; 
	final short JOUEURS = 1;
	
	final Main_SITM_GDX game;
	private Stage stage;
	private ShapeRenderer shapeRenderer;
	
	private Niveau niveau;
	private List<Mur2D> murs;
	private Goal2D goal;
	private Personnage2D perso;
	
	private int deltaX;
	private int deltaY;
	
	private int moveX;
	private int moveY;
	
	private World world ;
	private Box2DDebugRenderer debugRenderer;
	
	
	public float VIRTUAL_WIDTH  = 1040f;
	public float VIRTUAL_HEIGHT = 740f;
	private OrthographicCamera camera;
	
	private Body body;
	private BodyDef bodyDef;
	private Fixture fixture;
	
	private List<Fixture> fixtures;
	
	public GameScreen(final Main_SITM_GDX game, Niveau niveau) {
		super();
		this.game = game;
		this.niveau = niveau;
		this.murs = niveau.getListeDesMurs();
		this.goal = niveau.getGoal2D();
		this.perso = niveau.getPerso();

		world = new World(new Vector2(0F, 0F), true);
		world.setContactListener(new DetectionContact(this));
		world.step(1/60f, 6, 2);
		
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		
		debugRenderer = new Box2DDebugRenderer();
		
		for (Mur2D m : murs){ 
			BodyDef wallBodyDef = new BodyDef();  
			wallBodyDef.position.set(-500 + m.getWidth()/2 + m.getX(),
		                              250 - m.getHeight()/2 - m.getY()); 
			
			Body wallBody = world.createBody(wallBodyDef);
			PolygonShape wallBox = new PolygonShape();  
			wallBox.setAsBox(m.getWidth() / 2, m.getHeight() / 2);
			FixtureDef fixtureDef_ = new FixtureDef();
			fixtureDef_.filter.categoryBits = MURS_CATEGORY;
			fixtureDef_.filter.maskBits = MURS_MASK;
			//fixtureDef_.filter.groupIndex = MURS;
			fixtureDef_.isSensor = true;
			fixtureDef_.shape = wallBox;
			wallBody.createFixture(fixtureDef_);	
			//System.out.println(wallBody.getPosition());
		}

		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(-500 + perso.getWidth()/2 + perso.getX(),
				              250 - perso.getHeight()/2  - perso.getY());
		//bodyDef.position.set(perso.getX()+ 20,20 + perso.getY());

		body = world.createBody(bodyDef);
		PolygonShape perso_ = new PolygonShape();
		perso_.setAsBox(perso.getWidth() / 2, perso.getHeight() / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.filter.categoryBits = JOUEURS_CATEGORY;
		fixtureDef.filter.maskBits = JOUEURS_MASK;
		//fixtureDef.filter.groupIndex = JOUEURS;
		fixtureDef.isSensor = true;
		fixtureDef.shape = perso_;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData(perso);
		
		//perso_.dispose();
		
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		Gdx.graphics.setWindowedMode(1040, 740);
		        
		stage = new Stage();
		shapeRenderer = new ShapeRenderer();
		
		stage.addActor(goal);
		
//		InputProcessor backProcessor = new InputAdapter() {
//            @Override
//            public boolean keyDown(int keycode) {
//
//                if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK) ){
//                	game.setScreen(new LaunchScreen(game));
//                }
//                else {
//                	System.out.println(keycode);
//                }
//                return true;
//            }
//            
//        };
//
//
//        InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
//        Gdx.input.setInputProcessor(multiplexer);
		
//		GameKeyboardActor gameActor = new GameKeyboardActor();
//		gameActor.setTouchable(Touchable.enabled);
//		stage.addActor(gameActor);
		
		Gdx.input.setInputProcessor(this);	
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
        
        moveX += deltaX;
        moveY += deltaY;
        
        body.setTransform(-500 + perso.getWidth()/2 + moveX + perso.getX(),
        		           250 - perso.getHeight()/2  - perso.getY() + moveY, 0f);
        
        shapeRenderer.begin(ShapeType.Filled);
        
        if (! murs.isEmpty()){
        	shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(20, 20, 1000, 600);
        }
        
        
        shapeRenderer.setColor(0, 0, 0, 1);
        
        for (Mur2D m : murs){        	
        	shapeRenderer.rect(m.getX()+ 20 ,
        			           (20 + 600) - m.getY() - (m.getHeight()),
        			           m.getWidth(),
        			           m.getHeight());
	    }
        
        shapeRenderer.setColor(0.9F, 0.6F, 0.5F, 1);
        
        shapeRenderer.rect(perso.getX()+ 20 + moveX,
		           (20 + 600) - perso.getY() - (perso.getHeight()) + moveY,
		           perso.getWidth(),
		           perso.getHeight());
        
        //perso.setPosition(body.getPosition().x, body.getPosition().y);
        
        shapeRenderer.end();
        
        stage.getBatch().begin();
        stage.getBatch().draw(goal.getTexture(), goal.getX() + 20 , (20 + 600) - goal.getY() - goal.getHeight());
        stage.getBatch().end();
		
        
        //System.out.println(fixture.getBody().getPosition());
        debugRenderer.render(world, camera.combined);
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
		//goal.getTexture().dispose();
		//perso_.dispose()	
		stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.ESCAPE:
		case Keys.BACK:
			game.setScreen(new LaunchScreen(game));
			break;
		case Keys.LEFT:
			deltaX = -1;
//			Vector2 pos = body.getPosition();
//	        body.applyLinearImpulse(2f, 0f, pos.x, pos.y, true);
			break;
		case Keys.RIGHT:
			deltaX = 1;
			break;
		case Keys.UP:
			deltaY = 1;
			break;
		case Keys.DOWN:
			deltaY = -1;
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		deltaX = 0;
		deltaY = 0;
		return false;
	}

	@Override
	    public boolean keyTyped(char character){
		//System.out.println("typed");
		//System.out.println(character);
        return true;
	    }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//System.out.println("touchDown");
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//System.out.println("touchUp");
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		//System.out.println("move");
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
