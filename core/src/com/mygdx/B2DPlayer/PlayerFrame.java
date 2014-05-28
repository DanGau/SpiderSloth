package com.mygdx.B2DPlayer;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.Game.Play;
import com.mygdx.GlobalVars.GlobalVars;
import com.mygdx.GlobalVars.GlobalVars.BodyClass;

public class PlayerFrame
{
	private static Body body;
	
	public static Body create(float x, float y)
	{
		BodyDef bdef = new BodyDef();
		
		bdef.position.set(x, y);
		bdef.type = BodyType.DynamicBody;
		body = Play.getWorld().createBody(bdef);
		body.setUserData(BodyClass.PLAYER);
		
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox(20 / GlobalVars.PPM, 20 / GlobalVars.PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape2;
		fdef.density = 15f * GlobalVars.PPM;
		fdef.filter.categoryBits = GlobalVars.CATEGORY_PLAYER;
		fdef.filter.maskBits = GlobalVars.MASK_PLAYER;
		body.createFixture(fdef).setUserData(BodyClass.PLAYER);
		
		return body;
	}
}
