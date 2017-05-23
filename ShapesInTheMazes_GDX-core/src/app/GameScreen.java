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
			fixtureDef_.shape = wallBox;
			Fixture f = wallBody.createFixture(fixtureDef_);
			f.setUserData(m);
			wallBox.dispose();
		}

		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(-500 + perso.getWidth()/2 + perso.getX(),
				              250 - perso.getHeight()/2  - perso.getY());
		
		body = world.createBody(bodyDef);
		body.setFixedRotation(true);
		
		PolygonShape perso_ = new PolygonShape();
		perso_.setAsBox(perso.getWidth() / 2f, perso.getHeight() / 2f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.filter.categoryBits = JOUEURS_CATEGORY;
		fixtureDef.filter.maskBits = JOUEURS_MASK;
		fixtureDef.shape = perso_;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 10f;
		fixtureDef.restitution = 0f;
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData(perso);	
		perso_.dispose();
		
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		Gdx.graphics.setWindowedMode(1040, 740);
		        
		stage = new Stage();
		shapeRenderer = new ShapeRenderer();
		
		stage.addActor(goal);
		
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
        
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        //debugRenderer.render(world, camera.combined);
     
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
        
        shapeRenderer.rect(body.getPosition().x + 520 - perso.getWidth()/2,
		                   body.getPosition().y + 370 - perso.getHeight()/2,
		                   perso.getWidth(),
		                   perso.getHeight());

        shapeRenderer.end();
        
        stage.getBatch().begin();
        stage.getBatch().draw(goal.getTexture(), goal.getX() + 20 , (20 + 600) - goal.getY() - goal.getHeight());
        stage.getBatch().end();
        
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
			
			body.setLinearVelocity(-100f, body.getLinearVelocity().y);
			break;
		case Keys.RIGHT:
			body.setLinearVelocity(100f, body.getLinearVelocity().y);
			break;
		case Keys.UP:
			body.setLinearVelocity(body.getLinearVelocity().x, 100f);
			break;
		case Keys.DOWN:
			body.setLinearVelocity(body.getLinearVelocity().x, -100f);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		switch (keycode) {
		case Keys.LEFT:			
			body.setLinearVelocity(0f, body.getLinearVelocity().y);
			break;
		case Keys.RIGHT:
			body.setLinearVelocity(0f, body.getLinearVelocity().y);
			break;
		case Keys.UP:
			body.setLinearVelocity(body.getLinearVelocity().x, 0f);
			break;
		case Keys.DOWN:
			body.setLinearVelocity(body.getLinearVelocity().x, 0f);
			break;
		default:
			break;
		}
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

	public Body getBody() {
		return body;
	}	
}
