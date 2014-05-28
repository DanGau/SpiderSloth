package com.mygdx.Handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.GlobalVars.GlobalVars.BodyClass;

public class ContactHandler implements ContactListener
{
	private boolean playerOnGround;
	
	public ContactHandler()
	{
		super();
		
		playerOnGround = false;
	}
	
	@Override
	public void beginContact(Contact contact) 
	{
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		System.out.println(fa.getUserData());
		System.out.println(fb.getUserData());
		
		if((fa != null && fa.getUserData().equals(BodyClass.PLAYER)) || (fb != null && fb.getUserData().equals(BodyClass.PLAYER)))
		{
			playerOnGround = true;
			System.out.println("on ground");
		}
		
	}

	@Override
	public void endContact(Contact contact) 
	{
		playerOnGround = false;
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public boolean isPlayerOnGround()
	{
		return playerOnGround;
	}

}
