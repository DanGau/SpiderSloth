package com.mygdx.B2DGround;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.GlobalVars.GlobalVars;
import com.mygdx.game.Play;

public class GroundFrame 
{
	private static Body body;
	
	
	public static Body create()
	{
		//Bottom Platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(GlobalVars.V_WIDTH / 2 / GlobalVars.PPM, 5 / GlobalVars.PPM);
		bdef.type = BodyType.StaticBody;
		
		body = Play.getWorld().createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(GlobalVars.V_WIDTH, 5 / GlobalVars.PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = GlobalVars.CATEGORY_GROUND;
		fdef.filter.maskBits = GlobalVars.MASK_GROUND;
		
		body.createFixture(fdef);
		
		//Top Platform
		bdef = new BodyDef();
		bdef.position.set(GlobalVars.V_WIDTH / 2 / GlobalVars.PPM, GlobalVars.V_HEIGHT / GlobalVars.PPM);
		bdef.type = BodyType.StaticBody;
		
		body = Play.getWorld().createBody(bdef);
		
		shape = new PolygonShape();
		shape.setAsBox(GlobalVars.V_WIDTH, 5 / GlobalVars.PPM);
		
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = GlobalVars.CATEGORY_GROUND;
		fdef.filter.maskBits = GlobalVars.MASK_GROUND;
		
		body.createFixture(fdef);
		
		return body;
	}
}
