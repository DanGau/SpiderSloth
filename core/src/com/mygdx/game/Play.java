package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.B2DRope.RopeFrame;
import com.mygdx.GlobalVars.GlobalVars;

public class Play extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	AnimatedSloth as;
	AnimatedSloth as2;
	Background bg;
	DrawingRope dr;
	
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 480;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private OrthographicCamera b2dCam;
	
	private static World world;
	private Box2DDebugRenderer b2dr;
	
	Body body2;
	Body[] rope;
	RevoluteJoint[] rjRope;
	WeldJoint endJoint;
	
	
	@Override
	public void create () {
		
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getHeight());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		world = new World(new Vector2(0, -9.81f), false);
		b2dr = new Box2DDebugRenderer();
		
		//Create Rope
		rope = RopeFrame.create(15, V_WIDTH / 2 / GlobalVars.PPM, V_HEIGHT * .9f / GlobalVars.PPM);
		
		//Bottom Platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / GlobalVars.PPM, 120 / GlobalVars.PPM);
		bdef.type = BodyType.StaticBody;
		
		//Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50 / GlobalVars.PPM, 5 / GlobalVars.PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		
		//body.createFixture(fdef);
		
		//Top Platform
		bdef = new BodyDef();
		bdef.position.set(V_WIDTH / 2 / GlobalVars.PPM, V_HEIGHT / GlobalVars.PPM);
		bdef.type = BodyType.StaticBody;
		
		Body body = world.createBody(bdef);
		
		shape = new PolygonShape();
		shape.setAsBox(50 / GlobalVars.PPM, 5 / GlobalVars.PPM);
		
		fdef = new FixtureDef();
		fdef.shape = shape;
		
		body.createFixture(fdef);
		
		//Box for rope
		BodyDef bdef2 = new BodyDef();
		
		bdef2.position.set(rope[rope.length - 1].getPosition().x, rope[rope.length - 1].getPosition().y - 10 / GlobalVars.PPM);
		bdef2.type = BodyType.DynamicBody;
		body2 = world.createBody(bdef2);
		
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox(20 / GlobalVars.PPM, 20 / GlobalVars.PPM);
		FixtureDef fdef2 = new FixtureDef();
		fdef2.shape = shape2;
		fdef2.density = 1f * GlobalVars.PPM;
		body2.createFixture(fdef2);
		
		
		//Configure Camera
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, V_WIDTH / GlobalVars.PPM, V_HEIGHT / GlobalVars.PPM);
		
		
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		
		
		Vector2 ropeHitch = new Vector2(rope[0].getPosition().x, rope[0].getPosition().y);
		
		rjd.initialize(body, rope[0], ropeHitch);
		
		world.createJoint(rjd);
		
		//Attach Rope and Box
		WeldJointDef wjd = new WeldJointDef();
		
		ropeHitch = new Vector2(rope[rope.length - 1].getPosition().x, rope[rope.length - 1].getPosition().y);
		Vector2 boxHitch = new Vector2(body2.getPosition().x, body2.getPosition().y);
		
		DistanceJointDef djd = new DistanceJointDef();
		
		wjd.initialize(rope[rope.length - 1], body2, ropeHitch);
		
		djd.length = 30 / GlobalVars.PPM;
		
		endJoint = (WeldJoint) world.createJoint(wjd);
		
		/*
		//rope joint from ceiling to box
		RopeJointDef rjd2 = new RopeJointDef();
		Vector2 ceilingHitch = new Vector2(body.getPosition().x, body.getPosition().y);
		rjd2.bodyA = body2;
		rjd2.bodyB = body;
		
		rjd2.maxLength = 1 / GlobalVars.PPM;
		
		//world.createJoint(rjd2);*/
		
		
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
				
		if(Gdx.input.isTouched())
		{
			as.pause();
			bg.pause();
			dr.pause();	
			
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			body2.applyForceToCenter(new Vector2(100, 0), true);
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			body2.applyForceToCenter(new Vector2(-100, 0), true);
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			body2.applyForceToCenter(new Vector2(0, -100), true);
		}
		else if(Gdx.input.isKeyPressed(Keys.UP))
		{
			body2.applyForceToCenter(new Vector2(0, 100), true);
		}
		else if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if(endJoint != null)
			{
				world.destroyJoint(endJoint);
				endJoint = null;
				
				System.out.println(body2.getLinearVelocity());
			}
			
		}
		else
		{
			as.resume();
			bg.resume();
			dr.resume();
		}
		
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
		
		b2dr.render(world, b2dCam.combined);
		
		if(endJoint != null)
			System.out.println(body2.getLinearVelocity());
	}
	
	public void update(float step)
	{
		world.step(step, 6, 2);
	}
	
	public void dispose(){
		
	}
	
	public static World getWorld() { return world; };
}
