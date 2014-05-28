package com.mygdx.Game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.mygdx.B2DGround.GroundFrame;
import com.mygdx.B2DPlayer.PlayerFrame;
import com.mygdx.B2DRope.RopeFrame;
import com.mygdx.GlobalVars.GlobalVars;
import com.mygdx.Handlers.ContactHandler;
import com.mygdx.Handlers.JointHandler;

public class Play extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	AnimatedSloth as;
	AnimatedSloth as2;
	Background bg;
	DrawingRope dr;
	
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private OrthographicCamera b2dCam;
	
	private ContactHandler ch;
	
	private static World world;
	private Box2DDebugRenderer b2dr;
	
	Body player;
	Body body;
	Body[] rope;
	RevoluteJoint[] rjRope;
	static WeldJoint endJoint;
	boolean inputActive = false;
	
	static int curSeg = 0;
	
	static ArrayList<RopeFrame> ropes;
	
	
	@Override
	public void create () {
		
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getHeight());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GlobalVars.V_WIDTH, GlobalVars.V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, GlobalVars.V_WIDTH, GlobalVars.V_HEIGHT);
		
		ch = new ContactHandler();
		
		world = new World(new Vector2(0, -9.81f), false);
		b2dr = new Box2DDebugRenderer();
		world.setContactListener(ch);
		
		//Box to represent player
		player = PlayerFrame.create(GlobalVars.V_WIDTH / 2 / GlobalVars.PPM, GlobalVars.V_HEIGHT / 2 / GlobalVars.PPM);
		
		//Create Ground
		body = GroundFrame.create();
		
		System.out.println(player.getPosition().x + " " + player.getPosition().y);
		
		ropes = new ArrayList<RopeFrame>();
				
		
		//Configure Camera
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, GlobalVars.V_WIDTH / GlobalVars.PPM, GlobalVars.V_HEIGHT / GlobalVars.PPM);
		
		
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.internal("badlogic.jpg"));
		
		bg = new Background();
		bg.create();
		
		as = new AnimatedSloth();
		as.create();
		as.setPos(50, 0);
		
		as2 = new AnimatedSloth();
		as2.create();
		as2.setPos(50, 0);
		
		dr = new DrawingRope();
		dr.create();
		
	}

	@Override
	public void render () {
		Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isTouched(0))
		{
			player.applyForceToCenter(new Vector2(500, 0), true);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isTouched(1))
		{
			player.applyForceToCenter(new Vector2(-500, 0), true);
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			player.applyForceToCenter(new Vector2(0, -500), true);
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			player.applyForceToCenter(new Vector2(0, 10000), true);
		}
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_0))
		{
			if(endJoint == null)
			{
				newRope(player, body);
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched())
		{
			if(!inputActive && ch.isPlayerOnGround() )
			{
				player.applyForceToCenter(new Vector2(0, 100000), true);
				inputActive = true;
			}
			
			if(!inputActive && endJoint != null)
			{
				world.destroyJoint(endJoint);
				endJoint = null;
				inputActive = true;
				
				System.out.println(player.getLinearVelocity());
			}
			
			if(!inputActive && endJoint == null)
			{
				newRope(player, body);
				inputActive = true;
			}
			
			
			
		}
		else if(!Gdx.input.isKeyPressed(Keys.SPACE))
		{
			inputActive = false;
		}
		else
		{
			as.resume();
			bg.resume();
			dr.resume();
		}
		
		if(player.getLinearVelocity().x > 0)
			player.applyForceToCenter(new Vector2(500, -500), true);
		
		bg.render();
		//as.render();
		//as2.render();
		//dr.render();
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP)
		{
			accum -=STEP;
			update(STEP);
		}
		
		b2dCam.position.set(player.getPosition().x, GlobalVars.V_HEIGHT / GlobalVars.PPM / 2, 0);
		b2dCam.update();
		
		b2dr.render(world, b2dCam.combined);
		/*
		if(endJoint != null)
			System.out.println(player.getLinearVelocity());*/
	}
	
	public void update(float step)
	{
		world.step(step, 6, 2);
	}
	
	public void dispose(){
		
	}
	
	public static World getWorld() { return world; };
	
	public static void newRope(Body Box, Body body)
	{
		ropes.add(new RopeFrame(Box.getPosition().x, GlobalVars.V_HEIGHT * .9f / GlobalVars.PPM, Box.getPosition().x, Box.getPosition().y, (float) (Math.PI / 4)));
		
		System.out.println(ropes.get(curSeg).getRope() + " " + curSeg);
		
		if(ropes.get(curSeg).getRope() != null)
		{
			//Attach Wall and Rope
			world.createJoint(JointHandler.Rope2Wall(body, ropes.get(curSeg).getRope()[0]));
			
			//Attach Rope and Box		
			endJoint = (WeldJoint) world.createJoint(JointHandler.Box2Rope(Box, ropes.get(curSeg).getRope()[ropes.get(curSeg).getRopeLen() - 1]));
			
			Box.applyLinearImpulse(new Vector2(-300, -300), Box.getPosition(), true);
			
			curSeg++;
			
			System.out.println(curSeg);
		}
	}
}
