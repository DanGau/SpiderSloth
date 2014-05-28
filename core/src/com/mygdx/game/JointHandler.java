package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.GlobalVars.GlobalVars;

public class JointHandler 
{
	public static RevoluteJointDef Rope2Wall(Body wall, Body ropeSeg)
	{
		RevoluteJointDef rjd = new RevoluteJointDef();
		
		Vector2 ropeHitch = new Vector2(ropeSeg.getPosition().x, ropeSeg.getPosition().y + GlobalVars.SEG_HEIGHT * 2 / GlobalVars.PPM);
		
		rjd.initialize(wall, ropeSeg, ropeHitch);
		
		return rjd;
	}
	
	public static WeldJointDef Box2Rope(Body box, Body ropeSeg)
	{
		if(box.getLinearVelocity().x > 0)
			box.setLinearVelocity(Math.abs(box.getLinearVelocity().y) *2 / 3, 0);
		else
			box.setLinearVelocity((Math.abs(box.getLinearVelocity().y) * - 1) * 2 / 3, 0);
		
		WeldJointDef wjd = new WeldJointDef();
		
		Vector2 ropeHitch = new Vector2(ropeSeg.getPosition().x, ropeSeg.getPosition().y);
		
		DistanceJointDef djd = new DistanceJointDef();
		
		wjd.initialize(ropeSeg, box, ropeHitch);
		
		return wjd;
	}
}
