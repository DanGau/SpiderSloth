package com.mygdx.B2DRope;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.GlobalVars.GlobalVars;
import com.mygdx.game.Play;

public class RopeFrame {
		
	private static Body[] rope;
	private static RevoluteJoint[] rjRope;
	private static int ropeSegs;
	
	private static final float SEG_LEN = 4;
	
	public RopeFrame(float x1, float y1, float x2, float y2)
	{
		rope = create(x1, y1, x2, y2);
	}
	
	public static Body[] create(float x1, float y1, float x2, float y2)
	{
		float ropeLen = (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		
		ropeSegs = (int)(ropeLen * GlobalVars.PPM / GlobalVars.SEG_HEIGHT / 2);
		
		System.out.println("segments " + ropeSegs);
		
		if(ropeSegs < 2)
		{
			System.out.println("i am null");
			return null;
		}
		
		System.out.println("segments2 " + ropeSegs);
		
		float dy = (y2 - y1) / ropeSegs;
		
		float dx = (x2 - x1) / ropeSegs;
		
		//System.out.println(ropeLen + " " + ropeSegs);
		//System.out.println(x1 + " " + x2 + " " + dx);
		//System.out.println(y1 + " " + y2 + " " + dy);
		
		ropeSegs--;
		
		rope = new Body[ropeSegs];
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape seg = new PolygonShape();
		seg.setAsBox(GlobalVars.SEG_WIDTH / GlobalVars.PPM, GlobalVars.SEG_HEIGHT / GlobalVars.PPM);
		fdef.shape = seg;
		fdef.density = 10f * GlobalVars.PPM;
		
		float x = x1;
		float y = y1;
		
		for(int i = 0; i < ropeSegs; i++)
		{
			bdef.position.set(x, y);
			bdef.type = BodyType.DynamicBody;
			
			rope[i] = Play.getWorld().createBody(bdef);
			
			float angle = (float)(Math.atan(dy / dx) * 180 / Math.PI);
			//System.out.println(angle);
			
			//rope[i].setTransform(x, y, angle);
			
			//fdef.density = 10f / ropeSegs * GlobalVars.PPM;
			fdef.restitution = 1f;
			fdef.filter.categoryBits = GlobalVars.CATEGORY_ROPE;
			fdef.filter.maskBits = GlobalVars.MASK_ROPE;
			rope[i].createFixture(fdef);
			
			y += dy;
			x += dx;
		}
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		
		for(int i = 0; i < ropeSegs - 1; i++)
		{
			Vector2 bodA = new Vector2(rope[i].getPosition().x, rope[i].getPosition().y);
			Vector2 bodB = new Vector2(rope[i + 1].getPosition().x, rope[i + 1].getPosition().y);
			
			Vector2 anchor = new Vector2((bodA.x + bodB.x) / 2, (bodA.y + bodB.y) / 2);
			
			rjd.initialize(rope[i], rope[i + 1], anchor);

			rjd.enableLimit = false;
			
			Play.getWorld().createJoint(rjd);
		}
		
		return rope;
		
	}
	
	public Body[] getRope()
	{
		return rope;
	}
	
	public int getRopeLen()
	{
		return ropeSegs;
	}
}
