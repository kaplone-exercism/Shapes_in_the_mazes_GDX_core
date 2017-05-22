package app;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import models.Goal2D;
import models.Mur2D;
import models.Niveau;
import models.Personnage2D;
import utils.DetectionContact;

public class GameScreen_test implements Screen ,InputProcessor {
	
	final short MURS_CATEGORY = 0x0002;  // 0000000000000001 in binary
	final short JOUEURS_CATEGORY = 0x0001; // 0000000000000010 in binary
	final short MURS_MASK = ~MURS_CATEGORY; 
	final short JOUEURS_MASK = ~JOUEURS_CATEGORY;
	final short MURS = -1; 
	final short JOUEURS = 1;
	
	final float PIXELS_TO_METERS = 100f;
	
	final Main_SITM_GDX game;
	private Stage stage;

	private Personnage2D perso;

	private World world ;
	private Box2DDebugRenderer debugRenderer;
	
	
	public float VIRTUAL_WIDTH  = 1040f;
	public float VIRTUAL_HEIGHT = 740f;
	private OrthographicCamera camera;
	
	private Body body;
	private Body body_;
	
	public GameScreen_test(final Main_SITM_GDX game, Niveau niveau) {
		super();
		this.game = game;
		this.perso = niveau.getPerso();

		world = new World(new Vector2(0F, 0F), true);
		//world.setContactListener(new DetectionContact(this));
		world.step(1/60f, 6, 2);
		
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		
		debugRenderer = new Box2DDebugRenderer();

		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((perso.getX() + perso.getWidth()/2),
                             (perso.getY() + perso.getHeight()/2));

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(perso.getWidth()/2 ,
        		       perso.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
        
        BodyDef bodyDef_ = new BodyDef();
        bodyDef_.type = BodyDef.BodyType.StaticBody;
        bodyDef_.position.set((perso.getX() + perso.getWidth()/2 + 200),
                              (perso.getY() + perso.getHeight()/2 + 200));

        body_ = world.createBody(bodyDef_);

        PolygonShape shape_ = new PolygonShape();
        shape_.setAsBox(perso.getWidth() ,
        		       perso.getHeight());
        body_.createFixture(shape_, 0.0f);
        shape_.dispose();
        
     // Create our body definition
        BodyDef groundBodyDef = new BodyDef();  
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, 10));  

        // Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);  

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();  
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(camera.viewportWidth, 10.0f);
        // Create a fixture from our polygon shape and add it to our ground body  
        groundBody.createFixture(groundBox, 0.0f); 
        // Clean up after ourselves
        groundBox.dispose();

		Gdx.graphics.setWindowedMode(1040, 740);		        
		stage = new Stage();
		
		System.out.println("world body count = " + world.getBodyCount());
        System.out.println("world fixture count = " + world.getFixtureCount());
		
		Gdx.input.setInputProcessor(this);	
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8F, 0.7F, 0.6f, 0.1F);
		Gdx.gl.glClearColor(1F, 1F, 1f, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);
              
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
			//deltaX = -1;
//			Vector2 pos = body.getPosition();
//	        body.applyLinearImpulse(2f, 0f, pos.x, pos.y, true);
			body.setLinearVelocity(-100f, 0f);
			break;
		case Keys.RIGHT:
			//deltaX = 1;
			body.setLinearVelocity(100f, 0f);
			break;
		case Keys.UP:
			//deltaY = 1;
			body.setLinearVelocity(0f, 100f);
			break;
		case Keys.DOWN:
			//deltaY = -1;
			body.setLinearVelocity(0f, -100f);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		body.setLinearVelocity(0f, 0f);
//		deltaX = 0;
//		deltaY = 0;
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
