package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpiderSloth extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	AnimatedSloth as;
	AnimatedSloth as2;
	background bg;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		bg = new background();
		bg.create();
		
		as = new AnimatedSloth();
		as.create();
		as.setPos(50, 500);
		
		as2 = new AnimatedSloth();
		as2.create();
		as2.setPos(50, 0);
		
	}

	@Override
	public void render () {
		Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		
		if(Gdx.input.isTouched())
		{
			as.pause();
		}
		else
		{
			as.resume();
		}
		
		bg.render();
		as.render();
		as2.render();
	}
}
