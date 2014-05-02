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
	
	public static Body[] create(int segs, float x, float y)
	{
		ropeSegs = segs;
		
		/*rjRope = new RevoluteJoint[ropeSegs];
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		
		Vector2 start = new Vector2();
		Vector2 end = new Vector2();
		
		for(int i = 0; i < ropeSegs; i++)
		{
			start.set(x,  y);
			end.set(x, y + SEG_LEN);
			
			rjd.initialize(null, null, start);
			
			rjRope[i] = (RevoluteJoint) Play.getWorld().createJoint(rjd);
		}
		
		GearJointDef gjd = new GearJointDef();
		
		for(int i = 0; i < ropeSegs - 1; i++)
		{
			gjd.joint1 = rjRope[i];
			gjd.joint2 = rjRope[i + 1];
			
			Play.getWorld().createJoint(gjd);
		}
		
		return rjRope;*/
		
		rope = new Body[ropeSegs];
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape seg = new PolygonShape();
		seg.setAsBox(GlobalVars.SEG_WIDTH / GlobalVars.PPM, GlobalVars.SEG_HEIGHT / GlobalVars.PPM);
		fdef.shape = seg;
		
		for(int i = 0; i < ropeSegs; i++)
		{
			bdef.position.set(x, y);
			bdef.type = BodyType.DynamicBody;
			
			rope[i] = Play.getWorld().createBody(bdef);
			fdef.density = 100f * GlobalVars.PPM;
			fdef.restitution = .2f * GlobalVars.PPM;
			rope[i].createFixture(fdef);
			
			y -= GlobalVars.SEG_HEIGHT / GlobalVars.PPM;
		}
		
		RevoluteJointDef djd = new RevoluteJointDef();
		//djd.length = MAX_SEG_DIST;
		
		for(int i = 0; i < ropeSegs - 1; i++)
		{
			Vector2 bodA = new Vector2(rope[i].getPosition().x, rope[i].getPosition().y);
			Vector2 bodB = new Vector2(rope[i + 1].getPosition().x, rope[i + 1].getPosition().y);
			
			Vector2 anchor = new Vector2((bodA.x + bodB.x) / 2, (bodA.y + bodB.y) / 2);
			
			djd.initialize(rope[i], rope[i + 1], anchor);
			//djd.length = MAX_SEG_DIST / GlobalVars.PPM;
			//djd.dampingRatio = 1f;
			//djd.frequencyHz = 20f;
			djd.enableLimit = true;
			
			Play.getWorld().createJoint(djd);
		}
		
		return rope;
		
	}
}
