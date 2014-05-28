package com.mygdx.B2DRope;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.Game.Play;
import com.mygdx.GlobalVars.GlobalVars;
import com.mygdx.GlobalVars.GlobalVars.BodyClass;

public class RopeFrame {
		
	private static Body[] rope;
	private static RevoluteJoint[] rjRope;
	private static int ropeSegs;
	
	private static final float SEG_LEN = 4;
	
	public RopeFrame(float x1, float y1, float x2, float y2, float angle)
	{
		rope = create(x1, y1, x2, y2, angle);
	}
	
	public static Body[] create(float x1, float y1, float x2, float y2, float angle2)
	{
		float ropeLen, ropeX;
		
		System.out.println("ys " + y2 + " " + y1 + " " + angle2 + " " + Math.sin(angle2));
		
		if(angle2 < .01 || angle2 > Math.PI)
		{
			ropeLen = Math.abs(y2);
			ropeX = 0;
		}
		
		else
		{
			ropeLen = (float) Math.abs(((y2 - y1) / Math.sin(angle2)));
			
			ropeX = (float) Math.sqrt((ropeLen) * (ropeLen) - (y2 - y1) * (y2 - y1));
		}
		
		
		System.out.println("ropex " + ropeX);
		System.out.println("rope len " + ropeLen);
		
		System.out.println("rope len " + ropeLen);
		
		ropeSegs = (int)(ropeLen * GlobalVars.PPM / GlobalVars.SEG_HEIGHT / 2);
		
		System.out.println("segments " + ropeSegs);
		
		if(ropeSegs < 2 || ropeSegs > 9)
		{
			System.out.println("i am null");
			return null;
		}
		
		System.out.println("segments2 " + ropeSegs);
		
		float dy = (y2 - y1) / ropeSegs;
		
		float dx = ropeX / ropeSegs;
		
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
		
		float x = x2 + ropeX;
		float y = y1;
		
		System.out.println(angle2);
		
		for(int i = 0; i < ropeSegs; i++)
		{
			bdef.position.set(x, y);
			bdef.type = BodyType.DynamicBody;
			
			rope[i] = Play.getWorld().createBody(bdef);
			
			rope[i].setUserData(BodyClass.ROPE);
			
			
			rope[i].setTransform(x, y, (float) (angle2 - Math.PI / 2));

			
			fdef.density = 10f * GlobalVars.PPM;
			fdef.restitution = 1f;
			fdef.filter.categoryBits = GlobalVars.CATEGORY_ROPE;
			fdef.filter.maskBits = GlobalVars.MASK_ROPE;
			rope[i].createFixture(fdef).setUserData(BodyClass.ROPE);
			
			y += dy;
			x -= dx;
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
